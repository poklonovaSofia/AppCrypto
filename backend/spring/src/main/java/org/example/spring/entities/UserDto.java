package org.example.spring.entities;

public class UserDto {
    private String username;
    private String email;
    private String password;
    private Long id;

    public UserDto() {
    }

    public UserDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id=id;
    }
    public Long getId(){return  id;}
    @Override
    public String toString() {
        return username + "; " + email + "; ";
    }
}