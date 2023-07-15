package ru.geekbrains.march.market.auth.servicies;
//bob_johnson@gmail.com
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.geekbrains.march.market.auth.entities.User;
import ru.geekbrains.march.market.auth.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
@RequiredArgsConstructor
class UserServiceTest {
    private final UserRepository userRepository;
    @Test
    void findByUsername() {
        User testUser = new User();

        testUser.setUsername("bob");
        testUser.setId(1L);


        User userBob = userRepository.findById(1L).get();

        Assertions.assertNotNull(testUser);
        Assertions.assertNotNull(userBob);
        Assertions.assertNotNull(userBob.getId());
        Assertions.assertNotNull(testUser.getId());
        Assertions.assertEquals(testUser.getUsername(),userBob.getUsername());
        Assertions.assertEquals(testUser.getId(),userBob.getId());



    }

    @Test
    void loadUserByUsername() {
        User testUser = new User();

        testUser.setUsername("bob");
        testUser.setId(1L);


        User userBob = userRepository.findByUsername("bob").get();


        Assertions.assertNotNull(testUser);
        Assertions.assertNotNull(userBob);
        Assertions.assertNotNull(userBob.getUsername());
        Assertions.assertNotNull(testUser.getUsername());
        Assertions.assertEquals(testUser.getUsername(),userBob.getUsername());
        Assertions.assertEquals(testUser.getId(),userBob.getId());
    }
}