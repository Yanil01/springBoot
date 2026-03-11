package np.com.yanilmagar.journalApp.controller;

import np.com.yanilmagar.journalApp.entity.JournalEntry;
import np.com.yanilmagar.journalApp.entity.User;
import np.com.yanilmagar.journalApp.repository.JournalEntryRepository;
import np.com.yanilmagar.journalApp.service.JournalEntryService;
import np.com.yanilmagar.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @GetMapping
    public ResponseEntity<?>getAll(){
        return new ResponseEntity<>(journalEntryService.getAll(),HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<?>getAllJournalEntriesOfUser(@PathVariable String username){
        User user = userService.findByUserName(username);
        List<JournalEntry>all = user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){  //myEntry is an instance
        try {
            myEntry.setDate(LocalDate.now());
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{userName}/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable String userName, @PathVariable ObjectId id) {

        boolean deleted = journalEntryService.deleteById(id, userName);

        if (deleted) {
            return ResponseEntity.ok("Entry deleted");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
    }

    @PutMapping("{userName}/{myId}")
    public ResponseEntity<?> updateEntryById(@PathVariable String userName,@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null);
        if(old!=null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getContent().isEmpty()?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty()?newEntry.getContent():old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
