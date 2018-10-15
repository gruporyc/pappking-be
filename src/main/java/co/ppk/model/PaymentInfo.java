package co.ppk.model;


import javax.validation.constraints.NotNull;

public class PaymentInfo {

    @NotNull
    private String url;

    @NotNull
    private String provider;

    public PaymentInfo(@NotNull String url, @NotNull String provider) {
        this.url = url;
        this.provider = provider;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "url='" + url + '\'' +
                ", provider='" + provider + '\'' +
                '}';
    }
}
