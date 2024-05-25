/*
package com.example.languagelearningmodule.security;

import com.example.languagelearningmodule.User.UserRepository;
import com.example.languagelearningmodule.User.User;
import com.example.languagelearningmodule.security.permissions.Permission;
import com.example.languagelearningmodule.security.roles.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Primary
@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    public CustomUserDetailsService() {
            super();
    }

    public CustomUserDetailsService( UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        try {
            final User user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new UsernameNotFoundException("No user found with username: " + email);
                }

                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, getAuthorities(user.getRoles()));
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }


        private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
            return getGrantedAuthorities(getPrivileges(roles));
        }

        private List<String> getPrivileges(final Collection<Role> roles) {
            final List<String> privileges = new ArrayList<>();
            final List<Permission> collection = new ArrayList<>();
            for (final Role role : roles) {
                privileges.add(role.getName());
                collection.addAll(role.getPermissions());
            }
            for (final Permission item : collection) {
                privileges.add(item.getName());
            }

            return privileges;
        }

        private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            for (final String privilege : privileges) {
                authorities.add(new SimpleGrantedAuthority(privilege));
            }
            return authorities;
        }

}
*/
