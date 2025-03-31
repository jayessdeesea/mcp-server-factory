package user.jakecarr.service;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema;
import user.jakecarr.model.McpResource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Service class for managing MCP resources.
 * This class is responsible for creating, registering, and managing MCP resources.
 */
public class ResourceService {
    private static final Logger LOGGER = Logger.getLogger(ResourceService.class.getName());
    
    // Map of resource URIs to their providers
    private final Map<String, Object> resources = new HashMap<>();
    
    /**
     * Creates a new ResourceService.
     */
    public ResourceService() {
        // Initialize resources
        initializeResources();
    }
    
    /**
     * Registers all resources with the MCP server.
     * 
     * @param mcpServer The MCP server to register resources with
     */
    public void registerResources(McpSyncServer mcpServer) {
        LOGGER.info("Registering MCP resources...");
        
        // Register each resource provider with the MCP server
        for (Map.Entry<String, Object> entry : resources.entrySet()) {
            if (entry.getValue() instanceof McpResource.Provider resourceProvider) {
                LOGGER.info("Registering resource provider: " + entry.getKey());
                
                // Create a resource template for the provider
                McpSchema.ResourceTemplate resourceTemplate = new McpSchema.ResourceTemplate(
                        resourceProvider.getUriPattern(),
                        "Resource Template",
                        resourceProvider.getDescription(),
                        "text/markdown",
                        null);
                
                // Register the resource template with the MCP server
                // Note: There's no direct method to add resource templates in McpSyncServer
                // We'll register the resource template through the resource specification
                
                // Create a resource specification for the provider
                McpServerFeatures.SyncResourceSpecification resourceSpec = 
                    new McpServerFeatures.SyncResourceSpecification(
                        // Create a dummy resource for the specification
                        new McpSchema.Resource(
                            resourceProvider.getUriPattern(),
                            "Resource Provider",
                            resourceProvider.getDescription(),
                            "text/markdown",
                            null),
                        (exchange, request) -> {
                            // Get the resource from the provider
                            McpResource resource = resourceProvider.getResource(request.uri());
                            
                            if (resource == null) {
                                // Return an empty result if the resource is not found
                                return new McpSchema.ReadResourceResult(List.of());
                            }
                            
                            // Create a text resource content
                            McpSchema.TextResourceContents textContent = 
                                new McpSchema.TextResourceContents(
                                    resource.getUri(),
                                    resource.getMimeType(),
                                    resource.getContent());
                            
                            // Return the result
                            return new McpSchema.ReadResourceResult(List.of(textContent));
                        }
                    );
                
                // Register the resource with the MCP server
                mcpServer.addResource(resourceSpec);
            }
        }
        
        LOGGER.info("Registered " + resources.size() + " MCP resources");
    }
    
    /**
     * Gets a resource provider by URI.
     * 
     * @param uri The URI of the resource
     * @return The resource provider, or null if not found
     */
    public Object getResource(String uri) {
        return resources.get(uri);
    }
    
    /**
     * Gets all registered resources.
     * 
     * @return A map of resource URIs to their providers
     */
    public Map<String, Object> getAllResources() {
        return new HashMap<>(resources);
    }
    
    /**
     * Initializes all resources.
     */
    private void initializeResources() {
        LOGGER.info("Initializing MCP resources...");
        
        // Add the DocumentationResourceProvider
        user.jakecarr.model.impl.DocumentationResourceProvider documentationResourceProvider = 
            new user.jakecarr.model.impl.DocumentationResourceProvider();
        resources.put(documentationResourceProvider.getUriPattern(), documentationResourceProvider);
        
        LOGGER.info("Initialized " + resources.size() + " MCP resources");
    }
}
