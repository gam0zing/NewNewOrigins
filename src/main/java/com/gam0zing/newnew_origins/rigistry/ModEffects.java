package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.ModKeys;
import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.effects.ElfRegenerationEffect;
import com.gam0zing.newnew_origins.effects.ElfWither;
import com.gam0zing.newnew_origins.util.ModTools;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NewNewOrigins.MODID);

    public static final RegistryObject<MobEffect> ELF_REGENERATION = EFFECTS.register(ModKeys.ID.EFFECT_ID_ELF_REGENERATION,
            () -> new ElfRegenerationEffect(MobEffectCategory.BENEFICIAL, ModTools.intColor(120, 210, 90)));
    public static final RegistryObject<MobEffect> ELF_WITHER = EFFECTS.register(ModKeys.ID.EFFECT_ID_ELF_WITHER,
            () -> new ElfWither(MobEffectCategory.HARMFUL, ModTools.intColor(20, 40, 20)));

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
