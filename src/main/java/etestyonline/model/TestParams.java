package etestyonline.model;

import java.util.List;

public class TestParams {
    private Integer time;
    private Integer amount;
    private List<String> categoryIds;
    private String orgUnitId;

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnit) {
        this.orgUnitId = orgUnit;
    }
}
