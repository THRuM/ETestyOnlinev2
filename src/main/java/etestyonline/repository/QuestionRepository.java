package etestyonline.repository;

import etestyonline.model.Question;
import etestyonline.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepository extends MongoRepository<Question, String> {
    Question getQuestionById(String questionId);
    Question getQuestionByIdAndOwner(String questionId, User owner);

    Page<Question> getAllByOwner(User owner, Pageable pageable);
}
