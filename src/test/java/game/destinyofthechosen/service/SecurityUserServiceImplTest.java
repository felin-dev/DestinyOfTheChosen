package game.destinyofthechosen.service;

import game.destinyofthechosen.model.entity.RoleEntity;
import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.UserRoleEnum;
import game.destinyofthechosen.repository.UserRepository;
import game.destinyofthechosen.service.impl.SecurityUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class SecurityUserServiceImplTest {

    private SecurityUserServiceImpl serviceToTest;
    private UserEntity testUser;

    @Mock
    private UserRepository mockedUserRepository;

    @BeforeEach
    void init() {
        serviceToTest = new SecurityUserServiceImpl(mockedUserRepository);

        RoleEntity adminRole = new RoleEntity().setUserRole(UserRoleEnum.ADMIN);
        RoleEntity userRole = new RoleEntity().setUserRole(UserRoleEnum.USER);

        testUser = new UserEntity()
                .setUsername("felin")
                .setEmail("felin@gmail.com")
                .setUserRoles(Set.of(adminRole, userRole))
                .setPassword("testtest");
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("invalid_username"),
                "User invalid_username does not exist!");
    }

    @Test
    void testUserFound() {
        Mockito.when(mockedUserRepository.findByUsername(testUser.getUsername()))
                .thenReturn(java.util.Optional.ofNullable(testUser));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUser.getUsername());

        Assertions.assertEquals(userDetails.getUsername(), testUser.getUsername());

        String actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(", "));

        Assertions.assertEquals("ROLE_ADMIN, ROLE_USER", actualRoles);
    }
}
