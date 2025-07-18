package net.idioticghost.voidweaponry.item;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(VoidWeaponry.MOD_ID);

    public static final DeferredItem<Item> DRAGON_BONE = ITEMS.register("dragon_bone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> DRAGON_SCALE = ITEMS.register("dragon_scale",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOID_SHARD = ITEMS.register("void_shard",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDBOUND_GOLD = ITEMS.register("voidbound_gold",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOID_CATALYST = ITEMS.register("void_catalyst",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> VOIDGOLD_SMITHING_TEMPLATE = ITEMS.register("voidgold_smithing_template",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
