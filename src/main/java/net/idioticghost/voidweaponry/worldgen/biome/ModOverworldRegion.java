//package net.idioticghost.voidweaponry.worldgen.biome;
//
//import com.mojang.datafixers.util.Pair;
//import net.minecraft.core.Registry;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.biome.Biome;
//import net.minecraft.world.level.biome.Biomes;
//import net.minecraft.world.level.biome.Climate;
//import terrablender.api.ParameterUtils;
//import terrablender.api.Region;
//import terrablender.api.RegionType;
//import terrablender.api.VanillaParameterOverlayBuilder;
//
//import java.util.function.Consumer;
//
//public class ModOverworldRegion extends Region {
//
//
//    public ModOverworldRegion(ResourceLocation name, int weight) {
//        super(name, RegionType.OVERWORLD, weight);
//    }
//
//    @Override
//    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
//        this.addModifiedVanillaOverworldBiomes(mapper, modifiedVanillaOverworldBuilder ->
//                modifiedVanillaOverworldBuilder.replaceBiome(Biomes.DESERT, ModBiomes.DRIED_DEPTHS));
////            VanillaParameterOverlayBuilder builder = new VanillaParameterOverlayBuilder();
////            new ParameterUtils.ParameterPointListBuilder()
////                    .temperature(ParameterUtils.Temperature.span(ParameterUtils.Temperature.HOT, ParameterUtils.Temperature.WARM))
////                    .humidity(ParameterUtils.Humidity.span(ParameterUtils.Humidity.ARID, ParameterUtils.Humidity.DRY))
////                    .continentalness(ParameterUtils.Continentalness.NEAR_INLAND)
////                    .erosion(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_1)
////                    .depth(ParameterUtils.Depth.SURFACE, ParameterUtils.Depth.FLOOR)
////                    .weirdness(ParameterUtils.Weirdness.MID_SLICE_NORMAL_ASCENDING, ParameterUtils.Weirdness.MID_SLICE_NORMAL_DESCENDING)
////                    .build().forEach(point -> builder.add(point, ModBiomes.DRIED_DEPTHS));
////
////            builder.build().forEach(mapper::accept);
//    }
//}
