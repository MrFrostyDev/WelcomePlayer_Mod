package xyz.mrfrostydev.welcomeplayer.data;

public class ClientAudienceData {
    private static AudienceData data = new AudienceData();
    public static AudienceData get() {
        return data;
    }

    public static void setSmallData(AudienceData.AudienceDataSmall dataSmall) {
        ClientAudienceData.data.setDataSmall(dataSmall);
    }

    public static void setLargeData(AudienceData.AudienceDataLarge dataLarge) {
        ClientAudienceData.data.setDataLarge(dataLarge);
    }
}
