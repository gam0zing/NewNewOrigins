package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.entity.power_actions.AbstractAction;
import com.gam0zing.newnew_origins.entity.power_actions.DamageAction;
import com.gam0zing.newnew_origins.entity.power_actions.EffectAction;
import com.gam0zing.newnew_origins.entity.power_actions.HealAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModActions {
    private static final Map<ResourceLocation, Supplier<AbstractAction>> ACTIONS = new HashMap<>();

    public static void register(ResourceLocation id, Supplier<AbstractAction> constructor) {
        ACTIONS.put(id, constructor);
    }

    static {
        register(DamageAction.ID, DamageAction::new);
        register(HealAction.ID, HealAction::new);
        register(EffectAction.ID, EffectAction::new);
    }

    @Nullable
    public static AbstractAction deserialize(CompoundTag nbt) {
        ResourceLocation id = ResourceLocation.parse(nbt.getString("Id"));
        Supplier<AbstractAction> constructor = ACTIONS.get(id);
        if (constructor != null) {
            AbstractAction action = constructor.get();
            action.deserializeNBT(nbt);
            return action;
        }
        return null;
    }
}
