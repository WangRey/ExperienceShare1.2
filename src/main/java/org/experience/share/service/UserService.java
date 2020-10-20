package org.experience.share.service;

import org.experience.share.dao.ByOracle;
import org.experience.share.dao.Database;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    Database database;

    public void getUserInfo(){

        database.findUserInfoInDB();

    }

}
