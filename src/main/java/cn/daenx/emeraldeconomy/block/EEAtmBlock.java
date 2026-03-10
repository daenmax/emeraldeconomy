package cn.daenx.emeraldeconomy.block;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class EEAtmBlock extends Block {
    // 修改：使用 EnumProperty 替代 DirectionProperty
    // BlockStateProperties.HORIZONTAL_FACING 本身就是 EnumProperty<Direction> 类型
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public EEAtmBlock(Properties properties) {
        super(properties);
        // 设置默认状态为朝北
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        // 注册方向属性
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        // 获取玩家放置时的方向，并取反使方块正面朝向玩家
        Direction facing = context.getPlayer().getDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, facing);
    }
}