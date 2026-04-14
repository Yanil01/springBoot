package np.com.yanil.journalApp.controller;


import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import np.com.yanil.journalApp.service.UserEntryService;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;








@RestController
@RequestMapping("/users")
public class UserEntryController {

    private final UserEntryService userEntryService;

    private final UserEntryRepository userEntryRepository;

    public UserEntryController(UserEntryService userEntryService, UserEntryRepository userEntryRepository) {
        this.userEntryService = userEntryService;
        this.userEntryRepository = userEntryRepository;
    }


    @GetMapping
    public ResponseEntity<UserEntry> getUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);
        if (user != null){
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping
    public ResponseEntity<UserEntry> updateById(
                                                @RequestBody UserEntry newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.setUserName(newUser.getUserName());
        user.setUserPassword(newUser.getUserPassword());

        userEntryService.saveNewEntry(user);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?>deleteById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        userEntryRepository.deleteUserEntriesByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
