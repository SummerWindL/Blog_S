package com.sunyard.entity;

import java.util.Date;

public class TbUser {
    private Integer userid;

    private String username;

    private String emailaddress;

    private String password;

    private Date createtime;

    private Integer loginflag;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress == null ? null : emailaddress.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getLoginflag() {
        return loginflag;
    }

    public void setLoginflag(Integer loginflag) {
        this.loginflag = loginflag;
    }
}