package net.idioticghost.voidweaponry.worldgen.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.idioticghost.voidweaponry.block.ModBlocks;
import net.idioticghost.voidweaponry.worldgen.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class ShadowPineFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<ShadowPineFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    IntProvider.codec(0, 16).fieldOf("radius").forGetter(fp -> fp.radius),
                    IntProvider.codec(0, 16).fieldOf("offset").forGetter(fp -> fp.offset),
                    // Add startHeight as an int field in codec
                    Codec.intRange(0, 20).fieldOf("start_height").forGetter(fp -> fp.startHeight)
            ).apply(instance, ShadowPineFoliagePlacer::new)
    );

    private final IntProvider radius;
    private final IntProvider offset;
    private final int startHeight;

    public ShadowPineFoliagePlacer(IntProvider radius, IntProvider offset, int startHeight) {
        super(radius, offset);
        this.radius = radius;
        this.offset = offset;
        this.startHeight = startHeight;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFeatures.SHADOW_PINE_FOLIAGE_PLACER.value();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource random,
                                 TreeConfiguration config, int foliageHeight, FoliageAttachment attachment,
                                 int maxFreeRadius, int dx, int dz) {

        BlockPos topPos = attachment.pos();
        int layers = foliageHeight;

        // === Add a ring of leaves at Y = base + 0 around the trunk ===
        {
            int ringHeight = 0;
            int ringRadius = 1; // 1 block radius around trunk

            BlockPos ringCenter = topPos.above(ringHeight);

            for (int x = -ringRadius; x <= ringRadius; x++) {
                for (int z = -ringRadius; z <= ringRadius; z++) {
                    if (x == 0 && z == 0) continue; // Skip trunk block itself

                    BlockPos leafPos = ringCenter.offset(x, 0, z);
                    BlockState currentState = ((BlockGetter) level).getBlockState(leafPos);

                    if (currentState.isAir() || currentState.canBeReplaced()) {
                        BlockState leafBlock = random.nextFloat() < 0.75f
                                ? ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get().defaultBlockState()
                                : ModBlocks.SHADOW_PINE_LEAVES.get().defaultBlockState();

                        leafBlock = leafBlock.setValue(LeavesBlock.DISTANCE, 1);
                        setter.set(leafPos, leafBlock);
                    }
                }
            }
        }

        // === Existing main foliage layers loop ===
        for (int y = 0; y < layers; y++) {
            int invertedY = layers - 1 - y;

            int radius;
            if (invertedY < 2) {
                radius = Math.min(maxFreeRadius, invertedY + 3);
            } else {
                radius = Math.max(1, maxFreeRadius - ((invertedY - 2) / 2));
            }

            if (invertedY >= layers - 2) {
                radius = Math.max(0, radius - 1);
            }

            int offsetY = startHeight - y;
            int maxLeafHeight = 1;

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    if (x * x + z * z <= radius * radius) {
                        BlockPos leafPos = topPos.offset(x, offsetY, z);

                        int relativeY = leafPos.getY() - topPos.getY();
                        if (relativeY > maxLeafHeight) {
                            continue; // skip leaves above max height
                        }

                        // Check existing block to avoid overwriting trunk logs
                        BlockState currentState = ((BlockGetter) level).getBlockState(leafPos);

                        if (currentState.is(Blocks.ACACIA_WOOD) ||
                                currentState.is(Blocks.TUFF)) {
                            continue; // skip leaves replacing logs
                        }

                        if (!currentState.isAir() && !currentState.canBeReplaced()) {
                            continue; // skip if block is solid and not replaceable
                        }

                        float density = 0.9f - (Math.abs(x) + Math.abs(z)) * 0.1f;
                        density = Math.max(density, 0.3f);

                        if (random.nextFloat() < density) {
                            BlockState leafBlock = random.nextFloat() < 0.75f
                                    ? ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get().defaultBlockState()
                                    : ModBlocks.SHADOW_PINE_LEAVES.get().defaultBlockState();
                            leafBlock = leafBlock.setValue(LeavesBlock.DISTANCE, 1);
                            setter.set(leafPos, leafBlock);
                        }
                    }
                }
            }

            int spireHeight = 1 + random.nextInt(3);

            for (int i = 1; i <= spireHeight; i++) {
                BlockPos spirePos = topPos.above(maxLeafHeight + i);

                if (level.isStateAtPosition(spirePos, state -> state.canBeReplaced())) {
                    BlockState spireLeaf = random.nextFloat() < 0.75f
                            ? ModBlocks.PURPLE_SHADOW_PINE_LEAVES.get().defaultBlockState()
                            : ModBlocks.SHADOW_PINE_LEAVES.get().defaultBlockState();

                    setter.set(spirePos, spireLeaf);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int treeHeight, TreeConfiguration config) {
        return 3 + random.nextInt(3);
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource random, int dx, int dy, int dz, int radius, boolean giantTrunk) {
        return dx * dx + dz * dz > radius * radius;
    }
}