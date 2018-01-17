package etestyonline.service;

import etestyonline.model.Category;
import etestyonline.model.Question;
import etestyonline.model.util.QuestionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface QuestionService {

    @PreAuthorize("hasRole('TEACHER')")
    String addQuestion(QuestionDTO questionDTO);

    @PreAuthorize("hasRole('TEACHER')")
    String editQuestion(QuestionDTO questionDTO);

    @PreAuthorize("hasRole('TEACHER')")
    Question getQuestion(String questionId);

    @PreAuthorize("hasRole('TEACHER')")
    Page<Question> getAllQuestions(Pageable pageable);

    @PreAuthorize("hasRole('TEACHER')")
    QuestionDTO getQuestionDTO(String questionId);

    @PreAuthorize("hasRole('TEACHER')")
    void deleteQuestionById(String questionId);

    @PreAuthorize("hasRole('USER')")
    List<Question> getRandomQuestionsForCategoryAndAmount(List<Category> categories, Integer amount);
}
