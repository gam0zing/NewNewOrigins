package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.upgrade.IUpgradeComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModCapabilities {

    public static final Capability<IUpgradeComponent> UPGRADE_COMPONENT = CapabilityManager.get(new CapabilityToken<>() {});

    public static final ResourceLocation UPGRADE_COMPONENT_ID = NewNewOrigins.modLocation("upgrade_component");

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModCapabilities::onRegisterCapabilities);
    }

    private static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IUpgradeComponent.class);
    }
}