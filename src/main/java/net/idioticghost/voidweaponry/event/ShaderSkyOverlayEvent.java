package net.idioticghost.voidweaponry.event;

import com.mojang.blaze3d.vertex.PoseStack;
import net.idioticghost.voidweaponry.mixin.LevelRendererMixin;
import net.idioticghost.voidweaponry.worldgen.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
//
//@EventBusSubscriber(modid = "voidweaponry")
//public class ShaderSkyOverlayEvent {
//
//    @SubscribeEvent
//    public static void onRenderOverlay(RenderLevelStageEvent event) {
//        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_SKY) return;
//
//        Minecraft mc = Minecraft.getInstance();
//        if (mc.level == null) return;
//        if (mc.level.dimension() != ModDimensions.VOID_MAW_LEVEL_KEY) return;
//
//        boolean shadersActive = mc.gameRenderer != null && mc.gameRenderer.currentEffect() != null;
//        if (!shadersActive) return;
//
//        int ticks = ((net.minecraft.client.renderer.LevelRenderer) mc.levelRenderer).getTicks();
//        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
//
//        PoseStack poseStack = event.getPoseStack();
//        LevelRendererMixin.renderOverlaysWithShaders(poseStack, partialTick, ticks);
//    }
//}
