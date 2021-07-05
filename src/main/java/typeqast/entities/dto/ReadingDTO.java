package typeqast.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import typeqast.entities.Meter;

import java.math.BigInteger;
import java.time.Month;

public class ReadingDTO {

    private BigInteger id;

    @JsonIgnore
    private Meter meter;

    private Integer year;

    private Month month;

    private Long value;

    public ReadingDTO(Integer year, Month month, Long value) {
        this.year = year;
        this.month = month;
        this.value = value;
    }

    public ReadingDTO() {

    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Meter getMeter() {
        return meter;
    }

    public void setMeter(Meter meter) {
        this.meter = meter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
