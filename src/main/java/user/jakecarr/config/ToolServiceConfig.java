package user.jakecarr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.jakecarr.service.ToolService;

/**
 * Spring configuration class for the ToolService.
 * This class defines the ToolService bean.
 */
@Configuration
public class ToolServiceConfig {

    /**
     * Creates the ToolService bean.
     * 
     * @return A new instance of ToolService
     */
    @Bean
    public ToolService toolService() {
        return new ToolService();
    }
}
