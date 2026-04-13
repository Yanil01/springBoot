package np.com.yanil.journalApp.controller;

import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserEntry user){
        userEntryService.saveEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
