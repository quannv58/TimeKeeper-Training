
package com.gem.timekeeper.data.dto;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeDTO implements Serializable {

  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("employee_code")
  @Expose
  private String employeeCode;
  @SerializedName("full_name")
  @Expose
  private String fullName;
  @SerializedName("status")
  @Expose
  private String status;
  @SerializedName("birthday")
  @Expose
  private String birthday;
  @SerializedName("gender")
  @Expose
  private String gender;
  @SerializedName("address")
  @Expose
  private String address;
  @SerializedName("phone")
  @Expose
  private String phone;
  @SerializedName("department")
  @Expose
  private DepartmentDTO department;
  @SerializedName("avatar")
  @Expose
  private String avatar;
  @SerializedName("user")
  @Expose
  private UserDTO user;
  private final static long serialVersionUID = -6412712668838081994L;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getEmployeeCode() {
    return employeeCode;
  }

  public void setEmployeeCode(String employeeCode) {
    this.employeeCode = employeeCode;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public DepartmentDTO getDepartment() {
    return department;
  }

  public void setDepartment(DepartmentDTO department) {
    this.department = department;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

}
