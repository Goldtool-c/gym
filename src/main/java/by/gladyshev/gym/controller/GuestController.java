package by.gladyshev.gym.controller;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.repository.MemberRepository;
import by.gladyshev.gym.service.GetPriceService;
import by.gladyshev.gym.util.AuthFacade;
import by.gladyshev.gym.util.IsUserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/guest")
public class GuestController {

    private final MemberRepository repo;
    private final GetPriceService getPriceService;
    private final AuthFacade authFacade;
    @GetMapping("/trainers")
    public String trainers(Model model)
    {
        if(IsUserAuthenticated.isAuth())
        {
            Member member = repo.findByName(authFacade.getAuthentication().getName()).orElse(new Member());
            model.addAttribute("userId", member.getId());
        } else {
            model.addAttribute("userId", 0);
        }
        List<Member> members = repo.findByRoleOrRole(MemberRole.TRAINER, MemberRole.ADMIN);
        model.addAttribute("trainers", members);
        return "guest/trainers";
    }
    @GetMapping("/pricelist")
    public String priceList(Model model)
    {
        if(IsUserAuthenticated.isAuth())
        {
            Member member = repo.findByName(authFacade.getAuthentication().getName()).orElse(new Member());
            model.addAttribute("userId", member.getId());
        } else {
            model.addAttribute("userId", 0);
        }
        model.addAttribute("price", getPriceService.getPrice());
        return "guest/priceList";
    }
}
