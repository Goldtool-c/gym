package by.gladyshev.gym.entity;

import lombok.Data;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "members")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private MemberRole role;
}
