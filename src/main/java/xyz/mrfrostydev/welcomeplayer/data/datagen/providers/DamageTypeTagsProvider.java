package xyz.mrfrostydev.welcomeplayer.data.datagen.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.ModDamageTypes;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagsProvider extends net.minecraft.data.tags.DamageTypeTagsProvider {
    public DamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WelcomeplayerMain.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        this.tag(DamageTypeTags.BYPASSES_COOLDOWN)
                .add(
                        ModDamageTypes.SAW,
                        ModDamageTypes.SHOCK
                );

        this.tag(DamageTypeTags.NO_KNOCKBACK)
                .add(
                        ModDamageTypes.SAW,
                        ModDamageTypes.SHOCK
                );
    }
}
