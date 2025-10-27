package com.gam0zing.newnew_origins.entity.power_actions;

import com.gam0zing.newnew_origins.entity.NewNewPower;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractAction implements INBTSerializable<CompoundTag> {

    public enum ActionCategory {
        NORMAL,
        FRIENDLY,
        HARMFUL;

        public static ActionCategory getOrDefault(String name, @NotNull ActionCategory pDefault) {
            if (name == null || name.trim().isEmpty()) return pDefault;
            try {
                return ActionCategory.valueOf(name.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                return pDefault;
            }
        }
    }

    public static final ActionCategory DEFAULT_CATEGORY = ActionCategory.NORMAL;

    protected ActionCategory category = DEFAULT_CATEGORY;

    public abstract void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight);
    protected abstract void apply(NewNewPower power, Entity target);

    public void apply(@NotNull NewNewPower power, @NotNull Entity target, boolean smart) {
        if (!smart || power.getOwner() == null) this.apply(power, target);
        else if (canUse(power.getOwner(), target)) this.apply(power, target);
    }

    public boolean canUse(Entity owner, Entity target) {
        //如果是联机游戏，且正好禁用了PvP，就需要避免对任何玩家或玩家宠物施加负面效果
        //如果没有禁用PvP，就仅需要跳过药水云发射者和其宠物
        //对于玩家、玩家宠物、玩家傀儡之外的任何生物，仅施加通用效果和负面效果
        if (this.isFriendly(owner, target) && this.category != ActionCategory.HARMFUL) return false;
        if (!this.isFriendly(owner, target) && this.category == ActionCategory.FRIENDLY) return false;
        return true;
    }

    public boolean isFriendly(Entity owner, Entity target) {
        if (target.getServer() != null && !target.getServer().isPvpAllowed()) {
            return target instanceof Player
                    || target instanceof OwnableEntity pet && pet.getOwner() instanceof Player
                    || target instanceof IronGolem ironGolem && ironGolem.isPlayerCreated()
                    || target instanceof SnowGolem;
        }
        else {
            return target == owner
                    || target instanceof OwnableEntity pet && pet.getOwner() == owner
                    || target instanceof IronGolem ironGolem && ironGolem.isPlayerCreated()
                    || target instanceof SnowGolem;
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("ActionCategory", this.category.name());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.category = ActionCategory.getOrDefault(nbt.getString("ActionCategory"), DEFAULT_CATEGORY);
    }
}
