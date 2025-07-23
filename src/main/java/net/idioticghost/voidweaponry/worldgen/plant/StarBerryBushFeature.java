package net.idioticghost.voidweaponry.worldgen.plant;

import com.mojang.serialization.Codec;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.block.custom.StarBerryBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class StarBerryBushFeature extends Feature<NoneFeatureConfiguration> {

    public StarBerryBushFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        LevelAccessor level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        int attempts = 4;
        boolean placedAny = false;

        BlockPos leafLayerPos = origin.above(7);

        for (int i = 0; i < attempts; i++) {
            int dx = random.nextInt(7) - 3;
            int dz = random.nextInt(7) - 3;
            int dy = 0;

            BlockPos pos = leafLayerPos.offset(dx, dy, dz);
            BlockState stateAbove = level.getBlockState(pos.above());
            BlockState stateHere = level.getBlockState(pos);

            if ((stateHere.isAir() || stateHere.canBeReplaced()) &&
                    (stateAbove.getBlock() == ModBlocks.SHADOW_PINE_LEAVES.get() || stateAbove.getBlock() == ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get())) {
                level.setBlock(pos, ModBlocks.STAR_VINE.get().defaultBlockState().setValue(StarBerryBushBlock.BERRIES, true), 2);
                placedAny = true;
            }
        }

        return placedAny;
    }
}