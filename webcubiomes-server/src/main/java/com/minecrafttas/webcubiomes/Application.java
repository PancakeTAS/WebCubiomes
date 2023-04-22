package com.minecrafttas.webcubiomes;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot class for vaadin application
 * @author Pancake
 */
@SpringBootApplication
@PWA(name = "Web Cubiomes", shortName = "Web Cubiomes", offline = false, themeColor = "#233348")
@Theme(value = "webcubiomes", variant = Lumo.DARK)
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
