package by.gladyshev.gym.service;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.entity.User;
import org.springframework.stereotype.Service;

@Service
public class ShowMemberService {
    public String show(User authenticatedUser, Member member)
    {
        if(authenticatedUser.getRole() == MemberRole.ADMIN||
                authenticatedUser.getId()==member.getId())
        {
            return "show";
        }
        return "showInfo";
    }
}
