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
                    () -> new MiasmaResistanceEffect(MobEffectCategory.BENEFICIAL, 0));

    public static final Holder<MobEffect> MIASMA_SHIELDED =
            MOB_EFFECTS.register("miasma_shielded",
                    () -> new MiasmaShielded(MobEffectCategory.BENEFICIAL, 0xE9B115));

    public static final Holder<MobEffect> UNKILLABLE =
            MOB_EFFECTS.register("unkillable",
                    () -> new UnkillableEffect());

    public static final Holder<MobEffect> PARRY =
            MOB_EFFECTS.register("parry",
                    () -> new ParryEffect());

    public static final Holder<MobEffect> TEMPO =
            MOB_EFFECTS.register("tempo",
                    () -> new MasterfulTempoEffect(MobEffectCategory.BENEFICIAL,0));

    public static final Holder<MobEffect> CHANNELING =
            MOB_EFFECTS.register("channeling",
                    () -> new ChannelingEffect(MobEffectCategory.NEUTRAL,0));

    public static final Holder<MobEffect> FROSTBITE =
            MOB_EFFECTS.register("frostbite",
                    () -> new FrostbiteEffect());

    public static final Holder<MobEffect> ELECTROCUTION =
            MOB_EFFECTS.register("electrocution",
                    () -> new ElectrocutionEffect());

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}