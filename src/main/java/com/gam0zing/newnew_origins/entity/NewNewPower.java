package com.gam0zing.newnew_origins.entity;

import com.gam0zing.newnew_origins.entity.power_actions.AbstractAction;
import com.gam0zing.newnew_origins.rigistry.ModActions;
import com.gam0zing.newnew_origins.rigistry.ModEntities;
import com.gam0zing.newnew_origins.rigistry.ModTargeters;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class NewNewPower extends Entity {
    protected static final EntityDataAccessor<Float> DATA_RADIUS = SynchedEntityData.defineId(NewNewPower.class, EntityDataSerializers.FLOAT);
    protected static final EntityDataAccessor<Float> DATA_HEIGHT = SynchedEntityData.defineId(NewNewPower.class, EntityDataSerializers.FLOAT);
    public static final float DEFAULT_RADIUS = 1.0F;
    public static final float DEFAULT_HEIGHT = 1.0F;
    public static final float DEFAULT_X = 0.0F;
    public static final float DEFAULT_Y = 0.0F;
    public static final float DEFAULT_Z = 0.0F;

    protected Entity owner;

    protected UUID ownerUUID;
    protected boolean enabled = true;
    /// 这个选项将决定其是否会有选择地施加效果，比如友军识别
    protected boolean isSmart = false;
    protected boolean enabledEntityCooldown = false;
    /// 单位为tick
    protected int interval = 5;
    private int intervalTimer = 0;
    /// 单位为interval
    protected int entityCooldown = 1;
    protected List<AbstractAction> actions = new ArrayList<>();
    protected AbstractTargeter targeter;

    protected Map<Entity, Integer> entityCooldowns = new HashMap<>();
    protected Set<Entity> targets = new HashSet<>();

    public NewNewPower(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    public NewNewPower(Level pLevel) {
        this(ModEntities.NEWNEW_POWER.get(), pLevel);
    }
    public NewNewPower(Level pLevel, double pX, double pY, double pZ, @NotNull AbstractTargeter targeter) {
        this(pLevel);
        this.setPos(pX, pY, pZ);
        this.targeter = targeter;
    }
    public NewNewPower(@NotNull Entity entity, @NotNull AbstractTargeter targeter) {
        this(entity.level(), entity.getX() + DEFAULT_X, entity.getY() + DEFAULT_Y, entity.getZ() + DEFAULT_Z, targeter);
        this.setOwner(entity);
    }

    public @Nullable Entity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity living) {
                this.owner = living;
            }
        }
        return this.owner;
    }
    public void setOwner(@Nullable Entity pOwner) {
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    public boolean getEnabled() {
        return this.enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getIsSmart() {
        return this.isSmart;
    }
    public void setIsSmart(boolean isSmart) {
        this.isSmart = isSmart;
    }

    public boolean getEnabledEntityCooldown() {
        return this.enabledEntityCooldown;
    }
    public void setEnabledEntityCooldown(boolean enabledEntityCooldown) {
        this.enabledEntityCooldown = enabledEntityCooldown;
    }

    public int getInterval() {
        return this.interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getEntityCooldown() {
        return this.entityCooldown;
    }
    public void setEntityCooldown(int entityCooldown) {
        this.entityCooldown = entityCooldown;
    }

    public void setRadius(float value) {
        if (this.level().isClientSide()) return;
        this.getEntityData().set(DATA_RADIUS, value);
    }
    public float getRadius() {
        return this.getEntityData().get(DATA_RADIUS);
    }

    public void setHeight(float value) {
        if (this.level().isClientSide()) return;
        this.getEntityData().set(DATA_HEIGHT, value);
    }
    public float getHeight() {
        return this.getEntityData().get(DATA_HEIGHT);
    }

    public void setTargeter(AbstractTargeter targeter) {
        this.targeter = targeter;
    }
    public AbstractTargeter getTargeter() {
        return this.targeter;
    }

    public void addAction(AbstractAction action) {
        this.actions.add(action);
    }
    public void removeAction(AbstractAction action) {
        this.actions.remove(action);
    }
    public void clearActions(AbstractAction action) {
        this.actions.clear();
    }
    public List<AbstractAction> getActions() {
        return List.copyOf(this.actions);
    }

    protected void runEntityCooldown() {
        //迭代实体冷却
        for (Map.Entry<Entity, Integer> entry : this.entityCooldowns.entrySet()) {
            entry.setValue(entry.getValue() - 1);
        }
        //移除所有冷却归0、或死亡的实体
        this.entityCooldowns.entrySet().removeIf(entry ->
                entry.getValue() <= 0 || !entry.getKey().isAlive()
        );
    }

    protected void applyEntityCooldown(Entity entity) {
        this.entityCooldowns.put(entity, this.entityCooldown);
    }

    protected void applyActions(Entity entity) {
        this.applyEntityCooldown(entity);
        for (AbstractAction action : this.actions) {
            action.apply(this, entity, this.isSmart);
        }
    }

    protected void refreshTargets() {
        if (this.targeter != null) this.targets = this.targeter.get(this);
    }

    protected void onUpdate() {
        this.refreshTargets();
        for (Entity entity : this.targets) {
            if (!enabledEntityCooldown || !entityCooldowns.containsKey(entity)) applyActions(entity);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (--this.intervalTimer <= 0) {
            if (this.enabledEntityCooldown) this.runEntityCooldown();
            this.intervalTimer += this.interval;
            this.onUpdate();
        }
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, DEFAULT_RADIUS);
        this.getEntityData().define(DATA_HEIGHT, DEFAULT_HEIGHT);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        if (pCompound.hasUUID("Owner")) this.ownerUUID = pCompound.getUUID("Owner");
        this.enabled = pCompound.getBoolean("Enabled");
        this.isSmart = pCompound.getBoolean("IsSmart");
        this.enabledEntityCooldown = pCompound.getBoolean("EnabledEntityCooldown");
        this.interval = pCompound.getInt("Interval");
        this.entityCooldown = pCompound.getInt("EntityCooldown");
        this.setRadius(pCompound.getFloat("Radius"));
        this.setHeight(pCompound.getFloat("Height"));

        AbstractTargeter newTargeter = ModTargeters.deserialize(pCompound.getCompound("Targeter"));
        if (newTargeter != null) this.targeter = newTargeter;

        this.actions.clear();
        for (var actionTag : pCompound.getList("Actions", 10)) {
            AbstractAction action = ModActions.deserialize((CompoundTag) actionTag);
            if (action != null) this.actions.add(action);
        }
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        if (this.ownerUUID != null) pCompound.putUUID("Owner", this.ownerUUID);
        pCompound.putBoolean("Enabled", this.enabled);
        pCompound.putBoolean("IsSmart", this.isSmart);
        pCompound.putBoolean("EnabledEntityCooldown", this.enabledEntityCooldown);
        pCompound.putInt("Interval", this.interval);
        pCompound.putInt("EntityCooldown", this.entityCooldown);
        pCompound.putFloat("Radius", getRadius());
        pCompound.putFloat("Height", getHeight());

        if (this.targeter != null) pCompound.put("Targeter", this.targeter.serializeNBT());

        ListTag listTag = new ListTag();
        for (var action : this.actions) {
            listTag.add(action.serializeNBT());
        }
        pCompound.put("Actions", listTag);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> pKey) {
        if (DATA_RADIUS.equals(pKey) || DATA_HEIGHT.equals(pKey)) this.refreshDimensions();
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pPose) {
        return EntityDimensions.scalable(this.getRadius() * 2.0F, this.getHeight());
    }

    @Override
    public void refreshDimensions() {
        double x = this.getX();
        double y = this.getY();
        double z = this.getZ();
        super.refreshDimensions();
        this.setPos(x, y, z);
    }
}
