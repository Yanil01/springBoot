package np.com.yanil.journalApp.service;

import np.com.yanil.journalApp.entity.UserEntry;
import np.com.yanil.journalApp.repository.UserEntryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImpTests {

    @InjectMocks
    private UserDetailsServiceImp userDetailsServiceImp;

    @Mock
    private UserEntryRepository userEntryRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testLoadUserByUsername(){
        when(userEntryRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(UserEntry.builder().userName("ram").userPassword("dsbdsds").roles(new ArrayList<>()).build());
        UserDetails userDetails = userDetailsServiceImp.loadUserByUsername("ram");
        Assertions.assertNotNull(userDetails);

    }
}
