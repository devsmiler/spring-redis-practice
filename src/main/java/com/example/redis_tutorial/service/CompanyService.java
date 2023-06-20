package com.example.redis_tutorial.service;

import com.example.redis_tutorial.domain.Company;
import com.example.redis_tutorial.domain.Dividend;
import com.example.redis_tutorial.domain.ScrapResult;
import com.example.redis_tutorial.dto.CompanyDTO;
import com.example.redis_tutorial.repository.CompanyRepository;
import com.example.redis_tutorial.repository.DividendRepository;
import com.example.redis_tutorial.scrap.YahooFinanceScraper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final Trie trie;
    private final YahooFinanceScraper yahooFinanceScraper;
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public CompanyDTO save(String ticker) {
        if (companyRepository.existsByTicker(ticker)) {
            throw new RuntimeException("Already Exists");
        }
        return storeCompanyAndDividend(ticker);
    }
    public Page<Company> getAllCompany(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    private CompanyDTO storeCompanyAndDividend(String ticker) {
        CompanyDTO companyDTO = this.yahooFinanceScraper.scrapCompanyByTicker(ticker);
        if (ObjectUtils.isEmpty(companyDTO)) {
            throw new RuntimeException("failing");
        }

        ScrapResult scrap = this.yahooFinanceScraper.scrap(companyDTO);
        Company save = companyRepository.save(companyDTO.toEntity());
        List<Dividend> dividends = scrap.getDividends().stream()
                .map(dto -> Dividend.builder().companyId(save.getId()).dto(dto).build())
                .collect(Collectors.toList());
        dividendRepository.saveAll(dividends);
        addAutoCompleteKeyword(companyDTO.getName());
        return companyDTO;
    }

    public void addAutoCompleteKeyword(String keyword) {
        trie.put(keyword, null);
    }

    public List<String> autocomplete(String keyword) {
        return (List<String>) this.trie.prefixMap(keyword).keySet().stream().collect(Collectors.toList());
    }

    public void deleteAutoCompleteKeyword(String keyword) {
        trie.remove(keyword);
    }
}
