package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MembershipDAO;
import by.gladyshev.gym.entity.Membership;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/membership")
@RequiredArgsConstructor
public class MembershipController {
    private final MembershipDAO membershipDAO;

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("membership", membershipDAO.show(id));
        return "membership/show";
    }
    @PostMapping("/visit/{id}")
    public String visit(@PathVariable("id") int id)
    {
        Membership membership = (Membership) membershipDAO.show(id);
        membership.visit();
        membershipDAO.update(membership);//todo exclude double visit
        return "redirect:/membership/"+id;
    }
}
