package com.gam0zing.newnew_origins.origins.power.action;

import com.gam0zing.newnew_origins.origins.power.action.configuration.ForestElf_SpellRegeneration_ActionConfiguration;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.apace100.apoli.action.configuration.ExplodeConfiguration;
import io.github.apace100.apoli.util.MiscUtil;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import io.github.edwinmindcraft.apoli.common.power.configuration.ActiveSelfConfiguration;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

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
            Optional<Entity> opt$entityToSpawn = MiscUtil.getEntityWithPassengers(serverWorld, EntityType.AREA_EFFECT_CLOUD, null, entity.position(), entity.getYRot(), entity.getXRot());
            if (opt$entityToSpawn.isPresent()) {

                AreaEffectCloud entityToSpawn = (AreaEffectCloud) opt$entityToSpawn.get();

                entityToSpawn.setWaitTime(configuration.waitTime());
                entityToSpawn.setDuration(configuration.duration());
                entityToSpawn.setRadius(configuration.radius());
                entityToSpawn.setOwner((LivingEntity) entity);
                if (configuration.useRegeneration()) entityToSpawn.addEffect(new MobEffectInstance(MobEffects.REGENERATION, configuration.regenerationDuration(), configuration.regenerationLevel() - 1));
                if (configuration.useHunger()) entityToSpawn.addEffect(new MobEffectInstance(MobEffects.HUNGER, configuration.hungerDuration(), configuration.hungerLevel() - 1));
                if (configuration.useHarm()) entityToSpawn.addEffect(new MobEffectInstance(MobEffects.HARM, 1, configuration.harmLevel() - 1));

                entityToSpawn.setRadiusOnUse(0);
                entityToSpawn.setRadiusPerTick(0);
                entityToSpawn.setDurationOnUse(0);
                entityToSpawn.setFixedColor(16716947);

                serverWorld.tryAddFreshEntityWithPassengers(entityToSpawn);
            }
        }
    }
}
