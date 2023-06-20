package com.example.redis_tutorial.controller;

import com.example.redis_tutorial.dto.CompanyDTO;
import com.example.redis_tutorial.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {
    private final CompanyService companyService;
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword) {
        return ResponseEntity.ok(companyService.autocomplete(keyword));
    }

    @GetMapping()
    public ResponseEntity<?> searchCompany(Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompany(pageable));
    }

    @PostMapping()
    public ResponseEntity<?> addCompany(@RequestBody CompanyDTO companyDTO) {
        return ResponseEntity.ok(companyService.save(companyDTO.getTicker().trim()));
    }
}
