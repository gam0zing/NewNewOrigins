package com.gam0zing.newnew_origins.effects;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// 精灵凋零：造成魔法伤害，数值为效果等级*1，类似瞬间伤害
public class ElfWither extends InstantenousMobEffect {

    public ElfWither(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity pSource, @Nullable Entity pIndirectSource, @NotNull LivingEntity pLivingEntity, int pAmplifier, double pHealth) {
        int damage = (int)(pHealth * (double)(1.0f * (pAmplifier + 1)) + 0.5D);
        if (pSource == null) {
            pLivingEntity.hurt(pLivingEntity.damageSources().magic(), (float)damage);
        } else {
            pLivingEntity.hurt(pLivingEntity.damageSources().indirectMagic(pSource, pIndirectSource), (float)damage);
        }
    }
}
