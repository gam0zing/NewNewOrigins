package com.gam0zing.newnew_origins.entity;

import com.google.common.collect.Lists;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartAreaEffectCloud extends AreaEffectCloud {

    protected Map<Entity, Integer> cooldownEntities = new HashMap<>();
    protected List<MobEffectInstance> effects = new ArrayList<>();
    public int entityCooldown = 20;

    public SmartAreaEffectCloud(EntityType<? extends AreaEffectCloud> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SmartAreaEffectCloud(Level level, double x, double y, double z) {
        super(EntityType.AREA_EFFECT_CLOUD, level);
        this.setPos(x, y, z);
    }

    /// 该方法用于判断相对于某一个特定的生物，某个效果是正面还是负面buff
    protected int isHarmful(LivingEntity livingEntity, MobEffect mobEffect) {

        int ret;

        switch (mobEffect.getCategory()) {
            case BENEFICIAL -> {
                if (livingEntity.isInvertedHealAndHarm() && mobEffect == MobEffects.HEAL) return -1;
                else ret = 1;
            }
            case HARMFUL -> {
                if (livingEntity.isInvertedHealAndHarm() && mobEffect == MobEffects.HARM) return 1;
                else ret = -1;
            }
            default -> {
                ret = 0;
            }
        }

        return ret;
    }

    public void addEffect(@NotNull MobEffectInstance pEffectInstance) {
        super.addEffect(pEffectInstance);
        this.effects.add(pEffectInstance);
    }

    @Override
    public void tick() {
        this.baseTick();

        //判断是否为等待状态
        boolean isWaiting_client = this.isWaiting();
        //获取半径
        float radius = this.getRadius();
        //仅客户端逻辑，粒子渲染
        if (this.level().isClientSide) {
            //在非等待状态时有一半的概率返回
            if (isWaiting_client && this.random.nextBoolean()) {
                return;
            }
            ParticleOptions particleoptions = this.getParticle();
            int square;
            float radius_use;
            //等待时，面积和半径参数恒定；非等待时，面积向上取整
            if (isWaiting_client) {
                square = 2;
                radius_use = 0.2F;
            } else {
                square = Mth.ceil((float)Math.PI * radius * radius);
                radius_use = radius;
            }
            //由药水云面积来决定每Tick的循环次数
            for(int j = 0; j < square; ++j) {

                //-----此处开始为粒子坐标处理-----//
                //弧度随机数，由弧度2PI乘以0~1的随机值
                float random_rad = this.random.nextFloat() * ((float)Math.PI * 2F);
                //半径随机数，由使用半径乘以0~1的随机值的开方
                //粒子基于同心圆环的随机一点生成，为了粒子在面积上的均匀度，需要对随机数进行开方处理
                float random_radius = Mth.sqrt(this.random.nextFloat()) * radius_use;
                //此处决定粒子的位置
                //水平坐标需要经过上面两个随机数的变换，纵坐标则保持不变
                //算出角度和半径修饰后的X坐标
                double x = this.getX() + (double)(Mth.cos(random_rad) * random_radius);
                //算出角度和半径修饰后的Y坐标
                double z = this.getZ() + (double)(Mth.sin(random_rad) * random_radius);
                double y = this.getY();
                //-----此处开始为粒子颜色处理-----//
                double red;
                double green;
                double blue;
                //判断是否为药水粒子：
                //如果是药水粒子，在等待中为白色，或非等待中50%概率是白色，否则为覆盖或药水颜色
                //如果不是药水粒子且在等待状态，则为黑色
                //以上均不满足时，呈现为略带噪声的棕黑色
                if (particleoptions.getType() == ParticleTypes.ENTITY_EFFECT) {
                    int color = isWaiting_client && this.random.nextBoolean() ? 16777215 : this.getColor();
                    red = (double)((float)(color >> 16 & 255) / 255.0F);
                    green = (double)((float)(color >> 8 & 255) / 255.0F);
                    blue = (double)((float)(color & 255) / 255.0F);
                } else if (isWaiting_client) {
                    red = 0.0D;
                    green = 0.0D;
                    blue = 0.0D;
                } else {
                    red = (0.5D - this.random.nextDouble()) * 0.15D;
                    green = (double)0.01F;
                    blue = (0.5D - this.random.nextDouble()) * 0.15D;
                }
                //调用世界粒子生成方法，粒子生成
                this.level().addAlwaysVisibleParticle(particleoptions, x, y, z, red, green, blue);
            }
            //服务端生效部分
        } else {
            //检查持续时长
            if (this.tickCount >= this.getWaitTime() + this.getDuration()) {
                this.discard();
                return;
            }
            //检查是否在等待
            boolean isWaiting_server = this.tickCount < this.getWaitTime();
            //同步客户端和服务端状态
            if (isWaiting_client != isWaiting_server) {
                this.setWaiting(isWaiting_server);
            }
            //如果为等待状态则不生效
            if (isWaiting_server) {
                return;
            }
            //获取当前半径
            if (this.getRadiusPerTick() != 0.0F) {
                radius += this.getRadiusPerTick();
                //当半径小于0.5时，销毁物体
                if (radius < 0.5F) {
                    this.discard();
                    return;
                }
                //更新半径
                this.setRadius(radius);
            }
            //每秒4次
            if (this.tickCount % 5 == 0) {
                //实体独立冷却计算
                this.cooldownEntities.entrySet().removeIf((entityAndCooldown) -> this.tickCount >= entityAndCooldown.getValue());
                //整合所有药水效果
                List<MobEffectInstance> allEffects = Lists.newArrayList();
                for(MobEffectInstance mobeffectinstance : this.getPotion().getEffects()) {
                    allEffects.add(new MobEffectInstance(mobeffectinstance.getEffect(), mobeffectinstance.mapDuration((duration) -> {
                        //滞留型仅有1/4的效果持续时间
                        return duration / 4;
                    }), mobeffectinstance.getAmplifier(), mobeffectinstance.isAmbient(), mobeffectinstance.isVisible()));
                }
                //主效果以外的其他效果持续时间不受影响
                allEffects.addAll(this.effects);
                //如果药水效果为空，清空效果预选对象
                //否则开始应用效果
                if (allEffects.isEmpty()) {
                    this.cooldownEntities.clear();
                } else {
                    //获取药水云碰撞箱内的所有生物
                    List<LivingEntity> livingsInCollider = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
                    if (!livingsInCollider.isEmpty()) {
                        for(LivingEntity livingEntity : livingsInCollider) {
                            //判断生物是否处在冷却中，以及生物是否免疫药水效果
                            if (!this.cooldownEntities.containsKey(livingEntity) && livingEntity.isAffectedByPotions()) {
                                //计算生物和药水云中心点的距离，仅对半径以内的生物生效
                                double distanceX = livingEntity.getX() - this.getX();
                                double distanceZ = livingEntity.getZ() - this.getZ();
                                double distancePower2 = distanceX * distanceX + distanceZ * distanceZ;
                                if (distancePower2 <= (double)(radius * radius)) {
                                    //计入冷却列表
                                    this.cooldownEntities.put(livingEntity, this.tickCount + this.entityCooldown);
                                    //瞬间效果应用方式和持续效果有区别
                                    for(MobEffectInstance effectInstance : allEffects) {
                                        //如果是联机游戏，且正好禁用了PvP，就需要避免对任何玩家或玩家宠物施加负面效果
                                        //如果没有禁用PvP，就仅需要跳过药水云发射者和其宠物
                                        //对于玩家、玩家宠物、玩家傀儡之外的任何生物，仅施加自然效果和负面效果
                                        if (livingEntity.getServer() != null) {
                                            if (
                                                    livingEntity instanceof Player
                                                    || (livingEntity instanceof OwnableEntity pet && pet.getOwner() instanceof Player)
                                                    || (livingEntity instanceof IronGolem ironGolem && ironGolem.isPlayerCreated())
                                                    || (livingEntity instanceof SnowGolem)
                                            ) {
                                                if (this.isHarmful(livingEntity, effectInstance.getEffect()) == -1) continue;
                                            } else {
                                                if (this.isHarmful(livingEntity, effectInstance.getEffect()) == 1) continue;
                                            }
                                        }
                                        else {
                                            if (
                                                    livingEntity == this.getOwner()
                                                    || (livingEntity instanceof OwnableEntity pet && pet.getOwner() == this.getOwner())
                                                    || (livingEntity instanceof IronGolem ironGolem && ironGolem.isPlayerCreated())
                                                    || (livingEntity instanceof SnowGolem)
                                            ) {
                                                if (this.isHarmful(livingEntity, effectInstance.getEffect()) == -1) continue;
                                            } else {
                                                if (this.isHarmful(livingEntity, effectInstance.getEffect()) == 1) continue;
                                            }
                                        }

                                        //应用药水效果，原版实现，未改动
                                        if (effectInstance.getEffect().isInstantenous()) {
                                            effectInstance.getEffect().applyInstantenousEffect(this, this.getOwner(), livingEntity, effectInstance.getAmplifier(), 0.5D);
                                        } else {
                                            livingEntity.addEffect(new MobEffectInstance(effectInstance), this);
                                        }
                                }
                                    //计算半径变化，如果小于0.5则销毁自己
                                    if (this.getRadiusOnUse() != 0.0F) {
                                        radius += this.getRadiusOnUse();
                                        if (radius < 0.5F) {
                                            this.discard();
                                            return;
                                        }
                                        //应用变化后的半径
                                        this.setRadius(radius);
                                    }
                                    //计算持续时间变化，如果小于0则销毁自己
                                    if (this.getDurationOnUse() != 0) {
                                        this.setDuration(this.getDuration() + this.getDurationOnUse());
                                        if (this.getDuration() <= 0) {
                                            this.discard();
                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}