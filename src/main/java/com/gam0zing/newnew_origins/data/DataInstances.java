package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.origins.power.action.configuration.ForestElf_SpellRegeneration_ActionConfiguration;
import com.gam0zing.newnew_origins.rigistry.ModEntityActions;
import com.gam0zing.newnew_origins.util.ModTools;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.origins.registry.ModItems;
import io.github.edwinmindcraft.apoli.api.power.IActivePower;
import io.github.edwinmindcraft.apoli.api.power.PowerData;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.common.power.configuration.*;
import io.github.edwinmindcraft.apoli.common.registry.ApoliPowers;
import io.github.edwinmindcraft.apoli.common.registry.condition.ApoliEntityConditions;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.core.Holder;
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
                                ModTools.fullID(Powers.SPELL_REGENERATION.id())
                        )
                        .upgrades(
                                Upgrades.UP_TO_FOREST_ELF_2
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
                        .unchoosable(false)
                        .powers(
                                ModTools.fullID(Powers.SPELL_REGENERATION.id())
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
                        .unchoosable(false)
                        .powers(
                                ModTools.fullID(Powers.SPELL_REGENERATION_PLUS.id())
                        )
                        .upgrades(
                                Upgrades.UP_TO_FOREST_ELF_3
                        )
                        .build();
    }

    public static class Powers {
        //森精灵 再生术
        public static final DataSources.PowerWithID SPELL_REGENERATION = new DataSources.PowerWithID(
                ModKeys.ID.POWER_ID_SPELL_REGENERATION,
                new ConfiguredPower<>(
                        ApoliPowers.ACTIVE_SELF,
                        new ActiveSelfConfiguration(
                                800,
                                new HudRender(
                                        true,
                                        22,
                                        ResourceLocation.fromNamespaceAndPath("origins","textures/gui/community/spiderkolo/resource_bar_02.png"),
                                        new Holder.Direct<>(ApoliEntityConditions.constant(true)),
                                        false
                                ),
                                ModEntityActions.FOREST_ELF_SPELL_REGENERATION.get().configure(
                                        new ForestElf_SpellRegeneration_ActionConfiguration(
                                                false,
                                                0,
                                                20,
                                                999,
                                                1,
                                                0.15f,
                                                true,
                                                3,
                                                160,
                                                true,
                                                30,
                                                160,
                                                false,
                                                1
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

        public static final DataSources.PowerWithID SPELL_REGENERATION_PLUS = new DataSources.PowerWithID(
                ModKeys.ID.POWER_ID_SPELL_REGENERATION_PLUS,
                new ConfiguredPower<>(
                        ApoliPowers.ACTIVE_SELF,
                        new ActiveSelfConfiguration(
                                500,
                                new HudRender(
                                        true,
                                        22,
                                        ResourceLocation.fromNamespaceAndPath("origins","textures/gui/community/spiderkolo/resource_bar_02.png"),
                                        new Holder.Direct<>(ApoliEntityConditions.constant(true)),
                                        false
                                ),
                                ModEntityActions.FOREST_ELF_SPELL_REGENERATION.get().configure(
                                        new ForestElf_SpellRegeneration_ActionConfiguration(
                                                true,
                                                0,
                                                20,
                                                999,
                                                1,
                                                0.35f,
                                                true,
                                                4,
                                                160,
                                                false,
                                                30,
                                                160,
                                                true,
                                                48
                                        )
                                ),
                                IActivePower.Key.PRIMARY
                        ),
                        PowerData.builder()
                                .withName(ModKeys.Translatable.POWER_NAME_SPELL_REGENERATION_PLUS.key())
                                .withDescription(ModKeys.Translatable.POWER_DESCRIPTION_SPELL_REGENERATION_PLUS.key())
                                .build()
                )
        );
    }

    public static class Upgrades {

        public static final DataSources.UpgradeData UP_TO_FOREST_ELF_2 = DataSources.UpgradeData.builder()
                .origin(ModTools.fullID(ModKeys.ID.ORIGIN_ID_FOREST_ELF_2))
                .condition(ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_2)
                .announcement(ModKeys.Translatable.UPGRADE_TO_FOREST_ELF_2.key())
                .build();

        public static final DataSources.UpgradeData UP_TO_FOREST_ELF_3 = DataSources.UpgradeData.builder()
                .origin(ModTools.fullID(ModKeys.ID.ORIGIN_ID_FOREST_ELF_3))
                .condition(ModKeys.ID.ADVANCEMENT_ID_UP_TO_FOREST_ELF_3)
                .announcement(ModKeys.Translatable.UPGRADE_TO_FOREST_ELF_3.key())
                .build();
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
