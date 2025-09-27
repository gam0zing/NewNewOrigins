package com.gam0zing.newnew_origins;

import com.gam0zing.newnew_origins.data.DataSources;

/// 命名规则：同一进化链条上的种族，如果是以XXXX_1作为类似ID的，则共用同一个翻译名称
public class ModKeys {

    public static class ID {

        //layer id
        public static final String LAYER_ID = "layer_1";

        //origin id
        public static final String ORIGIN_ID_FOREST_ELF = "forest_elf";
        public static final String ORIGIN_ID_FOREST_ELF_2 = "forest_elf_2";
        public static final String ORIGIN_ID_FOREST_ELF_3 = "forest_elf_3";

        //power id


        //advancement id
        public static final String CONDITION_ID_ROOT = "root";

        public static final String CONDITION_ID_BECOME_FOREST_ELF = "become_forest_elf";
        public static final String CONDITION_ID_UP_TO_FOREST_ELF_2 = "up_to_forest_elf_2";
        public static final String CONDITION_ID_UP_TO_FOREST_ELF_3 = "up_to_forest_elf_3";
    }

    public static class Translatable {
        //origin name
        public static final DataSources.TranslateData ORIGIN_NAME_FOREST_ELF = new DataSources.TranslateData(
                "origin.name.forest_elf",
                "Forest Elf"
        );
        public static final DataSources.TranslateData ORIGIN_NAME_FOREST_ELF_2 = new DataSources.TranslateData(
                "origin.name.forest_elf_2",
                "Forest Elf"
        );
        public static final DataSources.TranslateData ORIGIN_NAME_FOREST_ELF_3 = new DataSources.TranslateData(
                "origin.name.forest_elf_3",
                "Forest Elf"
        );

        //origin description
        public static final DataSources.TranslateData ORIGIN_DESCRIPTION_FOREST_ELF = new DataSources.TranslateData(
                "origin.description.forest_elf",
                "A vibrant race, naturally adept at magic and nimble but slender. Recovers energy from sunlight by day; gains enhanced agility at night. Prefers sleeping high in tall trees. Key weakness: extreme susceptibility to burns."
        );
        public static final DataSources.TranslateData ORIGIN_DESCRIPTION_FOREST_ELF_2 = new DataSources.TranslateData(
                "origin.description.forest_elf_2",
                ORIGIN_DESCRIPTION_FOREST_ELF.value() + "\n" + "  Life Blessing Upgrade: \nNow can invoke [Spring], a more powerful area healing spell."
        );
        public static final DataSources.TranslateData ORIGIN_DESCRIPTION_FOREST_ELF_3 = new DataSources.TranslateData(
                "origin.description.forest_elf_3",
                ORIGIN_DESCRIPTION_FOREST_ELF_2.value() + "\n" + "  Withering World Upgrade: \nSpell [Regeneration] "
        );

        //power name


        //power description


        //upgrade info
        public static final DataSources.TranslateData UPGRADE_TO_FOREST_ELF_2 = new DataSources.TranslateData(
                "origin.upgrade_to.forest_elf_2",
                "Your origin has been upgraded just now."
        );
        public static final DataSources.TranslateData UPGRADE_TO_FOREST_ELF_3 = new DataSources.TranslateData(
                "origin.upgrade_to.forest_elf_3",
                "Your origin has been upgraded just now."
        );

        //advancement name
        public static final DataSources.TranslateData CONDITION_NAME_ROOT = new DataSources.TranslateData(
                "condition.name.root",
                "Welcome to NewNewWorld"
        );

        public static final DataSources.TranslateData CONDITION_NAME_BECOME_FOREST_ELF = new DataSources.TranslateData(
                "condition.name.become_forest_elf",
                "Become A Forest Elf"
        );
        public static final DataSources.TranslateData CONDITION_NAME_UP_TO_FOREST_ELF_2 = new DataSources.TranslateData(
                "condition.name.up_to_forest_elf_2",
                "Life Blessing"
        );
        public static final DataSources.TranslateData CONDITION_NAME_UP_TO_FOREST_ELF_3 = new DataSources.TranslateData(
                "condition.name.up_to_forest_elf_3",
                "Withering World"
        );

        //advancement description
        public static final DataSources.TranslateData CONDITION_DESCRIPTION_ROOT = new DataSources.TranslateData(
                "condition.description.root",
                "View more info about your origin in advancements."
        );

        public static final DataSources.TranslateData CONDITION_DESCRIPTION_BECOME_FOREST_ELF = new DataSources.TranslateData(
                "condition.description.become_forest_elf",
                ""
        );
        public static final DataSources.TranslateData CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_2 = new DataSources.TranslateData(
                "condition.description.up_to_forest_elf_2",
                ""  //进度描述：方便玩家快速查看升级方法，需要写清楚
        );
        public static final DataSources.TranslateData CONDITION_DESCRIPTION_UP_TO_FOREST_ELF_3 = new DataSources.TranslateData(
                "condition.description.up_to_forest_elf_3",
                ""
        );
    }
}
