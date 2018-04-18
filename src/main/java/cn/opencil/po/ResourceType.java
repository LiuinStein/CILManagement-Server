package cn.opencil.po;

import cn.opencil.validation.group.AddResourceTypeValidation;
import cn.opencil.validation.group.NotNullResourceTypeIdValidation;
import com.shaoqunliu.validation.annotation.DigitsPattern;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class ResourceType {
    @Positive
    @NotNull(groups = {
            NotNullResourceTypeIdValidation.class
    })
    private Integer id;

    @Size(max = 100)
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private String name;

    @Size(max = 300)
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private String description;

    @DigitsPattern(regexp = "[01]")
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private Boolean disposable;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Boolean getDisposable() {
        return disposable;
    }

    public void setDisposable(Boolean disposable) {
        this.disposable = disposable;
    }
}