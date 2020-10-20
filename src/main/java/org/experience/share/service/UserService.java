package org.experience.share.service;


import org.experience.share.dao.ByOracle;
import org.experience.share.dao.BySqlserver;

public class UserService {

    public void getUserInfo(){

        // BySqlserver u = new BySqlserver();
        // 修改为
        ByOracle u = new ByOracle();

        u.findUserInfoInDB();

    }

}
