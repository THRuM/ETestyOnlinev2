package etestyonline.web;

import etestyonline.model.Question;
import etestyonline.model.Test;
import etestyonline.model.TestParams;
import etestyonline.model.TestSpecs;
import etestyonline.model.util.SETTINGS;
import etestyonline.service.TestService;
import etestyonline.service.TestUtilService;
import etestyonline.service.exceptions.BadInputException;
import etestyonline.service.exceptions.QuestionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("test")
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestUtilService testUtilService;

    @RequestMapping(value = "begin", method = RequestMethod.GET)
    public String beginTest(Model model, HttpServletRequest request) {

        if(request.getSession().getAttribute("test") != null) {
            return "redirect:/test/question/0";
        }

        TestSpecs testSpecs = testUtilService.getTestSpecs();

        model.addAttribute("times", testSpecs.getTimes());
        model.addAttribute("amounts", testSpecs.getAmounts());
        model.addAttribute("categories", testSpecs.getCategories());
        model.addAttribute("testParams", new TestParams());

        return "beginTest";
    }

    @RequestMapping(value = "begin", method = RequestMethod.POST)
    public String beginTestPOST(@ModelAttribute("testParams") TestParams testParams, BindingResult result, HttpServletRequest request, Model model) {

        if(request.getSession().getAttribute("test") != null) {
            return "redirect:/test/question/0";
        }

        Test test;

        try {
            test = testService.makeTest(testParams);
            request.getSession().setAttribute("test", test);

        } catch (BadInputException | QuestionNotFoundException e) {
            result.reject("Test.wrongParams");

            TestSpecs testSpecs = testUtilService.getTestSpecs();

            model.addAttribute("times", testSpecs.getTimes());
            model.addAttribute("amounts", testSpecs.getAmounts());
            model.addAttribute("categories", testSpecs.getCategories());

            return "beginTest";
        }

        return "redirect:/test/question/0";
    }

    @RequestMapping(value = "question/{questionNumber}", method = RequestMethod.GET)
    public String testQuestion(@ModelAttribute("test") Test test, @PathVariable("questionNumber") String questionNumberString, Model model) {

        long secToEnd = testService.checkTimeForTest(test);

        if(secToEnd <= 0){
            return "redirect:/test/end";
        }

        int questionNumber = Integer.parseInt(questionNumberString);

        if(questionNumber >= test.getQuestions().size() || questionNumber < 0){
            return "redirect:/test/question/0";
        }

        int lastQuestionNumber = test.getQuestions().size() - 1;

        if(lastQuestionNumber == questionNumber){
            model.addAttribute("lastQuestion", true);
        }

        Question question = test.getQuestions().get(questionNumber);

        model.addAttribute("text", question.getText());
        model.addAttribute("answers", question.getAnswers());
        model.addAttribute("category", question.getCategory().getDescription());
        model.addAttribute("number",questionNumber);
        model.addAttribute("maxNumber", test.getQuestions().size()-1);
        model.addAttribute("ansLetters", SETTINGS.ansLetters);
        model.addAttribute("selectedAnswer", question.getSelectedAnswer());
        model.addAttribute("secToEnd", secToEnd);

        if(question.getImgStr() == null){
            model.addAttribute("image", testUtilService.getDefaultImage());
        } else {
            model.addAttribute("image", question.getImgStr());
        }

        return "testQuestion";
    }

    @RequestMapping(value = "question/{questionNumber}", method = RequestMethod.POST)
    public String testQuestionPOST(@ModelAttribute("test") Test test,
                                   @RequestParam(value = "selectedAnswer", defaultValue = "99") String selectedAnswer,
                                   @RequestParam(value = "direction", defaultValue = "forward") String direction,
                                   @PathVariable("questionNumber") String questionNumberString) {

        long secToEnd = testService.checkTimeForTest(test);

        if(secToEnd <= 0){
            return "redirect:/test/end";
        }

        int questionNumber = Integer.parseInt(questionNumberString);

        if(questionNumber >= test.getQuestions().size() || questionNumber < 0){
            return "redirect:/test/question/0";
        }

        Integer selectedAnswerNumber;

        try{
            selectedAnswerNumber = Integer.parseInt(selectedAnswer);
        } catch (NumberFormatException e){
            throw new BadInputException("Number of selected answer is not correct: " + selectedAnswer);
        }

        if(selectedAnswerNumber != 99 && (selectedAnswerNumber >= 4 || selectedAnswerNumber < 0)){
            throw new BadInputException("Number of selected answer is not correct: " + selectedAnswerNumber);
        }

        Question question = test.getQuestions().get(questionNumber);

        question.setSelectedAnswer(selectedAnswerNumber);

        int nextQuestionNumber = questionNumber + 1;

        if(nextQuestionNumber >= test.getQuestions().size()) {
            return "redirect:/test/review";
        }

        if("backward".equals(direction)){
            return "redirect:/test/question/" + (questionNumber - 1);
        } else {
            return "redirect:/test/question/" + (questionNumber + 1);
        }
    }

    @RequestMapping(value = "review", method = RequestMethod.GET)
    public String testReview(@ModelAttribute("test") Test test, Model model) {

        model.addAttribute("questions", test.getQuestions());
        model.addAttribute("ansLetters", SETTINGS.ansLetters);

        return "testReview";
    }

    @RequestMapping(value = "end", method = RequestMethod.GET)
    public String testEnd(@ModelAttribute("test") Test test, Model model, SessionStatus status) {

        model.addAttribute("result", testService.endTest(test));
        status.setComplete();

        return "testEnd";
    }

    @RequestMapping(value = "my", method = RequestMethod.GET)
    public String allMyTests(Model model, Pageable pageable) {

        model.addAttribute("showUser", false);
        model.addAttribute("tests", testService.allTests(pageable));

        return "allTests";
    }

    @RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public String allTestForUserId(@PathVariable("userId") String userId, Model model, Pageable pageable) {

        model.addAttribute("showUser", true);
        model.addAttribute("tests", testService.allTestsForUserId(userId, pageable));

        return "allTests";
    }

    @RequestMapping(value = "view/{testId}", method = RequestMethod.GET)
    public String testView(@PathVariable("testId") String testId, Model model) {

        model.addAttribute("testFn", testService.getTestForId(testId));

        return "testView";
    }
}
