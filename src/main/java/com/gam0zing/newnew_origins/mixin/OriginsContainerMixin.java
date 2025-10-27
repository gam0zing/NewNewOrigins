package com.gam0zing.newnew_origins.mixin;

import com.gam0zing.newnew_origins.event.SetOriginEvent;
import com.gam0zing.newnew_origins.rigistry.ModCapabilities;
import com.gam0zing.newnew_origins.upgrade.IUpgradeComponent;
import io.github.edwinmindcraft.origins.api.OriginsAPI;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import io.github.edwinmindcraft.origins.common.capabilities.OriginContainer;
import io.github.edwinmindcraft.origins.common.registry.OriginRegisters;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Mixin(value = OriginContainer.class, priority = 1000, remap = false)
public class OriginsContainerMixin {

    @Shadow @Final private Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> layers;

    @Shadow @Final private Player player;

    /// 该 Mixin 向设置起源的方法中注入了触发事件的逻辑。
    /// > 条件：即将更换的起源不为空
    /// > 如果条件满足，将进行如下操作：
    /// > * 检查进化 (需要 lasts)
    /// > * 根据进化情况触发参数不同的事件
    /// > 如果条件不满足，将进行如下操作：
    /// > * 获取 layers 信息，保存在 lasts 内
    @Inject(method = "setOriginInternal", at = @At("HEAD"))
    public void setOriginMixin(@NotNull ResourceKey<OriginLayer> layer, @NotNull ResourceKey<Origin> origin, boolean handlePowers, CallbackInfo ci) {
        if (player.level().isClientSide()) return;  //该方法仅在服务端生效
        //检查是否存在 UpgradeComponent
        LazyOptional<IUpgradeComponent> upgradeComponent = player.getCapability(ModCapabilities.UPGRADE_COMPONENT, null);
        if (upgradeComponent.resolve().isEmpty()) return;
        //检查是否构成有效更换
        Optional<Holder.Reference<OriginLayer>> layerHolder = OriginsAPI.getLayersRegistry().getHolder(layer);
        Optional<Holder.Reference<Origin>> newOriginHolder = OriginsAPI.getOriginsRegistry().getHolder(origin);
        if (layerHolder.isEmpty() || !layerHolder.get().isBound()) return;
        if (newOriginHolder.isEmpty() || !newOriginHolder.get().isBound()) return;
        //表 layers 可能为空，仅在第一次进入游戏时，且此时赋值起源为 Empty，如果出现其他情况，本次操作跳过
        ResourceKey<Origin> previous = this.layers.get(layer);
        Optional<Holder.Reference<Origin>> previousHolder = OriginsAPI.getOriginsRegistry().getHolder(previous);
        if (previousHolder.isEmpty() || !newOriginHolder.get().isBound()) return;

        //如果更换的起源是空的，则仅同步旧起源信息
        //由于同类起源对象不止一个，只能使用 ID 比较是否相同
        if (origin.location().equals(OriginRegisters.EMPTY.getId())) {
            upgradeComponent.resolve().get().putLast(Map.copyOf(this.layers));
            return;
        }

        //表 last 可能为空，表示新装模组，或者第一次选择起源，此时旧起源为 null，可以跳过“进化检查”步骤
        Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> last = upgradeComponent.resolve().get().getLast();
        ResourceKey<Origin> oldOrigin = last.get(layer);
        Optional<Holder.Reference<Origin>> oldOriginHolder = OriginsAPI.getOriginsRegistry().getHolder(oldOrigin);
        boolean isOldExist = oldOriginHolder.isPresent() && oldOriginHolder.get().isBound();

        //进化检查
        AtomicInteger isUpgrading = new AtomicInteger(0);
        if (isOldExist) {
            for (var originUpgrade : oldOriginHolder.get().value().getUpgrades()) {
                if (originUpgrade.origin().unwrapKey().isEmpty()) continue;
                if (newOriginHolder.get().unwrapKey().isEmpty()) continue;
                if (originUpgrade.origin().unwrapKey().get().location().equals(newOriginHolder.get().unwrapKey().get().location())) isUpgrading.getAndIncrement();
            }
        }
        boolean flag = isUpgrading.get() > 0;

        SetOriginEvent event;
        event = new SetOriginEvent(flag, player, layer, origin, oldOrigin);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
