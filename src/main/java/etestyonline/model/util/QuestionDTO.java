package etestyonline.model.util;

import javax.validation.constraints.*;
import java.io.InputStream;

public class QuestionDTO {
    @NotNull
    @Size(min = 10)
    private String text;

    @NotNull
    @Size(min = 1)
    private String answer1;

    @NotNull
    @Size(min = 1)
    private String answer2;

    @NotNull
    @Size(min = 1)
    private String answer3;

    @NotNull
    @Size(min = 1)
    private String answer4;

    @NotEmpty
    private String category;

    @NotNull
    @Min(value = 0)
    @Max(value = 3)
    private Integer correctAnswer;

    private InputStream imgInputStream;

    private String imgStr;

    private String questionId;

    private Boolean resetImage;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public InputStream getImgInputStream() {
        return imgInputStream;
    }

    public void setImgInputStream(InputStream imgInputStream) {
        this.imgInputStream = imgInputStream;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }

    public Boolean getResetImage() {
        return resetImage;
    }

    public void setResetImage(Boolean resetImage) {
        this.resetImage = resetImage;
    }
}
