package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.utils.ModTools;
import io.github.apace100.origins.origin.Impact;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModItems;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.origins.api.data.PartialLayer;
import io.github.edwinmindcraft.origins.api.data.PartialOrigin;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/// 这个类记录了所有数据对象
/// 被数据生成类直接调用
public class DataInstances {

    public static final PartialLayer ORIGIN_LAYERS =
            PartialLayer.builder()
                    .order(10)
                    .replace(false)
                    .origins(
                            Set.of(

                            )
                    )
                    .build();

    public static class Origins {

        public static final DataSources.ID_Holder<PartialOrigin> FOREST_ELF = new DataSources.ID_Holder<>(
                ModKeys.ID.ORIGIN_ID_FOREST_ELF,
                PartialOrigin.builder()
                        .unchoosable(false)
                        .order(10)
                        .impact(Impact.MEDIUM)
                        .icon(new ItemStack(Items.OAK_SAPLING))
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF.key())
                        .build()
        );

        public static final DataSources.ID_Holder<PartialOrigin> FOREST_ELF_2 = new DataSources.ID_Holder<>(
                ModKeys.ID.ORIGIN_ID_FOREST_ELF_2,
                PartialOrigin.builder()
                        .unchoosable(false)
                        .order(10)
                        .impact(Impact.MEDIUM)
                        .icon(new ItemStack(Items.OAK_SAPLING))
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF_2.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_2.key())
                        .build()
        );

        public static final DataSources.ID_Holder<PartialOrigin> FOREST_ELF_3 = new DataSources.ID_Holder<>(
                ModKeys.ID.ORIGIN_ID_FOREST_ELF_3,
                PartialOrigin.builder()
                        .unchoosable(false)
                        .order(10)
                        .impact(Impact.MEDIUM)
                        .icon(new ItemStack(Items.OAK_SAPLING))
                        .name(ModKeys.Translatable.ORIGIN_NAME_FOREST_ELF_3.key())
                        .description(ModKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_3.key())
                        .build()
        );
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
