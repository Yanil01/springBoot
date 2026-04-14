package np.com.yanil.journalApp.controller;


import np.com.yanil.journalApp.entity.JournalEntry;
import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.service.JournalEntryService;
import np.com.yanil.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final UserEntryService userEntryService;

    public JournalEntryController(JournalEntryService journalEntryService, UserEntryService userEntryService) {
        this.journalEntryService = journalEntryService;
        this.userEntryService = userEntryService;
    }

    @GetMapping
    public ResponseEntity<?> getJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<JournalEntry> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntriesByUser(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);

        if (user != null){
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);
        List <JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if (!collect.isEmpty()){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assert authentication != null;
        String username = authentication.getName();
        UserEntry user = userEntryService.findByUserName(username);

        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            journalEntryService.deleteById(myId, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

   @PutMapping("/{myId}")
   public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       assert authentication != null;
       String username = authentication.getName();
       UserEntry user = userEntryService.findByUserName(username);

       JournalEntry old = journalEntryService.findById(myId).orElse(null);
      if (old!= null){
         old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
         old.setContent((newEntry.getContent()!=null&& !newEntry.getContent().isEmpty())?newEntry.getContent():old.getContent());
      }
      journalEntryService.saveEntry(old);
      return old;
   }


}





