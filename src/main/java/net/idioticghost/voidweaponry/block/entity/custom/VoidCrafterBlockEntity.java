package net.idioticghost.voidweaponry.block.entity.custom;


import net.idioticghost.voidweaponry.block.entity.ModBlockEntities;
import net.idioticghost.voidweaponry.item.ModItems;
import net.idioticghost.voidweaponry.particle.ModParticles;
import net.idioticghost.voidweaponry.screen.custom.VoidCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

import java.util.Optional;

public class VoidCrafterBlockEntity extends BlockEntity implements GeoBlockEntity, MenuProvider {

    private static final int OUTPUT_SLOT = 9;
    private static final int MAX_PROGRESS = 360;

    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);
    public final ItemStackHandler itemHandler = createItemHandler();

    private Optional<IItemHandler> itemHandlerCapability = Optional.of(itemHandler);
    private final ContainerData data;

    private int progress = 0;
    private float rotation;

    // Animation state
    private AnimationController<VoidCrafterBlockEntity> animationController;
    private String currentAnimation = "";
    private long lastSwitchTick = 0;
    private boolean craftingAnimationPlayed = false;
    private boolean wasCrafting = false;

    public VoidCrafterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_CRAFTER_BE.get(), pos, state);
        this.data = createContainerData();
    }

    /* ------------------------ Client & Server Ticks ------------------------ */

    public static void clientTick(Level level, BlockPos pos, BlockState state, VoidCrafterBlockEntity be) {
        if (be.isCrafting()) {
            be.spawnParticles();
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, VoidCrafterBlockEntity be) {
        if (be.hasRecipe()) {
            be.increaseCraftingProgress();
            if (!be.wasCrafting) {
                be.craftingAnimationPlayed = false;
                be.wasCrafting = true;
            }

            if (be.hasCraftingFinished()) {
                be.craftItem();
                be.resetProgress();
                be.wasCrafting = false;
            }

            setChanged(level, pos, state);
            level.sendBlockUpdated(pos, state, state, 3);
        } else {
            be.resetProgress();
            be.wasCrafting = false;
            be.craftingAnimationPlayed = false;
        }
    }

    private void spawnParticles() {
        if (level == null) return;

        double blockX = worldPosition.getX();
        double blockY = worldPosition.getY();
        double blockZ = worldPosition.getZ();


        double targetX = blockX + 0.5;
        double targetY = blockY + 1.5;
        double targetZ = blockZ + 0.5;

        double spawnX = blockX + 0.5 + (level.random.nextDouble() - 0.5) * 2.0;
        double spawnY = blockY + 0.5 + (level.random.nextDouble()) * 2.0;
        double spawnZ = blockZ + 0.5 + (level.random.nextDouble() - 0.5) * 2.0;

        level.addParticle(
                ModParticles.VOID_WATCHER_PARTICLES.get(),
                spawnX, spawnY, spawnZ,
                targetX, targetY, targetZ
        );
    }
    /* ------------------------ Crafting Logic ------------------------ */

    private void resetProgress() {
        progress = 0;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean hasCraftingFinished() {
        return progress >= MAX_PROGRESS;
    }

    private boolean hasRecipe() {
        for (int i = 0; i < 9; i++) {
            if (!itemHandler.getStackInSlot(i).is(ModItems.VOID_SHARD.get())) {
                return false;
            }
        }

        ItemStack output = new ItemStack(ModItems.VOIDBOUND_GOLD.get());
        return canInsertItemIntoOutputSlot(output) && canInsertAmountIntoOutputSlot(output.getCount());
    }

    private void craftItem() {
        for (int i = 0; i < 9; i++) {
            itemHandler.extractItem(i, 1, false);
        }

        ItemStack output = new ItemStack(ModItems.VOIDBOUND_GOLD.get());
        ItemStack currentOutput = itemHandler.getStackInSlot(OUTPUT_SLOT);
        itemHandler.setStackInSlot(OUTPUT_SLOT,
                new ItemStack(output.getItem(), currentOutput.getCount() + output.getCount()));
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        ItemStack current = itemHandler.getStackInSlot(OUTPUT_SLOT);
        return current.isEmpty() || current.getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        ItemStack current = itemHandler.getStackInSlot(OUTPUT_SLOT);
        int max = current.isEmpty() ? 64 : current.getMaxStackSize();
        return current.getCount() + count <= max;
    }

    /* ------------------------ GeckoLib Animation ------------------------ */

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        animationController = new AnimationController<>(this, "controller", 0, this::predicate);
        controllers.add(animationController);
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {
        AnimationController<T> controller = state.getController();
        long tick = (long) getTick(this);

        if (isCrafting()) {
            if (!craftingAnimationPlayed) {
                craftingAnimationPlayed = true;
                currentAnimation = "animation.model.crafting";
                controller.setAnimation(RawAnimation.begin()
                        .then(currentAnimation, Animation.LoopType.PLAY_ONCE)
                        .then("animation.model.idle1", Animation.LoopType.LOOP));
                lastSwitchTick = tick;
            }
        } else {
            if (craftingAnimationPlayed) {
                craftingAnimationPlayed = false;
                currentAnimation = "animation.model.idle1";
                controller.setAnimation(RawAnimation.begin().then(currentAnimation, Animation.LoopType.LOOP));
                lastSwitchTick = tick;
            }

            if (tick - lastSwitchTick >= 360) {
                String next = chooseIdleAnimation();
                if (!next.equals(currentAnimation)) {
                    controller.setAnimation(RawAnimation.begin().then(next, Animation.LoopType.LOOP));
                    currentAnimation = next;
                    lastSwitchTick = tick;
                }
            }
        }

        return PlayState.CONTINUE;
    }

    private String chooseIdleAnimation() {
        double r = Math.random();
        if (r < 0.6) return "animation.model.idle1";
        else if (r < 0.7) return "animation.model.idle4";
        else if (r < 0.8) return "animation.model.idle5";
        else if (r < 0.9) return "animation.model.idle6";
        else if (r < 0.95) return "animation.model.idle2";
        else return "animation.model.idle3";
    }

    /* ------------------------ Inventory, UI, Data ------------------------ */

    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(10) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return slot >= 0 && slot < 9;
            }
        };
    }

    private ContainerData createContainerData() {
        return new ContainerData() {
            @Override public int get(int index) { return index == 0 ? progress : MAX_PROGRESS; }
            @Override public void set(int index, int value) { if (index == 0) progress = value; }
            @Override public int getCount() { return 2; }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.voidweaponry.void_crafter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        return new VoidCrafterMenu(id, inv, this, this.data);
    }

    public ItemStack getRenderedItemStack() {
        return isCrafting() ? itemHandler.getStackInSlot(4) : itemHandler.getStackInSlot(OUTPUT_SLOT);
    }

    public boolean isCrafting() {
        return progress > 0;
    }

    public float getRenderingRotation() {
        rotation += 0.5f;
        return rotation %= 360;
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    /* ------------------------ Capabilities & Saving ------------------------ */

    @Override
    public void onLoad() {
        super.onLoad();
        itemHandlerCapability = Optional.of(itemHandler);
    }

    public Optional<IItemHandler> getItemHandler() {
        return itemHandlerCapability;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider lookup) {
        tag.put("inventory", itemHandler.serializeNBT(lookup));
        tag.putInt("void_crafter.progress", progress);
        super.saveAdditional(tag, lookup);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider lookup) {
        super.loadAdditional(tag, lookup);
        itemHandler.deserializeNBT(lookup, tag.getCompound("inventory"));
        progress = tag.getInt("void_crafter.progress");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}