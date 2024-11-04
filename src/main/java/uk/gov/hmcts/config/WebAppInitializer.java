package uk.gov.hmcts.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.core.env.Environment;
import uk.gov.hmcts.pdm.publicdisplay.initialization.InitializationService;

@SuppressWarnings("PMD.LawOfDemeter")
public class WebAppInitializer implements ServletContextInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);
    
    public static final String SERVLET_NAME = "InitServlet";
    private static final String DB_USER_NAME = "pdda.db_user_name";
    
    @Autowired
    private Environment env;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        String dbUsername = env.getProperty(DB_USER_NAME);
        LOG.info("Database username: {}", dbUsername);
        InitializationService.getInstance().setEntityManagerFactory(entityManagerFactory);
        InitializationService.getInstance().setEnvironment(env);
        // httpOnly: if true then browser script won’t be able to access the cookie
        servletContext.getSessionCookieConfig().setHttpOnly(true); 
        // secure: if true then the cookie will be sent only over HTTPS connection
        servletContext.getSessionCookieConfig().setSecure(true); 
    }

}
