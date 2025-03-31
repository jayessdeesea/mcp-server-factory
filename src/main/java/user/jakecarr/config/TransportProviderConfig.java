package user.jakecarr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for the StdioServerTransportProvider.
 * This class defines the StdioServerTransportProvider bean.
 */
@Configuration
public class TransportProviderConfig {

    /**
     * Creates the StdioServerTransportProvider bean.
     * 
     * @return A new instance of StdioServerTransportProvider
     */
    @Bean
    public StdioServerTransportProvider stdioServerTransportProvider() {
        return new StdioServerTransportProvider(new ObjectMapper());
    }
}
