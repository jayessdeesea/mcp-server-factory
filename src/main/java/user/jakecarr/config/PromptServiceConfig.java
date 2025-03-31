package user.jakecarr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.jakecarr.service.PromptService;

/**
 * Spring configuration class for the PromptService.
 * This class defines the PromptService bean.
 */
@Configuration
public class PromptServiceConfig {

    /**
     * Creates the PromptService bean.
     * 
     * @return A new instance of PromptService
     */
    @Bean
    public PromptService promptService() {
        return new PromptService();
    }
}
