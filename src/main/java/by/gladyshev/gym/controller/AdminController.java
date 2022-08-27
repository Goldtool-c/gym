package by.gladyshev.gym.controller;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.PriceList;
import by.gladyshev.gym.form.PriceForm;
import by.gladyshev.gym.form.TextForm;
import by.gladyshev.gym.repository.MemberRepository;
import by.gladyshev.gym.service.GetPriceService;
import by.gladyshev.gym.service.SetPriceService;
import by.gladyshev.gym.service.SetTextService;
import by.gladyshev.gym.util.AuthFacade;
import by.gladyshev.gym.util.IsUserAuthenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final GetPriceService getPriceService;
    private final MemberRepository repo;
    private final AuthFacade authFacade;
    private final SetPriceService setPriceService;
    private final SetTextService setTextService;
    @GetMapping("/pricelist")
    @PreAuthorize("hasAuthority('post')")
    public String getPriceList(Model model) {
        if(IsUserAuthenticated.isAuth())
        {
            Member member = repo.findByName(authFacade.getAuthentication().getName()).orElse(new Member());
            model.addAttribute("userId", member.getId());
        } else {
            model.addAttribute("userId", 0);
        }
        model.addAttribute("price", getPriceService.getPrice());
        return "admin/pricelist";
    }
    @GetMapping("/setMainText")
    @PreAuthorize("hasAuthority('post')")
    public String setText(Model model)
    {
        model.addAttribute("text", new TextForm());
        return "admin/setText";
    }
    @PostMapping("/pricelist")
    @PreAuthorize("hasAuthority('post')")
    public String setPriceList(@ModelAttribute("price")PriceList form)
    {
        setPriceService.setPrice(form);
        return "redirect:/";
    }
    @PostMapping("/setMainText")
    @PreAuthorize("hasAuthority('post')")
    public String setText(@ModelAttribute("text") TextForm text)
    {
        setTextService.setText(text.getText());
        return "redirect:/";
    }


}
