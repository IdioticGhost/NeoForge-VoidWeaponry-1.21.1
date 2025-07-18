package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, VoidWeaponry.MOD_ID);

    public static final Supplier<CreativeModeTab> VOID_MISC_TAB = CREATIVE_MODE_TAB .register("void_misc_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()))
                    .title(Component.translatable("creativetab.voidweaponry.void_misc_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.VOID_SHARD.get());
                        output.accept(ModItems.VOID_CATALYST.get());
                        output.accept(ModItems.VOIDBOUND_GOLD.get());
                        output.accept(ModItems.DRAGON_BONE.get());
                        output.accept(ModItems.DRAGON_SCALE.get());


                    }).build());

    public static final Supplier<CreativeModeTab> VOID_WEAPONS_TAB = CREATIVE_MODE_TAB .register("void_weapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get()))
                    .title(Component.translatable("creativetab.voidweaponry.void_weapons_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.VOID_SHARD.get());
                        output.accept(ModItems.VOID_CATALYST.get());
                        output.accept(ModItems.VOIDBOUND_GOLD.get());
                        output.accept(ModItems.DRAGON_BONE.get());
                        output.accept(ModItems.DRAGON_SCALE.get());


                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
