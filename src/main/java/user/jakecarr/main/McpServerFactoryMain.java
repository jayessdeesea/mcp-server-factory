package user.jakecarr.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.jakecarr.config.AppConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main entry point for the MCP Server Factory application.
 * This class initializes the Spring context.
 * The MCP server is started automatically by the init method in McpServerService,
 * which is configured as the initMethod in the @Bean definition.
 */
public class McpServerFactoryMain {
    private static final Logger LOGGER = Logger.getLogger(McpServerFactoryMain.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Starting MCP Server Factory...");
        
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            LOGGER.info("MCP Server Factory started successfully. Press Ctrl+C to stop.");
            
            // Keep the application running until terminated
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to start MCP Server Factory", e);
            System.exit(1);
        }
    }
}
