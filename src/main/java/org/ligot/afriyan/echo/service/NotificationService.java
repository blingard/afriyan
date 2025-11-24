package org.ligot.afriyan.echo.service;

import org.ligot.afriyan.echo.entities.Alerts;
import org.ligot.afriyan.implement.TwilioService;
import org.ligot.afriyan.repository.IUtilisateurRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NotificationService {
    private final TwilioService twilioService;
    private final IUtilisateurRepository utilisateurRepository;

    public NotificationService(TwilioService twilioService, IUtilisateurRepository utilisateurRepository) {
        this.twilioService = twilioService;
        this.utilisateurRepository = utilisateurRepository;
    }

    public void notifyCSPRForReview(Set<String> phoneNumbers, String message) {
        try {
            twilioService.sendSms(phoneNumbers, message);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void notifyAuthoritiesForApproval(Alerts alert) {
    }

    public void broadcastActiveAlert(Alerts alert) {

    }

    public void notifyReporterOfRejection(Alerts alert) {
    }
}
