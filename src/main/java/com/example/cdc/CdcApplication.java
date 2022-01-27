package com.example.cdc;

import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@SpringBootApplication
public class CdcApplication {

  public static void main(String[] args) {
    SpringApplication.run(CdcApplication.class, args);
  }

  @Bean
  @ConfigurationProperties(prefix = "config.debezium")
  public Properties debeziumProperties() {
    return new Properties();
  }

  @Bean
  public CommandLineRunner debeziumRunner(Properties debeziumProperties) {
    return args -> {
      // Create the engine with this configuration ...
      try (DebeziumEngine<ChangeEvent<String, String>> engine =
          DebeziumEngine.create(Json.class)
              .using(debeziumProperties)
              .notifying(
                  record -> {
                    log.info("Record: {}", record);
                  })
              .build()) {
        // Run the engine asynchronously ...
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);

        try {
          executor.shutdown();
          while (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
            log.info("Waiting another 5 seconds for the embedded engine to shut down");
          }
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    };
  }
}
