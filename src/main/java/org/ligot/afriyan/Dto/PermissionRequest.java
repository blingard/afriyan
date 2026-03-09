package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ligot.afriyan.init.PermissionEnum;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * DTO for adding/removing a single permission
 */

public class PermissionRequest {

    @NotNull(message = "Permission is required")
    private Set<PermissionEnum> permission;

    public PermissionRequest() {
    }

    public PermissionRequest(@NotNull(message = "Permission is required") Set<PermissionEnum> permission) {
        this.permission = permission;
    }

    public Set<PermissionEnum> getPermission() {
        return permission;
    }

    public void setPermission(Set<PermissionEnum> permission) {
        this.permission = permission;
    }
}
