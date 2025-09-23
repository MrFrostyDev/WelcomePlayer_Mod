package xyz.mrfrostydev.welcomeplayer.utils;

import com.mojang.blaze3d.Blaze3D;

public class ClientUtil {

    /**
     * Returns the current tick that {@link org.lwjgl.glfw.GLFW GLFW} is running using {@link Blaze3D}
     * <p>
     * This will tick up the moment the game launches and continues until the game closes.
     */
    public static int getRenderTick(){
        return (int)(Blaze3D.getTime() * 20d);
    }
}
