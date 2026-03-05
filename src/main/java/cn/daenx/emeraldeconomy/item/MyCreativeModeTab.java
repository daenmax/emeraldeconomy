package cn.daenx.emeraldeconomy.item;

import cn.daenx.emeraldeconomy.Emeraldeconomy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MyCreativeModeTab {
    // 创建一个延迟注册表来持有创造模式标签页，所有标签页都将在"emeraldeconomy"命名空间下注册
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Emeraldeconomy.MOD_ID);

    // 创建一个ID为"emeraldeconomy:example_tab"的创造模式标签页，用于存放示例物品，放置在战斗标签页之后
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.emeraldeconomy"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> MyItems.ITEM_NOODLES.get().getDefaultInstance())
            // 将示例项添加到选项卡中。对于您自己的选项卡，此方法比事件更可取
            .displayItems((parameters, output) -> {
                output.accept(MyItems.ITEM_NOODLES.get());
                output.accept(MyItems.EXAMPLE_BLOCK_ITEM.get());
                output.accept(MyItems.TEST_1.get());
                output.accept(MyItems.TEST_2.get());
            }).build());

    public static void register(IEventBus eventBus) {
        // 将延迟注册表注册到模组事件总线，以便注册标签页
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
