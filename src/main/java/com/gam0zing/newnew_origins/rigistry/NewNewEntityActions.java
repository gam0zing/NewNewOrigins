package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.power.action.entity.EntityExplodeAction;
import io.github.edwinmindcraft.apoli.api.power.factory.EntityAction;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NewNewEntityActions {
    public static final DeferredRegister<EntityAction<?>> ENTITY_ACTIONS = DeferredRegister.create(ApoliRegistries.ENTITY_ACTION_KEY, NewNewOrigins.MODID);

    //public static final RegistryObject<EntityExplodeAction> ENTITY_EXPLODE = ENTITY_ACTIONS.register("entity_explode", EntityExplodeAction::new);

    public static void register(IEventBus modEventBus) {
        ENTITY_ACTIONS.register(modEventBus);
    }
}