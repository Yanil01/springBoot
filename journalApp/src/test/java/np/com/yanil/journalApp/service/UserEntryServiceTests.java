package np.com.yanil.journalApp.service;


import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class UserEntryServiceTests {

    @Autowired
    UserEntryRepository userEntryRepository;


    @ParameterizedTest
    @ValueSource(strings = {
            "ram",
            "yanil02",
            "jack02",
            "hari"
    })
    public void testFindByUserName(String userName){
        UserEntry userEntry = userEntryRepository.findByUserName(userName);
        assertNotNull(userEntry);
    }

    @Disabled
    @ParameterizedTest
    @CsvSource({
            "ram",
            "yanil02",
            "jack02",
            "hari"
    })
    public void testFindJournalByUserName(String userName){
        UserEntry userEntry = userEntryRepository.findByUserName(userName);
        assertFalse(userEntry.getJournalEntries().isEmpty());
    }
}
