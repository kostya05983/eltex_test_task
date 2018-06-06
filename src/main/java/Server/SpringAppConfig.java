package Server;

import Vaadin.VaadinServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"Vaadin"})
@EnableMongoRepositories(value = "Base")
public class SpringAppConfig implements WebApplicationInitializer {
    private final static Logger log = LogManager.getLogger(SpringAppConfig.class);

    @Override
    public void onStartup(ServletContext servletContext) {
        // Create the 'root' Spring application context
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : create the root Spring application context");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringAppConfig.class);

        // Manage the lifecycle of the root application context
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Manage the lifecycle of the root application context");
        servletContext.addListener(new ContextLoaderListener(rootContext));

        //add Vaadin Servlet
        ServletRegistration.Dynamic vaadin = servletContext
                .addServlet("vaadin", new VaadinServlet());
        vaadin.setLoadOnStartup(1);
        vaadin.addMapping("/*");
    }
}
