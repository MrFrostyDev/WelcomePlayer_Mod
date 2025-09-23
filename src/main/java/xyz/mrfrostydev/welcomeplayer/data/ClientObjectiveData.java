package xyz.mrfrostydev.welcomeplayer.data;

public class ClientObjectiveData {
    private static ObjectiveManagerData data = new ObjectiveManagerData();

    public static ObjectiveManagerData get() {
        return data;
    }

    public static void setData(ObjectiveManagerData data) {
        ClientObjectiveData.data = data;
    }

}
