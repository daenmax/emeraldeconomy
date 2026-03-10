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
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Emeraldeconomy.MOD_ID);

    // 修改：使用自定义的 EEAtmBlock 类而不是普通的 Block 类
    public static final DeferredBlock<EEAtmBlock> EE_ATM_BLOCK = registerBlock("ee_atm_block",
            properties -> new EEAtmBlock(properties.strength(1.0f, 6.0f).requiresCorrectToolForDrops()), true);

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, Supplier<BlockBehaviour.Properties> properties, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func, properties);
        if (shouldRegisterItem) MyItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> func, boolean shouldRegisterItem) {
        DeferredBlock<T> block = BLOCKS.registerBlock(name, func);
        if (shouldRegisterItem) MyItems.ITEMS.registerSimpleBlockItem(block);
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}