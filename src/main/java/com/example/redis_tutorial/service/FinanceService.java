package com.example.redis_tutorial.service;

import com.example.redis_tutorial.domain.Company;
import com.example.redis_tutorial.domain.Dividend;
import com.example.redis_tutorial.domain.ScrapResult;
import com.example.redis_tutorial.dto.CompanyDTO;
import com.example.redis_tutorial.dto.DividendDto;
import com.example.redis_tutorial.repository.CompanyRepository;
import com.example.redis_tutorial.repository.DividendRepository;
import com.example.redis_tutorial.scrap.YahooFinanceScraper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;

    @Cacheable(key = "#companyName", value = "finance")
    public ScrapResult getDividendByCompany(String companyName) {
        Company company = companyRepository.findByName(companyName).orElseThrow(
                () -> new RuntimeException("존재하지 않는 회사입니다.")
        );
        List<Dividend> dividends = dividendRepository.findByCompanyId(company.getId());

        List<DividendDto> dividendDtos = dividends.stream().map(e -> DividendDto.builder()
                .dateTime(e.getDateTime())
                .dividend(e.getDividend())
                .build()).collect(Collectors.toList());

        return ScrapResult.builder()
                .companyDTO(CompanyDTO.builder().name(company.getName()).ticker(company.getTicker()).build())
                .dividends(dividendDtos)
                .build();
    }
}
