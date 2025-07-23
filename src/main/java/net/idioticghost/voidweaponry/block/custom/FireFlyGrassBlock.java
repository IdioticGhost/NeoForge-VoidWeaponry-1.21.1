package net.idioticghost.voidweaponry.block.custom;

import net.idioticghost.voidweaponry.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Properties;

public class FireFlyGrassBlock extends GrassBlock {

    public FireFlyGrassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + 1.0 + random.nextDouble() * 0.2;
        double z = pos.getZ() + random.nextDouble();

        double dx = (random.nextDouble() - 0.5) * 0.01;
        double dy = (random.nextDouble() - 0.5) * 0.01;
        double dz = (random.nextDouble() - 0.5) * 0.01;

        level.addParticle(ModParticles.FIREFLY_PARTICLES.get(), x, y, z, dx, dy, dz);
     }
}