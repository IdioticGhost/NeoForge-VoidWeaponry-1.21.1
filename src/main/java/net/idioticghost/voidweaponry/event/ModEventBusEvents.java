package net.idioticghost.voidweaponry.event;


import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.entity.ModEntities;
import net.idioticghost.voidweaponry.entity.client.BlackholeKnifeProjectileModel;
import net.idioticghost.voidweaponry.entity.client.DragonFireballProjectileModel;
import net.idioticghost.voidweaponry.entity.client.WhiteholeKnifeProjectileModel;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = VoidWeaponry.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BlackholeKnifeProjectileModel.LAYER_LOCATION, BlackholeKnifeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(WhiteholeKnifeProjectileModel.LAYER_LOCATION, WhiteholeKnifeProjectileModel::createBodyLayer);
        event.registerLayerDefinition(DragonFireballProjectileModel.LAYER_LOCATION, DragonFireballProjectileModel::createBodyLayer);
    }
}