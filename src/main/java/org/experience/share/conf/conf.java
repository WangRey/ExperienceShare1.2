package org.experience.share.conf;

import org.experience.share.dao.ByOracle;
import org.experience.share.dao.BySqlserver;
import org.experience.share.dao.Database;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class conf {

    @Bean
    public Database bySqlserver(){

        return new BySqlserver();

    }
//    @Bean
    public Database byOracle(){

        return new ByOracle();

    }

}
