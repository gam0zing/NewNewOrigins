package com.gam0zing.newnew_origins.condition;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class UpgradeCondition extends Condition {

    public UpgradeCondition(ServerPlayer player, ResourceLocation id) {
        super(player, id);
    }


}
