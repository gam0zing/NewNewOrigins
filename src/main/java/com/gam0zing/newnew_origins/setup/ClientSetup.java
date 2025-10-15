package com.gam0zing.newnew_origins.setup;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.particles.SpellRegenerationParticle;
import com.gam0zing.newnew_origins.rigistry.ModParticles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/// 这个类用于注册粒子精灵等需要在客户端生效的东西
@Mod.EventBusSubscriber(modid = NewNewOrigins.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.SPELL_REGENERATION.value().get(), SpellRegenerationParticle.Provider::new);
    }
}
