package com.gam0zing.newnew_origins.data;


import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

/// 这个类存储了所有json的数据格式
/// 用于Provider中进行对象创建
public class DataSources {

    public record OriginData(
            String id,
            String name,
            String description,
            ItemStack iconItem,
            int impact,
            int order,
            boolean unchoosable,
            List<String> powers,
            List<UpgradeData> upgrades
    ) {}

    public record PowerData(
            String id,
            String name,
            String description
    ) {}

    public record UpgradeData(
            String condition,               //这个是进度ID，当这个进度达成时，就会升级
            String targetOrigin,            //要升级到的另一个起源的ID
            String announcement             //当升级事件发生时，将在聊天框发送消息 #####这里有点问题，升级消息会播报两次，可能Origins没做双端检测#####
    ) {}

    public record TranslateData(
            String key,
            String value
    ) {}

    public record AdvancementData(
            String id,                // 成就 ID（文件名用）
            String title,             // 标题翻译键
            String description,       // 描述翻译键
            ItemStack icon,           // 成就图标
            String parent,            // 父级成就 ID（可以为 null）
            Map<String, String> criteria, // 触发条件（key=条件名, value=触发类型）
            String reward             // 奖励描述（可选，比如指令、物品）
    ) {}

    /// 每个记录类的CODEC，用于读写JSON
    public static class Codecs {
        public static final Codec<UpgradeData> CODEC_UPGRADE = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("condition").forGetter(UpgradeData::condition),
                Codec.STRING.fieldOf("targetOrigin").forGetter(UpgradeData::targetOrigin),
                Codec.STRING.fieldOf("announcement").forGetter(UpgradeData::announcement)
        ).apply(instance, UpgradeData::new));

        public static final Codec<PowerData> CODEC_POWER = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("id").forGetter(PowerData::id),
                Codec.STRING.fieldOf("name").forGetter(PowerData::name),
                Codec.STRING.fieldOf("description").forGetter(PowerData::description)
        ).apply(instance, PowerData::new));

        public static final Codec<OriginData> CODEC_ORIGIN = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("id").forGetter(OriginData::id),
                Codec.STRING.fieldOf("name").forGetter(OriginData::name),
                Codec.STRING.fieldOf("description").forGetter(OriginData::description),
                ItemStack.CODEC.fieldOf("iconItem").forGetter(OriginData::iconItem),
                Codec.INT.fieldOf("impact").forGetter(OriginData::impact),
                Codec.INT.fieldOf("order").forGetter(OriginData::order),
                Codec.BOOL.fieldOf("unchoosable").forGetter(OriginData::unchoosable),
                Codec.STRING.listOf().fieldOf("powers").forGetter(OriginData::powers),
                CODEC_UPGRADE.listOf().fieldOf("upgrades").forGetter(OriginData::upgrades)
        ).apply(instance, OriginData::new));

        public static final Codec<AdvancementData> CODEC_ADVANCEMENT = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("id").forGetter(AdvancementData::id),
                Codec.STRING.fieldOf("title").forGetter(AdvancementData::title),
                Codec.STRING.fieldOf("description").forGetter(AdvancementData::description),
                ItemStack.CODEC.fieldOf("icon").forGetter(AdvancementData::icon),
                Codec.STRING.optionalFieldOf("parent", "").forGetter(AdvancementData::parent),
                Codec.unboundedMap(Codec.STRING, Codec.STRING).fieldOf("criteria").forGetter(AdvancementData::criteria),
                Codec.STRING.optionalFieldOf("reward", "").forGetter(AdvancementData::reward)
        ).apply(instance, AdvancementData::new));

        public static final Codec<TranslateData> CODEC_TRANSLATE = Codec.unboundedMap(Codec.STRING, Codec.STRING)
                .comapFlatMap(
                        map -> {
                            if (map.size() == 1) {
                                Map.Entry<String, String> entry = map.entrySet().iterator().next();
                                TranslateData data = new TranslateData(entry.getKey(), entry.getValue());
                                return DataResult.success(data);
                            } else {
                                return DataResult.error(() -> "Expected exactly one key-value pair");
                            }
                        },
                        translateData -> Map.of(translateData.key(), translateData.value())
                );

    }
}