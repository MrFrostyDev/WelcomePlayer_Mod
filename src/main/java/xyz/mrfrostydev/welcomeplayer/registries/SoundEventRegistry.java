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

    public static final DeferredHolder<SoundEvent, SoundEvent> BUZZ_SAW = SOUND_EVENTS.register(
            "buzz_saw",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "buzz_saw"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LASER_CHARGE = SOUND_EVENTS.register(
            "laser_charge",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "laser_charge"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LASER_BLAST = SOUND_EVENTS.register(
            "laser_blast",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "laser_blast"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SHOCK_HUM = SOUND_EVENTS.register(
            "shock_hum",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "shock_hum"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SHOCK_ZAP = SOUND_EVENTS.register(
            "shock_zap",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "shock_zap"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_BLAST = SOUND_EVENTS.register(
            "launcher_blast",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "launcher_blast"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LAUNCHER_CHARGE = SOUND_EVENTS.register(
            "launcher_charge",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "launcher_charge"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SHIELD_HIT = SOUND_EVENTS.register(
            "shield_hit",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "shield_hit"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> CONTRABAND_SIREN = SOUND_EVENTS.register(
            "contraband_siren",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "contraband_siren"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> PHASE_BLINK = SOUND_EVENTS.register(
            "phase_blink",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "phase_blink"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> PHASE_BLINK_BEEP = SOUND_EVENTS.register(
            "phase_blink_bleep",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "phase_blink_bleep"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> STASIS_IMPACT = SOUND_EVENTS.register(
            "stasis_impact",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "stasis_impact"
            ))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> LASER_CUTTER_SWING = SOUND_EVENTS.register(
            "laser_cutter_swing",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID,
                    "laser_cutter_swing"
            ))
    );
}
