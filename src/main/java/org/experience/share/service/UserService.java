package org.experience.share.service;

import org.experience.share.dao.ByOracle;
import org.experience.share.dao.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    @Qualifier(value = "bySqlserver")
    Database database;

    public void getUserInfo(){

        database.findUserInfoInDB();

    }

}
