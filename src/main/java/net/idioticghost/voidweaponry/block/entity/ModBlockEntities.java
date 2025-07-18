package net.idioticghost.voidweaponry.block.entity;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.entity.custom.VoidCrafterBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidCrafterBlockEntity>> VOID_CRAFTER_BE =
            BLOCK_ENTITIES.register("void_crafter_be",
                    () -> BlockEntityType.Builder.of(
                            VoidCrafterBlockEntity::new,
                            ModBlocks.VOID_CRAFTER.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}