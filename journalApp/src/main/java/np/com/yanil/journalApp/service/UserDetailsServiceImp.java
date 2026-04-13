package np.com.yanil.journalApp.service;

import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserEntryRepository userEntryRepository;

    public UserDetailsServiceImp(UserEntryRepository userEntryRepository) {
        this.userEntryRepository = userEntryRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntry user = userEntryRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        return User.builder()
                .username(user.getUserName())
                .password(user.getUserPassword())
                .roles(user.getRoles().toArray(new String[0]))
                .build();
    }
}