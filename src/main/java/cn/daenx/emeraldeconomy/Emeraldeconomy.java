package cn.daenx.emeraldeconomy;

import cn.daenx.emeraldeconomy.block.MyBlocks;
import cn.daenx.emeraldeconomy.item.MyCreativeModeTab;
import cn.daenx.emeraldeconomy.item.MyItems;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

// 这里的值应该与 META-INF/neoforge.mods.toml 文件中的条目匹配
@Mod(Emeraldeconomy.MOD_ID)
public class Emeraldeconomy {
    // 在公共位置定义模组ID，方便所有地方引用
    public static final String MOD_ID = "emeraldeconomy";
    // 直接引用一个slf4j日志记录器
    public static final Logger LOGGER = LogUtils.getLogger();


    // 模组类的构造函数是加载模组时运行的第一个代码。
    // FML会识别一些参数类型，如IEventBus或ModContainer，并自动传入。
    public Emeraldeconomy(IEventBus modEventBus, ModContainer modContainer) {
        // 注册commonSetup方法用于模组加载
        modEventBus.addListener(this::commonSetup);

        //注册物品
        MyItems.register(modEventBus);
        //注册方块
        MyBlocks.register(modEventBus);

        //注册创造模式标签页
        MyCreativeModeTab.register(modEventBus);

        // 注册我们自己以接收服务器和其他我们感兴趣的游戏事件。
        // 注意：仅当我们希望*这个*类（Emeraldeconomy）直接响应事件时，才需要这样做。
        // 如果这个类中没有像下面的onServerStarting()那样带有@SubscribeEvent注解的函数，就不要添加这行代码。
        NeoForge.EVENT_BUS.register(this);


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



    // 你可以使用SubscribeEvent让事件总线自动发现要调用的方法
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // 服务器启动时执行某些操作
        LOGGER.info("来自服务器启动的问候");
    }
}