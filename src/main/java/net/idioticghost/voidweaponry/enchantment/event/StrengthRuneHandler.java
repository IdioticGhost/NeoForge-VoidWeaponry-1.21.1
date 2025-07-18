package net.idioticghost.voidweaponry.enchantment.event;//package net.ghost.voidweaponry.enchantment.event;
//
//import net.ghost.voidweaponry.enchantment.ModEnchantments;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.item.BowItem;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.SwordItem;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.event.entity.EntityJoinLevelEvent;
//import net.minecraftforge.event.entity.living.LivingAttackEvent;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//@Mod.EventBusSubscriber(modid = "voidweaponry", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
//public class StrengthRuneHandler {
//
//    @SubscribeEvent
//    public static void onHurt(LivingHurtEvent event) {
//        LivingEntity entity = event.getEntity();
//        float reduction = 0f;
//
//        // PROTECTION-LIKE: armor damage reduction
//        for (ItemStack armor : entity.getArmorSlots()) {
//            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.STRENGTH_RUNE.get(), armor);
//            if (level > 0) {
//                reduction += 0.04f * level;
//            }
//        }
//
//        // SHARPNESS-LIKE: sword extra damage
//        if (event.getSource().getEntity() instanceof Player player) {
//            ItemStack weapon = player.getMainHandItem();
//            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.STRENGTH_RUNE.get(), weapon);
//            if (level > 0 && weapon.getItem() instanceof SwordItem) {
//                float extraDamage = 1.0f + 0.5f * level;
//                event.setAmount(event.getAmount() + extraDamage);
//            }
//        }
//
//        // apply reduction
//        if (reduction > 0f) {
//            float reduced = event.getAmount() * (1 - reduction);
//            event.setAmount(reduced);
//        }
//    }
//
//    // POWER-LIKE: increase arrow damage
//    @SubscribeEvent
//    public static void onArrowShot(EntityJoinLevelEvent event) {
//        if (!(event.getEntity() instanceof AbstractArrow arrow)) return;
//        if (!(arrow.getOwner() instanceof Player player)) return;
//
//        ItemStack bow = player.getUseItem();
//        if (bow.getItem() instanceof BowItem) {
//            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.STRENGTH_RUNE.get(), bow);
//            if (level > 0) {
//                arrow.setBaseDamage(arrow.getBaseDamage() + level * 1.25);
//            }
//        }
//    }
//}