package game.destinyofthechosen.service.impl;

import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecurityUserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public SecurityUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format("User with %s does not exist!",
                                        username)));

        return mapToUserDetails(userEntity);
    }

    private static UserDetails mapToUserDetails(UserEntity userEntity) {

        List<GrantedAuthority> authorities = userEntity
                .getUserRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getUserRole().name()))
                .collect(Collectors.toList());

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }
}
