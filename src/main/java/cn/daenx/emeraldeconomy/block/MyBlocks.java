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
    // 修改这里：在 properties 中添加 lightLevel 设置
    public static final DeferredBlock<EEAtmBlock> EE_ATM_BLOCK = registerBlock("ee_atm_block",
            properties -> new EEAtmBlock(properties
                .strength(1.0f, 6.0f)//硬度
                .requiresCorrectToolForDrops()//必须使用要求的工具挖掘才能掉落
                .lightLevel(state -> 15)  // 添加这一行，设置亮度为15
            ), true);

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