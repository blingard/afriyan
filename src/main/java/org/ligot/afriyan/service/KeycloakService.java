package org.ligot.afriyan.service;

import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;

import java.util.List;
import java.util.Map;

/**
 * Service for managing users in Keycloak
 */
public interface KeycloakService {

    /**
     * Authenticate a user and obtain OAuth2 tokens
     *
     * @param username user's username or email
     * @param password user's password
     * @return Map containing access_token, refresh_token, expires_in, etc.
     */
    Map<String, Object> login(String username, String password);

    /**
     * Refresh an access token using a refresh token
     *
     * @param refreshToken the refresh token
     * @return Map containing new access_token, refresh_token, expires_in, etc.
     */
    Map<String, Object> refreshToken(String refreshToken);

    /**
     * Logout a user by invalidating their refresh token
     *
     * @param refreshToken the refresh token to invalidate
     */
    void logout(String refreshToken) throws Exception;

    /**
     * Create a new user in Keycloak
     *
     * @param email     user email
     * @param password  user password
     * @param firstName first name
     * @param lastName  last name
     * @param enabled   whether user is enabled
     * @return user ID in Keycloak
     */
    String createUser(String username, String email, String password, String firstName, String lastName, boolean enabled);

    /**
     * Update an existing user
     * 
     * @param userId    Keycloak user ID
     * @param email     new email (optional)
     * @param firstName new first name (optional)
     * @param lastName  new last name (optional)
     */
    void updateUser(String userId, String email, String firstName, String lastName) throws Exception;

    /**
     * Delete a user from Keycloak
     *
     * @param userId Keycloak user ID
     */
    void deleteUser(String userId) throws Exception;

    /**
     * Get user by ID
     *
     * @param userId Keycloak user ID
     * @return UserRepresentation
     */
    UserRepresentation getUserById(String userId) throws Exception;

    /**
     * Get user by email
     *
     * @param email user email
     * @return UserRepresentation or null if not found
     */
    UserRepresentation getUserByEmail(String email) throws Exception;

    /**
     * Assign a role to a user
     *
     * @param userId   Keycloak user ID
     * @param roleName role name
     */
    void assignRole(String userId, String roleName) throws Exception;

    /**
     * Remove a role from a user
     *
     * @param userId   Keycloak user ID
     * @param roleName role name
     */
    void removeRole(String userId, String roleName) throws Exception;

    /**
     * Reset user password
     *
     * @param userId      Keycloak user ID
     * @param newPassword new password
     * @param temporary   whether password is temporary (user must change on next
     *                    login)
     */
    void resetPassword(String userId, String newPassword, boolean temporary);

    /**
     * Enable or disable a user
     *
     * @param userId  Keycloak user ID
     * @param enabled true to enable, false to disable
     */
    void setUserEnabled(String userId, boolean enabled) throws Exception;

    /**
     * Get all users in the realm
     *
     * @return list of users
     */
    List<UserRepresentation> getAllUsers() throws Exception;

    /**
     * Search users by username, email, or name
     *
     * @param searchTerm search string
     * @return list of matching users
     */
    List<UserRepresentation> searchUsers(String searchTerm) throws Exception;
}
