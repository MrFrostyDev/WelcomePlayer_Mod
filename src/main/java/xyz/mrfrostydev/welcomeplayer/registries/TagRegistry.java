package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class TagRegistry {

    // |------------------------------------------------------------------------------------|
    // |------------------------------------Block Tags--------------------------------------|
    // |------------------------------------------------------------------------------------|

    // |------------------------------------------------------------------------------------|
    // |-------------------------------------Item Tags--------------------------------------|
    // |------------------------------------------------------------------------------------|

    public static final TagKey<Item> BATTERY = TagKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "battery")
    );

    // |------------------------------------------------------------------------------------|
    // |------------------------------------Entity Tags-------------------------------------|
    // |------------------------------------------------------------------------------------|

    public static final TagKey<EntityType<?>> ANIMAL = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "animal")
    );

    public static final TagKey<EntityType<?>> UNDEAD = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "undead")
    );

    public static final TagKey<EntityType<?>> HOST_ROBOT = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "host_robot")
    );

    public static final TagKey<EntityType<?>> ARACHNID = TagKey.create(
            Registries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "arachnid")
    );

}
