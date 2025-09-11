package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;

import java.util.function.Predicate;

public class BatteryUtil {
    public static final Predicate<Holder<Item>> IS_BATTERY = new Predicate<Holder<Item>>() {
        @Override
        public boolean test(Holder<Item> item) {
            return item.is(TagRegistry.BATTERY);
        }
    };
}
