package com.gam0zing.newnew_origins.client.entity;

import com.gam0zing.newnew_origins.entity.NewNewPower;
import com.gam0zing.newnew_origins.entity.power_actions.AbstractAction;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class NewNewPowerRenderer extends EntityRenderer<NewNewPower> {

    public NewNewPowerRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull NewNewPower pEntity) {
        return ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/stone.png");
    }

    @Override
    public void render(@NotNull NewNewPower pEntity, float pEntityYaw, float pPartialTick, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        //目标选择器渲染
        if (pEntity.getTargeter() != null) pEntity.getTargeter().render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        //效果渲染
        Set<AbstractAction> actionSet = new HashSet<>(pEntity.getActions());
        for (AbstractAction action : actionSet) {
            action.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
        }
    }
}
