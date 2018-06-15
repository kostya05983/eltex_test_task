package Server;

import Vaadin.VaadinServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"Vaadin"})
@EnableMongoRepositories(value = "Base")
public class SpringAppConfig implements WebApplicationInitializer {
    private final static Logger log = LogManager.getRootLogger();

    @Override
    public void onStartup(ServletContext servletContext) {
        log.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : application context создан");
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(SpringAppConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));

        ServletRegistration.Dynamic vaadin = servletContext
                .addServlet("vaadin", new VaadinServlet());
        log.debug(MarkerManager.getMarker("SERVER"), new Object() {
        }.getClass().getEnclosingMethod().getName() + " : Сервлет Vaadin добавлен");
        vaadin.setLoadOnStartup(1);
        vaadin.addMapping("/*");
    }
}
