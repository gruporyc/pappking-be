package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class QueryResult {

    @NotNull
    private String queryText;

    @NotNull
    private Intent intent;

    @NotNull
    private float intentDetectionConfidence;

    @NotNull
    private String languageCode;

    @JsonProperty("queryText")
    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        this.queryText = queryText;
    }

    @JsonProperty("intent")
    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    @JsonProperty("intentDetectionConfidence")
    public float getIntentDetectionConfidence() {
        return intentDetectionConfidence;
    }

    public void setIntentDetectionConfidence(float intentDetectionConfidence) {
        this.intentDetectionConfidence = intentDetectionConfidence;
    }

    @JsonProperty("languageCode")
    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    @Override
    public String toString() {
        return "QueryResult{" +
                "queryText='" + queryText + '\'' +
                ", intent=" + intent +
                ", intentDetectionConfidence=" + intentDetectionConfidence +
                ", languageCode='" + languageCode + '\'' +
                '}';
    }
}
