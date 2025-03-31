package user.jakecarr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import user.jakecarr.service.McpServerService;
import user.jakecarr.service.PromptService;
import user.jakecarr.service.ResourceService;
import user.jakecarr.service.ToolService;

/**
 * Spring configuration class for the McpServerService.
 * This class defines the McpServerService bean.
 */
@Configuration
public class McpServerServiceConfig {

    /**
     * Creates the McpServerService bean with its dependencies.
     * 
     * @param toolService The ToolService bean
     * @param resourceService The ResourceService bean
     * @param promptService The PromptService bean
     * @param transportProvider The StdioServerTransportProvider bean
     * @return A new instance of McpServerService
     */
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public McpServerService mcpServerService(
            ToolService toolService,
            ResourceService resourceService,
            PromptService promptService,
            StdioServerTransportProvider transportProvider) {
        return new McpServerService(toolService, resourceService, promptService, transportProvider);
    }
}
