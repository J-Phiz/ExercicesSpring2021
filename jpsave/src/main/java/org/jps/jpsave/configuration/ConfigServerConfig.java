package org.jps.jpsave.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigServerConfig {

    @Value("${text.perso}")
    public String msg;

    @Value("${text.local.perso}")
    public String msgloc;

    @Bean
    public void maFonction() {
        System.out.println("Je suis lancé et le message est : " + msg + " et le message local est : " + msgloc);
    }


}
