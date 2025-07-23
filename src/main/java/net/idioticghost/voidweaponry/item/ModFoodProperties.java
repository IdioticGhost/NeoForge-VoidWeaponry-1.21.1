package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties COOKED_VOID_KELP = new FoodProperties.Builder().nutrition(1).saturationModifier(0.25f)
            .effect(new MobEffectInstance(MobEffects.CONFUSION, 150),2f)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100),1f)
            .effect(new MobEffectInstance(ModEffects.VOID_MIASMA, 150),1f).fast().build();

    public static final FoodProperties STAR_BERRIES = new FoodProperties.Builder().nutrition(1).saturationModifier(0.25f)
            .effect(new MobEffectInstance(MobEffects.GLOWING, 250),2f)
            .effect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500),1f).fast().build();
//            .effect(new MobEffectInstance(ModEffects.VOID_MIASMA, 150),1f).build(); an effect that actually makes you light up?
}
