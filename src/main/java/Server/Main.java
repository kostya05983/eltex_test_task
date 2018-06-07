package Server;


import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private final static Logger log = LogManager.getLogger(Main.class);
    private static final int PORT = 8080;
    private final static String LOCATION = "src/main/webapp";
    private final static String APP_BASE = ".";

    //started Tomcat
    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":created Tomcat");

        //set main Settings
        tomcat.setBaseDir(LOCATION);
        tomcat.setPort(PORT);
        tomcat.getHost().setAppBase(APP_BASE);
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", APP_BASE);
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":location was established");

        tomcat.start();
        tomcat.getServer().await();
        log.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + ":server was started");
    }


}
