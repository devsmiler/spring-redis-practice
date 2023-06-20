package com.example.redis_tutorial.dto;

import com.example.redis_tutorial.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyDTO {

    @NotBlank(message = "빈 티커는 안됩니다.")
    @NotNull(message = "허용하지 않습니다.")
    private String ticker;
    private String name;

    public Company toEntity(){
        return Company.builder()
                .ticker(ticker)
                .name(name)
                .build();
    }

}


