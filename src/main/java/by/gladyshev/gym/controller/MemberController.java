package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberDAO memberDAO;
    @GetMapping
    public String index(Model model)
    {
         model.addAttribute("members", memberDAO.index());
         return "member/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model)
    {
         model.addAttribute("member", memberDAO.show(id));
         System.out.println(memberDAO.show(id));
         return "member/show";
    }
    @GetMapping("/create")
    public String create(Model model)
    {
        model.addAttribute("member", new Member());
        //model.addAttribute("roles", MemberRole.values());
        return "member/create";
    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable int id, Model model)
    {
        model.addAttribute("member", memberDAO.show(id));
        return "member/update";
    }
    @PostMapping("/create")
    public String create(@ModelAttribute("member") Member member)
    {
        memberDAO.create(member);
        return "redirect:/";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("member") Member member)
    {
        memberDAO.update(member);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id)
    {
        memberDAO.delete(id);
        return "redirect:/";
    }

}
