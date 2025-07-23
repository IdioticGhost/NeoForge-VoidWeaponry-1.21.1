package net.idioticghost.voidweaponry.particle;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, VoidWeaponry.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_WATCHER_PARTICLES =
            PARTICLE_TYPES.register("void_watcher_particles", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLD_PARTICLES =
            PARTICLE_TYPES.register("gold_particles", () -> new SimpleParticleType(false));

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FIREFLY_PARTICLES =
            PARTICLE_TYPES.register("firefly_particles", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}