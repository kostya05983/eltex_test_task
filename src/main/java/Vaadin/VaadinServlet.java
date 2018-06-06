package Vaadin;

import com.vaadin.annotations.VaadinServletConfiguration;

import javax.servlet.annotation.WebServlet;

@WebServlet(value = {"/"}, asyncSupported = true)
@VaadinServletConfiguration(
        productionMode = true,
        ui = VaadinUI.class
)
public class VaadinServlet extends com.vaadin.server.VaadinServlet {
}
