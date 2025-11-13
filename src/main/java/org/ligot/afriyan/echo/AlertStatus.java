package org.ligot.afriyan.echo;

// Statuts possibles d'une alerte dans le workflow de validation
public enum AlertStatus {
    REPORTED,              // Signalée par le CC [60]
    PENDING_CCPR_REVIEW,   // En attente de révision par le CCPR [67]
    PENDING_AUTHORITY_APPROVAL, // Escaladée aux autorités [67, 114]
    ALERT_ACTIVE,          // Alerte active, déclenchée [70, 114]
    REJECTED,              // Rejetée par le CCPR [67]
    DISMISSED,             // Classée par les autorités [70]
    RESOLVED               // Crise résolue (post-activation)
}
