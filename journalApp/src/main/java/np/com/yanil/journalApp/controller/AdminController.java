package np.com.yanil.journalApp.controller;


import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.service.UserEntryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserEntryService userEntryService;

    public AdminController(UserEntryService userEntryService) {
        this.userEntryService = userEntryService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntry> all = userEntryService.getAllEntry();
        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody UserEntry userEntry){
        userEntryService.saveAdmin(userEntry);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
