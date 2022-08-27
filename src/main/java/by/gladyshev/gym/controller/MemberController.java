package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.User;
import by.gladyshev.gym.exception.UserAlreadyExistsException;
import by.gladyshev.gym.form.MemberSearchForm;
import by.gladyshev.gym.repository.MemberRepository;
import by.gladyshev.gym.repository.UserRepository;
import by.gladyshev.gym.service.GetTextService;
import by.gladyshev.gym.service.PaginationService;
import by.gladyshev.gym.service.ShowMemberService;
import by.gladyshev.gym.util.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberDAO memberDAO;
    private final AuthFacade authFacade;
    private final PaginationService paginationService;
    private final ShowMemberService showMemberService;
    private final UserRepository repo;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private int maxPage;
    @Value("${paginationStringNumber}")
    private int rowsNumber;
    private int maxID;
    @PostConstruct
    public void setMaxPage()
    {
        maxID = memberDAO.getID();
        maxPage = (maxID/rowsNumber);
        if(maxID%rowsNumber==1)
        {
            maxPage++;
        }
        if(maxPage==0)
        {
            maxPage=1;
        }
    }
    @GetMapping
    @PreAuthorize("hasAuthority('private:read')")
    public String index(Model model)
    {
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", 1);
        model.addAttribute("members", paginationService.getPage(1, maxID));
        model.addAttribute("searchForm", new MemberSearchForm());
        return "member/index";
    }
    @GetMapping("/page/{id}")
    @PreAuthorize("hasAuthority('private:read')")
    public String page(@PathVariable("id") int page, Model model)
    {
        model.addAttribute("members", paginationService.getPage(page, maxID));
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", page);
        model.addAttribute("searchForm", new MemberSearchForm());
        return "member/index";
    }
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public String show(@PathVariable("id") int id, Model model)
    {
        Member member = (Member) memberDAO.show(id);
        model.addAttribute("member", member);
        User authUser = repo.findByName(authFacade.getAuthentication().getName()).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
        model.addAttribute("authUser", authUser);
        model.addAttribute("userId", authUser.getId());
        return "member/"+showMemberService.show(authUser, member);
    }
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('post')")
    public String create(Model model)
    {
        model.addAttribute("member", new Member());
        //model.addAttribute("roles", MemberRole.values());
        return "member/create";
    }
    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String update(@PathVariable int id, Model model)
    {
        model.addAttribute("member", memberDAO.show(id));
        return "member/update";
    }
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('post')")
    public String create(@ModelAttribute("member") Member member)
    {
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        try {
            memberDAO.create(member);
        } catch (UserAlreadyExistsException e) {
            e.printStackTrace();
            throw new ResponseStatusException(
                    HttpStatus.FOUND, e.getLocalizedMessage()
            );
        }
        setMaxPage();
        return "redirect:/";
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String update(@PathVariable("id") int id, @ModelAttribute("member") Member member)
    {
        memberDAO.update(member);
        return "redirect:/member";
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String delete(@PathVariable("id") int id)
    {
        memberDAO.delete(id);
        setMaxPage();
        return "redirect:/member";
    }
    @PostMapping("/search")
    @PreAuthorize("hasAuthority('post')")
    public String search(@ModelAttribute("searchForm")MemberSearchForm form, Model model)
    {
        Member member = memberRepository.findByName(form.getName()).orElseThrow(()->
                 new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        ArrayList<Member> members = new ArrayList<>();
        members.add(member);
        model.addAttribute("maxPage", 1);
        model.addAttribute("page", 1);
        model.addAttribute("members", members);
        model.addAttribute("searchForm", new MemberSearchForm());
        return "member/index";
    }
}
