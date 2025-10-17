package xyz.mrfrostydev.welcomeplayer.registries;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class DataAttachmentRegistry {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> DAMAGE_SHIELD = ATTACHMENT_TYPES.register(
            "damage_shield",
            () -> AttachmentType.builder(holder -> 2).serialize(Codec.INT)
                    .copyOnDeath()
                    .build()
    );

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Integer>> REPULSION_COOLDOWN = ATTACHMENT_TYPES.register(
            "repulsion_cooldown",
            () -> AttachmentType.builder(holder -> 0).serialize(Codec.INT)
                    .copyOnDeath()
                    .build()
    );
}
