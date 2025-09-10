package xyz.mrfrostydev.welcomeplayer.entities;

import net.minecraft.world.phys.AABB;

public class EntityHitbox {
    private AABB box;

    public EntityHitbox(AABB box){
        this.box = box;
    }

    public EntityHitbox(double xs, double ys, double zs){
        this.box = new AABB(0, 0, 0, 0, 0, 0).inflate(xs / 2, ys / 2, zs / 2);
    }

    public AABB getAABB() {
        return box;
    }
}
