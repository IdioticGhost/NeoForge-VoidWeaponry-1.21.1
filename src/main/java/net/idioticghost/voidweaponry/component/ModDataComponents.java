package net.idioticghost.voidweaponry.component;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

import static net.minecraft.util.datafix.fixes.References.DATA_COMPONENTS;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(VoidWeaponry.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> THREW_KNIFE =
            register("threw_knife", builder -> builder.persistent(Codec.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> KNIFE_VERSION =
            register("knife_version", builder -> builder.persistent(Codec.BOOL));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> KATANA_HEAT_VALUE =
            register("katana_heat_value",builder -> builder.persistent(Codec.INT));




    private static <T>DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}