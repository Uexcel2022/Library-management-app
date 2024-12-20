package com.uexcel.library.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorizationDeniedEvents {

    @EventListener
    public void onFailed(org.springframework.security.authorization.event.AuthorizationDeniedEvent event){
        log.error("Authorization fail for user {} due to {}",
                event.getAuthentication().get().getName(), event.getAuthorizationDecision().toString());
    }
}
