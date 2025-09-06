package net.idioticghost.voidweaponry.attribute;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES =
            DeferredRegister.create(Registries.ATTRIBUTE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<Attribute, Attribute> HEALING_CUT =
            ATTRIBUTES.register("healing_cut",
                    () -> new RangedAttribute(
                            "attribute." + VoidWeaponry.MOD_ID + ".healing_cut",
                            0.0D,  // default (no cut)
                            0.0D,  // min
                            100.0D // max
                    ).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> STRENGTH =
            ATTRIBUTES.register("artifact_strength",
                    () -> new RangedAttribute(
                            "attribute." + VoidWeaponry.MOD_ID + ".artifact_strength",
                            0.0D,   // default base value
                            0.0D,   // min value
                            Double.MAX_VALUE // max value
                    ).setSyncable(true)
            );

    public static final DeferredHolder<Attribute, Attribute> SPEED =
            ATTRIBUTES.register("artifact_speed",
                    () -> new RangedAttribute(
                            "attribute." + VoidWeaponry.MOD_ID + ".artifact_speed",
                            0.0D,   // default speed bonus
                            0.0D,   // min
                            Double.MAX_VALUE // max
                    ).setSyncable(true)
            );
}