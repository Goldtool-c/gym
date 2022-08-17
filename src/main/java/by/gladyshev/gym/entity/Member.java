package by.gladyshev.gym.entity;

import by.gladyshev.gym.entity.impl.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Entity {
    private int id;
    private String name;
    private String password;
    private MemberRole role;
    private ArrayList<Membership> memberships = new ArrayList<>();
}
