package org.experience.share;

import org.experience.share.dao.BySqlserver;
import org.experience.share.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootTest
class ShareApplicationTests {

    @Test
    void contextLoads() {

        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        UserService userService = (UserService)ac.getBean("userService");
        userService.getUserInfo();

    }


}
