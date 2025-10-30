package uz.pdp.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthUserDAO;
import uz.pdp.domains.AuthUser;


import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDAO authUserDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username '%s'".formatted(username)));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authUser.getRole());
        return new User(authUser.getUname(), authUser.getPwd(), List.of(authority));
    }

}
