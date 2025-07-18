package net.idioticghost.voidweaponry.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties COOKED_VOID_KELP = new FoodProperties.Builder().nutrition(1).saturationModifier(0.25f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 150),2f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100),1f).fast().build();
            //.effect(new MobEffectInstance(MobEffects. , 400),1f).build();
}
