package com.example.redis_tutorial.domain;

import com.example.redis_tutorial.dto.DividendDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"companyId", "dateTime"}
                )
        }
)
public class Dividend {
    @Id
    @GeneratedValue
    private Long id;
    private Long companyId;
    private LocalDateTime dateTime;
    private String dividend;

//    @Builder
//    public Dividend(Long companyId, LocalDateTime dateTime, String dividend) {
//        this.companyId = companyId;
//        this.dateTime = dateTime;
//        this.dividend = dividend;
//    }

    @Builder
    public Dividend(Long companyId, DividendDto dto) {
        this.companyId = companyId;
        this.dateTime = dto.getDateTime();
        this.dividend = dto.getDividend();
    }
}
