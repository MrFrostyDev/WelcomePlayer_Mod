package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;

public class SoundDefinitionsProvider extends net.neoforged.neoforge.common.data.SoundDefinitionsProvider {
    public SoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, WelcomeplayerMain.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        add(SoundEventRegistry.BOUNCE_PAD_JUMP, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "bounce_pad_jump"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                // Enables streaming. Useful if audio is longer than a few seconds.
                                // Also has a parameterless overload that defers to stream(true).
                                .stream(false)
                                // If true, the sound will be loaded into memory on pack load, instead of when the sound is played.
                                // Vanilla uses this for underwater ambience sounds. Defaults to false.
                                // Also has a parameterless overload that defers to preload(true).
                                .preload(false),
                        sound("welcomeplayer:bounce_pad_jump")
                )
                .subtitle("sound.welcomeplayer.bounce_pad_jump")
                .replace(false)
        );

        add(SoundEventRegistry.BUZZ_SAW, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "buzz_saw"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:buzz_saw")
                )
                .subtitle("sound.welcomeplayer.buzz_saw")
                .replace(false)
        );

        add(SoundEventRegistry.LASER_CHARGE, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "laser_charge"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:laser_charge")
                )
                .subtitle("sound.welcomeplayer.laser_charge")
                .replace(false)
        );

        add(SoundEventRegistry.LASER_BLAST, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "laser_blast"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:laser_blast")
                )
                .subtitle("sound.welcomeplayer.laser_blast")
                .replace(false)
        );


        add(SoundEventRegistry.SHOCK_HUM, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_hum"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:shock_hum")
                )
                .subtitle("sound.welcomeplayer.shock_hum")
                .replace(false)
        );

        add(SoundEventRegistry.SHOCK_ZAP, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "shock_zap"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:shock_zap")
                )
                .subtitle("sound.welcomeplayer.shock_zap")
                .replace(false)
        );

        add(SoundEventRegistry.LAUNCHER_BLAST, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "launcher_blast"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:launcher_blast")
                )
                .subtitle("sound.welcomeplayer.launcher_blast")
                .replace(false)
        );

        add(SoundEventRegistry.LAUNCHER_CHARGE, SoundDefinition.definition()
                .with(
                        sound(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, "launcher_charge"))
                                .volume(1.0F)
                                .pitch(1.0F)
                                .weight(2)
                                .attenuationDistance(8)
                                .stream(false)
                                .preload(false),
                        sound("welcomeplayer:launcher_charge")
                )
                .subtitle("sound.welcomeplayer.launcher_charge")
                .replace(false)
        );
    }
}
