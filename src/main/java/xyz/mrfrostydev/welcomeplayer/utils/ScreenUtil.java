package xyz.mrfrostydev.welcomeplayer.utils;

import net.minecraft.client.Minecraft;
import xyz.mrfrostydev.welcomeplayer.client.gui.screens.SurveillancePadScreen;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;

public class ScreenUtil {
    public static void openSurveillancePad(Minecraft instance, AudienceData data){
        instance.setScreen(new SurveillancePadScreen(data));
    }
}
