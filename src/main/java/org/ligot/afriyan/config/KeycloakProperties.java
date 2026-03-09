package org.ligot.afriyan.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

/**
 * Configuration properties for Keycloak integration
 */
@Validated
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

    /**
     * Keycloak server URL (e.g., http://localhost:8180)
     */
    @NotBlank(message = "Keycloak auth server URL is required")
    private String url;

    /**
     * Realm name
     */
    @NotBlank(message = "Keycloak realm is required")
    private String realm;

    /**
     * Client ID for backend application
     */
    @NotBlank(message = "Keycloak client ID is required")
    private String clientid;

    /**
     * Client secret (for confidential clients)
     */
    private String clientsecret;

    /**
     * Admin username for Keycloak Admin Client
     */
    private String username;

    /**
     * Admin password for Keycloak Admin Client
     */
    private String password;

    /**
     * Enable/disable Keycloak integration
     * Default: true
     */
    private boolean enabled = true;

    /**
     * Use legacy JWT validation (for backward compatibility)
     * Default: false
     */
    private boolean uselegacyjwt = false;

    public KeycloakProperties() {
    }

    public KeycloakProperties(String url, String realm, String clientid, String clientsecret, String username, String password, boolean enabled, boolean uselegacyjwt) {
        this.url = url;
        this.realm = realm;
        this.clientid = clientid;
        this.clientsecret = clientsecret;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.uselegacyjwt = uselegacyjwt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getClientsecret() {
        return clientsecret;
    }

    public void setClientsecret(String clientsecret) {
        this.clientsecret = clientsecret;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUselegacyjwt() {
        return uselegacyjwt;
    }

    public void setUselegacyjwt(boolean uselegacyjwt) {
        this.uselegacyjwt = uselegacyjwt;
    }
}
