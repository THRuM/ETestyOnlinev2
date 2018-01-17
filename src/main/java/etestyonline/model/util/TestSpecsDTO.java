package etestyonline.model.util;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
/*
In case of added two object of the same class (different name) binding results in value from first in both objects.
    <spring:bind path="timeDTO.value">
        <input type="number" name="value" placeholder="${timeHeader}">
    </spring:bind>

    <spring:bind path="amountDTO.value">
        <input type="number" name="value" placeholder="${amountHeader}"
    </spring:bind>
 */
public class TestSpecsDTO {

    @Min(1)
    private Integer valueOne;

    @Min(1)
    private Integer valueTwo;

    MultipartFile file;

    public Integer getValueOne() {
        return valueOne;
    }

    public void setValueOne(Integer valueOne) {
        this.valueOne = valueOne;
    }

    public Integer getValueTwo() {
        return valueTwo;
    }

    public void setValueTwo(Integer valueTwo) {
        this.valueTwo = valueTwo;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
