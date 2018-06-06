package Server;

import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;



public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);
    private static final int PORT = 8080;
    private final static String LOCATION="src/main/webapp";

    public static void main(String[] args) throws Exception {
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(LOCATION);
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        StandardContext ctx = (StandardContext) tomcat.addWebapp("/",appBase);

        File additionWebInfClasses = new File("target/classes");

        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }

    private static String createTempDir() {
        try {
            log.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : started to create tempDir for Tomcat");
            File tempDir = File.createTempFile("tomcat","."+PORT);

            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            log.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create directory");
            return tempDir.getAbsolutePath();
        } catch(IOException ex) {
            log.error(new Object(){}.getClass().getEnclosingMethod().getName()+" : "+ex.getMessage());
            throw new RuntimeException(
                    "Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"),
                    ex
            );
        }
    }
}
