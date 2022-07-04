package com.reviewduck.service;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.reviewduck.domain.User;

@SpringBootTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저를 저장한다.")
    void save() {
        // when
        Long savedId = userService.save("user1");

        // then
        assertThat(savedId).isNotNull();
    }

    @Test
    @DisplayName("유저를 조회한다.")
    void findById() {
        // given
        Long savedId = userService.save("user1");

        // when
        User actual = userService.findById(savedId);

        // then
        assertThat(actual.getNickname()).isEqualTo("user1");
    }
}
