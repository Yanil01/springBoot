package np.com.yanilmagar.journalApp.service;

import np.com.yanilmagar.journalApp.entity.JournalEntry;
import np.com.yanilmagar.journalApp.entity.User;
import np.com.yanilmagar.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry journalEntry,String userName){
        User user = userService.findByUserName(userName);
        journalEntry.setDate(LocalDate.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveEntry(user);
    }

    public void saveEntry(JournalEntry journalEntry){
       journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry>getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry>findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public boolean deleteById(ObjectId id, String userName) {

        User user = userService.findByUserName(userName);

        if (user == null) {
            return false;
        }

        boolean removed = user.getJournalEntries()
                .removeIf(entry -> entry.getId().equals(id));

        if (removed) {
            userService.saveEntry(user);
            journalEntryRepository.deleteById(id);
        }

        return removed;
    }
}
