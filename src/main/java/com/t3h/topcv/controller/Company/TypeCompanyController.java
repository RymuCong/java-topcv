package com.t3h.topcv.controller.Company;

import com.t3h.topcv.dto.TypeCompanyResponse;
import com.t3h.topcv.repository.Company.TypeCompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class TypeCompanyController {

    private final TypeCompanyRepository typeCompanyRepository;

    @Autowired
    public TypeCompanyController(TypeCompanyRepository typeCompanyRepository) {
        this.typeCompanyRepository = typeCompanyRepository;
    }

    // get all type company
    @GetMapping("/typeCompany/all")
    public ResponseEntity<?> getAllTypeCompany() {

        TypeCompanyResponse response = new TypeCompanyResponse();
        response.setMessage("success");
        response.setData(typeCompanyRepository.findAll());

        return ResponseEntity.ok(response);
    }
}
