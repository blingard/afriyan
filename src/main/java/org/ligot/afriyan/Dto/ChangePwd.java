package org.ligot.afriyan.Dto;

public class ChangePwd {
    private String oldPwd;
    private String newPwd;
    private String confirmPwd;

    public ChangePwd() {
    }

    public ChangePwd(String oldPwd, String newPwd, String confirmPwd) {
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
        this.confirmPwd = confirmPwd;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getConfirmPwd() {
        return confirmPwd;
    }

    public void setConfirmPwd(String confirmPwd) {
        this.confirmPwd = confirmPwd;
    }

    @Override
    public String toString() {
        return "ChangePwd{" +
                "oldPwd='" + oldPwd + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", confirmPwd='" + confirmPwd + '\'' +
                '}';
    }
}
