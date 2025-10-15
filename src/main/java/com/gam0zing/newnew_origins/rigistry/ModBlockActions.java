package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.origins.power.action.BlockExplodeAction;
import io.github.edwinmindcraft.apoli.api.power.factory.BlockAction;
import io.github.edwinmindcraft.apoli.api.registry.ApoliRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockActions {
    public static final DeferredRegister<BlockAction<?>> BLOCK_ACTIONS = DeferredRegister.create(ApoliRegistries.BLOCK_ACTION_KEY, NewNewOrigins.MODID);

    public static final RegistryObject<BlockExplodeAction> NEW_EXPLODE = BLOCK_ACTIONS.register("new_explode", BlockExplodeAction::new);

    public static void register(IEventBus modEventBus) {
        BLOCK_ACTIONS.register(modEventBus);
    }
}