package co.ppk.dto;

public final class APIResponse {
    private final int httpCode;
    private final Object body;

    public APIResponse(final int httpCode, final Object body) {
        this.httpCode = httpCode;
        this.body = body;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public Object getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "APIResponse{" +
                "httpCode=" + httpCode +
                ", body=" + body +
                '}';
    }
}
