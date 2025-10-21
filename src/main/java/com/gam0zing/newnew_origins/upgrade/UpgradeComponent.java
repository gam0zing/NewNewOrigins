package com.gam0zing.newnew_origins.upgrade;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.event.SetOriginEvent;
import com.gam0zing.newnew_origins.rigistry.ModCapabilities;
import io.github.edwinmindcraft.origins.api.OriginsAPI;
import io.github.edwinmindcraft.origins.api.capabilities.IOriginContainer;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import io.github.edwinmindcraft.origins.api.registry.OriginsDynamicRegistries;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/// 该类仅生效于服务端
public class UpgradeComponent implements IUpgradeComponent, ICapabilitySerializable<Tag> {

    public static ResourceLocation ID = NewNewOrigins.modLocation("upgrade_component");

    //NBT 存储属性
    private Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> rootKeys = new HashMap<>();  // 事件相应时和 {this.roots} 一起赋值
    public Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> lastKeys = new HashMap<>();   // 由 OriginContainerMixin 读写
    //自动赋值属性
    private final Player player;
    private boolean enabled;
    private IOriginContainer originContainer;
    //事件赋值属性
    private Map<OriginLayer, Origin> roots = new HashMap<>();
    //Capability
    private final LazyOptional<IUpgradeComponent> capability = LazyOptional.of(() -> this);

    public UpgradeComponent(Player player) {
        this.player = player;
        MinecraftForge.EVENT_BUS.register(this);
        NewNewOrigins.LOGGER.debug("Capability：初始化{}", player != null);
    }

    /// 晚初始化：包含了对其他Capability的调用，所以需要放在反序列化阶段执行
    private void lateInit() {
        this.originContainer = getOriginContainer(player);
        enabled = this.originContainer != null;
        NewNewOrigins.LOGGER.debug("Capability：晚初始化{}", this.originContainer != null);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (!this.enabled || this.rootKeys.isEmpty()) return tag;
        ListTag rootsList = new ListTag();
        for (Map.Entry<ResourceKey<OriginLayer>, ResourceKey<Origin>> entry : this.rootKeys.entrySet()) {
            CompoundTag rootEntry = new CompoundTag();
            ResourceLocation layerKey = entry.getKey().location();
            rootEntry.putString("Layer", layerKey.toString());
            ResourceLocation originKey = entry.getValue().location();
            rootEntry.putString("Origin", originKey.toString());
            rootsList.add(rootEntry);
        }
        tag.put("Roots", rootsList);
        ListTag lastList = new ListTag();
        for (Map.Entry<ResourceKey<OriginLayer>, ResourceKey<Origin>> entry : this.lastKeys.entrySet()) {
            CompoundTag lastEntry = new CompoundTag();
            ResourceLocation layerKey = entry.getKey().location();
            lastEntry.putString("Layer", layerKey.toString());
            ResourceLocation originKey = entry.getValue().location();
            lastEntry.putString("Origin", originKey.toString());
            lastList.add(lastEntry);
        }
        tag.put("Lasts", lastList);
        NewNewOrigins.LOGGER.debug("Capability：序列化");
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        this.lateInit();
        if (!(nbt instanceof CompoundTag tag)) {
            NewNewOrigins.LOGGER.error("Invalid NBT data for UpgradeComponent");
            return;
        }
        if (!this.enabled) return;
        this.rootKeys.clear();
        if (tag.contains("Roots", Tag.TAG_LIST)) {
            ListTag rootsList = tag.getList("Roots", Tag.TAG_COMPOUND);
            for (int i = 0; i < rootsList.size(); i++) {
                CompoundTag rootEntry = rootsList.getCompound(i);
                if (rootEntry.contains("Layer", Tag.TAG_STRING) && rootEntry.contains("Origin", Tag.TAG_STRING)) {
                    ResourceLocation layerId = ResourceLocation.tryParse(rootEntry.getString("Layer"));
                    ResourceLocation originId = ResourceLocation.tryParse(rootEntry.getString("Origin"));
                    if (layerId != null && originId != null) {
                        ResourceKey<OriginLayer> layerKey = ResourceKey.create(OriginsDynamicRegistries.LAYERS_REGISTRY, layerId);
                        ResourceKey<Origin> originKey = ResourceKey.create(OriginsDynamicRegistries.ORIGINS_REGISTRY, originId);
                        this.rootKeys.put(layerKey, originKey);
                    } else {
                        NewNewOrigins.LOGGER.warn("Invalid resource location in root entry: layer={}, origin={}",
                                rootEntry.getString("Layer"), rootEntry.getString("Origin"));
                    }
                }
            }
        }
        this.lastKeys.clear();
        if (tag.contains("Lasts", Tag.TAG_LIST)) {
            ListTag lastList = tag.getList("Lasts", Tag.TAG_COMPOUND);
            for (int i = 0; i < lastList.size(); i++) {
                CompoundTag lastEntry = lastList.getCompound(i);
                if (lastEntry.contains("Layer", Tag.TAG_STRING) && lastEntry.contains("Origin", Tag.TAG_STRING)) {
                    ResourceLocation layerId = ResourceLocation.tryParse(lastEntry.getString("Layer"));
                    ResourceLocation originId = ResourceLocation.tryParse(lastEntry.getString("Origin"));
                    if (layerId != null && originId != null) {
                        ResourceKey<OriginLayer> layerKey = ResourceKey.create(OriginsDynamicRegistries.LAYERS_REGISTRY, layerId);
                        ResourceKey<Origin> originKey = ResourceKey.create(OriginsDynamicRegistries.ORIGINS_REGISTRY, originId);
                        this.lastKeys.put(layerKey, originKey);
                    } else {
                        NewNewOrigins.LOGGER.warn("Invalid resource location in last entry: layer={}, origin={}",
                                lastEntry.getString("Layer"), lastEntry.getString("Origin"));
                    }
                }
            }
        }
        this.roots = getOrigins(rootKeys);
        NewNewOrigins.LOGGER.debug("Capability：反序列化");
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return ModCapabilities.UPGRADE_COMPONENT.orEmpty(cap, this.capability);
    }

