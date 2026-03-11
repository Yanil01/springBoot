package np.com.yanilmagar.journalApp.repository;

import np.com.yanilmagar.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository <User, ObjectId> {
    User findByUserName(String userName);


}
