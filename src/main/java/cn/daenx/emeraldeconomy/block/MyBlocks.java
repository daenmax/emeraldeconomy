package cn.daenx.emeraldeconomy.block;

import cn.daenx.emeraldeconomy.Emeraldeconomy;
import cn.daenx.emeraldeconomy.item.MyItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class MyBlocks {
    // 创建一个延迟注册表来注册方块，所有方块都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Emeraldeconomy.MOD_ID);

    // 创建一个ID为"emeraldeconomy:ee_atm_block"的新方块，结合了命名空间和路径
    public static final DeferredBlock<Block> EE_ATM_BLOCK = registerBlock("ee_atm_block",
            properties -> new Block(properties.strength(1.0f, 6.0f).requiresCorrectToolForDrops()), true);


    private static <T extends Block>DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, Supplier<BlockBehaviour.Properties> properties, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func, properties);
        if (shouldRegisterItem) MyItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    private static <T extends Block>DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func);
        if (shouldRegisterItem) MyItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }


    public static void register(IEventBus eventBus) {
        // 将延迟注册表注册到模组事件总线，以便注册方块
        BLOCKS.register(eventBus);
    }
}
