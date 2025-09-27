package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.utils.ModTools;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.edwinmindcraft.origins.api.data.PartialOrigin;
import io.github.edwinmindcraft.origins.api.origin.ConditionedOrigin;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.Holder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
        JsonElement jsonElement;
        JsonArray jsonArray = null;

        Path path;

        OriginLayer layer = DataInstances.ORIGIN_LAYERS.create(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, ModKeys.ID.LAYER_ID));

        List<DataSources.ID_Holder<PartialOrigin>> origins = ModTools.getHolderFieldsAsList(DataInstances.Origins.class, PartialOrigin.class);
        List<DataSources.TranslateData> translates = ModTools.getFieldsAsList(ModKeys.Translatable.class, DataSources.TranslateData.class);
        List<Advancement> advancements = ModTools.getFieldsAsList(DataInstances.Advancements.class, Advancement.class);

        //生成Layer文件
        path = output.getOutputFolder().resolve("data/origins/origin_layers/origin.json");
        jsonElement = ModTools.getJsonElement(OriginLayer.CODEC, layer);
        if (jsonElement != null && jsonElement.isJsonObject()) {
            jsonArray = jsonElement.getAsJsonObject().get("origins").getAsJsonArray();
            for (DataSources.ID_Holder<PartialOrigin> holder : origins) {
                if (jsonArray != null) jsonArray.add(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, holder.id()).toString());
            }
            futures.add(DataProvider.saveStable(cache, jsonElement, path));
        }

        //生成起源文件
        for (DataSources.ID_Holder<PartialOrigin> holder : origins) {
            Origin origin = holder.object().create(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, holder.id()));
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/origins/" + holder.id() + ".json");
            jsonElement = ModTools.getJsonElement(Origin.CODEC, origin);
            if (jsonElement != null) {
                futures.add(DataProvider.saveStable(cache, ModTools.fixIcon(jsonElement), path));
            }
        }

        //生成进度文件
        for (Advancement advancement : advancements) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/advancements/" + advancement.getId().getPath() + ".json");
            futures.add(DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path));
        }

        //生成翻译文件
        path = output.getOutputFolder().resolve("assets/" + NewNewOrigins.MODID + "/lang/en_us.json");
        JsonObject jsonObject = new JsonObject();
        for (DataSources.TranslateData translate: translates) {
            jsonElement = ModTools.getJsonElement(DataSources.Codecs.CODEC_TRANSLATE, translate);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject transJson = jsonElement.getAsJsonObject();
                for (Map.Entry<String, JsonElement> entry : transJson.entrySet()) {
                    jsonObject.add(entry.getKey(), entry.getValue());
                }
            }
        }
        if (jsonObject.size() > 0) {
            futures.add(DataProvider.saveStable(cache, jsonObject, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "NewNewDataProvider" + NewNewOrigins.MODID;
    }
}
