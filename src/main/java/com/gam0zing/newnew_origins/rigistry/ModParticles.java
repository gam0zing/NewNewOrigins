package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.data.DataSources;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModParticles {
    private static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, NewNewOrigins.MODID);

    public static final ParticleRegistry SPELL_REGENERATION = registerParticle(
            ModKeys.ID.PARTICLE_ID_SPELL_REGENERATION,
            getSpritesByList(NewNewOrigins.fullID(ModKeys.ID.PARTICLE_ID_SPELL_REGENERATION), 8)
    );

    private static ParticleRegistry registerParticle(String name, List<String> particles) {
        RegistryObject<SimpleParticleType> registryObject = PARTICLES.register(name, () -> new SimpleParticleType(false));
        return new ParticleRegistry(registryObject, new DataSources.ParticleData(name, particles));
    }
    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    public record ParticleRegistry(RegistryObject<SimpleParticleType> value, DataSources.ParticleData data) {}

    private static List<String> getSpritesByList(String name, int count) {
        if (count <= 0) return List.of();
        List<String> ret = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ret.add(name + "_" + i);
        }
        return List.copyOf(ret);
    }

}
