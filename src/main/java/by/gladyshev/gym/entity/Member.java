package by.gladyshev.gym.entity;

import by.gladyshev.gym.entity.impl.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Entity {
    private int id;
    private String name;
    private String password;
    private MemberRole role;
    private ArrayList<Integer> memberships = new ArrayList<>();
}
