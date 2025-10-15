package com.gam0zing.newnew_origins.origins.power.action;

import com.gam0zing.newnew_origins.entity.SmartAreaEffectCloud;
import com.gam0zing.newnew_origins.origins.power.action.configuration.ForestElf_SpellRegeneration_ActionConfiguration;
import com.gam0zing.newnew_origins.rigistry.ModEffects;
import com.gam0zing.newnew_origins.util.ModTools;
import com.gam0zing.newnew_origins.util.ParticleHelper;
import com.gam0zing.newnew_origins.util.SoundHelper;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ForestElf_SpellRegeneration_Action extends EntityAction<ForestElf_SpellRegeneration_ActionConfiguration> {

    public ForestElf_SpellRegeneration_Action() {
        super(ForestElf_SpellRegeneration_ActionConfiguration.CODEC);
    }

    @Override
    public void execute(@NotNull ForestElf_SpellRegeneration_ActionConfiguration configuration, @NotNull Entity entity) {
        makeEffect(configuration, entity);
    }

    private void makeEffect(ForestElf_SpellRegeneration_ActionConfiguration configuration, Entity entity) {
        if (!entity.level().isClientSide()) {
            ServerLevel serverWorld = (ServerLevel)entity.level();

            AreaEffectCloud cloud;

            if (configuration.isSmart()) cloud = new SmartAreaEffectCloud(serverWorld, entity.getX(), entity.getY(), entity.getZ());
            else cloud = new AreaEffectCloud(serverWorld, entity.getX(), entity.getY(), entity.getZ());

            if (cloud instanceof SmartAreaEffectCloud) ((SmartAreaEffectCloud) cloud).entityCooldown = configuration.entityCooldown();
            cloud.setWaitTime(configuration.waitTime());
            cloud.setDuration(configuration.duration());
            cloud.setRadius(configuration.radius());
            cloud.setRadiusPerTick(configuration.radiusPerTick());
            cloud.setOwner((LivingEntity) entity);
            if (configuration.useRegeneration()) cloud.addEffect(new MobEffectInstance(ModEffects.ELF_REGENERATION.get(), configuration.regenerationDuration(), configuration.regenerationLevel() - 1));
            if (configuration.useHunger()) cloud.addEffect(new MobEffectInstance(MobEffects.HUNGER, configuration.hungerDuration(), configuration.hungerLevel() - 1));
            if (configuration.useHarm()) cloud.addEffect(new MobEffectInstance(ModEffects.ELF_WITHER.get(), 1, configuration.harmLevel() - 1));
            cloud.setRadiusOnUse(0);
            cloud.setDurationOnUse(0);
            cloud.setFixedColor(ModTools.intColor(40,200,10));
            cloud.setParticle(ParticleHelper.SPELL_REGENERATION);

            entity.level().playSound(null, entity.getX(), entity.getY() + entity.getEyeHeight() * 0.5f, entity.getZ(), SoundHelper.POWER_SPELL_REGENERATION, SoundSource.PLAYERS, 1, 1);

            serverWorld.tryAddFreshEntityWithPassengers(cloud);
        }
    }
}
