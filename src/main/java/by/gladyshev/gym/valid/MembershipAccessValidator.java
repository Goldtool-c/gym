package by.gladyshev.gym.valid;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.User;
import by.gladyshev.gym.repository.UserRepository;
import by.gladyshev.gym.util.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MembershipAccessValidator {
    private final AuthFacade authFacade;
    private final MemberDAO memberDAO;
    private final UserRepository repo;
    public boolean valid(Membership membership)
    {
        Member member = (Member) memberDAO.show(membership.getOwner());
        User user = repo.findByName(authFacade.getAuthentication().getName()).orElseThrow(()->
                new UsernameNotFoundException("This user is not exists")
        );
        return user.getRole() == MemberRole.ADMIN ||
                member.getName().equals(authFacade.getAuthentication().getName());
    }
}
