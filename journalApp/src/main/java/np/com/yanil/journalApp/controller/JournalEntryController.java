package np.com.yanil.journalApp.controller;

import np.com.yanil.journalApp.entity.JournalEntry;
import np.com.yanil.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/journal")
public class JournalEntryController {

   @Autowired
   private JournalEntryService journalEntryService;

   @PostMapping
    public boolean createEntry(@RequestBody JournalEntry myEntry){
       journalEntryService.saveEntry(myEntry);
       return true;
   }

   @GetMapping
    public List<JournalEntry> getAll(){
       return journalEntryService.getAllEntry();
   }
}
