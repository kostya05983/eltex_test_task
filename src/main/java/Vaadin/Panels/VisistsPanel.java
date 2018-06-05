package Vaadin.Panels;

import Base.Connect;
import com.mongodb.MongoClient;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

public class VisistsPanel extends Panel {

    private VerticalLayout verticalLayout;
    private Label amountLabel;

    public VisistsPanel(String caption) {
        super(caption);
        verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
    }

    public void init() {
        amountLabel = new Label();
        verticalLayout.addComponent(amountLabel);

        Thread thread = new Thread(new DaemonRefresh());
        thread.setDaemon(true);
        thread.start();
    }

    private class DaemonRefresh implements Runnable{
        @Override
        public void run() {
            while(true) {
                Connect connect = new Connect();
                amountLabel.setValue(String.valueOf(connect.getVisists()));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
