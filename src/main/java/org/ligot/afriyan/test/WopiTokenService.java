package org.ligot.afriyan.test;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WopiTokenService {

    private final Map<String, WopiToken> tokenStore = new ConcurrentHashMap<>();

    public String generate(String fileId, String userUUID, String username, boolean canWrite) {
        String tokenId = UUID.randomUUID().toString();
        WopiToken token = new WopiToken(
                fileId,
                userUUID, // in a real app, this might be userId
                username, // and this might be the display name
                canWrite,
                LocalDateTime.now().plusMinutes(60) // Token valid for 1 hour
        );
        tokenStore.put(tokenId, token);
        return tokenId;
    }

    public WopiToken validate(String access_token) {
        WopiToken token = tokenStore.get(access_token);
        if (token == null || token.getExpiry().isBefore(LocalDateTime.now())) {
            if (token != null) {
                tokenStore.remove(access_token);
            }
            throw new RuntimeException("Token invalide ou expiré");
        }
        return token;
    }
}
