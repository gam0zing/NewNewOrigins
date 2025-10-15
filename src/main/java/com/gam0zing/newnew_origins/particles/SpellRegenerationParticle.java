package com.gam0zing.newnew_origins.particles;

import com.gam0zing.newnew_origins.util.ModTools;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.lang.model.SourceVersion;

public class SpellRegenerationParticle extends TextureSheetParticle {

    private final SpriteSet sprites;

    public int light = ModTools.intColor(255, 255, 255);

    public SpellRegenerationParticle(ClientLevel level, double pX, double pY, double pZ, SpriteSet spriteSet, double pSpeedX, double pSpeedY, double pSpeedZ) {

        super(level, pX, pY, pZ, pSpeedX, pSpeedY, pSpeedZ);

        this.quadSize *= 1f;
        this.scale(1.15f);
        this.lifetime = 5 + (int) (Math.random() * 25);
        sprites = spriteSet;
        this.setSpriteFromAge(spriteSet);

        float f = this.random.nextFloat() * 0.2f + 0.8f;
        this.rCol = 1 * f;
        this.gCol = 1 * f;
        this.bCol = 1 * f;

        this.alpha = 0.8f;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(@NotNull SimpleParticleType particleType, @NotNull ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new SpellRegenerationParticle(level, x, y, z, this.sprites, dx, dy, dz);
        }
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return Math.max(super.getLightColor(pPartialTick), this.light);
    }
}
