package com.t3h.topcv.service;

import com.t3h.topcv.entity.Salary;
import com.t3h.topcv.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService{

    private final SalaryRepository salaryRepository;

    @Autowired
    public SalaryServiceImpl(SalaryRepository salaryRepository) {
        this.salaryRepository = salaryRepository;
    }

    @Override
    public List<Salary> findAll() {
        return salaryRepository.findAll();
    }
}
