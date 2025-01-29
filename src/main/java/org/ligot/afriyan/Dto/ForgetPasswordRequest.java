package org.ligot.afriyan.Dto;

public class ForgetPasswordRequest {
    private String login;

    private String code;
    private String password;
    private String confirm;

    public ForgetPasswordRequest() {
    }

    public ForgetPasswordRequest(String login, String code, String password, String confirm) {
        this.login = login;
        this.code = code;
        this.password = password;
        this.confirm = confirm;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
