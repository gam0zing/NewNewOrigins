package com.gam0zing.newnew_origins.origins.power.action.configuration;

import com.gam0zing.newnew_origins.ModDataTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.apace100.apoli.Apoli;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredBlockCondition;
import io.github.edwinmindcraft.calio.api.network.CalioCodecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.Lazy;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public final class BlockExplodeActionConfiguration implements IDynamicFeatureConfiguration {
    public static final Codec<BlockExplodeActionConfiguration> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            CalioCodecHelper.FLOAT.fieldOf("power").forGetter(BlockExplodeActionConfiguration::power),
            CalioCodecHelper.optionalField(ModDataTypes.LEVEL_EXPLOSION_INTERACTION, "explosion_interaction", Level.ExplosionInteraction.NONE).forGetter(BlockExplodeActionConfiguration::explosionInteraction),
            CalioCodecHelper.optionalField(CalioCodecHelper.BOOL, "damage_self", true).forGetter(BlockExplodeActionConfiguration::damageSelf),
            ConfiguredBlockCondition.optional("indestructible", Apoli.identifier("deny")).forGetter(BlockExplodeActionConfiguration::indestructible),
            ConfiguredBlockCondition.optional("destructible", Apoli.identifier("deny")).forGetter(BlockExplodeActionConfiguration::destructible),
            CalioCodecHelper.optionalField(CalioCodecHelper.BOOL, "create_fire", false).forGetter(BlockExplodeActionConfiguration::createFire)).apply(instance, BlockExplodeActionConfiguration::new));
    private final float power;
    private final Level.ExplosionInteraction explosionInteraction;
    private final boolean damageSelf;
    private final Holder<ConfiguredBlockCondition<?, ?>> indestructible;
    private final Holder<ConfiguredBlockCondition<?, ?>> destructible;
    private final boolean createFire;
    private final transient Lazy<ExplosionDamageCalculator> explosionCalculator;

    public BlockExplodeActionConfiguration(float power, Level.ExplosionInteraction explosionInteraction, boolean damageSelf, Holder<ConfiguredBlockCondition<?, ?>> indestructible, Holder<ConfiguredBlockCondition<?, ?>> destructible, boolean createFire) {
        this.power = power;
        this.explosionInteraction = explosionInteraction;
        this.damageSelf = damageSelf;
        this.indestructible = indestructible;
        this.destructible = destructible;
        this.createFire = createFire;
        this.explosionCalculator = Lazy.of(() -> this.indestructible().isBound() && this.destructible().isBound() ? new ExplosionDamageCalculator() {
            public @NotNull Optional<Float> getBlockExplosionResistance(@NotNull Explosion explosion, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull FluidState fluid) {
                Optional<Float> def = super.getBlockExplosionResistance(explosion, world, pos, state, fluid);
                Optional<Float> ovr = !ConfiguredBlockCondition.check(BlockExplodeActionConfiguration.this.indestructible(), (LevelReader)world, pos, () -> state) || BlockExplodeActionConfiguration.this.destructible().isBound() && ConfiguredBlockCondition.check(BlockExplodeActionConfiguration.this.destructible(), (LevelReader)world, pos, () -> state) ? Optional.empty() : Optional.of(100.0F);
                return ovr.isPresent() ? (def.isPresent() ? ((Float)def.get() > (Float)ovr.get() ? def : ovr) : ovr) : def;
            }
        } : new ExplosionDamageCalculator());
    }

    public float power() {
        return this.power;
    }

    public Level.ExplosionInteraction explosionInteraction() {
        return this.explosionInteraction;
    }

    public boolean damageSelf() {
        return this.damageSelf;
    }

    public Holder<ConfiguredBlockCondition<?, ?>> indestructible() {
        return this.indestructible;
    }

    public Holder<ConfiguredBlockCondition<?, ?>> destructible() {
        return this.destructible;
    }

    public boolean createFire() {
        return this.createFire;
    }

    public @NotNull ExplosionDamageCalculator calculator() {
        return (ExplosionDamageCalculator)this.explosionCalculator.get();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj != null && obj.getClass() == this.getClass()) {
            BlockExplodeActionConfiguration that = (BlockExplodeActionConfiguration)obj;
            return Float.floatToIntBits(this.power) == Float.floatToIntBits(that.power) && Objects.equals(this.explosionInteraction, that.explosionInteraction) && this.damageSelf == that.damageSelf && Objects.equals(this.indestructible, that.indestructible) && Objects.equals(this.destructible, that.destructible) && this.createFire == that.createFire;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.power, this.explosionInteraction, this.damageSelf, this.indestructible, this.destructible, this.createFire});
    }

    public String toString() {
        return "ExplodeConfiguration[power=" + this.power + ", explosionInteraction=" + this.explosionInteraction + ", damageSelf=" + this.damageSelf + ", indestructible=" + this.indestructible + ", destructible=" + this.destructible + ", createFire=" + this.createFire + "]";
    }
}
