package Server;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;



public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);
    private static final int PORT = 8080;
    private final static String LOCATION = "src/main/webapp";

    //started Tomcat
    public static void main(String[] args) throws Exception {
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName()+":created Tomcat");

        //set main Settings
        tomcat.setBaseDir(LOCATION);
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", appBase);
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName()+":location was established");

        //set resource settings
        File additionWebInfClasses = new File("target/classes");

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName()+":location of Resources was established");

        tomcat.start();
        tomcat.getServer().await();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName()+":server was started");
    }



}
