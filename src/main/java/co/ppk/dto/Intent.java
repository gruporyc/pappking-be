package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Intent {

    @NotNull
    private String name;

    @NotNull
    private String displayName;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("displayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Intent{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
