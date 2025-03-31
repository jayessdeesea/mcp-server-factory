package user.jakecarr.model.impl;

import user.jakecarr.model.McpTool;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple tool that responds to ping requests.
 * This tool can be used to check if the MCP server is alive and responsive.
 */
public class PingTool implements McpTool {
    private static final Logger LOGGER = Logger.getLogger(PingTool.class.getName());
    
    private static final String NAME = "ping";
    private static final String DESCRIPTION = "Responds to ping requests to check server availability";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    @Override
    public Map<String, Object> getInputSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("message", createStringProperty("Optional message to include in the response"));
        
        schema.put("properties", properties);
        // No required properties
        
        return schema;
    }
    
    @Override
    public Map<String, Object> getOutputSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        properties.put("success", createProperty("boolean", "Whether the tool execution was successful"));
        properties.put("message", createStringProperty("A message describing the result"));
        properties.put("data", createDataProperty());
        
        schema.put("properties", properties);
        
        return schema;
    }
    
    /**
     * Creates a data property for the output schema.
     * 
     * @return A map representing the data property
     */
    private Map<String, Object> createDataProperty() {
        Map<String, Object> dataProperty = new HashMap<>();
        dataProperty.put("type", "object");
        dataProperty.put("description", "The data returned by the tool");
        
        Map<String, Object> dataProperties = new HashMap<>();
        dataProperties.put("status", createStringProperty("The status of the ping operation"));
        dataProperties.put("timestamp", createStringProperty("The timestamp of the ping operation"));
        
        Map<String, Object> pingMessageProperty = createStringProperty("The optional message included in the ping request");
        pingMessageProperty.put("nullable", true);
        dataProperties.put("message", pingMessageProperty);
        
        dataProperty.put("properties", dataProperties);
        
        return dataProperty;
    }
    
    /**
     * Helper method to create a string property for a schema.
     * 
     * @param description The description of the property
     * @return A map representing a string property
     */
    private Map<String, Object> createStringProperty(String description) {
        return createProperty("string", description);
    }
    
    /**
     * Helper method to create a property of any type for a schema.
     * 
     * @param type The type of the property
     * @param description The description of the property
     * @return A map representing a property
     */
    private Map<String, Object> createProperty(String type, String description) {
        Map<String, Object> property = new HashMap<>();
        property.put("type", type);
        property.put("description", description);
        return property;
    }
    
    @Override
    public ToolResult execute(Map<String, Object> parameters) {
        LOGGER.info("Executing ping tool with parameters: " + parameters);
        
        // Get the optional message parameter
        String message = (String) parameters.getOrDefault("message", "");
        
        // Get the current timestamp
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        
        // Create the response
        StringBuilder response = new StringBuilder();
        response.append("PONG! Server is alive and responsive.\n");
        response.append("Timestamp: ").append(timestamp).append("\n");
        
        if (!message.isEmpty()) {
            response.append("Message: ").append(message).append("\n");
        }
        
        // Create a map with the response data
        Map<String, Object> data = new HashMap<>();
        data.put("status", "ok");
        data.put("timestamp", timestamp);
        data.put("message", message.isEmpty() ? null : message);
        
        return new ToolResult(true, response.toString(), data);
    }
}
