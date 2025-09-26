package com.gam0zing.newnew_origins.upgrade;

import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.resources.ResourceLocation;

public class UpgradeConditions {

    public static final

    class ConditionTrigger extends AbstractCriterionTriggerInstance {

        public ConditionTrigger(ResourceLocation pCriterion, ContextAwarePredicate pPlayer) {
            super(pCriterion, pPlayer);
        }
    }
}
