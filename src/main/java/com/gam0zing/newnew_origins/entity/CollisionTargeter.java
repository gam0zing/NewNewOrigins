package com.gam0zing.newnew_origins.entity;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/// 该目标选择器使用技能实体的碰撞体作为检测范围
public class CollisionTargeter extends AbstractTargeter {
    public enum ColliderType {
        CUBE,
        CIRCLE,
        ORB;

        public static ColliderType getOrDefault(String name, @NotNull ColliderType pDefault) {
            if (name == null || name.trim().isEmpty()) return pDefault;
            try {
                return ColliderType.valueOf(name.toUpperCase().trim());
            } catch (IllegalArgumentException e) {
                return pDefault;
            }
        }
    }

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, "collision_targeter");
    public static final ColliderType DEFAULT_COLLIDER_TYPE = ColliderType.CUBE;

    public ColliderType colliderType = DEFAULT_COLLIDER_TYPE;

    public CollisionTargeter(Class<? extends Entity> targetClass, ColliderType colliderType) {
        super(targetClass);
        this.colliderType = colliderType;
    }

    public CollisionTargeter() {
        this(Entity.class, ColliderType.CUBE);
    }

    @Override
    public void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

    }

    @Override
    public Set<Entity> get(NewNewPower power) {
        Set<Entity> ret = new HashSet<>();
        List<? extends Entity> entitiesInBox = power.level().getEntitiesOfClass(this.targetClass, power.getBoundingBox());
        for (Entity entity : entitiesInBox) {
            float pt = Minecraft.getInstance().getPartialTick();
            boolean inRange = true;
            switch (this.colliderType) {
                case CIRCLE -> {
                    if (!inCircle(power.getRadius(), power.getPosition(pt), entity.getPosition(pt))) inRange = false;
                }
                case ORB -> {
                    if (!inOrb(power.getRadius(), power.getHeight(), power.getPosition(pt).add(0D, power.getHeight() * 0.5D,0D), entity.getPosition(pt).add(0D, entity.getBbHeight() * 0.5D, 0D))) inRange = false;
                }
                default -> {}
            }
            if (inRange) {
                ret.add(entity);
            }
        }
        return ret;
    }

    protected static boolean inCircle(float radius, Vec3 center, Vec3 point) {
        return radius * radius
                >= (center.x() - point.x()) * (center.x() - point.x())
                + (center.z() - point.z()) * (center.z() - point.z());
    }

    protected static boolean inOrb(float radius, float height, Vec3 center, Vec3 point) {
        float scaleY = 0.5F * height / radius;
        return radius * radius
                >= (center.x() - point.x()) * (center.x() - point.x())
                + (center.z() - point.z()) * (center.z() - point.z())
                + (center.y() - point.y()) * (center.y() - point.y()) / (scaleY * scaleY);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);
        this.colliderType = ColliderType.getOrDefault(nbt.getString("ColliderType"), DEFAULT_COLLIDER_TYPE);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putString("Id", ID.toString());
        nbt.putString("ColliderType", this.colliderType.name());
        return nbt;
    }
}
