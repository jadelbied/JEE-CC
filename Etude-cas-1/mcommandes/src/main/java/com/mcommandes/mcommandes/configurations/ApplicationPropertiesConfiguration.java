package com.mcommandes.mcommandes.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("mes-config-ms")
@RefreshScope
public class ApplicationPropertiesConfiguration {
    private int lastCommandes;

    public int getLastCommandes() {
        return lastCommandes;
    }

    public void setLastCommandes(int lastCommandes) {
        this.lastCommandes = lastCommandes;
    }


}
