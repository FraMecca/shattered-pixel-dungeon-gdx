package api.rest;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfig extends Configuration {
    private String appName = "ProvaRest";
    @JsonProperty
    public String getAppName() {
        return appName;
    }

    @JsonProperty
    public void setAppName(final String appName) {
        this.appName = appName;
    }
}