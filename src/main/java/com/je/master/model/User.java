/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.je.master.model;

/**
 *
 * @author T39UH
 */
public class User {
    
    private String Id;
    private String Username;
    private String Password;
    private String level;
    private String Email;
    private String Devisi;

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }


    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getDevisi() {
        return Devisi;
    }

    public void setDevisi(String Devisi) {
        this.Devisi = Devisi;
    }
    
    
}
