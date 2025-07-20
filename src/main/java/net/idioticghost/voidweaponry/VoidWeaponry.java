package net.idioticghost.voidweaponry;

import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.entity.ModBlockEntities;
import net.idioticghost.voidweaponry.block.entity.client.VoidCrafterRenderer;
import net.idioticghost.voidweaponry.effect.ModEffects;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.custom.DeathArrowEntityHitInfo;
import net.idioticghost.voidweaponry.entity.renderer.DeathArrowRenderer;
import net.idioticghost.voidweaponry.item.ModCreativeModeTabs;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.particle.custom.GoldParticles;
import net.idioticghost.voidweaponry.particle.custom.VoidWatcherParticles;
import net.idioticghost.voidweaponry.screen.ModMenuTypes;
import net.idioticghost.voidweaponry.screen.custom.VoidCrafterScreen;
import net.idioticghost.voidweaponry.util.ModItemProperties;
import net.idioticghost.voidweaponry.worldgen.ModFeatures;
import net.idioticghost.voidweaponry.worldgen.biome.ModTerrablender;
import net.idioticghost.voidweaponry.worldgen.biome.surface.ModSurfaceRules;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import terrablender.api.SurfaceRuleManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(VoidWeaponry.MOD_ID)
public class VoidWeaponry {
    public static final String MOD_ID = "voidweaponry";
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public VoidWeaponry(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);



        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModEffects.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModParticles.register(modEventBus);
        ModEntities.register(modEventBus);

        ModFeatures.FEATURES.register(modEventBus);
        ModTerrablender.registerBiomes();


        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public class DeathArrowTracker {
        public static final Map<UUID, Map<UUID, DeathArrowEntityHitInfo>> HIT_MAP = new HashMap<>();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModSurfaceRules.makeRules());
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_LANTERN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_KELP_CROP.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_SEAGRASS.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VOID_CRAFTER.get(), RenderType.translucent());
            ModItemProperties.addCustomItemProperties();
        }

        @SubscribeEvent
        public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticles.VOID_WATCHER_PARTICLES.get(), VoidWatcherParticles.Provider::new);
            event.registerSpriteSet(ModParticles.GOLD_PARTICLES.get(), GoldParticles.Provider::new);
        }

        @SubscribeEvent
        public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(ModBlockEntities.VOID_CRAFTER_BE.get(), VoidCrafterRenderer::new);
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.VOID_CRAFTER_MENU.get(), VoidCrafterScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.DEATH_ARROW.get(), DeathArrowRenderer::new);
        }
    }
}