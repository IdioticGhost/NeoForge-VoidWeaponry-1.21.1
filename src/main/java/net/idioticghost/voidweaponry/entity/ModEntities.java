package net.idioticghost.voidweaponry.entity;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.entity.custom.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, VoidWeaponry.MOD_ID);

    public static final Supplier<EntityType<DeathArrowEntity>> DEATH_ARROW =
            ENTITIES.register("death_arrow", () ->
                    EntityType.Builder.<DeathArrowEntity>of(DeathArrowEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("death_arrow"));

    public static final DeferredHolder<EntityType<?>, EntityType<MaelstromRingEntity>> MAELSTROM_RING =
            ENTITIES.register("maelstrom_ring",
                    () -> EntityType.Builder.<MaelstromRingEntity>of(MaelstromRingEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("maelstrom_ring"));

    public static final Supplier<EntityType<BlackholeKnifeProjectileEntity>> BLACKHOLE_KNIFE =
            ENTITIES.register("blackhole_knife", () -> EntityType.Builder.<BlackholeKnifeProjectileEntity>of(BlackholeKnifeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.75f).build("blackhole_knife"));

    public static final Supplier<EntityType<WhiteholeKnifeProjectileEntity>> WHITEHOLE_KNIFE =
            ENTITIES.register("whitehole_knife", () -> EntityType.Builder.<WhiteholeKnifeProjectileEntity>of(WhiteholeKnifeProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.75f).build("whitehole_knife"));

    public static final DeferredHolder<EntityType<?>, EntityType<DragonFireballProjectileEntity>> DRAGON_FIREBALL =
            ENTITIES.register(
            "dragon_fireball",
                    () -> EntityType.Builder.<DragonFireballProjectileEntity>of(DragonFireballProjectileEntity::new, MobCategory.MISC)
                            .sized(1.5f, 1.5f)
                            .clientTrackingRange(10)
                            .updateInterval(1)
                            .build("dragon_fireball"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}