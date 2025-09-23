package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.items.BouncePadEntity;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }


    // |----------------------------------------------------------------------------------|
    // |-------------------------------------Entities-------------------------------------|
    // |----------------------------------------------------------------------------------|
    public static final DeferredHolder<EntityType<?>, EntityType<BouncePadEntity>> BOUNCE_PAD = ENTITIES.register(
            "bounce_pad",
            () -> EntityType.Builder.<BouncePadEntity>of(BouncePadEntity::new, MobCategory.MISC)
                    .sized(0.8F, 0.2F)
                    .eyeHeight(0.2F)
                    .clientTrackingRange(10)
                    .build("bounce_pad")
    );


    // |----------------------------------------------------------------------------------|
    // |--------------------------------------Items---------------------------------------|
    // |----------------------------------------------------------------------------------|
}
