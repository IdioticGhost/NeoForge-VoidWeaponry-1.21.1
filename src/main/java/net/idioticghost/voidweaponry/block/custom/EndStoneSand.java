package net.idioticghost.voidweaponry.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class EndStoneSand extends FallingBlock {
    public EndStoneSand() {
        super(Properties.of().mapColor(MapColor.SAND).strength(0.5f).sound(SoundType.SAND).pushReaction(PushReaction.DESTROY));



    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }
}
