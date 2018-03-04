package com.shaoqunliu.security.rwx;

/**
 * The {@code SinglePermission} class represents a single section of rwx permission.
 * @author <a href="https://www.shaoqunliu.cn/">Shaoqun Liu</a>
 * @since March 4, 2018
 */
public class SinglePermission {
    private boolean yourOwn = false;
    private boolean inGroup = false;
    private boolean others = false;

    /**
     * default constructor
     */
    public SinglePermission() {
        // nothing here
    }

    /**
     * @param own whether the owner has the permission
     * @param group whether the owner's group has the permission
     * @param others whether the others have the permission
     */
    public SinglePermission(boolean own, boolean group, boolean others) {
        this.yourOwn = own;
        this.inGroup = group;
        this.others = others;
    }

    public boolean toYourOwn() {
        return yourOwn;
    }

    public boolean toYourGroup() {
        return inGroup;
    }

    public boolean toOthers() {
        return others;
    }

    public void setYourOwn(boolean yourOwn) {
        this.yourOwn = yourOwn;
    }

    public void setInGroup(boolean inGroup) {
        this.inGroup = inGroup;
    }

    public void setOthers(boolean others) {
        this.others = others;
    }
}
