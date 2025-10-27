package com.gam0zing.newnew_origins.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public abstract class AbstractTargeter implements INBTSerializable<CompoundTag> {
    public Class<? extends Entity> targetClass;

    public AbstractTargeter(Class<? extends Entity> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight);
    public abstract Set<Entity> get(NewNewPower power);

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        String className = nbt.getString("targetClass");
        if (!className.isEmpty()) {
            try {
                //全限定名反射获取类型
                Class<?> clazz = Class.forName(className);
                if (Entity.class.isAssignableFrom(clazz)) {
                    @SuppressWarnings("unchecked")
                    Class<? extends Entity> entityClass = (Class<? extends Entity>) clazz;
                    this.targetClass = entityClass;
                }
            } catch (ClassNotFoundException e) {
                this.targetClass = Entity.class;
            }
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        if (targetClass != null) {
            nbt.putString("targetClass", targetClass.getName());
        }
        return nbt;
    }
}
