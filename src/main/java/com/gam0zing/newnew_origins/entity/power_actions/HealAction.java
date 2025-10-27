package com.gam0zing.newnew_origins.entity.power_actions;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.entity.NewNewPower;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class HealAction extends AbstractAction {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, "heal_action");

    public ActionCategory category = ActionCategory.FRIENDLY;

    public float heal;

    public HealAction(float heal) {
        this.heal = heal;
    }

    public HealAction() {
        this(0.0F);
    }

    @Override
    public void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

    }

    @Override
    protected void apply(NewNewPower power, Entity target) {
        if (!target.level().isClientSide() && target instanceof LivingEntity livingTarget) {
            livingTarget.heal(this.heal);
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putString("Id", ID.toString());
        nbt.putFloat("Heal", this.heal);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.heal = nbt.getFloat("Heal");
    }
}
