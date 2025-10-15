package com.gam0zing.newnew_origins.data;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.rigistry.ModParticles;
import com.gam0zing.newnew_origins.rigistry.ModSounds;
import com.gam0zing.newnew_origins.util.ModTools;
import com.google.gson.JsonElement;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/// 这个类是用于生成数据的工具类
/// 由入口类调用
public class NewNewDataProvider implements DataProvider {

    private final PackOutput output;

    public NewNewDataProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {

        List<CompletableFuture<?>> futures = new ArrayList<>();
        Path path;
        JsonElement jsonElement;

        DataSources.LayerData.Builder layer = DataInstances.LAYER_DEFAULT.toBuilder();

        List<DataSources.OriginData> origins = ModTools.getFieldsAsList(DataInstances.Origins.class, DataSources.OriginData.class);
        List<DataSources.PowerWithID> powers = ModTools.getFieldsAsList(DataInstances.Powers.class, DataSources.PowerWithID.class);
        List<Advancement> advancements = ModTools.getFieldsAsList(DataInstances.Advancements.class, Advancement.class);
        List<DataSources.TranslateData> translates = ModTools.getFieldsAsList(ModKeys.Translatable.class, DataSources.TranslateData.class);
        List<ModSounds.SoundRegistry> sounds = ModTools.getFieldsAsList(ModSounds.class, ModSounds.SoundRegistry.class);
        List<ModParticles.ParticleRegistry> particles = ModTools.getFieldsAsList(ModParticles.class, ModParticles.ParticleRegistry.class);

        for (DataSources.OriginData origin : origins) {
            layer.addOrigin(NewNewOrigins.MODID + ":" + origin.id());
        }
        path = output.getOutputFolder().resolve("data/origins/origin_layers/origin.json");
        jsonElement = ModTools.getJsonElement(DataSources.LayerData.CODEC, layer.build());
        if (jsonElement != null) {
            futures.add(DataProvider.saveStable(cache, jsonElement, path));
        }

        for (DataSources.OriginData origin : origins) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/origins/" + origin.id() + ".json");
            jsonElement = ModTools.getJsonElement(DataSources.OriginData.CODEC, origin);
            if (jsonElement != null) {
                futures.add(DataProvider.saveStable(cache, jsonElement, path));
            }
        }

        for (DataSources.PowerWithID power : powers) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/powers/" + power.id() + ".json");
            jsonElement = ModTools.getJsonElement(ConfiguredPower.CODEC, power.power());
            if (jsonElement != null) {
                futures.add(DataProvider.saveStable(cache, jsonElement, path));
            }
        }

        for (Advancement advancement : advancements) {
            path = output.getOutputFolder().resolve("data/" + NewNewOrigins.MODID + "/advancements/" + advancement.getId().getPath() + ".json");
            futures.add(DataProvider.saveStable(cache, advancement.deconstruct().serializeToJson(), path));
        }

        path = output.getOutputFolder().resolve("assets/" + NewNewOrigins.MODID + "/lang/en_us.json");
        Map<String, String> transMap = new HashMap<>();
        for (DataSources.TranslateData translate : translates) {
            transMap.put(translate.key(), translate.value());
        }
        jsonElement = ModTools.getJsonElement(DataSources.TranslateData.CODEC_MAP, transMap);
        futures.add(DataProvider.saveStable(cache, jsonElement, path));

        path = output.getOutputFolder().resolve("assets/" + NewNewOrigins.MODID + "/sounds.json");
        Map<String, DataSources.SoundData.Sounds> soundsMap = new HashMap<>();
        for (ModSounds.SoundRegistry sound : sounds) {
             soundsMap.put(sound.data().entry().getKey(), sound.data().entry().getValue());
        }
        jsonElement = ModTools.getJsonElement(DataSources.SoundData.CODEC_MAP, soundsMap);
        futures.add(DataProvider.saveStable(cache, jsonElement, path));

        for (ModParticles.ParticleRegistry particle : particles) {
            path = output.getOutputFolder().resolve("assets/" + NewNewOrigins.MODID + "/particles/" + particle.data().name() + ".json");
            jsonElement = ModTools.getJsonElement(DataSources.ParticleData.Particles.CODEC, particle.data().particles());
            futures.add(DataProvider.saveStable(cache, jsonElement, path));
        }

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "NewNewDataProvider" + NewNewOrigins.MODID;
    }
}
