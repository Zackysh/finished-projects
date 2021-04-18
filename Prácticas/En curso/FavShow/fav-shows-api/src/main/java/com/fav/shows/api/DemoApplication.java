package com.fav.shows.api;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {
  
  private static ConfigurableApplicationContext context;

  public static void main(String[] args) {
    context = SpringApplication.run(DemoApplication.class, args);
  }

  /**
   * This method allow this API track file changes.
   */
  public static void restart() {
    ApplicationArguments args = context.getBean(ApplicationArguments.class);

    Thread thread = new Thread(() -> {
      context.close();
      context = SpringApplication.run(DemoApplication.class, args.getSourceArgs());
    });

    thread.setDaemon(false);
    thread.start();
  }
}
