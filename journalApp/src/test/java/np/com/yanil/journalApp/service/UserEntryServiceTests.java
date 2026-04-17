package np.com.yanil.journalApp.service;


import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserEntryServiceTests {

    @Autowired
    UserEntryRepository userEntryRepository;

    @Test
    public void testFindByUserName(){
        UserEntry userEntry = userEntryRepository.findByUserName("ram"); 
        assertTrue(!userEntry.getJournalEntries().isEmpty());
    }
}
