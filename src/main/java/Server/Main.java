package Server;

import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.File;
import java.io.IOException;



public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);
    private static final int PORT = 8080;

    public static void main(String[] args) throws Exception {
        String appBase = ".";
        Tomcat tomcat = new Tomcat();
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : create Tomcat");
        tomcat.setBaseDir(createTempDir());
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(appBase);
        tomcat.addWebapp("",appBase);
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : set settings Tomcat");

        tomcat.start();
        tomcat.getServer().await();
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName()+" : tomcat start");
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
