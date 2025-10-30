package uz.pdp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uz.pdp.daos.AuthUserDAO;
import uz.pdp.domains.AuthUser;
import uz.pdp.dtos.UserRegisterDTO;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthUserDAO authUserDAO;
    private final PasswordEncoder encoder;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

    @GetMapping("/logout")

    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/logout");
        return modelAndView;
    }

    @GetMapping("/register")

    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("auth/register");
        return modelAndView;
    }


    @PostMapping("/register")
    public String register(@ModelAttribute UserRegisterDTO dto) {
        AuthUser authUser = AuthUser.builder()
                .uname(dto.username())
                .pwd(encoder.encode(dto.password()))
                .role("ROLE_USER") // qo‘shish shart
                .build();
         authUserDAO.save(authUser);
        return "redirect:/auth/login";
    }
}
