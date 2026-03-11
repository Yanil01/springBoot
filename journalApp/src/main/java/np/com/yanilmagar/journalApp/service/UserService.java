package np.com.yanilmagar.journalApp.service;


import np.com.yanilmagar.journalApp.entity.User;
import np.com.yanilmagar.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public void saveEntry(User user){
        userRepository.save(user);
    }

    public List<User>getAll(){
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }


}
