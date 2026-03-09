package org.ligot.afriyan.implement;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.ligot.afriyan.config.KeycloakProperties;

import org.ligot.afriyan.service.KeycloakService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementation of Keycloak user management service
 */
@Service
@ConditionalOnProperty(prefix = "keycloak", name = "enabled", havingValue = "true", matchIfMissing = false)
public class KeycloakServiceImpl implements KeycloakService {
    private static final Logger log = LoggerFactory.getLogger(KeycloakServiceImpl.class);

    private final Keycloak keycloakAdminClient;
    private final KeycloakProperties keycloakProperties;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KeycloakServiceImpl(Keycloak keycloakAdminClient, KeycloakProperties keycloakProperties) {
        this.keycloakAdminClient = keycloakAdminClient;
        this.keycloakProperties = keycloakProperties;
    }

    private RealmResource getRealmResource() {
        return keycloakAdminClient.realm(keycloakProperties.getRealm());
    }

    private UsersResource getUsersResource() {
        return getRealmResource().users();
    }

    @Override
    public Map<String, Object> login(String username, String password) {
        log.info("Authenticating user");

        String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                keycloakProperties.getUrl(),
                keycloakProperties.getRealm());

        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", keycloakProperties.getClientid());
        if (keycloakProperties.getClientsecret() != null && !keycloakProperties.getClientsecret().isBlank()) {
            formData.put("client_secret", keycloakProperties.getClientsecret());
        }
        formData.put("username", username);
        formData.put("password", password);
        formData.put("grant_type", "password");

