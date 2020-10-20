package org.experience.share.service;

import org.experience.share.dao.ByOracle;
import org.experience.share.dao.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    Database bySqlserver;

    public void getUserInfo(){

        bySqlserver.findUserInfoInDB();

    }

}
