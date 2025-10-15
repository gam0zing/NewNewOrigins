package com.gam0zing.newnew_origins.mixin;

import com.gam0zing.newnew_origins.rigistry.ModAttributes;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.configuration.power.ICooldownPowerConfiguration;
import io.github.edwinmindcraft.apoli.api.power.factory.power.CooldownPowerFactory;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CooldownPowerFactory.class)
public abstract class CooldownPowerFactoryMixin<T extends ICooldownPowerConfiguration> {

    @Shadow(remap = false) protected abstract long getLastUseTime(ConfiguredPower<T, ?> configuration, Entity entity);

    @Shadow(remap = false)
    public int getValue(ConfiguredPower<T, ?> configuration, Entity entity) {
        return Math.toIntExact((long)Mth.clamp((float)this.getRemainingDuration(configuration, entity), (float)this.getMinimum(configuration, entity), (float)this.getMaximum(configuration, entity)));
    }

    @Shadow(remap = false)
    public int getMaximum(ConfiguredPower<T, ?> configuration, Entity entity) {
        return ((ICooldownPowerConfiguration)configuration.getConfiguration()).duration();
    }

    @Shadow(remap = false)
    public int getMinimum(ConfiguredPower<T, ?> configuration, Entity entity) {
        return 0;
    }

    @Shadow(remap = false)
    protected long getRemainingDuration(ConfiguredPower<T, ?> configuration, Entity entity) {
        return Math.max(this.getLastUseTime(configuration, entity) + (long)((ICooldownPowerConfiguration)configuration.getConfiguration()).duration() - entity.getCommandSenderWorld().getGameTime(), 0L);
    }

    @Inject(method = "getRemainingDuration", at = @At("HEAD"), cancellable = true, remap = false)
    protected void getRemainingDurationMixin(ConfiguredPower<T, ?> configuration, Entity entity, CallbackInfoReturnable<Long> cir) {
        if (entity instanceof LivingEntity living) {
            if (living.getAttribute(ModAttributes.POWER_COOLDOWN_SPEED.get()) == null) return;
            double cooldownSpeed = living.getAttribute(ModAttributes.POWER_COOLDOWN_SPEED.get()).getValue();
            long ret = this.getMaximum(configuration, entity);
            if (cooldownSpeed == 0) cir.setReturnValue(ret);
            ret = Math.max(this.getLastUseTime(configuration, entity) + this.getMaximum(configuration, entity) - entity.getCommandSenderWorld().getGameTime(), 0L);
            cir.setReturnValue(ret);
        }
    }

    @Inject(method = "getMaximum", at = @At("HEAD"), cancellable = true, remap = false)
    public void getMaximumMixin(ConfiguredPower<T, ?> configuration, Entity entity, CallbackInfoReturnable<Integer> cir) {
        if (entity instanceof LivingEntity living) {
            if (living.getAttribute(ModAttributes.POWER_COOLDOWN_SPEED.get()) == null) return;
            double cooldownSpeed = living.getAttribute(ModAttributes.POWER_COOLDOWN_SPEED.get()).getValue();
            if (cooldownSpeed == 0) cir.setReturnValue(Integer.MAX_VALUE);
            cir.setReturnValue((int) (configuration.getConfiguration().duration() / cooldownSpeed));
        }
    }
}
