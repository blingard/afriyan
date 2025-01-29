package org.ligot.afriyan.Dto;

public class StatistiqueMensuelle {
    private String name;
    private Long uv;
    private Long pv = 2400L;
    private Long amt = 2400L;

    public StatistiqueMensuelle() {
    }

    public StatistiqueMensuelle(String name, Long uv) {
        this.name = name;
        this.uv = uv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUv() {
        return uv;
    }

    public void setUv(Long uv) {
        this.uv = uv;
    }

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public Long getAmt() {
        return amt;
    }

    public void setAmt(Long amt) {
        this.amt = amt;
    }
}
