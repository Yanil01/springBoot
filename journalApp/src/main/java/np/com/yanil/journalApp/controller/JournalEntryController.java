package np.com.yanil.journalApp.controller;

import np.com.yanil.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    private List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/{id}")
    private JournalEntry getById(@PathVariable String id){
        return journalEntries.get(id);
    }

    @PostMapping
    private boolean createEntry(@RequestBody JournalEntry myEntry){
        journalEntries.put(myEntry.getId(),myEntry);
        return true ;
    }

    @DeleteMapping("/{id}")
    private JournalEntry deleteById(@PathVariable String id){
        return journalEntries.remove(id);
    }

    @PutMapping("/{id}")
    private JournalEntry updateById(@PathVariable String id, @RequestBody JournalEntry myEntry){
        return journalEntries.put(id,myEntry);
    }
}
