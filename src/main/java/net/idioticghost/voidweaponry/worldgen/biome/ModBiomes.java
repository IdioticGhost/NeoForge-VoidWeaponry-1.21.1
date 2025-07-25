package net.idioticghost.voidweaponry.worldgen.biome;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.worldgen.ModPlacedFeatures;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomes {
    public static final ResourceKey<Biome> DRIED_DEPTHS = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "dried_depths"));

    public static final ResourceKey<Biome> CORAL_WASTES = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "coral_wastes"));

    public static final ResourceKey<Biome> FORGOTTEN_BEACH = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "forgotten_beach"));

    public static final ResourceKey<Biome> SHROUDED_FOREST = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "shrouded_forest"));

    public static final ResourceKey<Biome> ASHEN_TUNDRA = ResourceKey.create(Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(VoidWeaponry.MOD_ID, "ashen_tundra"));

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(DRIED_DEPTHS, driedDepths(context));
        context.register(CORAL_WASTES, coralWastes(context));
        context.register(FORGOTTEN_BEACH, forgottenBeach(context));
        context.register(SHROUDED_FOREST, shroudedForest(context));
        context.register(ASHEN_TUNDRA, ashenTundra(context));
    }



    public static void globalOverworldGeneration(BiomeGenerationSettings.Builder builder) {
        builder.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
        //BiomeDefaultFeatures.addDefaultCrystalFormations(builder);
        //BiomeDefaultFeatures.addDefaultMonsterRoom(builder);
        //BiomeDefaultFeatures.addDefaultUndergroundVariety(builder);
        //BiomeDefaultFeatures.addDefaultSprings(builder);
        //BiomeDefaultFeatures.addSurfaceFreezing(builder);
    }

    //DRIED DEPTHS

    private static Biome driedDepths(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        //spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        //BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        //BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        //BiomeDefaultFeatures.addFerns(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.OBSIDIAN_SPIKE_PLACED_KEY));

        //BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x000000)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x411060)
                        .ambientParticle(new AmbientParticleSettings(
                                ModParticles.VOID_WATCHER_PARTICLES.get(),
                                0.004F
                        ))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                        .backgroundMusic(null).build())
                .build();
    }

    //CORAL WASTES

    private static Biome coralWastes(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        //spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        //BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        //BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        //BiomeDefaultFeatures.addFerns(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_TREE_PLACED_KEY));
//
//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_CLAW_PLACED_KEY));
//
//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_MUSHROOM_PLACED_KEY));

        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x323232)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x646464)
                        .ambientParticle(new AmbientParticleSettings(
                                ParticleTypes.WHITE_ASH,
                                0.02F
                        ))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                        .backgroundMusic(null).build())
                .build();
    }


    //BEACH

    private static Biome forgottenBeach(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        //spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        //BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        //BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        //BiomeDefaultFeatures.addFerns(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addExtraGold(biomeBuilder);

        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_TREE_PLACED_KEY));
//
//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_CLAW_PLACED_KEY));
//
//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.DEAD_CORAL_MUSHROOM_PLACED_KEY));

        //BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x323232)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x646464)
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                        .backgroundMusic(null).build())
                .build();
    }

    //SHROUDED FOREST

    private static Biome shroudedForest(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        //spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        //BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        //BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        //globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        BiomeDefaultFeatures.addFerns(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addExtraGold(biomeBuilder);

//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

//        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.OBSIDIAN_SPIKE_PLACED_KEY));

        //BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x1595b0)
                        .grassColorOverride(0x4d3926)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0x1595b0)
                        .ambientParticle(new AmbientParticleSettings(
                                ModParticles.VOID_WATCHER_PARTICLES.get(),
                                0.004F
                        ))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                        .backgroundMusic(null).build())
                .build();
    }

    //ASHEN TUNDRA

    private static Biome ashenTundra(BootstrapContext<Biome> context) {
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        //spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 5, 4, 4));

        //BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        //BiomeDefaultFeatures.commonSpawns(spawnBuilder);

        BiomeGenerationSettings.Builder biomeBuilder =
                new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        //BiomeDefaultFeatures.addForestFlowers(biomeBuilder);
        //BiomeDefaultFeatures.addFerns(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        //BiomeDefaultFeatures.addExtraGold(biomeBuilder);

//        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.TREES_PLAINS);

//        biomeBuilder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES,
//                context.lookup(Registries.PLACED_FEATURE).getOrThrow(ModPlacedFeatures.OBSIDIAN_SPIKE_PLACED_KEY));

        //BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        //BiomeDefaultFeatures.addDefaultExtraVegetation(biomeBuilder);

        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .downfall(0.8f)
                .temperature(0.7f)
                .generationSettings(biomeBuilder.build())
                .mobSpawnSettings(spawnBuilder.build())
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .waterColor(0x000000)
                        .waterFogColor(0xbf1b26)
                        .skyColor(0x1595b0)
                        .grassColorOverride(0x7f03fc)
                        .foliageColorOverride(0xd203fc)
                        .fogColor(0xffffff)
                        .ambientParticle(new AmbientParticleSettings(
                                ParticleTypes.WHITE_ASH,
                                0.1F
                        ))
                        .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                        .ambientLoopSound(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_LOOP)
                        .ambientAdditionsSound(new AmbientAdditionsSettings(SoundEvents.AMBIENT_SOUL_SAND_VALLEY_ADDITIONS, 0.0111D))
                        .backgroundMusic(null).build())
                .build();
    }
}
