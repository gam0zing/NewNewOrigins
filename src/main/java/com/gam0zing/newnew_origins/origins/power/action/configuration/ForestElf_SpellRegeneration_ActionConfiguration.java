package com.gam0zing.newnew_origins.origins.power.action.configuration;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;

public record ForestElf_SpellRegeneration_ActionConfiguration(
        boolean isSmart,                    //是否区分敌我
        int waitTime,                       //生效延迟
        int duration,                       //持续时长，为-1时药水云永不消失
        int entityCooldown,                 //单个实体冷却
        float radius,                       //半径
        float radiusPerTick,                //半径每tick变化量
        boolean useRegeneration,            //启用再生
        int regenerationLevel,              //再生效果等级
        int regenerationDuration,           //再生效果持续时间
        boolean useHunger,                  //启用饥饿
        int hungerLevel,                    //饥饿效果等级
        int hungerDuration,                 //饥饿效果持续时间
        boolean useHarm,                    //启用伤害
        int harmLevel                       //瞬间伤害效果等级
) implements IDynamicFeatureConfiguration {
    public static final Codec<ForestElf_SpellRegeneration_ActionConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("isSmart").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::isSmart),
            Codec.INT.fieldOf("waitTime").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::waitTime),
            Codec.INT.fieldOf("duration").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::duration),
            Codec.INT.fieldOf("entityCooldown").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::entityCooldown),
            Codec.FLOAT.fieldOf("radius").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::radius),
            Codec.FLOAT.fieldOf("radiusPerTick").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::radiusPerTick),
            Codec.BOOL.fieldOf("useRegeneration").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::useRegeneration),
            Codec.INT.fieldOf("regenerationLevel").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::regenerationLevel),
            Codec.INT.fieldOf("regenerationDuration").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::regenerationDuration),
            Codec.BOOL.fieldOf("useHunger").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::useHunger),
            Codec.INT.fieldOf("hungerLevel").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::hungerLevel),
            Codec.INT.fieldOf("hungerDuration").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::hungerDuration),
            Codec.BOOL.fieldOf("useHarm").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::useHarm),
            Codec.INT.fieldOf("harmLevel").forGetter(ForestElf_SpellRegeneration_ActionConfiguration::harmLevel)
    ).apply(instance, ForestElf_SpellRegeneration_ActionConfiguration::new));
}
