package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.rigistry.NewNewBlockActions;
import com.gam0zing.newnew_origins.utils.ModTools;
import io.github.apace100.apoli.action.configuration.ExplodeConfiguration;
import io.github.apace100.apoli.data.DamageSourceDescription;
import io.github.apace100.apoli.power.factory.condition.ItemConditions;
import io.github.apace100.origins.power.OriginsEntityConditions;
import io.github.apace100.origins.registry.ModItems;
import io.github.edwinmindcraft.apoli.api.configuration.NoConfiguration;
import io.github.edwinmindcraft.apoli.api.power.PowerData;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.ItemCondition;
import io.github.edwinmindcraft.apoli.common.action.configuration.BlockConfiguration;
import io.github.edwinmindcraft.apoli.common.action.configuration.DamageConfiguration;
import io.github.edwinmindcraft.apoli.common.action.configuration.DamageItemConfiguration;
import io.github.edwinmindcraft.apoli.common.action.configuration.ExperienceConfiguration;
import io.github.edwinmindcraft.apoli.common.power.configuration.ActionOnBlockBreakConfiguration;
import io.github.edwinmindcraft.apoli.common.power.configuration.ActionOnItemUseConfiguration;
import io.github.edwinmindcraft.apoli.common.registry.ApoliPowers;
import io.github.edwinmindcraft.apoli.common.registry.action.ApoliEntityActions;
import io.github.edwinmindcraft.apoli.common.registry.action.ApoliItemActions;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliBlockConditions;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliEntityConditions;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliItemConditions;
import io.github.edwinmindcraft.origins.common.condition.configuration.OriginConfiguration;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
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
                                ModTools.fullID(Powers.GOLDEN_EXPLOSION.id())
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
                        PowerData.DEFAULT
                )
        );

        /// 未完成的
        /// 想知道什么作用就看变量和类的名字
        /// 大概作用是在使用任何物品时对自己和物品持续造成伤害
        /// ！！！！！重要：Apoli模组（起源前置之一，提供了能力的实现框架）抽象工厂模式的运作方式，需要你去问AI，你可以问他需要哪些代码来搞清楚功能，先发一点源码去问，然后你再按提示把剩下的代码发给他，弄懂了这个就比较好写，建议问阿里千问，别问deepseek或者gpt
/*        public static DataSources.PowerWithID GOLDEN_ENTITY_EXPLOSION = new DataSources.PowerWithID(
                ModKeys.ID.POWER_ID_GOLDEN_EXPLOSION,
                new ConfiguredPower<>(
                        ApoliPowers.ACTION_ON_ITEM_USE,
                        new ActionOnItemUseConfiguration(
                                new Holder.Direct<>(ApoliItemConditions.constant(true)),
                                new Holder.Direct<>(ApoliEntityActions.DAMAGE.get().configure(new DamageConfiguration(
                                        DamageTypes.MAGIC,
                                        new DamageSourceDescription(
                                                //查源码
                                        ),
                                        1
                                ))),
                                new Holder.Direct<>(ApoliItemActions.DAMAGE.get().configure(new DamageItemConfiguration(
                                        1,
                                        true
                                ))),
                                ActionOnItemUseConfiguration.TriggerType.DURING,
                                1
                        ),
                        PowerData.DEFAULT
                )
        );*/
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
