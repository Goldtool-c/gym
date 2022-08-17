package by.gladyshev.gym.form;

import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.MembershipPeriod;
import by.gladyshev.gym.entity.MembershipType;
import by.gladyshev.gym.entity.MembershipVisit;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MembershipForm {
    private MembershipType type;
    private String dateFrom;
    private MembershipPeriod period;
    private MembershipVisit visits;
}
