package user.jakecarr.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Spring configuration class for the MCP Server Factory application.
 * This class imports all the individual configuration classes.
 */
@Configuration
@ComponentScan(basePackages = "user.jakecarr")
@Import({
    ToolServiceConfig.class,
    ResourceServiceConfig.class,
    PromptServiceConfig.class,
    McpServerServiceConfig.class,
    TransportProviderConfig.class
})
public class AppConfig {
    // No beans defined here - each bean is defined in its own configuration class
}
