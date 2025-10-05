package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.origins.power.action.ForestElf_SpellRegeneration_Action;
import com.gam0zing.newnew_origins.origins.power.action.configuration.ForestElf_SpellRegeneration_ActionConfiguration;
import com.gam0zing.newnew_origins.rigistry.NewNewBlockActions;
import com.gam0zing.newnew_origins.rigistry.NewNewEntityActions;
import com.gam0zing.newnew_origins.utils.ModTools;
import com.gam0zing.newnew_origins.utils.TagProvider;
import com.mojang.datafixers.types.templates.Tag;
import io.github.apace100.apoli.action.configuration.ExplodeConfiguration;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.origins.registry.ModItems;
import io.github.edwinmindcraft.apoli.api.power.IActivePower;
import io.github.edwinmindcraft.apoli.api.power.PowerData;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.common.action.configuration.BlockConfiguration;
import io.github.edwinmindcraft.apoli.common.action.configuration.ExperienceConfiguration;
import io.github.edwinmindcraft.apoli.common.action.configuration.SpawnEntityConfiguration;
import io.github.edwinmindcraft.apoli.common.action.meta.NothingConfiguration;
import io.github.edwinmindcraft.apoli.common.power.configuration.*;
import io.github.edwinmindcraft.apoli.common.registry.ApoliPowers;
import io.github.edwinmindcraft.apoli.common.registry.action.ApoliEntityActions;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliBlockConditions;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliEntityConditions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.Blocks;

/// 这个类记录了所有数据对象
/// 被数据生成类直接调用
public class DataInstances {

    public static final DataSources.LayerData LAYER_DEFAULT =
            DataSources.LayerData.builder().order(10).replace(true).build();

    public static class Origins {

