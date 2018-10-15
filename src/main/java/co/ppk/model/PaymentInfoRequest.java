package co.ppk.model;

import javax.validation.constraints.NotNull;

public class PaymentInfoRequest {

    @NotNull
    private double ammount;

    @NotNull
    private String concept;

    public PaymentInfoRequest(@NotNull double ammount, @NotNull String concept) {
        this.ammount = ammount;
        this.concept = concept;
    }

    public double getAmmount() {
        return ammount;
    }

    public void setAmmount(double ammount) {
        this.ammount = ammount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    @Override
    public String toString() {
        return "PaymentInfoRequest{" +
                "ammount=" + ammount +
                ", concept='" + concept + '\'' +
                '}';
    }
}
