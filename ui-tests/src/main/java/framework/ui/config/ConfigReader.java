package framework.ui.config;

public class ConfigReader {

    public static String getBaseUrl() {
        String env = System.getProperty("demo", "staging");

        switch (env.toLowerCase()) {
            case "staging":
                return "https://insiderone.com";
            case "demo":
                return "https://insiderone.com";
            case "prod":
            default:
                return "https://insiderone.com";
        }
    }

    public static String getUrl(String path) {
        return getBaseUrl() + path;
    }
}
