//package net.idioticghost.voidweaponry.client;
//
//import com.mojang.blaze3d.shaders.FogShape;
//import net.minecraft.client.Camera;
//import net.minecraft.client.Minecraft;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Holder;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.biome.Biome;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.client.event.ViewportEvent;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EventBusSubscriber(value = Dist.CLIENT)
//public class FogHandler {
//
//    private static final ResourceKey<Biome> SHROUDED_FOREST = ResourceKey.create(
//            Registries.BIOME, ResourceLocation.fromNamespaceAndPath("voidweaponry", "shrouded_forest")
//    );
//    private static final ResourceKey<Biome> CORAL_WASTES = ResourceKey.create(
//            Registries.BIOME, ResourceLocation.fromNamespaceAndPath("voidweaponry", "coral_wastes")
//    );
//
//    private static final Map<ResourceKey<Biome>, FogSettings> FOG_SETTINGS = new HashMap<>();
//
//    static {
//        FOG_SETTINGS.put(SHROUDED_FOREST, new FogSettings(0.1f, 3.0f, 0.08235294117647059f, 0.5843137254901961f, 0.6901960784313725f));
//    }
//
//    @SubscribeEvent
//    public static void onRenderFog(ViewportEvent.RenderFog event) {
//        Level level = Minecraft.getInstance().level;
//        if (level == null) return;
//
//        Holder<Biome> biome = getCurrentBiome(event.getCamera(), level);
//        FogSettings settings = getFogSettings(biome);
//
//        if (settings != null) {
//            event.setNearPlaneDistance(settings.near);
//            event.setFarPlaneDistance(settings.far);
//        }
//    }
//
//    @SubscribeEvent
//    public static void onFogColor(ViewportEvent.ComputeFogColor event) {
//        Level level = Minecraft.getInstance().level;
//        if (level == null) return;
//
//        Holder<Biome> biome = getCurrentBiome(event.getCamera(), level);
//        FogSettings settings = getFogSettings(biome);
//
//        if (settings != null) {
//            event.setRed(settings.r);
//            event.setGreen(settings.g);
//            event.setBlue(settings.b);
//        }
//    }
//
//    private static Holder<Biome> getCurrentBiome(Camera camera, Level level) {
//        BlockPos pos = BlockPos.containing(camera.getPosition());
//        return level.getBiome(pos);
//    }
//
//    private static FogSettings getFogSettings(Holder<Biome> biome) {
//        for (ResourceKey<Biome> key : FOG_SETTINGS.keySet()) {
//            if (biome.is(key)) {
//                return FOG_SETTINGS.get(key);
//            }
//        }
//        return null;
//    }
//
//    private record FogSettings(float near, float far, float r, float g, float b) {}
//}