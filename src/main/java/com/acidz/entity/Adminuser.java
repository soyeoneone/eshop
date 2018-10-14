package com.acidz.entity;

public class Adminuser {
  private String id;
  private String name;
  private String password;
  private java.sql.Timestamp lastlogintime;
  private Long level;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public java.sql.Timestamp getLastlogintime() {
    return lastlogintime;
  }

  public void setLastlogintime(java.sql.Timestamp lastlogintime) {
    this.lastlogintime = lastlogintime;
  }

  public Long getLevel() {
    return level;
  }

  public void setLevel(Long level) {
    this.level = level;
  }
}
