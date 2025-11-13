package org.ligot.afriyan.utils;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailReader {
    public static List<Message> readEmails() throws Exception{
        String host = "mx-dc03.ewodi.net";
        String username = "youthfp@youthfp.cm";
        String password = "@Lingot75";

        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");

        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();
            System.err.println(messages.length);

            for (Message message : messages) {
                System.err.println("Sujet: " + message.getSubject());
                System.err.println("Expéditeur: " + message.getFrom()[0]);
                System.err.println("Expéditeur Type: " + message.getFrom()[0].getType());
                System.err.println("Expéditeur To String: " + message.getFrom()[0].toString());
                System.err.println("getContentType: " + message.getContentType());
                System.err.println("message.getInputStream(): " + message.getInputStream());



                /*Object content = message.getContent();
                if (content instanceof String) {
                    System.err.println("Contenu (texte brut): " + content);
                } else if (content instanceof Multipart) {
                    Multipart multipart = (Multipart) content;
                    for (int i = 0; i < multipart.getCount(); i++) {
                        BodyPart part = multipart.getBodyPart(i);
                        String disposition = part.getDisposition();
                        System.err.println("disposition : "+disposition);

                        // Si c’est une pièce jointe
                        if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT))) {
                            System.err.println("Pièce jointe 11: " + part.getFileName());
                        } else {
                            // Sinon on lit le contenu texte/HTML
                            System.err.println("Contenus (partie " + i + "): " + part.getContent());
                        }
                    }
                } else {
                    System.err.println("Contenues de type inconnu: " + content.getClass());
                }*/

                System.err.println("-----------------------------");
            }
            List<Message> message = List.of();

            inbox.close(false);
            store.close();
            return message;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
