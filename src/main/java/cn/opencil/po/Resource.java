package cn.opencil.po;

import cn.opencil.validation.group.AddResourceTypeValidation;
import cn.opencil.validation.group.NotNullResourceIdValidation;
import com.shaoqunliu.validation.annotation.DatabaseColumnReference;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

public class Resource {
    @Positive
    @NotNull(groups = {
            NotNullResourceIdValidation.class
    })
    private Integer id;

    @Positive
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    @DatabaseColumnReference(table = "t_resource_type", column = "id", groups = {
            AddResourceTypeValidation.class
    })
    private Integer typeId;

    @Positive
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    @DatabaseColumnReference(table = "t_rbac_user", column = "id", groups = {
            AddResourceTypeValidation.class
    })
    private Long purchaserId;

    @PositiveOrZero
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private Long unitPrice;

    @PositiveOrZero
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private Integer remaining;

    @PositiveOrZero
    @NotNull(groups = {
            AddResourceTypeValidation.class
    })
    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Long getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(Long purchaserId) {
        this.purchaserId = purchaserId;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}