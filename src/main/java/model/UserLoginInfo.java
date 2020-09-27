package model;

public class UserLoginInfo {

    private String username;
    private String password;

    public UserLoginInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
