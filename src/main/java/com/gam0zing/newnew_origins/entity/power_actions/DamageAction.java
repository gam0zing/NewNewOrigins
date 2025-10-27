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

public class DamageAction extends AbstractAction {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, "damage_action");

    public ActionCategory category = ActionCategory.HARMFUL;

    public float damage;
    public boolean noKnockback;
    protected ResourceKey<DamageType> damageType;

    protected DamageSource damageSource;

    public DamageAction(float damage, ResourceKey<DamageType> damageType, boolean noKnockback) {
        this.damage = damage;
        this.damageType = damageType;
        this.noKnockback = noKnockback;
    }
    public DamageAction(float damage, ResourceKey<DamageType> damageType) {
        this(damage, damageType, false);
    }
    public DamageAction() {
        this(0.0F, DamageTypes.GENERIC);
    }

    @Override
    public void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

    }

    @Override
    protected void apply(NewNewPower power, Entity target) {
        if (!target.level().isClientSide() && target instanceof LivingEntity livingTarget) {
            DamageSource damageSource = this.getDamageSource(power);
            livingTarget.hurt(damageSource, this.damage);
        }
    }

    protected DamageSource getDamageSource(NewNewPower power) {
        if (this.damageSource != null && this.damageSource.typeHolder().is(this.damageType)) return this.damageSource;
        else return this.createDamageSource(power);
    }

    protected DamageSource createDamageSource(NewNewPower power) {
        Holder<DamageType> holder = getTypeHolder(this.damageType, power);
        if (!holder.isBound()) return power.damageSources().generic();
        return new DamageSource(holder, power, power.getOwner(), power.getOwner() == null ? power.position() : power.getOwner().position());
    }

    protected Holder<DamageType> getTypeHolder(ResourceKey<DamageType> resourceKey, @NotNull NewNewPower power) {
        Registry<DamageType> damageTypes = power.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        return damageTypes.getHolderOrThrow(resourceKey);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putString("Id", ID.toString());
        nbt.putFloat("Damage", this.damage);
        nbt.putBoolean("NoKnockback", this.noKnockback);
        nbt.putString("DamageType", this.damageType.location().toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.damage = nbt.getFloat("Damage");
        this.noKnockback = nbt.getBoolean("NoKnockback");
        String typeString = nbt.getString("DamageType");
        if (ResourceLocation.isValidResourceLocation(typeString)) this.damageType = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.parse(typeString));
        else this.damageType = DamageTypes.GENERIC;
    }
}
