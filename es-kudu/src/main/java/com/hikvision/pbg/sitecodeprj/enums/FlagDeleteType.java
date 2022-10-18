package com.hikvision.pbg.sitecodeprj.enums;

public enum FlagDeleteType {

    WAIT(0,"待删除"),
    DONE(1,"已删除"),
    ;

    private int type;
    private String description;

    FlagDeleteType(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