    //#region Capability接口方法：被其他对象使用
    @Override
    public void putLast(Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> layers) {
        this.lastKeys = layers;
    }

    @Override
    public Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> getLast() {
        return Map.copyOf(this.lastKeys);
    }
    //#endregion


    public void refreshRoots(ResourceKey<OriginLayer> key, ResourceKey<Origin> value) {
        this.rootKeys.put(key, value);
        this.roots = getOrigins(rootKeys);
        NewNewOrigins.LOGGER.debug("Capability：重置根起源");
    }

    /// 该方法将重置该起源系列每个进化条件的成就
    /// 应当传入根起源，否则可能无法定位成就
    public void refreshAdvancements(@Nullable Origin origin) {
        if (origin == null) return;
        Set<Advancement> advancements = new HashSet<>();
        getAllUpgradeAdvancements(origin, advancements, new HashSet<>());
        advancements.forEach(advancement -> revokeAdvancement((ServerPlayer) this.player, advancement));
        NewNewOrigins.LOGGER.debug("Capability：重置进度");
    }

    //#region 进度相关
    /// 传入一个起源，将该起源的所有进化成就全部加入列表，并对该起源的所有进化起源再次调用这个方法
    /// @param lastStep 用于在单个递归分支调用内记录已经存在的进化目标，防止循环引用导致的崩溃，仅应该传入new HashSet()
    private void getAllUpgradeAdvancements(@NotNull Origin origin, Set<Advancement> output, Set<ResourceKey<Origin>> lastStep) {
        if (this.player.getServer() == null) return;

        origin.getUpgrades().forEach(upgrade -> {
            //先查看是不是有效进化，一般不可能出现这类情况，这一步仅用于规避编译器警告
            if (upgrade.origin().unwrapKey().isEmpty()) return;
            if (!upgrade.origin().isBound()) return;
            //记录当前进化的成就
            Advancement advancement = player.getServer().getAdvancements().getAdvancement(upgrade.advancement());
            if (advancement != null) output.add(advancement);
            //记录进化起源，进行循环引用检查，如果构成循环引用，则将当前分支的递归中断
            ResourceKey<Origin> resourceKey = upgrade.origin().unwrapKey().get();
            if (lastStep.contains(resourceKey)) return;
            //如果不构成循环引用，则记录起源，并继续进入子分支
            Set<ResourceKey<Origin>> thisStep = new HashSet<>(lastStep);
            thisStep.add(resourceKey);
            getAllUpgradeAdvancements(upgrade.origin().get(), output, thisStep);
        });
    }

    /// 撤销传入进度的所有条件
    private void revokeAdvancement(ServerPlayer player, Advancement advancement) {
        AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
        for (String criterion : progress.getCompletedCriteria()) {
            player.getAdvancements().revoke(advancement, criterion);
        }
    }
    //#endregion

    /// 起源更换事件相应：
    /// 每修改一个起源触发一次。
    /// 触发该事件时起源还没有更换。
    @SubscribeEvent
    public void setOriginHandler(SetOriginEvent event) {
        if (!this.enabled) return;
        if (!event.isUpgrade()) {
            //如果不是进化
            //退回旧起源的进化成就
            this.refreshAdvancements(getOrigin(this.rootKeys, event.getLayer()));
            //更新根起源
            this.refreshRoots(event.getLayer(), event.getNewOrigin());
        }
    }

    /// 清理
    public void invalidate() {
        this.capability.invalidate();
    }

    //#region 公共静态方法
    private static @Nullable IOriginContainer getOriginContainer(Player player) {
        return player.getCapability(OriginsAPI.ORIGIN_CONTAINER).resolve().orElse(null);
    }

    private static Map<OriginLayer, Origin> getOrigins(Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> keys) {
        Map<OriginLayer, Origin> ret = new HashMap<>();
        keys.forEach(((rkLayer, rkOrigin) -> {
            Optional<Holder.Reference<OriginLayer>> layerHolder = OriginsAPI.getLayersRegistry().getHolder(rkLayer);
            Optional<Holder.Reference<Origin>> originHolder = OriginsAPI.getOriginsRegistry().getHolder(rkOrigin);
            if (layerHolder.isEmpty() || !(layerHolder.get()).isBound()) return;
            if (originHolder.isEmpty() || !(originHolder.get()).isBound()) return;
            ret.put(layerHolder.get().value(), originHolder.get().value());
        }));
        return ret;
    }

    private static @Nullable Origin getOrigin(Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> layers, ResourceKey<OriginLayer> layer) {
        ResourceKey<Origin> origin = layers.get(layer);
        Optional<Holder.Reference<Origin>> originHolder = OriginsAPI.getOriginsRegistry().getHolder(origin);
        if (originHolder.isPresent() && originHolder.get().isBound()) return originHolder.get().value();
        else return null;
    }

    private static @Nullable Origin getOrigin(ResourceKey<Origin> origin) {
        Optional<Holder.Reference<Origin>> originHolder = OriginsAPI.getOriginsRegistry().getHolder(origin);
        if (originHolder.isPresent() && originHolder.get().isBound()) return originHolder.get().value();
        else return null;
    }
    //#endregion
}
