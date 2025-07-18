package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
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

                        output.accept(ModBlocks.ENDSTONE_SAND_BLOCK.get());
                        output.accept(ModBlocks.VOID_ORE.get());
                        output.accept(ModBlocks.VOIDGROWTH_PLANKS.get());
                        output.accept(ModBlocks.VOIDGROWTH_STAIRS.get());
                        output.accept(ModBlocks.VOIDGROWTH_SLAB.get());
                        output.accept(ModBlocks.VOIDGROWTH_FENCE.get());
                        output.accept(ModBlocks.VOIDGROWTH_FENCE_GATE.get());
                        output.accept(ModBlocks.VOIDGROWTH_BUTTON.get());
                        output.accept(ModBlocks.VOIDGROWTH_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.VOIDGROWTH_DOOR.get());
                        output.accept(ModBlocks.VOIDGROWTH_TRAPDOOR.get());
                        output.accept(ModBlocks.VOIDGROWTH_STAIRS.get());
                        output.accept(ModBlocks.VOID_KELP_BLOCK.get());
                        output.accept(ModBlocks.VOID_LANTERN.get());

                        output.accept(ModBlocks.GNARLED_SHADOW_PINE.get());
                        output.accept(ModBlocks.SHADOW_PINE_LOG.get());
                        output.accept(ModBlocks.SHADOW_PINE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_SHADOW_PINE_WOOD.get());
                        output.accept(ModBlocks.STRIPPED_SHADOW_PINE_LOG.get());
                        output.accept(ModBlocks.SHADOW_PINE_LEAVES.get());
                        output.accept(ModBlocks.SHADOW_PINE_SAPLING.get());

                        output.accept(ModItems.VOID_KELP.get());
                        output.accept(ModItems.COOKED_VOID_KELP.get());
                        output.accept(ModItems.VOID_SHARD.get());
                        output.accept(ModItems.VOIDBOUND_GOLD.get());
                        output.accept(ModItems.VOID_CATALYST.get());

                        output.accept(ModItems.DRAGON_BONE.get());

                        output.accept(ModBlocks.VOID_CRAFTER.get());


                    }).build());

    public static final Supplier<CreativeModeTab> VOID_WEAPONS_TAB = CREATIVE_MODE_TAB .register("void_weapons_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.DEATH_BOW.get()))
                    .title(Component.translatable("creativetab.voidweaponry.void_weapons_tab"))
                    .displayItems((itemDisplayParameters, output) -> {

                        output.accept(ModItems.VOIDGOLD_SWORD.get());
                        output.accept(ModItems.VOIDGOLD_PICKAXE.get());
                        output.accept(ModItems.VOIDGOLD_AXE.get());
                        output.accept(ModItems.VOIDGOLD_SHOVEL.get());
                        output.accept(ModItems.VOIDGOLD_HOE.get());
                        output.accept(ModItems.VOID_MULTITOOL.get());

                        output.accept(ModItems.NETHERITE_BOW.get());

                        output.accept(ModItems.VOIDGOLD_HELMET.get());
                        output.accept(ModItems.VOIDGOLD_CHESTPLATE.get());
                        output.accept(ModItems.VOIDGOLD_LEGGINGS.get());
                        output.accept(ModItems.VOIDGOLD_BOOTS.get());
                        output.accept(ModItems.VOIDGOLD_SMITHING_TEMPLATE.get());

                        output.accept(ModItems.DEATH_BOW.get());
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
