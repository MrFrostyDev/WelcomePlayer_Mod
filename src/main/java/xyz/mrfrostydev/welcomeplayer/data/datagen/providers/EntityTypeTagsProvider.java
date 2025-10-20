package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.registries.EntityRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagsProvider extends net.minecraft.data.tags.EntityTypeTagsProvider {
    public EntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WelcomeplayerMain.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(TagRegistry.ANIMAL)
                .add(
                        EntityType.COW,
                        EntityType.PIG,
                        EntityType.SHEEP,
                        EntityType.GOAT,
                        EntityType.CHICKEN,
                        EntityType.SQUID,
                        EntityType.GLOW_SQUID,
                        EntityType.WOLF,
                        EntityType.CAMEL,
                        EntityType.CAT,
                        EntityType.PARROT,
                        EntityType.PANDA,
                        EntityType.DONKEY,
                        EntityType.HORSE,
                        EntityType.MOOSHROOM,
                        EntityType.HOGLIN,
                        EntityType.RAVAGER
                );

        this.tag(TagRegistry.HOST_ROBOT)
                .add(
                        EntityRegistry.SERVICE_BOT.get(),
                        EntityRegistry.HANDIBOT.get(),
                        EntityRegistry.ERADICATOR.get()
                );

        this.tag(TagRegistry.UNDEAD)
                .add(
                        EntityType.SKELETON,
                        EntityType.SKELETON_HORSE,
                        EntityType.WITHER_SKELETON,
                        EntityType.BOGGED,
                        EntityType.STRAY,
                        EntityType.ZOMBIE,
                        EntityType.ZOMBIE_VILLAGER,
                        EntityType.ZOMBIE_HORSE,
                        EntityType.HUSK,
                        EntityType.DROWNED
                );

        this.tag(TagRegistry.BUG)
                .add(
                        EntityType.SPIDER,
                        EntityType.CAVE_SPIDER,
                        EntityType.SILVERFISH,
                        EntityType.BEE
                );

        this.tag(TagRegistry.SCARY)
                .add(
                        EntityType.SPIDER,
                        EntityType.CAVE_SPIDER,
                        EntityType.PILLAGER,
                        EntityType.VINDICATOR,
                        EntityType.EVOKER,
                        EntityType.RAVAGER,
                        EntityType.ILLUSIONER,
                        EntityType.ZOMBIE,
                        EntityType.SKELETON,
                        EntityType.ZOMBIE_HORSE,
                        EntityType.SKELETON_HORSE,
                        EntityType.WITHER_SKELETON,
                        EntityType.CREEPER,
                        EntityType.DROWNED,
                        EntityType.STRAY,
                        EntityType.HUSK,
                        EntityType.WARDEN,
                        EntityType.BOGGED,
                        EntityType.SILVERFISH,
                        EntityType.ENDERMAN,
                        EntityType.PHANTOM,
                        EntityType.BLAZE
                );
    }
}
