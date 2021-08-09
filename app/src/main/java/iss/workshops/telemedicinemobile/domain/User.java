package iss.workshops.telemedicinemobile.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User
{
    @Expose
    @SerializedName("id")
    private Long id;
    @Expose
    @SerializedName("username")
    private String userName;

    @Expose
    @SerializedName("password")
    private String password;

    @Expose
    @SerializedName("role")
    private Role role;

    public User() {
    }


    public User(Long id, String userName, String password, Role role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
