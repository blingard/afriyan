package org.ligot.afriyan.init;

/**
 * Enum defining all application permissions (functionalities)
 * Permissions are grouped by module for better organization
 */
public enum PermissionEnum {
    CREATE_THEME(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_PARTENAIRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_PARTENAIRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PARTENAIRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_THEME(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_THEME(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_THEME(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_ABOUT(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_ABOUT(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_ABOUT(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_TEMOIGNAGE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.USER, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_TEMOIGNAGE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.USER, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_TEMOIGNAGE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.USER, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_TEMOIGNAGE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.USER, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_ARTICLE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_ARTICLE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_ARTICLE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_ARTICLE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),


    CREATE_CERTIFICATE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_CERTIFICATE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_CERTIFICATE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_USRAJ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_USRAJ_ADMIN(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_USRAJ(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_USRAJ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_USRAJ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_DENONCIATION(new RolesName[]{RolesName.USER, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_DENONCIATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_DENONCIATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_DENONCIATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    ADD_ROLE_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    REMOVE_ROLE_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    ADD_USER_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    REMOVE_USER_GROUPE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    SEND_ONE_SMS(new RolesName[]{RolesName.MAIRE, RolesName.LOCAL_AUTHORITY, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    SEND_GROUP_SMS(new RolesName[]{RolesName.MAIRE, RolesName.LOCAL_AUTHORITY, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_MISSION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_MISSION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_MISSION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_MISSION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_WORK(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_WORK(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_WORK(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_PARAMETER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_PARAMETER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PARAMETER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_PRODUCT(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_PRODUCT(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PRODUCT(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_PRODUCT(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_PUBLICATION(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_PUBLICATION(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PUBLICATION(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_PUBLICATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_RAPPORT(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE,RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_RAPPORT(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_RAPPORT(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE,RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_RAPPORT(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),


    PERMISSION_ROLE_READ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    PERMISSION_ROLE_MANAGE_PERMISSIONS(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_SERVICE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_SERVICE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_SERVICE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_SERVICE(new RolesName[]{RolesName.GESTIONNAIRECENTRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    GET_SLIDER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_SLIDER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_SLIDER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_SLIDER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),

    CREATE_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_ADMIN_USER(new RolesName[]{RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_PREFET_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_MAIRE_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CCRP_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CC_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CP_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_USER(new RolesName[]{RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_OWNE_USER(RolesName.values()),
    SEE_DASHBOARD(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    SEE_QRCODE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_VALEUR(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_VALEUR(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_VALEUR(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_VALEUR(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_ALERT(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    VALIDATE_ALERT(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_ALERT(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_ALERT_TYPE(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_ALERT_TYPE(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_ALERT_TYPE_ADMIN(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_ALERT_TYPE(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_WEATHER(new RolesName[]{RolesName.LOCAL_AUTHORITY, RolesName.MAIRE, RolesName.COMMUNITY_COMMITTEE, RolesName.CCPR_COMMITTEE, RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_CHAPTER_ADMIN(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PARAGRAPH_ADMIN(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_CHAPTER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_CHAPTER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CHAPTER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_FORMATION_ADMIN(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_FORMATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_FORMATION_USER(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_FORMATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    ADD_QUIZZ_FORMATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_FORMATION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_PARAGRAPH(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_PARAGRAPH(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_PARAGRAPH(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CHAPITRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_CHAPITRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_CHAPITRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_CHAPITRE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_MODULE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_MODULE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_MODULE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    DELETE_MODULE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_QUIZ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_QUIZ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_QUIZ(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_QUESTION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_QUESTION(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_CATEGORIE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_CATEGORIE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_CATEGORIE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_MENU(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    GET_SONDAGE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    CREATE_SONDAGE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT}),
    UPDATE_SONDAGE(new RolesName[]{RolesName.ADMIN, RolesName.SUPERADMIN, RolesName.ROOT});

    private final RolesName[] roles;

    PermissionEnum(RolesName[] roles) {
        this.roles = roles;
    }

    public RolesName[] getRoles() {
        return roles;
    }

    /**
     * Get module name from permission
     * 
     * @return module name (first part before underscore)
     */
    public String getModule() {
        return this.name().split("_")[0];
    }

    /**
     * Get action from permission
     * 
     * @return action (part after module)
     */
    public String getAction() {
        String[] parts = this.name().split("_", 2);
        return parts.length > 1 ? parts[1] : "";
    }

    /**
     * Get human-readable name
     * 
     * @return formatted permission name
     */
    public String getDisplayName() {
        return this.name().replace("_", " ").toLowerCase();
    }
}
