package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.init.PermissionEnum;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * DTO for synchronizing multiple permissions
 */

public class SyncPermissionsRequest {

    @NotEmpty(message = "Permissions list cannot be empty")
    private Set<PermissionEnum> permissions;

    public SyncPermissionsRequest() {
    }

    public SyncPermissionsRequest(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    public Set<PermissionEnum> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEnum> permissions) {
        this.permissions = permissions;
    }
}
