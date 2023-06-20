package com.example.redis_tutorial.controller;

import com.example.redis_tutorial.service.FinanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {
    private final FinanceService financeService;
    @GetMapping("/dividend/{companyName}")
    public ResponseEntity<?> getDividend(@PathVariable String companyName) {
        return ResponseEntity.ok(financeService.getDividendByCompany(companyName));
    }
}
