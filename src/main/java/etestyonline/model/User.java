package etestyonline.model;

import etestyonline.model.util.SETTINGS;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class User implements Serializable{
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    @DBRef
    private OrgUnit orgUnit;
    private boolean enabled;
    private List<String> roles;
    @DBRef
    private User principal;

    public User() {
        enabled = false;
        roles = Arrays.asList("ROLE_USER");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public OrgUnit getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(OrgUnit orgUnit) {
        this.orgUnit = orgUnit;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public User getPrincipal() {
        return principal;
    }

    public void setPrincipal(User principal) {
        if(principal.getRoles().contains("ROLE_TEACHER"))
            this.principal = principal;
    }

    public String getEffectiveRole(){
        String effectiveRole = "USER";

        if(roles.contains(SETTINGS.TEACHER))
            effectiveRole = "TEACHER";

        if(roles.contains(SETTINGS.ADMIN))
            effectiveRole = "ADMIN";

        return effectiveRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isEnabled() != user.isEnabled()) return false;
        if (!getId().equals(user.getId())) return false;
        if (!getName().equals(user.getName())) return false;
        if (!getEmail().equals(user.getEmail())) return false;
        if (!getPassword().equals(user.getPassword())) return false;
        if (!getOrgUnit().equals(user.getOrgUnit())) return false;
        if (!getRoles().equals(user.getRoles())) return false;
        return getPrincipal().equals(user.getPrincipal());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getEmail().hashCode();
        result = 31 * result + getPassword().hashCode();
        result = 31 * result + getOrgUnit().hashCode();
        result = 31 * result + (isEnabled() ? 1 : 0);
        result = 31 * result + getRoles().hashCode();
        return result;
    }
}
