package org.experience.share.service;
import org.experience.share.dao.ByOracle;
import org.experience.share.dao.Database;

public class UserService {

    Database database;

    public void getUserInfo(){

        database.findUserInfoInDB();

    }

    public void setDatabase(Database database) {

        this.database = database;

    }

}
