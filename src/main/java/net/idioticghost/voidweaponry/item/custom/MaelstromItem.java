package net.idioticghost.voidweaponry.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.custom.MaelstromRingEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

public class MaelstromItem extends SwordItem {

    private static final String FROZEN_POS_TAG = "MaelstromFrozenPos";
    private static final String FROZEN_TICKS_TAG = "MaelstromFrozenTicks";
    private static final String HOLD_START_TAG = "MaelstromHoldStart";
    private static final String RELEASE_TICK_TAG = "MaelstromReleaseTick";

    private static final int MAX_HOLD_TICKS = 120; // 6 seconds
    private static final int COOLDOWN_TICKS = 160; // 8 seconds
    private static final int PARTICLE_LIFETIME_TICKS = 400;
    private static final double MAX_RADIUS = 10.0;

    private static final Map<Player, Boolean> lastHitWasFrostbite = new WeakHashMap<>();

    public MaelstromItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, @Nullable LivingEntity entity) {
        return MAX_HOLD_TICKS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        }

        ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);

        if (!level.isClientSide && !player.hasEffect(ModEffects.CHANNELING)) {
            player.addEffect(new MobEffectInstance(ModEffects.CHANNELING, MAX_HOLD_TICKS, 0, false, true));
        }

        player.getPersistentData().putInt(HOLD_START_TAG, player.tickCount);

        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (!(entity instanceof Player player) || level.isClientSide) return;

        int usedTicks = this.getUseDuration(stack, player) - count;

        if (!player.hasEffect(ModEffects.CHANNELING)) {
            player.releaseUsingItem(); // cancel early if effect removed
            return;
        }

        if (usedTicks >= MAX_HOLD_TICKS - 1) {
            player.releaseUsingItem();
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;

        player.getCooldowns().addCooldown(this, COOLDOWN_TICKS);

        int heldTicks = this.getUseDuration(stack, player) - timeLeft;

        if (player.hasEffect(ModEffects.CHANNELING)) {
            player.removeEffect(ModEffects.CHANNELING);
        }

        Vec3 pos = player.position();
        player.getPersistentData().putDouble(FROZEN_POS_TAG + "_x", pos.x);
        player.getPersistentData().putDouble(FROZEN_POS_TAG + "_y", pos.y);
        player.getPersistentData().putDouble(FROZEN_POS_TAG + "_z", pos.z);
        player.getPersistentData().putInt(FROZEN_TICKS_TAG, heldTicks);
        player.getPersistentData().putInt(RELEASE_TICK_TAG, player.tickCount);
        player.getPersistentData().remove(HOLD_START_TAG);

        if (!level.isClientSide) {
            double radius = MAX_RADIUS * Math.min(heldTicks / (double) MAX_HOLD_TICKS, 1.0);
            MaelstromRingEntity pulse = new MaelstromRingEntity(
                    ModEntities.MAELSTROM_RING.get(), level, player, radius
            );
            pulse.setPos(pos.x, pos.y, pos.z);
            level.addFreshEntity(pulse);
        }

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.GLASS_BREAK,
                SoundSource.PLAYERS,
                1.0f,
                0.5f
        );

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.ELYTRA_FLYING,
                SoundSource.PLAYERS,
                1.0f,
                0
        );

        player.level().playSound(
                null,
                player.getX(),
                player.getY(),
                player.getZ(),
                SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP,
                SoundSource.PLAYERS,
                1.0f,
                0
        );

        if (!level.isClientSide) {
            double radius = MAX_RADIUS * Math.min(heldTicks / (double) MAX_HOLD_TICKS, 1.0);
            MaelstromRingEntity pulse = new MaelstromRingEntity(
                    ModEntities.MAELSTROM_RING.get(), level, player, radius
            );
            pulse.setPos(pos.x, pos.y, pos.z);
            level.addFreshEntity(pulse);
        }
    }

    private static float particleRotation = 0f;

    public static void handleHoldParticles(Player player) {
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return;

        CompoundTag data = player.getPersistentData();

        int heldTicks;
        Vec3 basePos;

        if (player.isUsingItem() && player.getUseItem().getItem() instanceof MaelstromItem) {
            if (!data.contains(HOLD_START_TAG)) data.putInt(HOLD_START_TAG, player.tickCount);
            int startTick = data.getInt(HOLD_START_TAG);
            heldTicks = player.tickCount - startTick;
            basePos = player.position();

            // Update frozen data
            data.putDouble(FROZEN_POS_TAG + "_x", basePos.x);
            data.putDouble(FROZEN_POS_TAG + "_y", basePos.y);
            data.putDouble(FROZEN_POS_TAG + "_z", basePos.z);
            data.putInt(FROZEN_TICKS_TAG, heldTicks);
        } else {
            int releaseTick = data.getInt(RELEASE_TICK_TAG);
            int ticksSinceRelease = player.tickCount - releaseTick;
            if (ticksSinceRelease > PARTICLE_LIFETIME_TICKS) return;

            heldTicks = data.getInt(FROZEN_TICKS_TAG);
            basePos = new Vec3(
                    data.getDouble(FROZEN_POS_TAG + "_x"),
                    data.getDouble(FROZEN_POS_TAG + "_y"),
                    data.getDouble(FROZEN_POS_TAG + "_z")
            );

            data.remove(HOLD_START_TAG);
        }

        // Charge circle
        spawnHoldCircleParticles(serverLevel, basePos, heldTicks);

        // Snowflake orbit
        particleRotation = spawnSnowCircleParticles(serverLevel, basePos, heldTicks, particleRotation);
    }

    private static void spawnHoldCircleParticles(ServerLevel level, Vec3 basePos, int heldTicks) {
        double chargeFraction = Math.min(1.0, heldTicks / (double) MAX_HOLD_TICKS);
        double radius = MAX_RADIUS * chargeFraction;
        int segments = (int) (radius * 30);

        for (int i = 0; i < segments; i++) {
            double angle = (2 * Math.PI * i) / segments;
            double x = basePos.x + radius * Math.cos(angle);
            double z = basePos.z + radius * Math.sin(angle);
            double y = basePos.y + 0.1;

            float t = (float) chargeFraction;
            Vector3f color = new Vector3f(0.3f + 0.7f * t, 0.5f + 0.5f * t, 1.0f);
            float size = 1f; // constant size

            level.sendParticles(new DustParticleOptions(color, size), x, y, z, 1, 0, 0.025, 0, 0);
        }
    }

    // Snowflake orbiting particles
    private static float spawnSnowCircleParticles(ServerLevel level, Vec3 center, int heldTicks, float rotation) {
        double chargeFraction = Math.min(1.0, heldTicks / (double) MAX_HOLD_TICKS);
        double radius = MAX_RADIUS * chargeFraction;
        int segments = 8;

        // Height and size scaling
        double maxHeight = 5.0;
        double yOffset = maxHeight * chargeFraction + 2;
        float particleSize = 0.6f + 0.4f * (float) chargeFraction;

        // Increment rotation
        rotation += 0.15f;
        if (rotation > Math.PI * 2) rotation -= Math.PI * 2;

        for (int i = 0; i < segments; i++) {
            double angle = rotation + i * (Math.PI / 4);
            double x = center.x + radius * Math.cos(angle);
            double z = center.z + radius * Math.sin(angle);
            double y = center.y + yOffset;

            // Send particle with small size scaling
            level.sendParticles(ParticleTypes.SNOWFLAKE, x, y, z, 1, 0.01, 0.01, 0.01, 0);
        }

        return rotation; // return updated rotation
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof Player player)) return super.hurtEnemy(stack, target, attacker);

        // Check which effect to apply for this player
        boolean applyFrostbite = true;
        if (lastHitWasFrostbite.containsKey(player)) {
            applyFrostbite = !lastHitWasFrostbite.get(player);
        }

        // Apply the effect
        if (applyFrostbite) {
            target.addEffect(new MobEffectInstance(ModEffects.FROSTBITE, 200, 0, false, true));
        } else {
            target.addEffect(new MobEffectInstance(ModEffects.ELECTROCUTION, 200, 0, false, true));
        }

        // Record what we just applied
        lastHitWasFrostbite.put(player, applyFrostbite);

        return super.hurtEnemy(stack, target, attacker);
    }
}