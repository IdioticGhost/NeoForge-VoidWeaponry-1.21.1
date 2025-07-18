package net.idioticghost.voidweaponry.effect;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, VoidWeaponry.MOD_ID);

    public static final Holder<MobEffect> VOID_MIASMA =
            MOB_EFFECTS.register("void_miasma",
                    () -> new VoidMiasmaEffect(MobEffectCategory.HARMFUL, 0));

    public static final Holder<MobEffect> MIASMA_RESISTANCE =
            MOB_EFFECTS.register("miasma_resistance",
                    () -> new VoidMiasmaEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final Holder<MobEffect> MIASMA_SHIELDED =
            MOB_EFFECTS.register("miasma_shielded",
                    () -> new VoidMiasmaEffect(MobEffectCategory.BENEFICIAL, 0xE9B115));

    public static final Holder<MobEffect> UNKILLABLE =
            MOB_EFFECTS.register("unkillable",
                    () -> new VoidMiasmaEffect(MobEffectCategory.BENEFICIAL, 0xE9B115));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}