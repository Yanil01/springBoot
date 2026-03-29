package np.com.yanil.journalApp.service;

import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserEntryService {

    @Autowired
    private UserEntryRepository userEntryRepository;

    public void saveEntry(UserEntry userEntry){
        userEntryRepository.save(userEntry);
    }

    public List<UserEntry> getAllEntry(){
        return userEntryRepository.findAll();
    }

    public UserEntry findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }
}
