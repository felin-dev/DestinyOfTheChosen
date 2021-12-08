package game.destinyofthechosen.web;

import game.destinyofthechosen.model.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UserEntity testUser;

    @BeforeEach
    void init() {
//        RoleEntity adminRole = new RoleEntity().setUserRole(UserRoleEnum.ADMIN);
//        RoleEntity userRole = new RoleEntity().setUserRole(UserRoleEnum.USER);
//
//        userRoleRepository.saveAll(List.of(adminRole, userRole));
//
//        testUser = new UserEntity()
//                .setUsername("felin")
//                .setEmail("felin@gmail.com")
//                .setUserRoles(Set.of(adminRole, userRole))
//                .setPassword("testtest");
//
//        userRepository.save(testUser);
    }

    @Test
    void createEnemyGetAllZonesAndItems() {

    }
}