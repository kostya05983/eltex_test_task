package Vaadin.Panels;

import Base.Connect;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VisistsPanel extends Panel {

    private final String VISITORS = "visitors";
    private final Logger logger = LogManager.getLogger(VisistsPanel.class);
    private VerticalLayout verticalLayout;
    private Label amountLabel;

    public VisistsPanel(String caption) {
        super(caption);
        logger.debug(new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : visitsPanel Constructor");
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
    }

    public void init() {
        this.setPrimaryStyleName(VISITORS);
        amountLabel = new Label("0");
        verticalLayout.addComponent(amountLabel);
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : initialized amountLabel with 0");

        //started Daemon to update counting
        DaemonRefresh daemonRefresh = new DaemonRefresh();
        daemonRefresh.setDaemon(true);
        daemonRefresh.start();
        logger.debug(new Object() {
        }.getClass().getEnclosingMethod().getName() + " : daemon started");
    }

    private class DaemonRefresh extends Thread {
        private final Logger logger = LogManager.getLogger(DaemonRefresh.class);

        @Override
        public void run() {
            while (isAlive()) {
                //update counting from db
                Connect connect = new Connect();
                amountLabel.setValue(String.valueOf(connect.getVisists()));
                logger.debug(new Object() {
                }.getClass().getEnclosingMethod().getName() + " : amount of visitors was gotten");
                try {
                    logger.debug(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : sleep 5s");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
                }
            }
        }
    }


}