        public static final DataSources.OriginData FOREST_ELF =
                DataSources.OriginData.builder()
                        .id(ModKeys.ID.ORIGIN_ID_FOREST_ELF)
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF.key())
                        .icon(DataSources.ItemData.builder().fromItemStack(new ItemStack(Items.OAK_SAPLING)).build())
                        .impact(2)
                        .order(2)
                        .unchoosable(false)
                        .powers(
                                ModTools.fullID(Powers.SPELL_REGENERATION.id())
                        )
                        .upgrades(

                        )
                        .build();
        public static final DataSources.OriginData FOREST_ELF_2 =
                DataSources.OriginData.builder()
                        .id(ModKeys.ID.ORIGIN_ID_FOREST_ELF_2)
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF_2.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_2.key())
                        .icon(DataSources.ItemData.builder().fromItemStack(new ItemStack(Items.OAK_SAPLING)).build())
                        .impact(2)
                        .order(2)
                        .unchoosable(true)
                        .powers(

                        )
                        .upgrades(

                        )
                        .build();
        public static final DataSources.OriginData FOREST_ELF_3 =
                DataSources.OriginData.builder()
                        .id(ModKeys.ID.ORIGIN_ID_FOREST_ELF_3)
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF_3.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_3.key())
                        .icon(DataSources.ItemData.builder().fromItemStack(new ItemStack(Items.OAK_SAPLING)).build())
                        .impact(2)
                        .order(2)
                        .unchoosable(true)
                        .powers(
                        )
                        .upgrades(
                        )
                        .build();
    }

    public static class Powers {

        //能力演示：这个能力会使玩家挖掘金块时发生爆炸
        public static DataSources.PowerWithID GOLDEN_EXPLOSION = new DataSources.PowerWithID(
                //第一部分：能力注册名称
                ModKeys.ID.POWER_ID_GOLDEN_EXPLOSION,
                //第二部分：能力本体
                new ConfiguredPower<>(
                        //装配工厂，和结构组件配套使用
                        ApoliPowers.ACTION_ON_BLOCK_BREAK,
                        //结构组件，决定能力的功能
                        //包含条件和行为，以及相关配置
                        //这个是方块破坏相关的结构组件
                        new ActionOnBlockBreakConfiguration(
                                //条件1：方块为金块
                                new Holder.Direct<>(ApoliBlockConditions.BLOCK.get().configure(new BlockConfiguration(Blocks.GOLD_BLOCK))),
                                //行为1：增加经验值2000
                                new Holder.Direct<>(ApoliEntityActions.ADD_EXPERIENCE.get().configure(new ExperienceConfiguration(2000, 0))),
                                //行为2：发生巨大爆炸，范围为半径49格
                                new Holder.Direct<>(NewNewBlockActions.NEW_EXPLODE.get().configure(new ExplodeConfiguration(
                                        49,
                                        Explosion.BlockInteraction.KEEP,
                                        true,
                                        new Holder.Direct<>(ApoliBlockConditions.constant(true)),
                                        new Holder.Direct<>(ApoliBlockConditions.constant(false)),
                                        false
                                ))),
                                //配置1：是否需要符合工具采集条件
                                false
                        ),
                        //暂时没用过这个，不知道有什么用
                        PowerData.builder()
                                .withName(ModKeys.Translatable.POWER_NAME_GOLDEN_EXPLOSION.key())
                                .withDescription(ModKeys.Translatable.POWER_DESCRIPTION_GOLDEN_EXPLOSION.key())
                                .build()
                )
        );

        /// 投掷雪球的方法
        public static DataSources.PowerWithID THROWING_SNOWBALL = new DataSources.PowerWithID(
                ModKeys.ID.POWER_ID_THROWING_SNOWBALL,
                new ConfiguredPower<>(
                        //调用发射弹射物工厂模板
                        ApoliPowers.FIRE_PROJECTILE,
                        //对应结构类
                        new FireProjectileConfiguration(
                                //冷却时间，单位：tick
                                20,
                                //渲染冷却条
                                new HudRender(
                                        //是否渲染
                                        true,
                                        //冷却条在一张美术资源中的位置，这个模组提供的资源从上往下排列，并从0开始编号，所以12代表从上往下第13个样式
                                        12,
                                        //美术资源在文件中的位置
                                        ResourceLocation.fromNamespaceAndPath("origins","textures/gui/community/huang/resource_bar_01.png"),
                                        //显示条件，设定为默认true，即常态显示
                                        new Holder.Direct<>(ApoliEntityConditions.constant(true)),
                                        //冷却条倒转，如果这是一个消耗能量的技能，那么冷却条可以倒转为能量条，仅改变显示模式，不改变技能逻辑，所以冷却型技能设置为true时，依然是冷却型技能，而非充能型技能
                                        false
                                ),
                                //弹射物实体
                                EntityType.SNOWBALL,
                                //总数量
                                3,
                                //发射速度
                                3f,
                                //弹体发散
                                3f,
                                //调用声音
                                SoundEvents.SNOWBALL_THROW,
                                //弹射物带有的tag
                                null,
                                //调用哪个技能按键
                                IActivePower.Key.PRIMARY,
                                //每个弹射物发射的时间间隔，单位：tick
                                3,
                                //发射准备时间，单位：tick
                                0,
                                //发射的弹射物执行的动作
                                new Holder.Direct<>(ApoliEntityActions.NOTHING.get().configure(new NothingConfiguration<>())),
                                //发射者执行的动作
                                new Holder.Direct<>(ApoliEntityActions.NOTHING.get().configure(new NothingConfiguration<>()))
                        ),
                        PowerData.builder()
                                //技能名称，翻译键
                                .withName(ModKeys.Translatable.POWER_NAME_THROWING_SNOWBALL.key())
                                //技能描述，翻译键
                                .withDescription(ModKeys.Translatable.POWER_DESCRIPTION_THROWING_SNOWBALL.key())
                                .build()
                )
        );

        public static final DataSources.PowerWithID SPELL_REGENERATION = new DataSources.PowerWithID(
                ModKeys.ID.POWER_ID_SPELL_REGENERATION,
                new ConfiguredPower<>(
                        ApoliPowers.ACTIVE_SELF,
                        new ActiveSelfConfiguration(
                                100,
                                new HudRender(
                                        true,
                                        9,
                                        ResourceLocation.fromNamespaceAndPath("origins","textures/gui/community/spiderkolo/resource_bar_02.png"),
                                        new Holder.Direct<>(ApoliEntityConditions.constant(true)),
                                        false
                                ),
                                NewNewEntityActions.FOREST_ELF_SPELL_REGENERATION.get().configure(
                                        new ForestElf_SpellRegeneration_ActionConfiguration(
                                                0,
                                                10,
                                                4,
                                                true,
                                                4,
                                                160,
                                                true,
                                                30,
                                                160,
                                                false,
                                                3
                                        )
                                ),
                                IActivePower.Key.PRIMARY
                        ),
                        PowerData.builder()
                                .withName(ModKeys.Translatable.POWER_NAME_SPELL_REGENERATION.key())
                                .withDescription(ModKeys.Translatable.POWER_DESCRIPTION_SPELL_REGENERATION.key())
                                .build()
                )
        );
    }

    public static class Advancements {
        //根节点
        public static final Advancement CONDITION_ROOT =
                Advancement.Builder.advancement()
                        .display(
                                new ItemStack(ModItems.ORB_OF_ORIGIN.get()),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_NAME_ROOT.key()),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_DESCRIPTION_ROOT.key()),
                                null,
                                FrameType.TASK,
                                false,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.ADVANCEMENT_ID_ROOT, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.ADVANCEMENT_ID_ROOT));
        //森精灵 选择起源
        public static final Advancement CONDITION_BECOME_FOREST_ELF =
                Advancement.Builder.advancement()
                        .parent(CONDITION_ROOT)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_NAME_BECOME_FOREST_ELF.key()),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_DESCRIPTION_BECOME_FOREST_ELF.key()),
                                null,
                                FrameType.TASK,
                                false,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.ADVANCEMENT_ID_BECOME_FOREST_ELF, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.ADVANCEMENT_ID_BECOME_FOREST_ELF));
        //森精灵 升到2级
        public static final Advancement CONDITION_UP_TO_FOREST_ELF_2 =
                Advancement.Builder.advancement()
                        .parent(CONDITION_BECOME_FOREST_ELF)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_NAME_UP_TO_FOREST_ELF_2.key()),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_DESCRIPTION_UP_TO_FOREST_ELF_2.key()),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_2, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_2));
        //森精灵 升到3级
        public static final Advancement CONDITION_UP_TO_FOREST_ELF_3 =
                Advancement.Builder.advancement()
                        .parent(CONDITION_UP_TO_FOREST_ELF_2)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_NAME_UP_TO_FOREST_ELF_3.key()),
                                Component.translatable(ModKeys.Translatable.ADVANCEMENT_DESCRIPTION_UP_TO_FOREST_ELF_3.key()),
                                null,
                                FrameType.CHALLENGE,
                                true,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_3, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_3));
    }

}
