package com.gam0zing.newnew_origins.mixin;

import com.gam0zing.newnew_origins.condition.UpgradeComponent;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayer.class, priority = 1000)
public class ServerPlayerMixin {

    @Unique
    private UpgradeComponent newnew_origins1201$upgradeComponent;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initMixin(MinecraftServer pServer, ServerLevel pLevel, GameProfile pGameProfile, CallbackInfo ci) {
        this.newnew_origins1201$upgradeComponent = new UpgradeComponent((ServerPlayer) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        this.newnew_origins1201$upgradeComponent.tick();
    }
}
