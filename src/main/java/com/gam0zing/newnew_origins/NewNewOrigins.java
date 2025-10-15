package com.gam0zing.newnew_origins;

import com.gam0zing.newnew_origins.rigistry.*;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NewNewOrigins.MODID)
public class NewNewOrigins {
    public static final String MODID = "newnew_origins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NewNewOrigins(FMLJavaModLoadingContext context) {

        ModAttributes.register(context.getModEventBus());
        ModEffects.register(context.getModEventBus());
        ModParticles.register(context.getModEventBus());
        ModSounds.register(context.getModEventBus());
        ModBlockActions.register(context.getModEventBus());
        ModEntityActions.register(context.getModEventBus());
    }
}