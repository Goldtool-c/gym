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
    }



}
