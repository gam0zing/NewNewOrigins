package com.gam0zing.newnew_origins.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

/// 这个类存储了所有json的数据格式
/// 用于Provider中进行对象创建
public class DataSources {

    public record LayerData(
            int order,
            boolean replace,
            List<String> origins
    ) {
        public LayerData {
            origins = List.copyOf(origins);
        }
        public static class Builder {
            private int order = 0;
            private boolean replace = false;
            private final List<String> origins = new ArrayList<>();

            public Builder order(int value) {
                order = value;
                return this;
            }

            public Builder replace(boolean value) {
                replace = value;
                return this;
            }

            public Builder origins(String... values) {
                Collections.addAll(origins, values);
                return this;
            }

            public Builder addOrigin(String value) {
                Collections.addAll(origins, value);
                return this;
            }

            public Builder clearOrigins() {
                origins.clear();
                return this;
            }

            public LayerData build() {
                return new LayerData(order, replace, List.copyOf(origins));
            }
        }
        public Builder toBuilder() {
            return new Builder()
                    .order(order)
                    .replace(replace)
                    .origins(origins.toArray(String[]::new));
        }
        public static Builder builder() {
            return new Builder();
        }
    }

    public record OriginData(
            String id,
            String name,
            String description,
            ItemData icon,
            int impact,
            int order,
            boolean unchoosable,
            List<String> powers,
            List<UpgradeData> upgrades
    ) {
        public OriginData {
            powers = List.copyOf(powers);
            upgrades = List.copyOf(upgrades);
        }
        public static class Builder {
            private String id = "undefined";
            private String name = "undefined";
            private String description = "undefined";
            private ItemData icon = ItemData.builder().fromItemStack(new ItemStack(Items.COMMAND_BLOCK)).build();
            private int impact = 0;
            private int order = 0;
            private boolean unchoosable = false;
            private final List<String> powers = new ArrayList<>();
            private final List<UpgradeData> upgrades = new ArrayList<>();

            public Builder id(String value) {
                id = value;
                return this;
            }

            public Builder name(String value) {
                name = value;
                return this;
            }

            public Builder description(String value) {
                description = value;
                return this;
            }

            public Builder icon(ItemData value) {
                icon = value;
                return this;
            }

            public Builder impact(int value) {
                impact = value;
                return this;
            }

            public Builder order(int value) {
                order = value;
                return this;
            }

            public Builder unchoosable(boolean value) {
                unchoosable = value;
                return this;
            }

            public Builder powers(String... values) {
                Collections.addAll(powers, values);
                return this;
            }

            public Builder upgrades(UpgradeData... values) {
                Collections.addAll(upgrades, values);
                return this;
            }

            public OriginData build() {
                return new OriginData(id, name, description, icon, impact, order,
                        unchoosable, List.copyOf(powers), List.copyOf(upgrades));
            }
        }
        public static Builder builder() {
            return new Builder();
        }
        public Builder toBuilder() {
            return new Builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .icon(icon)
                    .impact(impact)
                    .order(order)
                    .unchoosable(unchoosable)
                    .powers(powers.toArray(String[]::new))
                    .upgrades(upgrades.toArray(UpgradeData[]::new));
        }
    }

    public record PowerData(
            String id,
            String name,
            String description
    ) {}

    public record UpgradeData(
            String condition,
            String origin,
            String announcement
    ) {
        public static class Builder {
            private String condition = "undefined";
            private String origin = "undefined";
            private String announcement = "undefined";

            public Builder condition(String value) {
                condition = value;
                return this;
            }

            public Builder origin(String value) {
                origin = value;
                return this;
            }

            public Builder announcement(String value) {
                announcement = value;
                return this;
            }

            public UpgradeData build() {
                return new UpgradeData(condition, origin, announcement);
            }
        }
        public Builder toBuilder() {
            return new Builder()
                    .condition(condition)
                    .origin(origin)
                    .announcement(announcement);
        }
        public static Builder builder() {
            return new Builder();
        }
    }

    public record TranslateData(
            String key,
            String value
    ) {}

    public record ItemData(
            String item,
            int count,
            CompoundTag tag
    ) {
        public ItemData {
            if (count < 1) {
                throw new IllegalArgumentException("count must be positive");
            }
        }
        public static class Builder {
            private String item = "minecraft:command_block";
            private int count = 1;
            private CompoundTag tag = null;

            public Builder item(String value) {
                item = value;
                return this;
            }

            public Builder item(ResourceLocation value) {
                item = value.toString();
                return this;
            }

            public Builder fromItemStack(ItemStack value) {
                ResourceLocation rl = ForgeRegistries.ITEMS.getKey(value.getItem());
                if (rl == null) return this;
                item = rl.toString();
                count = value.getCount();
                tag = value.getTag();
                return this;
            }

            public Builder count(int value) {
                count = value;
                return this;
            }

            public Builder tag(CompoundTag value) {
                tag = value;
                return this;
            }

            public ItemData build() {
                return new ItemData(item, count, tag);
            }
        }
        public Builder toBuilder() {
            return new Builder()
                    .item(item)
                    .count(count)
                    .tag(tag);
        }
        public static Builder builder() {
            return new Builder();
        }
    }

    /// 每个记录类的CODEC，用于读写JSON
    public static class Codecs {
        public static final Codec<LayerData> CODEC_LAYER = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("order").forGetter(LayerData::order),
                Codec.BOOL.fieldOf("replace").forGetter(LayerData::replace),
                Codec.STRING.listOf().fieldOf("origins").forGetter(LayerData::origins)
        ).apply(instance, LayerData::new));

        public static final Codec<ItemData> CODEC_ITEM = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("item").forGetter(ItemData::item),
                Codec.INT.fieldOf("count").forGetter(ItemData::count),
                CompoundTag.CODEC.fieldOf("tag").forGetter(ItemData::tag)
        ).apply(instance, ItemData::new));

        public static final Codec<UpgradeData> CODEC_UPGRADE = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("condition").forGetter(UpgradeData::condition),
                Codec.STRING.fieldOf("origin").forGetter(UpgradeData::origin),
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
                CODEC_ITEM.fieldOf("icon").forGetter(OriginData::icon),
                Codec.INT.fieldOf("impact").forGetter(OriginData::impact),
                Codec.INT.fieldOf("order").forGetter(OriginData::order),
                Codec.BOOL.fieldOf("unchoosable").forGetter(OriginData::unchoosable),
                Codec.STRING.listOf().fieldOf("powers").forGetter(OriginData::powers),
                CODEC_UPGRADE.listOf().fieldOf("upgrades").forGetter(OriginData::upgrades)
        ).apply(instance, OriginData::new));

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