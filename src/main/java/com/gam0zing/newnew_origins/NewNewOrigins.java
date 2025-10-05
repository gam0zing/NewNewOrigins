package com.gam0zing.newnew_origins;

import com.gam0zing.newnew_origins.rigistry.NewNewBlockActions;
import com.gam0zing.newnew_origins.rigistry.NewNewEntityActions;
import com.gam0zing.newnew_origins.rigistry.NewNewEntityConditions;
import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NewNewOrigins.MODID)
public class NewNewOrigins {
    public static final String MODID = "newnew_origins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public NewNewOrigins(FMLJavaModLoadingContext context) {
        NewNewBlockActions.register(context.getModEventBus());
        NewNewEntityActions.register(context.getModEventBus());
    }
}