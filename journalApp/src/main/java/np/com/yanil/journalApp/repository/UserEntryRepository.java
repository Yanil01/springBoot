package np.com.yanil.journalApp.repository;


import np.com.yanil.journalApp.entity.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserEntryRepository extends MongoRepository<UserEntry, ObjectId> {

    UserEntry findByUserName(String userName);
}
