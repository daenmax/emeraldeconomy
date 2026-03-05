package cn.daenx.emeraldeconomy;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

// 这里的值应该与 META-INF/neoforge.mods.toml 文件中的条目匹配
@Mod(Emeraldeconomy.MOD_ID)
public class Emeraldeconomy {
    // 在公共位置定义模组ID，方便所有地方引用
    public static final String MOD_ID = "emeraldeconomy";
    // 直接引用一个slf4j日志记录器
    public static final Logger LOGGER = LogUtils.getLogger();
    // 创建一个延迟注册表来注册方块，所有方块都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    // 创建一个延迟注册表来注册物品，所有物品都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    // 创建一个延迟注册表来持有创造模式标签页，所有标签页都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    // 创建一个ID为"emeraldeconomy:example_block"的新方块，结合了命名空间和路径
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", p -> p.mapColor(MapColor.STONE));
    // 创建一个ID为"emeraldeconomy:example_block"的新物品，结合了命名空间和路径
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // 创建一个ID为"emeraldeconomy:example_id"的新食物物品，营养值为1，饱食度为2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // 创建一个ID为"emeraldeconomy:example_tab"的创造模式标签页，用于存放示例物品，放置在战斗标签页之后
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.emeraldeconomy")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    // 模组类的构造函数是加载模组时运行的第一个代码。
    // FML会识别一些参数类型，如IEventBus或ModContainer，并自动传入。
    public Emeraldeconomy(IEventBus modEventBus, ModContainer modContainer) {
        // 注册commonSetup方法用于模组加载
        modEventBus.addListener(this::commonSetup);

        // 将延迟注册表注册到模组事件总线，以便注册方块
        BLOCKS.register(modEventBus);
        // 将延迟注册表注册到模组事件总线，以便注册物品
        ITEMS.register(modEventBus);
        // 将延迟注册表注册到模组事件总线，以便注册标签页
        CREATIVE_MODE_TABS.register(modEventBus);

        // 注册我们自己以接收服务器和其他我们感兴趣的游戏事件。
        // 注意：仅当我们希望*这个*类（Emeraldeconomy）直接响应事件时，才需要这样做。
        // 如果这个类中没有像下面的onServerStarting()那样带有@SubscribeEvent注解的函数，就不要添加这行代码。
        NeoForge.EVENT_BUS.register(this);

        // 将物品注册到创造模式标签页
        modEventBus.addListener(this::addCreative);

        // 注册我们模组的ModConfigSpec，以便FML可以为我们创建和加载配置文件
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // 一些通用的设置代码
        LOGGER.info("来自通用设置的问候");

        if (Config.LOG_DIRT_BLOCK.getAsBoolean()) {
            LOGGER.info("泥土方块 >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }

        LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(), Config.MAGIC_NUMBER.getAsInt());

        Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
    }

    // 将示例方块物品添加到建筑方块标签页
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // 你可以使用SubscribeEvent让事件总线自动发现要调用的方法
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // 服务器启动时执行某些操作
        LOGGER.info("来自服务器启动的问候");
    }
}