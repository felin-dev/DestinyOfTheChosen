package game.destinyofthechosen.web;

import game.destinyofthechosen.model.entity.UserEntity;
import game.destinyofthechosen.model.enumeration.HeroRoleEnum;
import game.destinyofthechosen.model.view.HeroInfoViewModel;
import game.destinyofthechosen.model.view.UserHeroSelectViewModel;
import game.destinyofthechosen.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@TestPropertySource(properties = "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL9Dialect")
public class HeroControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private PasswordEncoder passwordEncoder;
    @MockBean private EnemyService enemyService;
    @MockBean private ZoneService zoneService;
    @MockBean private ItemService itemService;
    @MockBean private ModelMapper modelMapper;
    @MockBean private HeroService heroService;
    @MockBean private UserService userService;

    @Test
    void getHeroesEmptyWithNoUserLoggedRedirect() throws Exception {
        mockMvc.perform(get("/heroes/select"))
                .andExpect(status().is(302));
    }

    @Test
    @WithMockUser("felin")
    void getHeroesEmptyWithNoHeroesCreated() throws Exception {
        Mockito.when(userService.getUserWithOwnedHeroes("felin"))
                .thenReturn(new UserHeroSelectViewModel());

        mockMvc.perform(get("/heroes/select"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser("felin")
    void getHeroesEmptyWithHeroesCreated() throws Exception {
        Mockito.when(userService.getUserWithOwnedHeroes("felin"))
                .thenReturn(new UserHeroSelectViewModel()
                        .setCurrentHero(new HeroInfoViewModel())
                        .setHeroes(List.of(new HeroInfoViewModel()
                                .setHeroId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"))
                                .setName("Felixi")
                                .setHeroRole(HeroRoleEnum.HUNTER))));


        mockMvc.perform(get("/heroes/select"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Felixi")));
    }
}