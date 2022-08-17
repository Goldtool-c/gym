package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MembershipDAO;
import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.form.MembershipForm;
import by.gladyshev.gym.service.MembershipCreateService;
import by.gladyshev.gym.service.VisitService;
import by.gladyshev.gym.valid.MembershipAccessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipDAO membershipDAO;
    private final MembershipCreateService createService;
    private final MembershipAccessValidator membershipAccessValidator;
    private final VisitService visitService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('public:read')")
    public String show(@PathVariable("id") int id, Model model)
    {
        Membership temp = (Membership) membershipDAO.show(id);
        if(!membershipAccessValidator.valid(temp))
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this membership");
        }
        model.addAttribute("membership", temp);
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
        return "redirect:/";
    }
}
