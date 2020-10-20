package org.experience.share.dao;

import org.springframework.stereotype.Repository;

//@Repository
public class ByOracle implements Database{

    public void findUserInfoInDB() {

        System.out.println("通过Oracle获取User信息");

    }
}
