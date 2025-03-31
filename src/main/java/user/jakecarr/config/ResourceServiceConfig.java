package user.jakecarr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.jakecarr.service.ResourceService;

/**
 * Spring configuration class for the ResourceService.
 * This class defines the ResourceService bean.
 */
@Configuration
public class ResourceServiceConfig {

    /**
     * Creates the ResourceService bean.
     * 
     * @return A new instance of ResourceService
     */
    @Bean
    public ResourceService resourceService() {
        return new ResourceService();
    }
}
