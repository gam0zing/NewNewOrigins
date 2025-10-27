package com.gam0zing.newnew_origins.rigistry;

import com.gam0zing.newnew_origins.entity.AbstractTargeter;
import com.gam0zing.newnew_origins.entity.CollisionTargeter;
import com.gam0zing.newnew_origins.entity.power_actions.AbstractAction;
import com.gam0zing.newnew_origins.entity.power_actions.DamageAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ModTargeters {
    private static final Map<ResourceLocation, Supplier<AbstractTargeter>> TARGETERS = new HashMap<>();

    public static void register(ResourceLocation id, Supplier<AbstractTargeter> constructor) {
        TARGETERS.put(id, constructor);
    }

    static {
        register(CollisionTargeter.ID, CollisionTargeter::new);
    }

    @Nullable
    public static AbstractTargeter deserialize(CompoundTag nbt) {
        ResourceLocation id = ResourceLocation.parse(nbt.getString("Id"));
        Supplier<AbstractTargeter> constructor = TARGETERS.get(id);
        if (constructor != null) {
            AbstractTargeter action = constructor.get();
            action.deserializeNBT(nbt);
            return action;
        }
        return null;
    }
}
