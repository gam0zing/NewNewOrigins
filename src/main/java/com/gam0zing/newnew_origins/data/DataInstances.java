package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.OriginKeys;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Map;

/// 这个类记录了所有数据对象
/// 被数据生成类直接调用
public class DataInstances {

    public static class Origins {
        //森精灵 1级
        public static final DataSources.OriginData FOREST_ELF = new DataSources.OriginData(
                OriginKeys.ID.ORIGIN_ID_FOREST_ELF, OriginKeys.Translatable.ORIGIN_NAME_FOREST_ELF.key(), OriginKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF.key(),
                new ItemStack(Items.OAK_SAPLING), 2, 0, false,
                List.of(
                ),
                List.of(
                        Upgrades.UP_TO_FOREST_ELF_2
                )
        );
        //森精灵 2级
        public static final DataSources.OriginData FOREST_ELF_2 = new DataSources.OriginData(
                OriginKeys.ID.ORIGIN_ID_FOREST_ELF_2, OriginKeys.Translatable.ORIGIN_NAME_FOREST_ELF.key(), OriginKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_2.key(),
                new ItemStack(Items.OAK_SAPLING), 2, 0, true,
                List.of(
                ),
                List.of(
                        Upgrades.UP_TO_FOREST_ELF_3
                )
        );
        //森精灵 3级
        public static final DataSources.OriginData FOREST_ELF_3 = new DataSources.OriginData(
                OriginKeys.ID.ORIGIN_ID_FOREST_ELF_3, OriginKeys.Translatable.ORIGIN_NAME_FOREST_ELF.key(), OriginKeys.Translatable.ORIGIN_DESCRIPTION_FOREST_ELF_3.key(),
                new ItemStack(Items.OAK_SAPLING), 2, 0, true,
                List.of(
                ),
                List.of(
                )
        );
    }

    public static class Powers {

    }

    public static class Upgrades {
        public static final DataSources.UpgradeData UP_TO_FOREST_ELF_2 =
                new DataSources.UpgradeData(OriginKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_2, OriginKeys.ID.ORIGIN_ID_FOREST_ELF_2, OriginKeys.Translatable.CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_2.key());
        public static final DataSources.UpgradeData UP_TO_FOREST_ELF_3 =
                new DataSources.UpgradeData(OriginKeys.ID.CONDITION_ID_UP_TO_FOREST_ELF_3, OriginKeys.ID.ORIGIN_ID_FOREST_ELF_3, OriginKeys.Translatable.CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_3.key());
    }

    public static class Achievements {
        public static final DataSources.AdvancementData FIRST_FOREST_ELF = new DataSources.AdvancementData(
                "first_forest_elf",                                // ID
                "advancement.newneworigins.first_forest_elf.title", // 标题翻译键
                "advancement.newneworigins.first_forest_elf.desc",  // 描述翻译键
                new ItemStack(Items.OAK_SAPLING),                   // 图标
                null,                                               // 父级成就
                Map.of("become_elf", "origins:become_forest_elf"),  // 条件：触发器
                ""                                                  // 奖励（留空）
        );
    }
}
