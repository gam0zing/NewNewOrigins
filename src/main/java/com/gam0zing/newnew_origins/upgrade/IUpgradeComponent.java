package com.gam0zing.newnew_origins.upgrade;

import io.github.edwinmindcraft.origins.api.origin.Origin;
import io.github.edwinmindcraft.origins.api.origin.OriginLayer;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Map;

public interface IUpgradeComponent extends INBTSerializable<Tag> {
    /// 更新旧起源
    void putLast(Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> layers);
    /// 获取旧起源
    Map<ResourceKey<OriginLayer>, ResourceKey<Origin>> getLast();

}
