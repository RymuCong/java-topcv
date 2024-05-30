package com.t3h.topcv.controller.Job;

import com.t3h.topcv.dto.LevelJobResponse;
import com.t3h.topcv.repository.Job.LevelJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
@RequestMapping("/jobs")
public class LevelJobController {

    private final LevelJobRepository levelJobRepo;

    @Autowired
    public LevelJobController(LevelJobRepository levelJobRepo) {
        this.levelJobRepo = levelJobRepo;
    }

    @GetMapping("/leveljob/getAll")
    public ResponseEntity<?> getAllLevelJob() {

        LevelJobResponse response = new LevelJobResponse();

        response.setMessage("success");
        response.setData(levelJobRepo.findAll());

        return ResponseEntity.ok(response);
    }
}
