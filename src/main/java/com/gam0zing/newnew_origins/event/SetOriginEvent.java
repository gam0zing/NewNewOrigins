package com.gam0zing.newnew_origins.event;

import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import org.jetbrains.annotations.Nullable;

public class SetOriginEvent extends Event {

    private final boolean isUpgrade;
    private final Player player;
    private final ResourceKey<OriginLayer> layer;
    private final ResourceKey<Origin> newOrigin;
    private final ResourceKey<Origin> oldOrigin;

    public boolean isUpgrade() {
        return this.isUpgrade;
    }
    public Player getPlayer() {
        return this.player;
    }
    public ResourceKey<OriginLayer> getLayer() {
        return this.layer;
    }
    public ResourceKey<Origin> getNewOrigin() {
        return this.newOrigin;
    }
    public ResourceKey<Origin> getOldOrigin() {
        return this.oldOrigin;
    }

    public SetOriginEvent(boolean isUpgrade, Player player, ResourceKey<OriginLayer> layer, ResourceKey<Origin> newOrigin, @Nullable ResourceKey<Origin> oldOrigin) {
        this.isUpgrade = isUpgrade;
        this.player = player;
        this.layer = layer;
        this.newOrigin = newOrigin;
        this.oldOrigin = oldOrigin;
    }

    @Override
    public boolean hasResult() {
        return false;
    }
}
