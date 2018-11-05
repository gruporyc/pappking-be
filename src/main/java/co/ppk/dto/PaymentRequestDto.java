package co.ppk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentRequestDto {
    private String customerId;
    private String serviceId;
    private Boolean operator;
    private float amount;

    @JsonProperty("customerId")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @JsonProperty("serviceId")
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @JsonProperty("operator")
    public Boolean getOperator() {
        return operator;
    }

    public void setOperator(Boolean operator) {
        this.operator = operator;
    }

    @JsonProperty("amount")
    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PaymentRequestDto{" +
                "customerId='" + customerId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", operator=" + operator +
                ", amount=" + amount +
                '}';
    }
}
