package com.gam0zing.newnew_origins.entity.power_actions;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.entity.NewNewPower;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class EffectAction extends AbstractAction {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, "effect_action");

    public ActionCategory category = ActionCategory.NORMAL;

    protected List<MobEffectInstance> effects = new ArrayList<>();

    public EffectAction() {}

    @Override
    public void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {

    }

    @Override
    protected void apply(NewNewPower power, Entity target) {
        if (!target.level().isClientSide() && target instanceof LivingEntity livingTarget) {
            for (MobEffectInstance effect : this.effects) {
                livingTarget.addEffect(new MobEffectInstance(effect));
            }
        }
    }

    public List<MobEffectInstance> getEffects() {
        return List.copyOf(this.effects);
    }
    public void addEffect(MobEffectInstance effect) {
        this.effects.add(effect);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = super.serializeNBT();
        nbt.putString("Id", ID.toString());

        ListTag listTag = new ListTag();
        for (MobEffectInstance effect : this.effects) {
            CompoundTag tag = new CompoundTag();
            effect.save(tag);
            listTag.add(tag);
        }
        nbt.put("Effects", listTag);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        super.deserializeNBT(nbt);

        effects.clear();
        for (Tag tag : nbt.getList("Effects", 10)) {
            MobEffectInstance effect = MobEffectInstance.load((CompoundTag) tag);
            if (effect != null) this.effects.add(effect);
        }
    }
}
