package Vaadin.Panels;

import Vaadin.VaadinUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

public class VisitsPanel extends Panel {

    private final String VISITORS = "visitors";
    private final Logger logger = LogManager.getRootLogger();
    private VerticalLayout verticalLayout;
    private Label amountLabel;
    private VaadinUI context;

    public VisitsPanel(String caption, VaadinUI context) {
        super(caption);
        logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
        }.getClass().getEnclosingConstructor().getName() + " : конструктор visitsPanel с параметрами caption="+caption+" VaadinUI="+context);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        this.context = context;
    }

    public void init() {
        this.setPrimaryStyleName(VISITORS);
        amountLabel = new Label("0");
        verticalLayout.addComponent(amountLabel);

        //запускаем демона
        DaemonRefresh daemonRefresh = new DaemonRefresh();
        daemonRefresh.setDaemon(true);
        daemonRefresh.start();

    }

    private class DaemonRefresh extends Thread {
        private final Logger logger = LogManager.getLogger(DaemonRefresh.class);

        @Override
        public void run() {
            logger.debug(MarkerManager.getMarker("SERVER"),new Object() {
            }.getClass().getEnclosingMethod().getName() + " : демон запущен");

            while (isAlive()) {
                //обновляем счетчик из контекста
                amountLabel.setValue(String.valueOf(context.getVisits()));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(MarkerManager.getMarker("SERVER"),new Object() {
                    }.getClass().getEnclosingMethod().getName() + " : " + e.getMessage());
                }
            }
        }
    }


}
