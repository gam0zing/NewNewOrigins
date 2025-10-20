package com.gam0zing.newnew_origins;

import com.gam0zing.newnew_origins.rigistry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(NewNewOrigins.MODID)
public class NewNewOrigins {
    public static final String MODID = "newnew_origins";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NewNewOrigins(FMLJavaModLoadingContext context) {
        IEventBus eventBus = context.getModEventBus();

        ModAttributes.register(eventBus);
        ModCapabilities.register(eventBus);
    }

    /// 获取全称路径
    public static ResourceLocation modLocation(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}