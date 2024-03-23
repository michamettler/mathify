package ch.zhaw.mathify.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing the settings of the application
 */
public class Settings {
    private final HttpSettingsModel httpSettingsModel;
    private final HttpsSettingsModel https;

    @JsonCreator
    public Settings(@JsonProperty(value = "http", required = true) HttpSettingsModel httpSettingsModel,
                    @JsonProperty("https") HttpsSettingsModel https) {
        this.httpSettingsModel = httpSettingsModel;
        this.https = https;
    }

    public HttpSettingsModel getHttp() {
        return httpSettingsModel;
    }

    public HttpsSettingsModel getHttps() {
        return https;
    }


    /**
     * Class representing the http settings
     */
    public record HttpSettingsModel(int port) {
        /**
         * Constructor necessary for deserialization (required = true will be checked here)
         * @param port the port to listen on
         */
        @JsonCreator
        public HttpSettingsModel(@JsonProperty(value = "port", required = true) int port) {
            this.port = port;
        }

    }

    /**
     * Class representing the https settings
     */
    public record HttpsSettingsModel(int port, String cert, String key) {
    }
}
