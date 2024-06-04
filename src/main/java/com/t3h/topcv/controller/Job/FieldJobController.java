package com.t3h.topcv.controller.Job;

import com.t3h.topcv.entity.job.Field_Job;
import com.t3h.topcv.entity.job.Type_Jobs;
import com.t3h.topcv.repository.Job.FieldJobRepository;
import com.t3h.topcv.repository.Job.TypeJobRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class FieldJobController {

    private final FieldJobRepository fieldJobRepo;

    public FieldJobController(FieldJobRepository fieldJobRepo) {
        this.fieldJobRepo = fieldJobRepo;
    }

    // get all type job
    @GetMapping("/typeJob/getAll")
    public ResponseEntity<?> getAllTypeJob() {

        List<Field_Job> fieldJobs = fieldJobRepo.findAll();

        return ResponseEntity.ok(fieldJobs);
    }
}
