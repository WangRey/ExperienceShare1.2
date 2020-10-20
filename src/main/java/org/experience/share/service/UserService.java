package org.experience.share.service;


import org.experience.share.dao.BySqlserver;

public class UserService {

    public void getUserInfo(){

//        通过new对象的方式
        BySqlserver u = new BySqlserver();

        u.findUserInfoInDB();

    }

}
