package net.idioticghost.voidweaponry.worldgen.biome.surface;

import net.idioticghost.voidweaponry.block.ModBlocks;

import net.idioticghost.voidweaponry.worldgen.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

    public class ModSurfaceRules {

        private static final SurfaceRules.RuleSource VOID_TOP = makeStateRule(ModBlocks.ENDSTONE_SAND_BLOCK.get());
        private static final SurfaceRules.RuleSource VOID_FILLER = makeStateRule(ModBlocks.ENDSTONE_SAND_BLOCK.get());
        private static final SurfaceRules.RuleSource VOID_STONE = makeStateRule(Blocks.END_STONE);

        public static SurfaceRules.RuleSource makeRules() {
            return SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.isBiome(ModBiomes.DRIED_DEPTHS, ModBiomes.CORAL_WASTES),
                            SurfaceRules.sequence(
                                    SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, VOID_TOP),
                                    SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, VOID_FILLER),
                                    SurfaceRules.ifTrue(SurfaceRules.DEEP_UNDER_FLOOR, VOID_STONE),
                                    SurfaceRules.ifTrue(SurfaceRules.VERY_DEEP_UNDER_FLOOR, VOID_STONE)
                            )
                    )
            );
        }

        private static SurfaceRules.RuleSource makeStateRule(Block block) {
            return SurfaceRules.state(block.defaultBlockState());
        }
    }