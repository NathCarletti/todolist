package br.com.nathaliacarletti.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //getters and setters para todos os atributos
//se quero isoladamente, usa-se @getters ou @setters em cima do componente
@Entity(name= "tb_users") //nome da tabela 
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id; //gerador de id

    @Column(unique = true)
    private String username;
    
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    /*public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }*/

    // getters setters
    
}
