package np.com.yanilmagar.journalApp.controller;


import np.com.yanilmagar.journalApp.entity.User;

import np.com.yanilmagar.journalApp.repository.UserRepository;
import np.com.yanilmagar.journalApp.service.UserService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }


    @GetMapping("/{userName}")
    public ResponseEntity<?> getUserByUserName(@PathVariable String userName) {
        User user = userService.findByUserName(userName);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {

        User userInDb = userService.findByUserName(userName);

        if (userInDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        userInDb.setUserName(user.getUserName());
        userInDb.setUserPassword(user.getUserPassword());

        userService.saveEntry(userInDb);

        return ResponseEntity.ok(userInDb);
    }
}
