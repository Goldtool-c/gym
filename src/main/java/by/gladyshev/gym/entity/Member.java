package by.gladyshev.gym.entity;

import by.gladyshev.gym.entity.impl.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@javax.persistence.Entity
@Table(name = "members")
public class Member implements Entity {
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
    @Transient
    private ArrayList<Membership> memberships = new ArrayList<>();
    @Column(name = "description")
    private String description;
}
