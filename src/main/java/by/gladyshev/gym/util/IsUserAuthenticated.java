package by.gladyshev.gym.util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class IsUserAuthenticated {
    public static boolean isAuth()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  !(authentication instanceof AnonymousAuthenticationToken);
    }
}
