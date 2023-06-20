package com.example.redis_tutorial.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Company {
    @Id @GeneratedValue
    private Long id;

    private String ticker;
    private String name;

    @Builder
    public Company(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;
    }

}
