package com.mkr.oodpatterns.behavioral;

interface Authenticator {
    void login();

    Authenticator passwordAuthenticator =
            () -> System.out.println("Logging in with username and password");
    Authenticator ssoAuthenticator =
            () -> System.out.println("Logging in with Single Sign-On");
}
//class PasswordAuthenticator implements Authenticator {
//    @Override
//    public void login() {
//        System.out.println("Logging in with username and password");
//    }
//}
//class SSOAuthenticator implements Authenticator {
//    @Override
//    public void login() {
//        System.out.println("Logging in with Single Sign-On");
//    }
//}
class LoginPage {
    public void loginUser(Authenticator authenticator) {
        authenticator.login();
    }
}
public class StrategyDemo {
    public static void main(String[] args) {
        var loginPage = new LoginPage();
        loginPage.loginUser(Authenticator.passwordAuthenticator);
        loginPage.loginUser(Authenticator.ssoAuthenticator);
    }
}
