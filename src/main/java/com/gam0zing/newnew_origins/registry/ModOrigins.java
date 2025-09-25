package com.gam0zing.newnew_origins.registry;

import com.gam0zing.newnew_origins.NewNewOrigins;
import com.gam0zing.newnew_origins.OriginKeys;
import io.github.apace100.origins.origin.Impact;
import io.github.edwinmindcraft.origins.api.data.PartialOrigin;
import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.registry.OriginsDynamicRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModOrigins {

    public static final DeferredRegister<Origin> MOD_ORIGINS =
            DeferredRegister.create(OriginsDynamicRegistries.ORIGINS_REGISTRY, NewNewOrigins.MODID);

    //#region 注册种族
    public static final RegistryObject<Origin> FOREST_ELF =
            MOD_ORIGINS.register(OriginKeys.ORIGIN_ID_FOREST_ELF, ModOrigins::forestElfProvider);

    //#endregion

    //#region 种族对象的提供方法
    public static Origin forestElfProvider() {
        PartialOrigin partial = PartialOrigin.builder()
                .name(OriginKeys.ORIGIN_NAME_FOREST_ELF)
                .description(OriginKeys.ORIGIN_DESCRIPTION_FOREST_ELF)
                .icon(new ItemStack(Items.OAK_SAPLING))
                .impact(Impact.MEDIUM)
                .order(1)
                .unchoosable(false)
                .powers(
                )
                .upgrades(
                )
                .build();

        return partial.create(ResourceLocation.fromNamespaceAndPath(NewNewOrigins.MODID, OriginKeys.ORIGIN_ID_FOREST_ELF));
    }
    //#endregion
}
