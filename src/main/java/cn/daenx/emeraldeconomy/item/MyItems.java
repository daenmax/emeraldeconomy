package cn.daenx.emeraldeconomy.item;

import cn.daenx.emeraldeconomy.Emeraldeconomy;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MyItems {
    // 创建一个延迟注册表来注册方块，所有方块都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Emeraldeconomy.MOD_ID);
    // 创建一个延迟注册表来注册物品，所有物品都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Emeraldeconomy.MOD_ID);

    // 创建一个ID为"emeraldeconomy:test_1"的新物品，结合了命名空间和路径
    public static final DeferredItem<Item> TEST_1 = ITEMS.registerSimpleItem("test_1", Item.Properties::new);
    public static final DeferredItem<Item> TEST_2 = ITEMS.registerSimpleItem("test_2", () -> new Item.Properties());


    // 创建一个ID为"emeraldeconomy:example_block"的新方块，结合了命名空间和路径
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", p -> p.mapColor(MapColor.STONE));
    // 创建一个ID为"emeraldeconomy:example_block"的新物品，结合了命名空间和路径
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // 创建一个ID为"emeraldeconomy:item_noodles"的新食物物品，营养值为1，饱食度为2
    public static final DeferredItem<Item> ITEM_NOODLES = ITEMS.registerSimpleItem("item_noodles", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));



    public static void register(IEventBus eventBus) {
        // 将延迟注册表注册到模组事件总线，以便注册方块
        BLOCKS.register(eventBus);
        // 将延迟注册表注册到模组事件总线，以便注册物品
        ITEMS.register(eventBus);
    }
}
