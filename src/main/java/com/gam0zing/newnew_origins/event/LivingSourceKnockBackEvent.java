package com.gam0zing.newnew_origins.event;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class LivingSourceKnockBackEvent extends LivingKnockBackEvent {

    protected Entity source;

    public LivingSourceKnockBackEvent(LivingEntity target, @Nullable Entity source, float strength, double ratioX, double ratioZ) {
        super(target, strength, ratioX, ratioZ);
    }

    public Entity getSource() {
        return this.source;
    }
    public void setSource(Entity source) {
        this.source = source;
    }
}
