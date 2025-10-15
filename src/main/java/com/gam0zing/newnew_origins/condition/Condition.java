package com.gam0zing.newnew_origins.condition;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public abstract class Condition {
    private final ServerPlayer player;
    private final ResourceLocation id;

    public Condition(ServerPlayer player, ResourceLocation id) {
        this.player = player;
        this.id = id;
    }

    public void execute() {
        Advancement advancement = player.server.getAdvancements().getAdvancement(id);
        if (advancement == null) return;
        player.getAdvancements().award(advancement, "newnew_condition");
    }
    public void unexecute() {
        Advancement advancement = player.server.getAdvancements().getAdvancement(id);
        if (advancement == null) return;
        player.getAdvancements().revoke(advancement, "newnew_condition");
    }
}
