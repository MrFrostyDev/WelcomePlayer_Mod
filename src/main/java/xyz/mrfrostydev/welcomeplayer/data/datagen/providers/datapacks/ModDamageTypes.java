package xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;

public interface ModDamageTypes {
    ResourceKey<DamageType> SAW = registerKey("saw");
    ResourceKey<DamageType> LASER = registerKey("laser");

    static void bootstrap(BootstrapContext<DamageType> context) {
        context.register(SAW, new DamageType("saw", 0.1F));
        context.register(LASER, new DamageType("laser", 0.1F));
    }

    static ResourceKey<DamageType> registerKey(String id){
        return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(WelcomeplayerMain.MOD_ID, id));
    }
}
