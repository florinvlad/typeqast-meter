package typeqast.entities;

public class AggregateReading {

    private Integer year;
    private Long total;

    public AggregateReading(Integer year, Long total) {
        this.year = year;
        this.total = total;
    }

    public AggregateReading() {

    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
