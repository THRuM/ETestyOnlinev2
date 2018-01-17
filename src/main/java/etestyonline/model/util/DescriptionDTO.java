package etestyonline.model.util;

import javax.validation.constraints.Pattern;

public class DescriptionDTO {
    @Pattern(regexp = SETTINGS.QUESTION_REGEXP)
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
