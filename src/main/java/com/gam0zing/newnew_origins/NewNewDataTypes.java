package com.gam0zing.newnew_origins;

import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;
import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class NewNewDataTypes {

    public static final SerializableDataType<Level.ExplosionInteraction> LEVEL_EXPLOSION_INTERACTION;

    static {
        LEVEL_EXPLOSION_INTERACTION = SerializableDataType.mapped(Level.ExplosionInteraction.class, HashBiMap.create(
                ImmutableBiMap.of(
                        "none", Level.ExplosionInteraction.NONE,
                        "block", Level.ExplosionInteraction.BLOCK,
                        "mob", Level.ExplosionInteraction.MOB,
                        "tnt", Level.ExplosionInteraction.TNT)));
    }
}
