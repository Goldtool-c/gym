package by.gladyshev.gym.service;

import by.gladyshev.gym.dao.MembershipDAO;
import by.gladyshev.gym.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final MembershipDAO membershipDAO;
    public void visit(int id)
    {
        Membership membership = (Membership) membershipDAO.show(id);
        if (System.currentTimeMillis()-membership.getLastVisit()<=3600000)//one hour
        {
            return;
        }
        membership.visit();
        membershipDAO.update(membership);
    }
}
