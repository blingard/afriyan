package org.ligot.afriyan.echo;

// Rôles impliqués dans le processus de validation et de gestion des alertes [3, 14, 54-62]
public enum ValidationRole {
    ADMIN, // Comités Communautaires (CC) [2, 58]
    COMMUNITY_COMMITTEE, // Comités Communautaires (CC) [2, 58]
    CCPR_COMMITTEE,      // Commissions Communales de Préparation et de Réponses aux crises (CCPR) [2, 54]
    LOCAL_AUTHORITY,     // Autorités Locales (Sous-préfet, Maire) [3, 114]
    MAIRE,     // Autorités Locales (Sous-préfet, Maire) [3, 114]
    TECHNICAL_SERVICES,  // Services Techniques (DAADER, DAEPIA, SANTE, etc.) [56]
    NGO_PARTNER          // ONG et autres partenaires [61]
}
