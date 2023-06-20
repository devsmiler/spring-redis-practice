package com.example.redis_tutorial.scrap;

import com.example.redis_tutorial.domain.ScrapResult;
import com.example.redis_tutorial.dto.CompanyDTO;

public interface Scraper {
    CompanyDTO scrapCompanyByTicker(String ticker);

    ScrapResult scrap(CompanyDTO companyDTO);
}
