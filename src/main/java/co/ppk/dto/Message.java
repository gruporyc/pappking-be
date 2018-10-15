package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class Message {

    @NotNull
    private String responseId;

    @NotNull
    private  QueryResult queryResult;

    @NotNull
    private String session;

    @JsonProperty("responseId")
    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    @JsonProperty("queryResult")
    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    @JsonProperty("session")
    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Message{" +
                "responseId='" + responseId + '\'' +
                ", queryResult=" + queryResult +
                ", session='" + session + '\'' +
                '}';
    }
}
