package com.t3h.topcv.controller;

import com.t3h.topcv.dto.SalaryResponse;
import com.t3h.topcv.entity.Salary;
import com.t3h.topcv.service.SalaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/salary")
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> findAll() {

        List<Salary> salaries = salaryService.findAll();

        SalaryResponse response = new SalaryResponse();

        response.setMessage("success");
        response.setData(salaries);

        return ResponseEntity.ok(salaries);
    }
}
