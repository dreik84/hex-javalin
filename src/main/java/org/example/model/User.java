package org.example.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    @ToString.Include
    private String name;
    private String email;
    private String password;
    private int age;

    public User(String name, String email, String password, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
