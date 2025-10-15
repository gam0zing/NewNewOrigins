package com.gam0zing.newnew_origins.util;

import com.gam0zing.newnew_origins.rigistry.ModParticles;
import net.minecraft.core.particles.ParticleOptions;

/// 这个类仅仅方便粒子的调用，因为调用粒子时传递的参数为ParticleOptions
public class ParticleHelper {
    public static final ParticleOptions SPELL_REGENERATION = ModParticles.SPELL_REGENERATION.value().get();
}
