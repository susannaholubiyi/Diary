package africa.semicolon.DiaryThatRemembers.repository;

import africa.semicolon.DiaryThatRemembers.data.model.Diary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends MongoRepository<Diary, String> {

}
