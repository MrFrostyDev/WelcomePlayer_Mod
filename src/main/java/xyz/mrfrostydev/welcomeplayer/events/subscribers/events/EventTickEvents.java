package xyz.mrfrostydev.welcomeplayer.events.subscribers.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceEvent;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.AudienceEvents;
import xyz.mrfrostydev.welcomeplayer.entities.mobs.handibot.HandibotEntity;
import xyz.mrfrostydev.welcomeplayer.registries.DataAttachmentRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.SoundEventRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceEventUtil;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;

import javax.annotation.Nullable;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class EventTickEvents {

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event){
        if(!(event.getLevel() instanceof ServerLevel svlevel))return;
        AudienceEvent goingEvent = AudienceEventUtil.getGoingEvent(svlevel);
        int levelTickCount = event.getLevel().getServer().getTickCount();

        if(levelTickCount % 400 == 0 && goingEvent.is(AudienceEvents.CONTRABAND)){
            svlevel.players().forEach(p -> {
                if(p.isHolding(i -> i.is(TagRegistry.CONTRABAND) && canBeSeenOnSurface(svlevel, p))){
                    int offset = 0;
                    for(int i=0; i<5; i++){
                        BlockPos pos = findRandomSpawnPos(svlevel, p.getOnPos(), offset, 20);
                        if(pos == null) pos = p.getOnPos();
                        HandibotEntity bot = new HandibotEntity(svlevel, false);
                        bot.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                        bot.setTarget(p);
                        bot.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, p);
                        svlevel.addFreshEntity(bot);
                        offset++;
                    }

                    svlevel.playSound(null,
                            p.getX(), p.getEyeY(), p.getZ(),
                            SoundEventRegistry.CONTRABAND_SIREN.get(), SoundSource.NEUTRAL,
                            1.0F, svlevel.random.nextFloat() * 0.4F + 0.8F
                    );
                    AudienceUtil.sendDialog(Component.translatable("dialog.welcomeplayer.event.contraband.caught"), p);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onTickEntity(EntityTickEvent.Post event){
       Entity entity = event.getEntity();

       int tick = event.getEntity().tickCount;
       if(tick % 20 == 0){
           if (entity.hasData(DataAttachmentRegistry.REPULSION_COOLDOWN.get())){
               int cooldown = entity.getData(DataAttachmentRegistry.REPULSION_COOLDOWN.get());
               if(cooldown > 0){
                   entity.setData(DataAttachmentRegistry.REPULSION_COOLDOWN.get(), cooldown - 1);
               }
               else{
                   entity.removeData(DataAttachmentRegistry.REPULSION_COOLDOWN.get());
               }
           }
       }
    }

    @Nullable
    private static BlockPos findRandomSpawnPos(ServerLevel svlevel, BlockPos targetPos, int offsetMultiplier, int maxTry) {
        int i = offsetMultiplier == 0 ? 2 : 2 - offsetMultiplier;
        BlockPos.MutableBlockPos pPos = new BlockPos.MutableBlockPos();
        SpawnPlacementType spawnplacementtype = SpawnPlacements.getPlacementType(EntityType.RAVAGER);

        for (int i1 = 0; i1 < maxTry; i1++) {
            float f = svlevel.random.nextFloat() * (float) (Math.PI * 2);
            int j = targetPos.getX() + Mth.floor(Mth.cos(f) * 10.0F * (float)i) + svlevel.random.nextInt(6);
            int l = targetPos.getZ() + Mth.floor(Mth.sin(f) * 10 * (float)i) + svlevel.random.nextInt(6);
            int k = svlevel.getHeight(Heightmap.Types.WORLD_SURFACE, j, l);
            pPos.set(j, k, l);
            if (svlevel
                    .hasChunksAt(
                            pPos.getX() - 10,
                            pPos.getZ() - 10,
                            pPos.getX() + 10,
                            pPos.getZ() + 10
                    )
                    && svlevel.isPositionEntityTicking(pPos)
                    && (spawnplacementtype.isSpawnPositionOk(svlevel, pPos, EntityType.RAVAGER) || svlevel.getBlockState(pPos.below()).is(Blocks.SNOW)
                    && svlevel.getBlockState(pPos).isAir()
            )) {
                return pPos;
            }
        }
        return null;
    }

    private static boolean canBeSeenOnSurface(ServerLevel svlevel, Player player) {
        if (!svlevel.isClientSide) {
            BlockPos blockpos = BlockPos.containing(player.getX(), player.getEyeY(), player.getZ());
            float brightness = svlevel.getBrightness(LightLayer.SKY, blockpos);
            if (svlevel.random.nextFloat() * 30.0F < (brightness - 0.4F) * 2.0F
                    && svlevel.canSeeSky(blockpos)) {
                return true;
            }
        }
        return false;
    }

}
