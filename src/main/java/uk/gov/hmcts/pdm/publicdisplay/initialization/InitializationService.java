package uk.gov.hmcts.pdm.publicdisplay.initialization;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class InitializationService {

    private EntityManagerFactory entityManagerFactory;
    
    private Environment env;
    
    private Authentication authentication;
    
    /**
     * Singleton instance.
     */
    private static final InitializationService SELF = new InitializationService();
    
    /**
     * Don't instantiate me.
     */
    private InitializationService() {
        // private constructor
    }
    
    /**
     * Singleton accessor.
     * 
     * @return InitializationService
     */
    public static InitializationService getInstance() {
        return SELF;
    }
    

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    
    public Environment getEnvironment() {
        return env;
    }
    
    public void setEnvironment(Environment env) {
        this.env = env;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        this.authentication = authentication;
    }
}
