package com.gam0zing.newnew_origins;

import com.gam0zing.newnew_origins.registry.ModOrigins;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NewNewOrigins.MODID)
public class NewNewOrigins {
    public static final String MODID = "newnew_origins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NewNewOrigins(FMLJavaModLoadingContext context) {

        ModOrigins.MOD_ORIGINS.register(context.getModEventBus());
    }
}