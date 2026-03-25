package np.com.yanil.journalApp.controller;

import np.com.yanil.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
