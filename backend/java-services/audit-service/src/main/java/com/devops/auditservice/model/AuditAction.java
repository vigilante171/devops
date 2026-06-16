package com.devops.auditservice.model;

public enum AuditAction {
    LOGIN,
    LOGOUT,
    REGISTER,
    CREATE,
    UPDATE,
    DELETE,
    RUN_PIPELINE,
    COMPLETE_PIPELINE,
    DEPLOY,
    ROLLBACK,
    ACKNOWLEDGE_ALERT,
    RESOLVE_ALERT,
    READ_NOTIFICATION,
    CHANGE_ROLE,
    SYSTEM_EVENT
}
