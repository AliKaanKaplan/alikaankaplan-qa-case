package framework.api.config;

public final class Endpoints {

    public static final String BASE_URI = "https://petstore.swagger.io/v2";

    public static final String PET = "/pet";
    public static final String PET_FIND_BY_STATUS = PET + "/findByStatus";

    private Endpoints() {
    }
}
