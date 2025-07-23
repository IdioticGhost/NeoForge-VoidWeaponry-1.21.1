package net.idioticghost.voidweaponry.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Properties;

public class LeafPileBlock extends Block {
    public LeafPileBlock(Properties properties) {
        super(properties.noCollission());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if (!entity.isSpectator() && !entity.isPassenger()) {
            entity.setDeltaMovement(
                    entity.getDeltaMovement().multiply(0.9, 0.9, 0.9)
            );
            if (entity.getDeltaMovement().y < 0.0) {
                entity.setDeltaMovement(entity.getDeltaMovement().x, -0.05, entity.getDeltaMovement().z);
            }
            entity.fallDistance = 0;
        }
    }
}