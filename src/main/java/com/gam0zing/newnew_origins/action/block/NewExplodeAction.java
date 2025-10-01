package com.gam0zing.newnew_origins.action.block;

import io.github.apace100.apoli.action.configuration.ExplodeConfiguration;
import io.github.edwinmindcraft.apoli.api.power.factory.BlockAction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/// Apoli的爆炸调用非常奇怪，没有爆炸音效、粒子、击退，并且伤害始终为1
/// 这个类是为了修正个情况并代替原本的爆炸行为
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
            world.explode(null, null, calculator, pos.getX(), pos.getY(), pos.getZ(), configuration.power(), configuration.createFire(), explosionInteraction);
        }
    }
}