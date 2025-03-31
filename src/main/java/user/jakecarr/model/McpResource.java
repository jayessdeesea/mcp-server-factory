package user.jakecarr.model;

/**
 * Interface for MCP resources.
 * Resources are data sources that provide information to clients.
 */
public interface McpResource extends McpComponent {
    
    /**
     * Gets the URI of the resource.
     * 
     * @return The resource URI
     */
    String getUri();
    
    /**
     * Gets the MIME type of the resource.
     * 
     * @return The MIME type
     */
    String getMimeType();
    
    /**
     * Gets the content of the resource.
     * 
     * @return The resource content
     */
    String getContent();
    
    /**
     * Gets the type of the component.
     * For resources, this is always ComponentType.RESOURCE.
     * 
     * @return ComponentType.RESOURCE
     */
    @Override
    default ComponentType getType() {
        return ComponentType.RESOURCE;
    }
    
    /**
     * Class representing a resource provider.
     * Resource providers generate resources based on URIs.
     */
    interface Provider {
        
        /**
         * Gets the URI pattern for this provider.
         * The pattern defines which URIs this provider can handle.
         * 
         * @return The URI pattern
         */
        String getUriPattern();
        
        /**
         * Gets a resource for the specified URI.
         * 
         * @param uri The URI of the resource
         * @return The resource, or null if not found
         */
        McpResource getResource(String uri);
        
        /**
         * Gets the description of this provider.
         * 
         * @return The provider description
         */
        String getDescription();
    }
}
