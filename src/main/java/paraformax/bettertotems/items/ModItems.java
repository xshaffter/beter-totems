package paraformax.bettertotems.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import paraformax.bettertotems.BetterTotems;
import paraformax.bettertotems.items.custom.Heart;
import paraformax.bettertotems.items.custom.HeartFragment;
import paraformax.bettertotems.items.custom.TotemFragment;
import paraformax.bettertotems.items.totems.*;
import paraformax.bettertotems.items.totems.boss_totems.ThunderTotem;

public class ModItems {
    public static final Item ENHANCED_TOTEM = new EnhancedTotem();
    public static final Item INVENTORY_TOTEM = new InventoryTotem();
    public static final Item TANK_TOTEM = new TankTotem();
    public static final Item CURSED_TOTEM = new CursedTotem();
    public static final Item CHORUS_TOTEM = new ChorusTotem();
    public static final Item TOTEM_FRAGMENT = new TotemFragment();
    public static final Item HEART = new Heart();
    public static final Item HEART_FRAGMENT = new HeartFragment();
    public static final Item THUNDER_TOTEM = new ThunderTotem();

    private static void registerItem(String name, Item item) {
        Registry.register(Registry.ITEM, new Identifier(BetterTotems.MOD_ID, name), item);
    }

    public static void registerModItems() {
        registerItem("enhanced_totem", ENHANCED_TOTEM);
        registerItem("inventory_totem", INVENTORY_TOTEM);
        registerItem("tank_totem", TANK_TOTEM);
        registerItem("cursed_totem", CURSED_TOTEM);
        registerItem("chorus_totem", CHORUS_TOTEM);
        registerItem("totem_fragment", TOTEM_FRAGMENT);
        registerItem("heart", HEART);
        registerItem("heart_fragment", HEART_FRAGMENT);
        registerItem("thunder_totem", THUNDER_TOTEM);
    }
}
