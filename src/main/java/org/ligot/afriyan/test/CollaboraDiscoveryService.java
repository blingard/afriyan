package org.ligot.afriyan.test;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

// CollaboraDiscoveryService.java — parse le XML de discovery
@Service
public class CollaboraDiscoveryService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String getEditorUrl(String collaboraUrl, String extension, String action) {
        String xml = restTemplate.getForObject(
                collaboraUrl + "/hosting/discovery", String.class);
        System.err.println(collaboraUrl);

        System.err.println(xml);

        // Parser le XML pour trouver l'URL selon l'extension
        // Ex: .docx → "edit" → http://localhost:9980/browser/xxxx/cool.html
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            Document doc = factory.newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
            NodeList apps = doc.getElementsByTagName("app");
            for (int i = 0; i < apps.getLength(); i++) {
                Element app = (Element) apps.item(i);
                NodeList actions = app.getElementsByTagName("action");
                for (int j = 0; j < actions.getLength(); j++) {
                    Element act = (Element) actions.item(j);
                    if (act.getAttribute("ext").equals(extension)
                            && act.getAttribute("name").equals(action)) {
                        return act.getAttribute("urlsrc")
                                .replaceAll("<[^>]+>", ""); // retirer les placeholders WOPI
                    }
                }
            }
            throw new RuntimeException(
                    "Extension '" + extension + "' ou action '" + action + "' non supportée par Collabora.");
        } catch (Exception e) {
            if (e instanceof RuntimeException)
                throw (RuntimeException) e;
            throw new RuntimeException("Erreur lors de la configuration de l'éditeur Collabora : " + e.getMessage(), e);
        }
    }
}
