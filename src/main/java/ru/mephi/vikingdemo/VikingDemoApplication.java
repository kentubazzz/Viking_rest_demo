package ru.mephi.vikingdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.mephi.vikingdemo.controller.VikingListener;
import ru.mephi.vikingdemo.gui.VikingDesktopFrame;
import ru.mephi.vikingdemo.service.VikingService;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class VikingDemoApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VikingDemoApplication.class);
        app.setHeadless(false);

        ConfigurableApplicationContext context = app.run(args);
        VikingService vikingService = context.getBean(VikingService.class);
        VikingListener vikingListener = context.getBean(VikingListener.class);

        SwingUtilities.invokeLater(() -> {
            VikingDesktopFrame frame = new VikingDesktopFrame(vikingService);
            vikingListener.setGui(frame);

            // Загружаем существующих викингов через слушателя
            vikingService.addListener(vikings -> {});
            frame.refreshTable(vikingService.findAll());

            frame.setVisible(true);
        });
    }
}