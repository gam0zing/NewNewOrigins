package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.entity.NewNewPower;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NewNewOrigins.MODID);

    public static final RegistryObject<EntityType<NewNewPower>> NEWNEW_POWER = ENTITY_TYPES.register(
            ModKeys.ID.NEW_NEW_POWER,
            () -> EntityType.Builder.<NewNewPower>of(NewNewPower::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .fireImmune()
                    .clientTrackingRange(10)
                    .canSpawnFarFromPlayer()
                    .updateInterval(2)
                    .build(NewNewOrigins.fullId(ModKeys.ID.NEW_NEW_POWER))
    );

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
