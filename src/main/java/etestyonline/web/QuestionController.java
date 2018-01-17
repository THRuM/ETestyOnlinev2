package etestyonline.web;

import etestyonline.model.util.QuestionDTO;
import etestyonline.service.CategoryService;
import etestyonline.service.QuestionService;
import etestyonline.service.TestUtilService;
import etestyonline.service.exceptions.CategoryNotFoundException;
import etestyonline.service.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TestUtilService testUtilService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allQuestions(Model model, Pageable pageable){

        model.addAttribute("questions", questionService.getAllQuestions(pageable));

        return "allQuestions";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addQuestion(Model model){
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setCorrectAnswer(0);

        questionDTO.setImgStr(testUtilService.getDefaultImage());

        model.addAttribute("question", questionDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("addQuestion", true);

        return "addQuestion";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addQuestionPOST(@ModelAttribute("question") @Valid QuestionDTO questionDTO, BindingResult result,
                                  @RequestParam("file") MultipartFile file, Model model) throws IOException {

        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("addQuestion", true);
            ((QuestionDTO)model.asMap().get("question")).setImgStr(testUtilService.getDefaultImage());
            return "addQuestion";
        }

        questionDTO.setImgInputStream(file.getInputStream());

        try{
            questionService.addQuestion(questionDTO);
        } catch (CategoryNotFoundException e){
            result.reject("NotEmpty.category");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("addQuestion", true);
            return "addQuestion";
        }

        return "redirect:/question/all";
    }

    @RequestMapping(value = "/edit/{questionId}", method = RequestMethod.GET)
    public String editQuestion(@PathVariable("questionId") String questionId, Model model) {
        QuestionDTO questionDTO = questionService.getQuestionDTO(questionId);

        if(questionDTO.getImgStr() == null){
            questionDTO.setImgStr(testUtilService.getDefaultImage());
        }

        model.addAttribute("question", questionDTO);
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("addQuestion", false);

        return "addQuestion";
    }

    @RequestMapping(value = "/edit/{questionId}", method = RequestMethod.POST)
    public String editQuestionPOST(@PathVariable("questionId") String questionId, @ModelAttribute("question") @Valid QuestionDTO questionDTO, BindingResult result,
                                   @RequestParam("file") MultipartFile file ,Model model) throws IOException {
        if(result.hasErrors()) {
            model.addAttribute("addQuestion", false);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "addQuestion";
        }

        questionDTO.setImgInputStream(file.getInputStream());

        try {
            questionService.editQuestion(questionDTO);
        }catch (QuestionNotFoundException | CategoryNotFoundException e){
            result.reject("NotEmpty.category");
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("addQuestion", false);
            return "addQuestion";
        }

        return "redirect:/question/all";
    }

    @RequestMapping(value = "/delete/{questionId}", method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("questionId") String questionId) {

        questionService.deleteQuestionById(questionId);

        return "redirect:/question/all";
    }
}
