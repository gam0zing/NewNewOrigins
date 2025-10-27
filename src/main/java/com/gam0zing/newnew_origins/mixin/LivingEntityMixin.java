package com.gam0zing.newnew_origins.mixin;

import com.gam0zing.newnew_origins.entity.NewNewPower;
import com.gam0zing.newnew_origins.entity.power_actions.AbstractAction;
import com.gam0zing.newnew_origins.entity.power_actions.DamageAction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1000)
public class LivingEntityMixin {

    @Unique
    private DamageSource newNewOrigins_1_20_1$currentDamageSource;

    @Redirect(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"))
    private void redirectKnockback(LivingEntity instance, double pStrength, double pX, double pZ) {
        if (!newNewOrigins_1_20_1$noKnockback(instance, this.newNewOrigins_1_20_1$currentDamageSource)) {
            instance.knockback(pStrength, pX, pZ);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"))
    private void onHurtStart(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.newNewOrigins_1_20_1$currentDamageSource = source;
    }
    @Inject(method = "hurt", at = @At("RETURN"))
    private void onHurtEnd(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.newNewOrigins_1_20_1$currentDamageSource = null;
    }

    @Unique
    private boolean newNewOrigins_1_20_1$noKnockback(LivingEntity entity, DamageSource source) {
        Entity directEntity = source.getDirectEntity();
        boolean ret = false;
        if (directEntity instanceof NewNewPower power) {
            for (AbstractAction action : power.getActions()) {
                if (action instanceof DamageAction damageAction) {
                    ret = damageAction.noKnockback;
                }
            }
        }
        return ret;
    }
}