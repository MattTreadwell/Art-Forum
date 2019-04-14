package com;

import ML.ImageProcess;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Config implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent even) {
        even.getServletContext().setAttribute("ip", new ImageProcess());
    }
}
