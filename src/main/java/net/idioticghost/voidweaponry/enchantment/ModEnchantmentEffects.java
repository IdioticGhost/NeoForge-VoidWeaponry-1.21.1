package net.idioticghost.voidweaponry.enchantment;//package net.ghost.voidweaponry.enchantment;
//
//import com.mojang.serialization.MapCodec;
//import net.ghost.voidweaponry.VoidWeaponry;
//import net.ghost.voidweaponry.enchantment.event.StrengthRuneHandler;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
//import net.minecraftforge.eventbus.api.IEventBus;
//import net.minecraftforge.registries.DeferredRegister;
//import net.minecraftforge.registries.RegistryObject;
//
//import static net.minecraft.core.component.DataComponents.ENCHANTMENTS;
//
//public class ModEnchantmentEffects {
//    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS =
//            DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, VoidWeaponry.MOD_ID);
//
////    public static final RegistryObject<Enchantment> STRENGTH_RUNE = ENCHANTMENTS.register("strength_rune",
////            () -> new StrengthRuneHandler() // replace with your actual enchantment constructor or a simple base one
////    );
//
//    public static void register(IEventBus eventBus) {
//        ENTITY_ENCHANTMENT_EFFECTS.register(eventBus);
//    }
//}
////