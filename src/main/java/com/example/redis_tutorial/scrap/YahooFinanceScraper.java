package com.example.redis_tutorial.scrap;

import com.example.redis_tutorial.domain.ScrapResult;
import com.example.redis_tutorial.dto.CompanyDTO;
import com.example.redis_tutorial.dto.DividendDto;
import com.example.redis_tutorial.dto.constants.Month;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements Scraper{
    private static final String URL = "https://finance.yahoo.com/quote/%s/history?period1=%d&period2=%d&interval=1mo";
    private static final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400;
    @Override
    public ScrapResult scrap(CompanyDTO companyDTO) {
        ScrapResult scrapResult = new ScrapResult();
        scrapResult.setCompanyDTO(companyDTO);
        try {
            long now = System.currentTimeMillis() / 1000 ;
            String format = String.format(URL, companyDTO.getTicker(), START_TIME, now);
            Connection connect = Jsoup.connect(format);
            Document document = connect.get();

            Elements elements = document.getElementsByAttributeValue("data-test", "historical-prices");
            Element element = elements.get(0);
            Element element1 = element.children().get(1);

            List<DividendDto> dividendList = new ArrayList<>();

            for (Element e : element1.children()) {
                String txt = e.text();
                if(!txt.endsWith("Dividend")){
                    continue;
                }
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.parseInt(splits[1].replace(",",""));
                int year = Integer.parseInt(splits[2]);
                String dividend = splits[3];
                if (month < 0) {
                    throw new RuntimeException("잘못된 연 월일입니다.");
                }
                dividendList.add(
                        DividendDto.builder()
                                .dateTime(LocalDateTime.of(year, month, day, 0, 0))
                                .dividend(dividend)
                                .build()
                );

            }
            scrapResult.setDividends(dividendList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scrapResult;
    }

    @Override
    public CompanyDTO scrapCompanyByTicker(String ticker) {
        System.out.println("ScrapCompany by Ticker");
        String url = String.format(SUMMARY_URL, ticker, ticker);
        try {
            Document document = Jsoup.connect(url).get();
            Element h1 = document.getElementsByTag("h1").get(0);

            String title = h1.text().split("\\(")[0].trim();
            return CompanyDTO.builder().ticker(ticker).name(title).build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
