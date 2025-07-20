package net.idioticghost.voidweaponry.block;

import net.idioticghost.voidweaponry.VoidWeaponry;
import net.idioticghost.voidweaponry.block.custom.*;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.worldgen.tree.ModTreeGrowers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(VoidWeaponry.MOD_ID);

    //MISC
    public static final DeferredBlock<Block> VOID_LANTERN = registerBlock("void_lantern",
            () -> new VoidLantern(BlockBehaviour.Properties.of().strength(3f)
                    .lightLevel(state -> state.getValue(VoidLantern.CLICKED) ? 15 : 0)) {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
                    if (Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.void_lantern_shift_down_1.tooltip"));
                        pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.void_lantern_shift_down_2.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.void_lantern_1.tooltip"));
                        pTooltipComponents.add(Component.translatable("tooltip.voidweaponry.void_lantern_2.tooltip"));
                    }
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }});

    //Normal Blocks


    public static final DeferredBlock<Block> ENDSTONE_SAND_BLOCK = registerBlock("endstone_sand_block",
            () -> new EndStoneSand());

    public static final DeferredBlock<Block> VOID_KELP_BLOCK = BLOCKS.register("void_kelp_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WET_GRASS)));


    //ORES

    public static final DeferredBlock<Block> VOID_ORE = registerBlock("void_ore",
            () -> new DropExperienceBlock(UniformInt.of(2,4),BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    //FLORA
        //SHADOWPINE

    public static final DeferredBlock<Block> VOIDGROWTH_PLANKS = registerBlock("voidgrowth_planks",
            () -> new Block(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.WOOD)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<StairBlock> VOIDGROWTH_STAIRS = registerBlock("voidgrowth_stairs",
            () -> new StairBlock(ModBlocks.VOIDGROWTH_PLANKS.get().defaultBlockState(),BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<SlabBlock> VOIDGROWTH_SLAB = registerBlock("voidgrowth_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<PressurePlateBlock> VOIDGROWTH_PRESSURE_PLATE = registerBlock("voidgrowth_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.ACACIA,BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<ButtonBlock> VOIDGROWTH_BUTTON = registerBlock("voidgrowth_button",
            () -> new ButtonBlock(BlockSetType.ACACIA, 15, BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<FenceBlock> VOIDGROWTH_FENCE = registerBlock("voidgrowth_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<FenceGateBlock> VOIDGROWTH_FENCE_GATE = registerBlock("voidgrowth_fence_gate",
            () -> new FenceGateBlock(WoodType.ACACIA, BlockBehaviour.Properties.of().strength(2f)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<DoorBlock> VOIDGROWTH_DOOR = registerBlock("voidgrowth_door",
            () -> new DoorBlock(BlockSetType.ACACIA, BlockBehaviour.Properties.of().strength(2f).noOcclusion()) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<TrapDoorBlock> VOIDGROWTH_TRAPDOOR = registerBlock("voidgrowth_trapdoor",
            () -> new TrapDoorBlock(BlockSetType.ACACIA, BlockBehaviour.Properties.of().strength(2f).noOcclusion()) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<RotatedPillarBlock> SHADOW_PINE_LOG = registerBlock("shadow_pine_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> SHADOW_PINE_WOOD = registerBlock("shadow_pine_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SHADOW_PINE_LOG = registerBlock("stripped_shadow_pine_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_SHADOW_PINE_WOOD = registerBlock("stripped_shadow_pine_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));

    public static final DeferredBlock<Block> GNARLED_SHADOW_PINE = registerBlock("gnarled_shadow_pine",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)));
    public static final DeferredBlock<Block> SHADOW_PINE_LEAVES = registerBlock("shadow_pine_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)) {
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return false;
                }
            });
    public static final DeferredBlock<Block> SHADOW_PINE_SAPLING = registerBlock("shadow_pine_sapling",
            () -> new SaplingBlock(ModTreeGrowers.SHADOW_PINE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)));

    //PLANTS

    public static final DeferredBlock<Block> VOID_KELP_CROP = BLOCKS.register("void_kelp_crop",
            () -> new VoidKelpCrop());

    public static final DeferredBlock<Block> VOID_SEAGRASS = BLOCKS.register("void_seagrass",
            () -> new VoidSeagrass());

    public static final DeferredBlock<Block> VOID_KELP_TOP = BLOCKS.register("void_kelp_top",
            () -> new VoidKelpTopBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE) // or MapColor.WATER if you want blue
                    .noCollission()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.WET_GRASS)
                    .pushReaction(PushReaction.DESTROY)
            )
    );

    public static final DeferredBlock<Block> NAUTILUS_SHELL_BLOCK = registerBlock("nautilus_shell_block",
            () -> new NautilusShellBlock(BlockBehaviour.Properties.of().noOcclusion()));

    //MACHINES

    public static final DeferredBlock<Block> VOID_CRAFTER = BLOCKS.register("void_crafter",
            () -> new VoidCrafterBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .noOcclusion()
            )
    );


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}