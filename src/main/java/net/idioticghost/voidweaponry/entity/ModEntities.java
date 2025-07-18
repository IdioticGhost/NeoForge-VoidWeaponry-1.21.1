package net.idioticghost.voidweaponry.entity;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.entity.custom.DeathArrowEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, VoidWeaponry.MOD_ID);

    public static final Supplier<EntityType<DeathArrowEntity>> DEATH_ARROW =
            ENTITIES.register("death_arrow", () ->
                    EntityType.Builder.<DeathArrowEntity>of(DeathArrowEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f) // same size as normal arrows
                            .clientTrackingRange(4)
                            .updateInterval(20)
                            .build("death_arrow"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}