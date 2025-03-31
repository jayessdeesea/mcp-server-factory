package user.jakecarr.model;

import java.util.Map;

/**
 * Interface for MCP tools.
 * Tools are executable functions that perform actions and return results.
 */
public interface McpTool extends McpComponent {
    
    /**
     * Executes the tool with the specified parameters.
     * 
     * @param parameters The parameters for the tool execution
     * @return The result of the tool execution
     */
    ToolResult execute(Map<String, Object> parameters);
    
    /**
     * Gets the input schema for the tool.
     * The schema defines the parameters that the tool accepts.
     * 
     * @return The input schema
     */
    Map<String, Object> getInputSchema();
    
    /**
     * Gets the output schema for the tool.
     * The schema defines the structure of the tool's result.
     * 
     * @return The output schema
     */
    Map<String, Object> getOutputSchema();
    
    /**
     * Gets the type of the component.
     * For tools, this is always ComponentType.TOOL.
     * 
     * @return ComponentType.TOOL
     */
    @Override
    default ComponentType getType() {
        return ComponentType.TOOL;
    }
    
    /**
     * Class representing the result of a tool execution.
     */
    class ToolResult {
        private final boolean success;
        private final String message;
        private final Object data;
        
        /**
         * Creates a new ToolResult.
         * 
         * @param success Whether the tool execution was successful
         * @param message A message describing the result
         * @param data The data returned by the tool
         */
        public ToolResult(boolean success, String message, Object data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        /**
         * Gets whether the tool execution was successful.
         * 
         * @return true if successful, false otherwise
         */
        public boolean isSuccess() {
            return success;
        }
        
        /**
         * Gets the message describing the result.
         * 
         * @return The message
         */
        public String getMessage() {
            return message;
        }
        
        /**
         * Gets the data returned by the tool.
         * 
         * @return The data
         */
        public Object getData() {
            return data;
        }
    }
}
