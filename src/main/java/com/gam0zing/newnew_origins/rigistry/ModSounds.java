package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.data.DataSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NewNewOrigins.MODID);

    public static final SoundRegistry POWER_SPELL_REGENERATION = registerSoundEvent(ModKeys.ID.SOUND_ID_SPELL_REGENERATION);

    private static SoundRegistry registerSoundEvent(String name) {
        return new SoundRegistry(SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, name))),new DataSources.SoundData(name, List.of(NewNewOrigins.MODID + ":" + name.replace(".", "/"))));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

    public record SoundRegistry(RegistryObject<SoundEvent> value, DataSources.SoundData data) {}
}