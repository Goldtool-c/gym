package by.gladyshev.gym.controller;

import by.gladyshev.gym.dao.MemberDAO;
import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.User;
import by.gladyshev.gym.repository.UserRepository;
import by.gladyshev.gym.service.PaginationService;
import by.gladyshev.gym.service.ShowMemberService;
import by.gladyshev.gym.util.AuthFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberDAO memberDAO;
    private final AuthFacade authFacade;
    private final PaginationService paginationService;
    private final ShowMemberService showMemberService;
    private final UserRepository repo;
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
    //@PreAuthorize("hasAuthority('private:read')")
    public String index(Model model)
    {
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", 1);
        model.addAttribute("members", paginationService.getPage(1, maxID));
        return "member/index";
    }
    @GetMapping("/page/{id}")
    @PreAuthorize("hasAuthority('private:read')")
    public String page(@PathVariable("id") int page, Model model)
    {
        model.addAttribute("members", paginationService.getPage(page, maxID));
        model.addAttribute("maxPage", maxPage);
        model.addAttribute("page", page);
        return "member/index";
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('public:read')")
    public String show(@PathVariable("id") int id, Model model)
    {
        Member member = (Member) memberDAO.show(id);
        model.addAttribute("member", member);
        User authUser = repo.findByName(authFacade.getAuthentication().getName()).orElseThrow(() ->
                new UsernameNotFoundException("User doesn't exists"));
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
        memberDAO.create(member);
        setMaxPage();
        return "redirect:/";
    }
    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String update(@PathVariable("id") int id, @ModelAttribute("member") Member member)
    {
        memberDAO.update(member);
        return "redirect:/";
    }
    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('post')")
    public String delete(@PathVariable("id") int id)
    {
        memberDAO.delete(id);
        setMaxPage();
        return "redirect:/";
    }

}
