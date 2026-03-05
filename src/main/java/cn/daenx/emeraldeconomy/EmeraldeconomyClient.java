package cn.daenx.emeraldeconomy;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

// 这个类不会在专用服务器上加载。从这里访问客户端代码是安全的。
@Mod(value = Emeraldeconomy.MOD_ID, dist = Dist.CLIENT)
// 你可以使用EventBusSubscriber自动注册类中所有带有@SubscribeEvent注解的静态方法
@EventBusSubscriber(modid = Emeraldeconomy.MOD_ID, value = Dist.CLIENT)
public class EmeraldeconomyClient {
    public EmeraldeconomyClient(ModContainer container) {
        // 允许NeoForge为这个模组的配置创建一个配置界面。
        // 配置界面的访问方式：进入模组界面 > 点击你的模组 > 点击配置。
        // 不要忘记将你的配置选项的翻译添加到en_us.json文件中。
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        // 一些客户端设置代码
        Emeraldeconomy.LOGGER.info("来自客户端设置的问候");
        Emeraldeconomy.LOGGER.info("MINECRAFT用户名 >> {}", Minecraft.getInstance().getUser().getName());
    }
}