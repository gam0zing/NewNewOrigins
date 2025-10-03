package com.gam0zing.newnew_origins.power.action.entity;

import com.gam0zing.newnew_origins.power.action.configuration.NewExplodeConfiguration;
import com.mojang.serialization.Codec;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/// 在方块爆炸的基础上，增加伤害来源
public class EntityExplodeAction extends EntityAction<NewExplodeConfiguration> {

    public EntityExplodeAction(Codec<NewExplodeConfiguration> codec) {
        super(codec);
    }

    @Override
    public void execute(NewExplodeConfiguration explodeConfiguration, @NotNull Entity entity) {
        makeEffect(entity, explodeConfiguration.calculator(), entity.getX(), entity.getY(), entity.getZ(), explodeConfiguration.power(), explodeConfiguration.createFire(), explodeConfiguration.explosionInteraction());
    }

    private void makeEffect(Entity entity, ExplosionDamageCalculator calculator, double x, double y, double z, float power, boolean createFire, Level.ExplosionInteraction explosionInteraction) {
        try (Level level = entity.level()) {
            if (level.isClientSide()) return;
            level.explode(entity, entity.damageSources().explosion(entity, entity), calculator, x, y, z, power, createFire, explosionInteraction);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
