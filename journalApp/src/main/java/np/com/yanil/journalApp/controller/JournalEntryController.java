package np.com.yanil.journalApp.controller;


import np.com.yanil.journalApp.entity.JournalEntry;
import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.service.JournalEntryService;
import np.com.yanil.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getJournalEntriesOfUser(@PathVariable String username){
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

    @PostMapping("/{username}")
    public ResponseEntity<JournalEntry> createJournalEntriesByUser(@RequestBody JournalEntry myEntry, @PathVariable String username){
        UserEntry user = userEntryService.findByUserName(username);
        if (user != null){
            journalEntryService.saveEntry(myEntry,username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<JournalEntry> all = journalEntryService.getAllEntry();
        if(all != null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{username}/{myId}")
    public ResponseEntity<?> getById(@PathVariable ObjectId myId, @PathVariable String username){
        UserEntry user = userEntryService.findByUserName(username);
        if (user!=null){
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String username){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        if(journalEntry.isPresent()){
            journalEntryService.deleteById(myId, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


    }

   @PutMapping("/{username}/{myId}")
   public JournalEntry updateJournalById(@PathVariable ObjectId myId,@PathVariable String username, @RequestBody JournalEntry newEntry){
      JournalEntry old = journalEntryService.findById(myId).orElse(null);
      if (old!= null){
         old.setTitle(!newEntry.getTitle().isEmpty() ? newEntry.getTitle() : old.getTitle());
         old.setContent((newEntry.getContent()!=null&& !newEntry.getContent().isEmpty())?newEntry.getContent():old.getContent());
      }
      journalEntryService.saveEntry(old);
      return old;
   }


}





