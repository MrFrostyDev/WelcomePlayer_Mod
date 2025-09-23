package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class SoundEventRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus event){
        SOUND_EVENTS.register(event);
    }

    // All vanilla sounds use variable range events.
    public static final DeferredHolder<SoundEvent, SoundEvent> BOUNCE_PAD_JUMP = SOUND_EVENTS.register(
            "bounce_pad_jump", // must match the resource location on the next line
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "bounce_pad_jump"
            ))
    );
}
