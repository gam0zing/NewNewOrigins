package com.gam0zing.newnew_origins.power.action.block;

import io.github.apace100.apoli.action.configuration.ExplodeConfiguration;
import io.github.edwinmindcraft.apoli.api.power.factory.BlockAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/// Apoli的爆炸能力没有使用完整的原版explode方法，导致没有声音、粒子的表现
/// 这个类是为了修正这个情况并代替原本的爆炸行为
public class NewExplodeAction extends BlockAction<ExplodeConfiguration> {

    public NewExplodeAction() {
        super(ExplodeConfiguration.CODEC);
    }

    public void execute(@NotNull ExplodeConfiguration configuration, @NotNull Level world, @NotNull BlockPos pos, @NotNull Direction direction) {
        this.makeEffect(configuration, world, pos, direction);
    }

    public void makeEffect(ExplodeConfiguration configuration, Level world, BlockPos pos, Direction direction) {
        if (!world.isClientSide()) {
            ExplosionDamageCalculator calculator = configuration.calculator();
            Level.ExplosionInteraction explosionInteraction = switch (configuration.destructionType()) {
                case KEEP -> Level.ExplosionInteraction.NONE;
                case DESTROY_WITH_DECAY -> Level.ExplosionInteraction.TNT;
                default -> Level.ExplosionInteraction.BLOCK;
            };
            Objects.requireNonNull(world.getServer()).execute(() -> {
                world.explode(null, null, calculator, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, configuration.power(), configuration.createFire(), explosionInteraction);
            });
        }
    }
}