package com.gam0zing.newnew_origins.client;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.client.entity.NewNewPowerRenderer;
import com.gam0zing.newnew_origins.rigistry.ModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = NewNewOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupHandler {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.NEWNEW_POWER.get(), NewNewPowerRenderer::new);
    }
}
