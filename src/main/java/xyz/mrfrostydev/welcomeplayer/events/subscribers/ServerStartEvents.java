package xyz.mrfrostydev.welcomeplayer.events.subscribers;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.LevelEvent;
import xyz.mrfrostydev.welcomeplayer.WelcomeplayerMain;
import xyz.mrfrostydev.welcomeplayer.data.AudienceData;
import xyz.mrfrostydev.welcomeplayer.data.VendorShopData;
import xyz.mrfrostydev.welcomeplayer.utils.AudienceUtil;
import xyz.mrfrostydev.welcomeplayer.utils.VendorUtil;

@EventBusSubscriber(modid = WelcomeplayerMain.MOD_ID)
public class ServerStartEvents {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event){
        if (event.getLevel() instanceof ServerLevel svlevel && svlevel.dimension() == Level.OVERWORLD) {
            DimensionDataStorage dataStorage = svlevel.getDataStorage();
            if(dataStorage.get(AudienceData.factory(), "audience_data") == null){
                AudienceUtil.computeAudienceData(svlevel);
            }
            if(dataStorage.get(VendorShopData.factory(), "vendor_data") == null){
                VendorUtil.computeVendorData(svlevel);
            }
        }
    }
}