        return performTokenRequest(tokenUrl, formData);
    }

    @Override
    public Map<String, Object> refreshToken(String refreshToken) {
        try {
            log.info("Refreshing access token");

            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token",
                    keycloakProperties.getUrl(),
                    keycloakProperties.getRealm());

            Map<String, String> formData = new HashMap<>();
            formData.put("client_id", keycloakProperties.getClientid());
            if (keycloakProperties.getClientsecret() != null && !keycloakProperties.getClientsecret().isBlank()) {
                formData.put("client_secret", keycloakProperties.getClientsecret());
            }
            formData.put("refresh_token", refreshToken);
            formData.put("grant_type", "refresh_token");

            return performTokenRequest(tokenUrl, formData);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void logout(String refreshToken) throws Exception {
        log.info("Logging out user");

        String logoutUrl = String.format("%s/realms/%s/protocol/openid-connect/logout",
                keycloakProperties.getUrl(),
                keycloakProperties.getRealm());

        Map<String, String> formData = new HashMap<>();
        formData.put("client_id", keycloakProperties.getClientid());
        if (keycloakProperties.getClientsecret() != null && !keycloakProperties.getClientsecret().isBlank()) {
            formData.put("client_secret", keycloakProperties.getClientsecret());
        }
        formData.put("refresh_token", refreshToken);

        String formBody = buildFormBody(formData);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(logoutUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) {
            log.error("Logout failed: {} - {}", response.statusCode(), response.body());
            throw new RuntimeException("Logout failed: " + response.body());
        }

        log.info("User logged out successfully");
    }

    /**
     * Perform a token request to Keycloak
     */
    private Map<String, Object> performTokenRequest(String tokenUrl, Map<String, String> formData) {
        try {
            String formBody = buildFormBody(formData);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(tokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(formBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                @SuppressWarnings("unchecked")
                Map<String, Object> tokenResponse = objectMapper.readValue(response.body(), Map.class);
                log.info("Token obtained successfully");
                return tokenResponse;
            } else {
                log.error("Token request failed: {} - {}", response.statusCode(), response.body());
                throw new RuntimeException("Authentication failed: " + response.body());
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error during token request: " + ex.getMessage(), ex);

        }

    }

    /**
     * Build URL-encoded form body from map
     */
    private String buildFormBody(Map<String, String> formData) {
        return formData.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                        URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .reduce((p1, p2) -> p1 + "&" + p2)
                .orElse("");
    }

    @Override
    public String createUser(String username, String email, String password, String firstName, String lastName, boolean enabled) {
        log.info("Creating user in Keycloak: {}", email);
        try {
            // Step 1: Obtain admin token from master realm using admin-cli
            String adminTokenUrl = keycloakProperties.getUrl() + "/realms/master/protocol/openid-connect/token";
            Map<String, String> adminTokenData = new HashMap<>();
            adminTokenData.put("client_id", "admin-cli");
            adminTokenData.put("username", keycloakProperties.getUsername());
            adminTokenData.put("password", keycloakProperties.getPassword());
            adminTokenData.put("grant_type", "password");

            HttpRequest tokenRequest = HttpRequest.newBuilder()
                    .uri(URI.create(adminTokenUrl))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(buildFormBody(adminTokenData)))
                    .build();

            HttpResponse<String> tokenResponse = httpClient.send(tokenRequest, HttpResponse.BodyHandlers.ofString());
            if (tokenResponse.statusCode() != 200) {
                log.error("Failed to obtain admin token: {} - {}", tokenResponse.statusCode(), tokenResponse.body());
                throw new RuntimeException("Cannot obtain admin token: " + tokenResponse.body());
            }

            @SuppressWarnings("unchecked")
            Map<String, Object> tokenData = objectMapper.readValue(tokenResponse.body(), Map.class);
            String adminToken = (String) tokenData.get("access_token");
            log.info("Admin token obtained successfully");

            // Step 2: Build user JSON
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("username", username);
            userMap.put("email", email);
            userMap.put("firstName", firstName);
            userMap.put("lastName", lastName);
            userMap.put("enabled", enabled);
            userMap.put("emailVerified", false);

            if (password != null && !password.isBlank()) {
                Map<String, Object> credential = new HashMap<>();
                credential.put("type", "password");
                credential.put("value", password);
                credential.put("temporary", false);
                userMap.put("credentials", List.of(credential));
            }

            String userJson = objectMapper.writeValueAsString(userMap);

            // Step 3: Create user in youthfp realm via admin REST API
            String createUserUrl = keycloakProperties.getUrl() + "/admin/realms/" + keycloakProperties.getRealm()
                    + "/users";
            HttpRequest createRequest = HttpRequest.newBuilder()
                    .uri(URI.create(createUserUrl))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + adminToken)
                    .POST(HttpRequest.BodyPublishers.ofString(userJson))
                    .build();

            HttpResponse<String> createResponse = httpClient.send(createRequest, HttpResponse.BodyHandlers.ofString());

            if (createResponse.statusCode() == 201) {
                String location = createResponse.headers().firstValue("Location").orElse("");
                String userId = location.isEmpty() ? null : location.substring(location.lastIndexOf('/') + 1);
                log.info("User created successfully in Keycloak with ID: {}", userId);
                return userId;
            } else if (createResponse.statusCode() == 409) {
                log.warn("User already exists in Keycloak: {}", email);
                return findExistingUserId(email, adminToken);
            } else {
                log.error("Failed to create user: {} - {}", createResponse.statusCode(), createResponse.body());
                throw new RuntimeException("Failed to create user in Keycloak: " + createResponse.body());
            }

        } catch (RuntimeException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creating user: {}", ex.getMessage(), ex);
            throw new RuntimeException("Error creating user: " + ex.getMessage(), ex);
        }
    }

    /**
     * Find an existing Keycloak user ID by email (used when user already exists)
     */
    private String findExistingUserId(String email, String adminToken) {
        try {
            String searchUrl = keycloakProperties.getUrl() + "/admin/realms/" + keycloakProperties.getRealm()
                    + "/users?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) + "&exact=true";
            HttpRequest req = HttpRequest.newBuilder()
                    .uri(URI.create(searchUrl))
                    .header("Authorization", "Bearer " + adminToken)
                    .GET()
                    .build();
            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() == 200) {
                @SuppressWarnings("unchecked")
                List<Map<String, Object>> users = objectMapper.readValue(resp.body(), List.class);
                if (!users.isEmpty()) {
                    return (String) users.get(0).get("id");
                }
            }
        } catch (Exception e) {
            log.warn("Could not retrieve existing user ID for: {}", email);
        }
        return null;
    }

    @Override
    public void updateUser(String userId, String email, String firstName, String lastName) throws Exception {
        log.info("Updating user in Keycloak: {}", userId);

        UserResource userResource = getUsersResource().get(userId);
        UserRepresentation user = userResource.toRepresentation();

        if (email != null && !email.isBlank()) {
            user.setEmail(email);
            user.setUsername(email);
        }
        if (firstName != null && !firstName.isBlank()) {
            user.setFirstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            user.setLastName(lastName);
        }

        userResource.update(user);
        log.info("User updated successfully: {}", userId);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        log.info("Deleting user from Keycloak: {}", userId);
        getUsersResource().delete(userId);
        log.info("User deleted successfully: {}", userId);
    }

    @Override
    public UserRepresentation getUserById(String userId) throws Exception {
        return getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public UserRepresentation getUserByEmail(String email) throws Exception {
        List<UserRepresentation> users = getUsersResource().search(email, true);

        if (users.isEmpty()) {
            log.warn("User not found with email: {}", email);
            return null;
        }

        if (users.size() > 1) {
            log.warn("Multiple users found with email: {}", email);
        }

        return users.get(0);
    }

    @Override
    public void assignRole(String userId, String roleName) throws Exception {
        log.info("Assigning role '{}' to user: {}", roleName, userId);

        UserResource userResource = getUsersResource().get(userId);

        // Get realm role
        RoleRepresentation role = getRealmResource()
                .roles()
                .get(roleName)
                .toRepresentation();

        // Assign role to user
        userResource.roles().realmLevel().add(Collections.singletonList(role));

        log.info("Role '{}' assigned successfully to user: {}", roleName, userId);
    }

    @Override
    public void removeRole(String userId, String roleName) throws Exception {
        log.info("Removing role '{}' from user: {}", roleName, userId);

        UserResource userResource = getUsersResource().get(userId);

        // Get realm role
        RoleRepresentation role = getRealmResource()
                .roles()
                .get(roleName)
                .toRepresentation();

        // Remove role from user
        userResource.roles().realmLevel().remove(Collections.singletonList(role));

        log.info("Role '{}' removed successfully from user: {}", roleName, userId);
    }

    @Override
    public void resetPassword(String userId, String newPassword, boolean temporary) {
        log.info("Resetting password for user: {}", userId);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(newPassword);
        credential.setTemporary(temporary);

        getUsersResource().get(userId).resetPassword(credential);
        log.info("Password reset successfully for user: {}", userId);
    }

    @Override
    public void setUserEnabled(String userId, boolean enabled) throws Exception {
        log.info("Setting user enabled status to '{}' for user: {}", enabled, userId);

        UserResource userResource = getUsersResource().get(userId);
        UserRepresentation user = userResource.toRepresentation();
        user.setEnabled(enabled);
        userResource.update(user);

        log.info("User enabled status updated successfully: {}", userId);
    }

    @Override
    public List<UserRepresentation> getAllUsers() throws Exception {
        log.info("Fetching all users from Keycloak");
        return getUsersResource().list();
    }

    @Override
    public List<UserRepresentation> searchUsers(String searchTerm) throws Exception {
        log.info("Searching users in Keycloak with term: {}", searchTerm);
        return getUsersResource().search(searchTerm);
    }
}
