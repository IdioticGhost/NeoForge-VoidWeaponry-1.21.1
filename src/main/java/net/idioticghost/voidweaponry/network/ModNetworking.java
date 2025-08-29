package net.idioticghost.voidweaponry.network;

import net.idioticghost.voidweaponry.component.ModDataComponents;
import net.idioticghost.voidweaponry.entity.custom.BlackholeKnifeProjectileEntity;
import net.idioticghost.voidweaponry.entity.custom.WhiteholeKnifeProjectileEntity;
import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = "voidweaponry")
public class ModNetworking {

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar("voidweaponry");

        registrar.playToServer(
                AbilityKeyPacket.TYPE,
                AbilityKeyPacket.CODEC,
                (packet, context) -> {
                    context.enqueueWork(() -> {
                        var player = context.player();
                        if (player == null) return;

                        var held = player.getMainHandItem();
                        System.out.println("[DEBUG] Server sees player holding: " + held);

                        if (held.is(ModItems.DEATH_BOW.get())) {
                            player.sendSystemMessage(Component.literal("Death Bow ability!"));
                        } else if (held.is(ModItems.BLACKHOLE_KNIFE.get())) {
                            boolean current = held.getOrDefault(ModDataComponents.KNIFE_VERSION.get(), Boolean.FALSE);


                            // Check if player still has a thrown knife in the world
                            boolean hasBlackKnifeEntity = !player.level().getEntitiesOfClass(
                                    BlackholeKnifeProjectileEntity.class,
                                    player.getBoundingBox().inflate(128), // radius to search
                                    e -> e.getOwner() == player
                            ).isEmpty();

                            boolean hasWhiteKnifeEntity = !player.level().getEntitiesOfClass(
                                    WhiteholeKnifeProjectileEntity.class,
                                    player.getBoundingBox().inflate(128), // radius to search
                                    e -> e.getOwner() == player
                            ).isEmpty();

                            if (hasBlackKnifeEntity || hasWhiteKnifeEntity) {
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.ITEM_BREAK, SoundSource.PLAYERS, 1.5f, 1.0f);
                                player.sendSystemMessage(Component.literal("Cannot use this right now.").withStyle(ChatFormatting.RED));
                            } else {
                                // Knife not out, safe to toggle
                                held.set(ModDataComponents.KNIFE_VERSION.get(), !current);
                                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                                        SoundEvents.WOLF_ARMOR_REPAIR, SoundSource.PLAYERS, 1.5f, 1.0f);
                            }
                        }

                        else {
                            player.sendSystemMessage(Component.literal("No matching item in hand!"));
                        }
                    });
                }
        );
    }
}