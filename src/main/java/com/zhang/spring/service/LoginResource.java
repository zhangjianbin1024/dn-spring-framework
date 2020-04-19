package com.zhang.spring.service;

public class LoginResource {
    private LoginService loginService;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginService getLoginService() {
        return loginService;
    }

    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    public void login() {
        loginService.login();
    }
}