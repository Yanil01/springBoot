package np.com.yanil.journalApp.service;

import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Component
public class UserEntryService {

    private final UserEntryRepository userEntryRepository;

    public UserEntryService(UserEntryRepository userEntryRepository) {
        this.userEntryRepository = userEntryRepository;
    }

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(UserEntry userEntry){
        userEntry.setUserPassword(Objects.requireNonNull(passwordEncoder.encode(userEntry.getUserPassword())));
        userEntry.setRoles(Arrays.asList("USER"));
        userEntryRepository.save(userEntry);
    }

    public void saveNewUser(UserEntry userEntry){
        userEntryRepository.save(userEntry);
    }
    public List<UserEntry> getAllEntry(){
        return userEntryRepository.findAll();
    }

    public UserEntry findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }
}
