package api.rest;

public class RestSharedData {

    private static RestEngine istance = null;

    public static RestEngine getRestIstance () {
        if (istance == null) {
           istance = new RestEngine();
        }
        return istance;
    }
}
