package com.gam0zing.newnew_origins.condition;

import net.minecraft.server.level.ServerPlayer;

/// 用法，在UpgradeCenter中为每个SeverPlayer创建一个Component，用于管理其Upgrade
/// 功能：提取玩家NBT，获取起源信息，根据起源信息递归查找进化链，退回进化链关联的所有进度，当达成条件时，尝试触发关联进度
public class UpgradeComponent {

    private final ServerPlayer player;
    private int ticks = 0;

    public UpgradeComponent(ServerPlayer player) {
        this.player = player;
    }

    public void tick() {
        this.ticks++;
        if (this.ticks >= 20) {
            this.ticks = 0;

            checkOrigin();
        }
    }

    private void checkOrigin() {

    }
}
