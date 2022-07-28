package by.gladyshev.gym;

import by.gladyshev.gym.entity.Member;
import by.gladyshev.gym.entity.MemberRole;
import by.gladyshev.gym.entity.Membership;
import by.gladyshev.gym.entity.MembershipType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
        /*Member member = new Member(1, "Denis", "", MemberRole.MEMBER);
        System.out.println(member);
        Membership membership = new Membership(1, 1, MembershipType.LIMITED,
                "12.07.2022", "12.08.2022", 12);
        System.out.println(membership);*/
    }



}
