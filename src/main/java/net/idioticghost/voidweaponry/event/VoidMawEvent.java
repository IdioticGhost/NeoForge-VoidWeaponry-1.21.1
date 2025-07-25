package net.idioticghost.voidweaponry.event;

import net.idioticghost.voidweaponry.worldgen.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "voidweaponry")
public class VoidMawEvent {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        ServerLevel currentLevel = player.serverLevel();
        ResourceKey<Level> dim = currentLevel.dimension();

        if (player.getY() < currentLevel.getMinBuildHeight()) {
            if (dim == Level.END) {
                ServerLevel targetLevel = player.server.getLevel(ModDimensions.VOID_MAW_LEVEL_KEY);
                if (targetLevel != null) {
                    DimensionTransition transition = new DimensionTransition(
                            targetLevel,
                            new Vec3(0.5, 162, 0.5),
                            Vec3.ZERO,
                            player.getYRot(),
                            player.getXRot(),
                            DimensionTransition.DO_NOTHING
                    );
                    player.changeDimension(transition);
                }
            } else if (dim == ModDimensions.VOID_MAW_LEVEL_KEY) {
                player.teleportTo(currentLevel, player.getX(), 400, player.getZ(), player.getYRot(), player.getXRot());
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.level().isClientSide()) return;
        if (!(entity.level() instanceof ServerLevel serverLevel)) return;

        ResourceKey<Level> dim = serverLevel.dimension();

        if (dim == ModDimensions.VOID_MAW_LEVEL_KEY) {
            DamageSource source = event.getSource();

            if ("fall".equals(source.getMsgId())) {
                event.setAmount(0f);
            }
        }
    }

    private static final int PLATFORM_RADIUS = 3;
    private static final int SET_AIR_HEIGHT = 5;

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        Entity entity = event.getEntity();
        ResourceKey<Level> targetDimension = event.getDimension();

        if (targetDimension == ModDimensions.VOID_MAW_LEVEL_KEY) {
            MinecraftServer server = entity.getServer();
            if (server == null) return;

            ServerLevel targetLevel = server.getLevel(targetDimension);
            if (targetLevel == null) return;

            spawnObsidianPlatform(targetLevel, new BlockPos(0, 160, 0));
            setAirHeight(targetLevel, new BlockPos(0,160,0));
        }
    }

    private static void spawnObsidianPlatform(ServerLevel level, BlockPos center) {
        BlockState obsidian = Blocks.OBSIDIAN.defaultBlockState();

        for (int x = -PLATFORM_RADIUS; x <= PLATFORM_RADIUS; x++) {
            for (int z = -PLATFORM_RADIUS; z <= PLATFORM_RADIUS; z++) {
                BlockPos pos = center.offset(x, 0, z);
                level.setBlockAndUpdate(pos, obsidian);
            }
        }
    }

    private static void setAirHeight(ServerLevel level, BlockPos center) {
        BlockState air = Blocks.AIR.defaultBlockState();

        for (int y = 1; y <= SET_AIR_HEIGHT; y++) {
            for (int x = -PLATFORM_RADIUS; x <= PLATFORM_RADIUS; x++) {
                for (int z = -PLATFORM_RADIUS; z <= PLATFORM_RADIUS; z++) {
                    BlockPos pos = center.offset(x, y, z);
                    level.setBlockAndUpdate(pos, air);
                }
            }
        }
    }
}