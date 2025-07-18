package net.idioticghost.voidweaponry.item.custom;

import com.google.common.collect.ImmutableMap;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.item.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Map;

public class ModArmorItem extends ArmorItem {

    // Use Holder<ArmorMaterial> to match constructor and getMaterial()
    private static final Map<Holder<ArmorMaterial>, List<MobEffectInstance>> MATERIAL_TO_EFFECT_MAP =
            new ImmutableMap.Builder<Holder<ArmorMaterial>, List<MobEffectInstance>>()
                    .put(ModArmorMaterials.VOIDGOLD_ARMOR_MATERIAL,
                            List.of(
                                    new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 210, 0, true, false),
                                    new MobEffectInstance(ModEffects.MIASMA_RESISTANCE, 210, 0, true, false)
                            ))
                    .build();

    public ModArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_armor_1.tooltip"));
        tooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_armor_2.tooltip"));
        tooltipComponents.add(Component.translatable("tooltip.voidweaponry.voidgold_armor_3.tooltip"));
        super.appendHoverText(stack, context, tooltipComponents, flag);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (!level.isClientSide() && entity instanceof Player player) {
            if (hasFullSuitOfArmorOn(player)) {
                evaluateArmorEffects(player);
            }
        }
    }

    private void evaluateArmorEffects(Player player) {
        for (Map.Entry<Holder<ArmorMaterial>, List<MobEffectInstance>> entry : MATERIAL_TO_EFFECT_MAP.entrySet()) {
            Holder<ArmorMaterial> armorMaterial = entry.getKey();
            List<MobEffectInstance> effects = entry.getValue();

            if (hasPlayerCorrectArmorOn(armorMaterial, player)) {
                applyEffectsToPlayer(player, effects);
            }
        }
    }

    private void applyEffectsToPlayer(Player player, List<MobEffectInstance> effects) {
        for (MobEffectInstance effect : effects) {
            player.addEffect(new MobEffectInstance(
                    effect.getEffect(),
                    210,
                    effect.getAmplifier(),
                    effect.isAmbient(),
                    effect.isVisible()
            ));
        }
    }

    private boolean hasPlayerCorrectArmorOn(Holder<ArmorMaterial> material, Player player) {
        ItemStack boots = player.getInventory().getArmor(0);
        ItemStack leggings = player.getInventory().getArmor(1);
        ItemStack chestplate = player.getInventory().getArmor(2);
        ItemStack helmet = player.getInventory().getArmor(3);

        return !boots.isEmpty() && boots.getItem() instanceof ArmorItem b && b.getMaterial().equals(material) &&
                !leggings.isEmpty() && leggings.getItem() instanceof ArmorItem l && l.getMaterial().equals(material) &&
                !chestplate.isEmpty() && chestplate.getItem() instanceof ArmorItem c && c.getMaterial().equals(material) &&
                !helmet.isEmpty() && helmet.getItem() instanceof ArmorItem h && h.getMaterial().equals(material);
    }

    private boolean hasFullSuitOfArmorOn(Player player) {
        return !player.getInventory().getArmor(0).isEmpty() &&
                !player.getInventory().getArmor(1).isEmpty() &&
                !player.getInventory().getArmor(2).isEmpty() &&
                !player.getInventory().getArmor(3).isEmpty();
    }
}