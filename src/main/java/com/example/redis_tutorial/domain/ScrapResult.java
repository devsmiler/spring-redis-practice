package com.example.redis_tutorial.domain;

import com.example.redis_tutorial.dto.CompanyDTO;
import com.example.redis_tutorial.dto.DividendDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScrapResult {
    private CompanyDTO companyDTO;
    private List<DividendDto> dividends;

    public ScrapResult() {
        this.dividends = new ArrayList<>();
    }

    @Builder
    public ScrapResult(CompanyDTO companyDTO, List<DividendDto> dividends) {
        this.companyDTO = companyDTO;
        this.dividends = dividends;
    }
}
