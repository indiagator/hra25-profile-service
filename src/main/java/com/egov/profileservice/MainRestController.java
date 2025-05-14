package com.egov.profileservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("api/v1")
@CrossOrigin("*") // Allow cross-origin requests from any host
public class MainRestController
{
    private static final Logger log = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    CustomerpiiRepository customerpiiRepository;

    @Autowired
    ApplicationContext ctx;

//    @Autowired
//    @Qualifier("authValidateWebClient")
//    WebClient authValidateWebClient;

    @Autowired
    TokenService tokenService;

    @GetMapping("getfullname/{userid}")
    public ResponseEntity<String> getFullname(@PathVariable("userid") Long id,
                                              @RequestHeader("Authorization") String token
                                             )
    {

        log.info("Received request to get fullname for user ID: {}", id);

        if(tokenService.validateToken(token,ctx))
        {
             log.info("Valid token: {}", token);
            return ResponseEntity.ok("dummy name");
        }
        else
        {
            return ResponseEntity.status(401).body("Invalid token");
        }
//        Credential credential = credentialRepository.findById(id).get();
//        return ResponseEntity.ok(credential.getEmail());

    }

    @PostMapping("save/userpii")
    public ResponseEntity<String> saveUserPii(@RequestBody Customerpii customerpii,
                                                @RequestHeader("Authorization") String token
                                              )
    {
        // Forward request to Auth-Service for Token Validation
        // If token is valid, update user PII in the database

        log.info("Received request to save user PII: {}", customerpii);
        log.info("forwarding request to auth-service for token validation");

        WebClient authValidateWebClient = ctx.getBean("authValidateWebClientEurekaDiscovered", WebClient.class);

        String authResponse =authValidateWebClient.get()
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Thread is Blocked until the response is received

        log.info("Response from auth-service: {}", authResponse);

        if(authResponse.equals("INVALID"))
        {
            log.info("Invalid token: {}", token);
            return ResponseEntity.status(401).body("Invalid token");
        }
        else if (authResponse.equals("VALID"))
        {
            log.info("Valid token: {}", token);
            // Return success or failure response
            customerpiiRepository.save(customerpii);
            return ResponseEntity.ok("User PII updated successfully");
        }
        else
        {
            log.info("Error in auth-service: {}", authResponse);
            return ResponseEntity.status(500).body("Internal Server Error");
        }


    }

}
