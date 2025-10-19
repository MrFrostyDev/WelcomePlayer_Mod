package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class PlayerUtil {
    /**
     * Method to add an attribute modifier to a player. This uses an {@link AttributeModifierPair}.
     */
    public static void addAttributeModifier(Player player, AttributeModifierPair attributeModifier) {
        AttributeInstance atrInstance = player.getAttribute(attributeModifier.attribute);
        if (atrInstance == null) return;
        atrInstance.addOrReplacePermanentModifier(attributeModifier.modifier);
    }

    /**
     * Method to remove an attribute modifier from a player. This requires an {@link AttributeModifierPair}.
     */
    public static void removeAttributeModifier(Player player, AttributeModifierPair attributeModifier) {
        AttributeInstance atrInstance = player.getAttribute(attributeModifier.attribute);
        if (atrInstance == null) return;
        atrInstance.removeModifier(attributeModifier.modifier);
    }

    private static AttributeModifierPair createAttributeModifier(Holder<Attribute> attribute, String path, double value, AttributeModifier.Operation operation){
        return new AttributeModifierPair(attribute,
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, path),
                        value,
                        operation)
        );
    }

    public record AttributeModifierPair(Holder<Attribute> attribute, AttributeModifier modifier){};
}
