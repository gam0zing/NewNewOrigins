package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.utils.ModTools;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/// 这个类是用于生成数据的工具类
/// 由入口类调用
public class OriginDataProvider implements DataProvider {

    private final PackOutput output;

    public OriginDataProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {

        List<CompletableFuture<?>> futures = new ArrayList<>();
        List<DataSources.OriginData> origins = ModTools.getFieldsAsList(DataInstances.Origins.class, DataSources.OriginData.class);
        List<DataSources.PowerData> powers = ModTools.getFieldsAsList(DataInstances.Powers.class, DataSources.PowerData.class);

        Path path;
        JsonElement jsonElement;

        for (DataSources.OriginData origin : origins) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/origins/" + origin.id() + ".json");
            jsonElement = ModTools.getJsonElement(DataSources.Codecs.CODEC_ORIGIN, origin);
            if (jsonElement != null) {
                futures.add(DataProvider.saveStable(cache, jsonElement, path));
            }
        }

        for (DataSources.PowerData power : powers) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/powers/" + power.id() + ".json");
            jsonElement = ModTools.getJsonElement(DataSources.Codecs.CODEC_POWER, power);
            if (jsonElement != null) {
                futures.add(DataProvider.saveStable(cache, jsonElement, path));
            }
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "NewNewDataProvider" + NewNewOrigins.MODID;
    }
}
