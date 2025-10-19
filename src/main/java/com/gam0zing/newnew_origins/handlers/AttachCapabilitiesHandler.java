package com.gam0zing.newnew_origins.handlers;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.upgrade.UpgradeComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NewNewOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AttachCapabilitiesHandler {

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof ServerPlayer player && !player.level().isClientSide()) {
            event.addCapability(UpgradeComponent.ID, new UpgradeComponent(player));
        }
    }
}