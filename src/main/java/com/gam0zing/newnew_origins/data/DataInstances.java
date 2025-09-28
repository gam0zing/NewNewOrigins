package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import io.github.apace100.origins.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

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

    }

    public static class Upgrades {

    }

    public static class Advancements {
        //根节点
        public static final Advancement CONDITION_ROOT =
                Advancement.Builder.advancement()
                        .display(
                                new ItemStack(ModItems.ORB_OF_ORIGIN.get()),
                                Component.translatable(ModKeys.Translatable.CONDITION_NAME_ROOT.key()),
                                Component.translatable(ModKeys.Translatable.CONDITION_DESCRIPTION_ROOT.key()),
                                null,
                                FrameType.TASK,
                                false,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.CONDITION_ID_ROOT, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.CONDITION_ID_ROOT));
        //森精灵 选择起源
        public static final Advancement CONDITION_BECOME_FOREST_ELF =
                Advancement.Builder.advancement()
                        .parent(CONDITION_ROOT)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.CONDITION_NAME_BECOME_FOREST_ELF.key()),
                                Component.translatable(ModKeys.Translatable.CONDITION_DESCRIPTION_BECOME_FOREST_ELF.key()),
                                null,
                                FrameType.TASK,
                                false,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.CONDITION_ID_BECOME_FOREST_ELF, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.CONDITION_ID_BECOME_FOREST_ELF));
        //森精灵 升到2级
        public static final Advancement CONDITION_UP_TO_FOREST_ELF_2 =
                Advancement.Builder.advancement()
                        .parent(CONDITION_BECOME_FOREST_ELF)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.CONDITION_NAME_UP_TO_FOREST_ELF_2.key()),
                                Component.translatable(ModKeys.Translatable.CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_2.key()),
                                null,
                                FrameType.GOAL,
                                true,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_2, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_2));
        //森精灵 升到3级
        public static final Advancement CONDITION_UP_TO_FOREST_ELF_3 =
                Advancement.Builder.advancement()
                        .parent(CONDITION_UP_TO_FOREST_ELF_2)
                        .display(
                                new ItemStack(Items.OAK_SAPLING),
                                Component.translatable(ModKeys.Translatable.CONDITION_NAME_UP_TO_FOREST_ELF_3.key()),
                                Component.translatable(ModKeys.Translatable.CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_3.key()),
                                null,
                                FrameType.CHALLENGE,
                                true,
                                true,
                                false
                        )
                        .addCriterion(ModKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_3, new Criterion(new ImpossibleTrigger.TriggerInstance()))
                        .build(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_3));
    }

}
