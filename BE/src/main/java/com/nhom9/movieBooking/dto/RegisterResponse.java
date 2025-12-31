package com.nhom9.movieBooking.dto;

public class RegisterResponse {
    private Integer userId;
    private String email;
    private String fullName;
    private String roleUser;
    public RegisterResponse(Integer userId, String email, String fullName, String roleUser) {
        this.userId = userId;
        this.email = email;
        this.fullName = fullName;
        this.roleUser = roleUser;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getRoleUser() {
        return roleUser;
    }
    public void setRoleUser(String roleUser) {
        this.roleUser = roleUser;
    }
    

}
