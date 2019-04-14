package com;

import ML.ImageProcess;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static org.apache.logging.log4j.web.WebLoggerContextUtils.getServletContext;

@WebListener
public class Config implements ServletContextListener {
    // This class loads the ImageProcess class on server start
    // This allows it to be accessible without having to reload the neural net each time
    // VERY IMPORTANT: Classes will not change unless server is fully restarted

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("ip", new ImageProcess(getServletContext().getRealPath("/") + "/resources/model.zip"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
