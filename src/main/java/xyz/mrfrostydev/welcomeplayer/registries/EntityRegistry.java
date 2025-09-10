package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }


    // |----------------------------------------------------------------------------------|
    // |-------------------------------------Entities-------------------------------------|
    // |----------------------------------------------------------------------------------|

    // |----------------------------------------------------------------------------------|
    // |--------------------------------------Items---------------------------------------|
    // |----------------------------------------------------------------------------------|
}
