package xyz.mrfrostydev.welcomeplayer.entities;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import java.util.List;

@FunctionalInterface
public interface EntityHitboxAction {
   <T extends Entity> void applyOnHit(ServerLevel svlevel, List<Entity> entities, T inflictor);
}
