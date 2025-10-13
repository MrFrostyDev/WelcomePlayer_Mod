package xyz.mrfrostydev.welcomeplayer.blocks.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;
import xyz.mrfrostydev.welcomeplayer.damages.ShockDamageSource;
import xyz.mrfrostydev.welcomeplayer.data.datagen.providers.datapacks.ModDamageTypes;
import xyz.mrfrostydev.welcomeplayer.entities.projectiles.ShockBoltProjectile;
import xyz.mrfrostydev.welcomeplayer.registries.BlockRegistry;
import xyz.mrfrostydev.welcomeplayer.registries.TagRegistry;

import java.util.List;

public class TeslaBlockEntity extends BlockEntity implements GeoBlockEntity {
    private static final float COIL_RANGE = 3.2F;
    private static final float TICK_COOLDOWN = 30;

    public TeslaBlockEntity(BlockPos pos, BlockState blockState) {
        super(BlockRegistry.RETRO_TESLA_COIL_ENTITY.get(), pos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BlockEntity entity) {
        if (!(entity instanceof TeslaBlockEntity blockEntity)) return;

        if(level instanceof ServerLevel){
            int tick = ((ServerLevel)level).getServer().getTickCount();
            if(tick % TICK_COOLDOWN == 0){
                AABB area = new AABB(pos.above()).inflate(COIL_RANGE);
                area = area.setMaxY(area.maxY - 1);
                area = area.setMinY(area.minY + 1);
                List<Entity> entities = level.getEntities((Entity)null, area, e -> !e.getType().is(TagRegistry.HOST_ROBOT) && !e.isSpectator());
                for(Entity e : entities){
                    if(!(e instanceof LivingEntity target))continue;
                    Vec3 shootPos = new Vec3(pos.getX() + 0.5, pos.getY() + 1.7, pos.getZ() + 0.5);
                    ShockBoltProjectile shockBolt = new ShockBoltProjectile(level, shootPos, new Vec3(e.getX(), e.getEyeY() - 0.2, e.getZ()));
                    level.addFreshEntity(shockBolt);

                    target.hurt(new ShockDamageSource(level.registryAccess().holderOrThrow(ModDamageTypes.SHOCK), null), 6);

                    LightningBolt lightning = EntityType.LIGHTNING_BOLT.create(level);
                    lightning.setDamage(0.0F);
                    target.thunderHit((ServerLevel) level, lightning);
                }
            }
        }

    }

    // |------------------------------------------------------------|
    // |------------------------Data/Network------------------------|
    // |------------------------------------------------------------|

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries){
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
    }

    // |------------------------------------------------------------|
    // |-------------------------Animations-------------------------|
    // |------------------------------------------------------------|

    private final AnimatableInstanceCache animCache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animCache;
    }
}
