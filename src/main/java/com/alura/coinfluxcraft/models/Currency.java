package main.java.com.alura.coinfluxcraft.models;

public class Currency {
    private String code;
    private String name;
    private Double rate;

    public Currency() {}

    public Currency(String code, String name, Double rate) {
        this.code = code;
        this.name = name;
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return String.format("%s (%s): %.4f", name != null ? name : code, code, rate);
    }
}
