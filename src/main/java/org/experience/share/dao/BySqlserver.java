package org.experience.share.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

//@Component
//@Repository
public class BySqlserver implements Database{

    public void findUserInfoInDB() {

        System.out.println("通过Sqlserver获取User信息");

    }
}
