package np.com.yanil.journalApp.controller;


import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.service.UserEntryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;


import java.util.List;


@RestController
@RequestMapping("/users")
public class UserEntryController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getUsers(){
        List<UserEntry> user = userEntryService.getAllEntry();
        if (user!= null && !user.isEmpty()){
            return new ResponseEntity<>(userEntryService.getAllEntry(), HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/{username}")
    public ResponseEntity<UserEntry> getUserById(@PathVariable String username){
        UserEntry user =  userEntryService.findByUserName(username);

        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntry user){
        userEntryService.saveEntry(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping("/{username}")
    public ResponseEntity<UserEntry> updateById(@PathVariable String username,
                                                @RequestBody UserEntry newUser) {
        UserEntry user = userEntryService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setUserName(newUser.getUserName());
        user.setUserPassword(newUser.getUserPassword());

        userEntryService.saveEntry(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
