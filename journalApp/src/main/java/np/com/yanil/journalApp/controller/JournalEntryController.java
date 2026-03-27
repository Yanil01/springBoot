package np.com.yanil.journalApp.controller;

import np.com.yanil.journalApp.entity.JournalEntry;
import np.com.yanil.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

   @Autowired
   private JournalEntryService journalEntryService;

   @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
      myEntry.setDate(LocalDateTime.now());
       journalEntryService.saveEntry(myEntry);
       return true;
   }

   @GetMapping
    public List<JournalEntry> getAll(){
       return journalEntryService.getAllEntry();
   }

   @GetMapping("/{myId}")
   public JournalEntry getById(@PathVariable ObjectId myId){
      return journalEntryService.findById(myId).orElse(null);
   }

   @DeleteMapping("/{myId}")
   public boolean deleteById(@PathVariable ObjectId myId){
      journalEntryService.deleteById(myId);
      return true;
   }

   @PutMapping("/{myId}")
   public JournalEntry updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
      JournalEntry old = journalEntryService.findById(myId).orElse(null);
      if (old!= null){
         old.setTitle(((newEntry.getTitle() != null) && !newEntry.getTitle().isEmpty()) ? newEntry.getTitle() : old.getTitle());
         old.setContent((newEntry.getContent()!=null&& !newEntry.getContent().isEmpty())?newEntry.getContent():old.getContent());
      }
      journalEntryService.saveEntry(old);
      return old;
   }
}
