
package com.gem.timekeeper.data.dto;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DepartmentDTO implements Serializable {

  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("code")
  @Expose
  private String code;
  @SerializedName("title")
  @Expose
  private String title;
  @SerializedName("manager_id")
  @Expose
  private String managerId;
  private final static long serialVersionUID = -9003881043228046882L;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getManagerId() {
    return managerId;
  }

  public void setManagerId(String managerId) {
    this.managerId = managerId;
  }

}
