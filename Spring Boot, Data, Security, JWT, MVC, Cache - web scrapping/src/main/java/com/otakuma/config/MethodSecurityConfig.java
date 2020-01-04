package com.otakuma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.core.GrantedAuthorityDefaults;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

	
    /** enleve la verification du préfixe ROLE_ à @Secured */
    @Override
    protected AccessDecisionManager accessDecisionManager() {
        AffirmativeBased manager = (AffirmativeBased) super.accessDecisionManager();
        setAuthorityRolePrefix(manager, "");
        return manager;
    }

    private void setAuthorityRolePrefix(AffirmativeBased manager, String rolePrefix) {
        manager.getDecisionVoters().stream()
            .filter(RoleVoter.class::isInstance)
            .map(RoleVoter.class::cast)
            .forEach(it -> it.setRolePrefix(rolePrefix));
    }

    /** enleve la verification du préfixe ROLE_ à @PreAuthorize */
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}