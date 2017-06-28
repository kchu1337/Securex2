package byAJ.Securex.services;

import byAJ.Securex.models.*;
import byAJ.Securex.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by student on 6/28/17.
 */
@Transactional
public class SSUserDetailsServices implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSUserDetailsServices.class);
    private UserRepository userRepository;
    public SSUserDetailsServices(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                return null;
            }

            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), getAuthorities(user));
        }
        catch(Exception e){

        }
        return null;
    }

    private Set<GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role : user.getRoles()){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}
