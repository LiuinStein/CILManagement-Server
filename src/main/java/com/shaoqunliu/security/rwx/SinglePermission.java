package com.shaoqunliu.security.rwx;

public class SinglePermission {
    private boolean yourOwn = false;
    private boolean inGroup = false;
    private boolean others = false;

    public SinglePermission() {
        // nothing here
    }

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
