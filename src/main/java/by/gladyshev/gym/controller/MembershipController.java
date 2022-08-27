package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.dao.MembershipDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.form.MembershipForm;
import by.gladyshev.gym.repository.MemberRepository;
import by.gladyshev.gym.service.MembershipCreateService;
import by.gladyshev.gym.service.VisitService;
import by.gladyshev.gym.util.AuthFacade;
import by.gladyshev.gym.valid.MembershipAccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipDAO membershipDAO;
    private final MemberDAO memberDAO;
    private final MemberRepository memberRepository;
    private final MembershipCreateService createService;
    private final MembershipAccessValidator membershipAccessValidator;
    private final VisitService visitService;
    private final AuthFacade authFacade;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('public:read')")
    public String show(@PathVariable("id") int id, Model model)
    {
        Membership temp = (Membership) membershipDAO.show(id);
        Member user = (Member) memberDAO.show(temp.getOwner());
        Member activeUser = memberRepository.findByName(authFacade.getAuthentication().getName()).orElseThrow(()->
                new UsernameNotFoundException("This user is not exists")
        );
        if(!membershipAccessValidator.valid(temp))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this membership");
        }
        model.addAttribute("activeUser", activeUser);
        model.addAttribute("membership", temp);
        model.addAttribute("user", user);
        return "membership/show";
    }
    @GetMapping("/create/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String create(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("id", id);
        model.addAttribute("membership", new MembershipForm());
        return "membership/create";
    }
    @PostMapping("/visit/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String visit(@PathVariable("id") int id)
    {
        visitService.visit(id);
        return "redirect:/membership/"+id;
    }
    @PostMapping("/create/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String create(@ModelAttribute("membership") MembershipForm form,
                         @PathVariable("id") int id)
    {
        Membership membership = createService.create(id, form);
        membershipDAO.create(membership);
        return "redirect:/member";
    }
}
