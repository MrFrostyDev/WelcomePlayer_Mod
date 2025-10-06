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
    }
}
