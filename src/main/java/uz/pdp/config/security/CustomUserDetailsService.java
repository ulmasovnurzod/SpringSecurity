package uz.pdp.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.daos.AuthRoleDAO;
import uz.pdp.daos.AuthUserDAO;
import uz.pdp.domains.AuthRole;
import uz.pdp.domains.AuthUser;


import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthUserDAO authUserDAO;
    private final AuthRoleDAO authRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by username '%s'".formatted(username)));

        Long userId = authUser.getId();
        List<AuthRole> roles = authRoleDAO.findAllByUserID(userId);

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (AuthRole role : roles) {

            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getCode()));
        }

        return new User(authUser.getUname(), authUser.getPwd(), authorities);
    }


}
