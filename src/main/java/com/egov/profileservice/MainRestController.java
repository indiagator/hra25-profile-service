package com.egov.profileservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*") // Allow cross-origin requests from any host
public class MainRestController {


    @Autowired
    CredentialRepository credentialRepository;

    @GetMapping("getfullname/{userid}")
    public ResponseEntity<String> getFullname(@PathVariable("userid") Long id)
    {
        Credential credential = credentialRepository.findById(id).get();
        return ResponseEntity.ok(credential.getEmail());
    }

}
