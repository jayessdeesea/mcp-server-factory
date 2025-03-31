package user.jakecarr.model;

/**
 * Base interface for all MCP components (tools, resources, prompts).
 */
public interface McpComponent {
    
    /**
     * Gets the name of the component.
     * 
     * @return The component name
     */
    String getName();
    
    /**
     * Gets the description of the component.
     * 
     * @return The component description
     */
    String getDescription();
    
    /**
     * Gets the type of the component.
     * 
     * @return The component type (tool, resource, or prompt)
     */
    ComponentType getType();
    
    /**
     * Enum representing the different types of MCP components.
     */
    enum ComponentType {
        TOOL,
        RESOURCE,
        PROMPT
    }
}
