package net.idioticghost.voidweaponry.network;

import net.idioticghost.voidweaponry.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record AbilityKeyPacket() implements CustomPacketPayload {

    // Packet identifier
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("voidweaponry", "ability_key");

    // Packet TYPE
    public static final Type<AbilityKeyPacket> TYPE = new Type<>(ID);

    // StreamCodec for empty packet
    public static final StreamCodec<RegistryFriendlyByteBuf, AbilityKeyPacket> CODEC =
            StreamCodec.unit(new AbilityKeyPacket());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}