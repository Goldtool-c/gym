package by.gladyshev.gym.entity;

import by.gladyshev.gym.entity.impl.Entity;
import lombok.Data;

@Data
public class Membership implements Entity {
    private final int id;
    private final int belongsTo;
    private final MembershipType type;
    private final String from;
    private final String to;
    private final int visitNumber;
}
