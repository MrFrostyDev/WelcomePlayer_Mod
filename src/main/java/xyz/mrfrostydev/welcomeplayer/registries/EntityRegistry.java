package xyz.mrfrostydev.welcomeplayer.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.entities.items.BouncePadEntity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.service_bot.ServiceBotEntity;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.LaserBlastProjectile;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.eradicator.EradicatorEntity;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot.HandibotEntity;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, WelcomeplayerMain.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }


    // |----------------------------------------------------------------------------------|
    // |-------------------------------------Entities-------------------------------------|
    // |----------------------------------------------------------------------------------|

    // |----------------------------------------------------------------------------------|
    // |-------------------------------------Entities-------------------------------------|
    // |----------------------------------------------------------------------------------|
    public static final DeferredHolder<EntityType<?>, EntityType<ServiceBotEntity>> SERVICE_BOT = ENTITIES.register(
            "service_bot",
            () -> EntityType.Builder.<ServiceBotEntity>of(ServiceBotEntity::new, MobCategory.CREATURE)
                    .sized(0.8F, 1.3F)
                    .eyeHeight(1.2F)
                    .clientTrackingRange(8)
                    .build("service_bot")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<HandibotEntity>> HANDIBOT = ENTITIES.register(
            "handibot",
            () -> EntityType.Builder.<HandibotEntity>of(HandibotEntity::new, MobCategory.MONSTER)
                    .sized(0.9F, 2.0F)
                    .eyeHeight(1.7F)
                    .clientTrackingRange(8)
                    .build("handibot")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<EradicatorEntity>> ERADICATOR = ENTITIES.register(
            "eradicator",
            () -> EntityType.Builder.<EradicatorEntity>of(EradicatorEntity::new, MobCategory.MONSTER)
                    .sized(2.6F, 3.2F)
                    .eyeHeight(2.6F)
                    .clientTrackingRange(8)
                    .build("eradicator")
    );

    // |----------------------------------------------------------------------------------|
    // |--------------------------------------Items---------------------------------------|
    // |----------------------------------------------------------------------------------|
    public static final DeferredHolder<EntityType<?>, EntityType<BouncePadEntity>> BOUNCE_PAD = ENTITIES.register(
            "bounce_pad",
            () -> EntityType.Builder.<BouncePadEntity>of(BouncePadEntity::new, MobCategory.MISC)
                    .sized(0.8F, 0.2F)
                    .eyeHeight(0.2F)
                    .clientTrackingRange(10)
                    .build("bounce_pad")
    );

    public static final DeferredHolder<EntityType<?>, EntityType<LaserBlastProjectile>> LASER_BLAST_PROJECTILE = ENTITIES.register(
            "laser_blast_projectile",
            () -> EntityType.Builder.<LaserBlastProjectile>of(LaserBlastProjectile::new, MobCategory.MISC)
                    .sized(0.4F, 0.4F)
                    .eyeHeight(0.1F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("laser_blast_projectile")
            );
}
