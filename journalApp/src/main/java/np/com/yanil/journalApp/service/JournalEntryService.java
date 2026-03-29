package np.com.yanil.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import np.com.yanil.journalApp.entity.JournalEntry;
import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.JournalEntryRepository;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    public void saveEntry(JournalEntry journalEntry, String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userEntryService.saveEntry(user);
    }
    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntry(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry>findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String username){
        UserEntry user = userEntryService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userEntryService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }


}
