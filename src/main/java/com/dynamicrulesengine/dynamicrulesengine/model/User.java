package com.dynamicrulesengine.dynamicrulesengine.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String email;
    private  String userRole;
    private String hashedPassword;

}
