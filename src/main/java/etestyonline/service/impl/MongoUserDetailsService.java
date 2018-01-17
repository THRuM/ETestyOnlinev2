package etestyonline.service.impl;

import etestyonline.model.User;
import etestyonline.model.CustomUserDetails;
import etestyonline.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MongoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private static Logger logger = LogManager.getLogger(MongoUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) {

        User user = userRepository.findByEmail(email);

        if(user == null){
            logger.error("No user with email: " + email);
            throw new UsernameNotFoundException("No user with email: " + email);
        }

        boolean enabled = user.isEnabled();
        boolean accountNonExpired = true;
        boolean credentialsNonExpires = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = getAuthorities(user.getRoles());

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getEmail(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpires, accountNonLocked, authorities);

        customUserDetails.setName(user.getName());
        customUserDetails.setOrgUnit(user.getOrgUnit());

        return customUserDetails;
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : roles){
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }
}
