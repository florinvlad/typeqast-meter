package typeqast.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.time.Month;

@Entity
@Table(name = "reading")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigint unsigned")
    private BigInteger id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "meter_id", nullable = false)
    private Meter meter;

    @Min(1900)
    @Max(2021)
    @Column(name = "year", nullable = false, columnDefinition = "int default 1900")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "month", nullable = false, columnDefinition = "varchar(25)")
    private Month month;

    @Column(name = "value", nullable = false, columnDefinition = "bigint default 0")
    private Long value;

    public Reading(BigInteger id) {
        this.id = id;
    }

    public Reading(Integer year, Month month, Long value) {
        this.year = year;
        this.month = month;
        this.value = value;
    }
}
