package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.repository.MemberRepository;
import by.gladyshev.gym.service.GetTextService;
import by.gladyshev.gym.util.AuthFacade;
import by.gladyshev.gym.util.IsUserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemberRepository repo;
    private final AuthFacade authFacade;
    private final GetTextService getTextService;
    @GetMapping
    public String index(Model model)
    {
        if(IsUserAuthenticated.isAuth())
        {
            Member member = repo.findByName(authFacade.getAuthentication().getName()).orElse(new Member());
            model.addAttribute("userId", member.getId());
        } else {
            model.addAttribute("userId", 0);
        }
        model.addAttribute("mainText", getTextService.getText());
        return "index";
    }
}
