package org.experience.share;

import org.experience.share.dao.BySqlserver;
import org.experience.share.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShareApplicationTests {

    @Test
    void contextLoads() {

        UserService userService = new UserService();

        userService.getUserInfo();

    }


}
