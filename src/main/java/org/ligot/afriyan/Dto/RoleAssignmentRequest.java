package org.ligot.afriyan.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * DTO for role assignment
 */

public class RoleAssignmentRequest {

    @NotNull(message = "Role ID is required")
    private Long roleId;

    public RoleAssignmentRequest() {
    }

    public RoleAssignmentRequest(@NotNull(message = "Role ID is required") Long roleId) {
        this.roleId = roleId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
