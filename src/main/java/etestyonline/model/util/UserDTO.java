package etestyonline.model.util;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordMatches
public class UserDTO {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 15)
    @Pattern(regexp = "\\A[\\d\\w_-]+\\Z")
    private String name;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9!#\\\\&$%'*+=?^`{}|~_-](\\.?[a-zA-Z0-9\\\\!#$&%'*+=?^`{}|~_-]){0,}@[a-zA-Z0-9]+\\.(?!-)([a-zA-Z0-9]?((-?[a-zA-Z0-9]+)+\\.(?!-))){0,}[a-zA-Z0-9]{2,8}$")
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
