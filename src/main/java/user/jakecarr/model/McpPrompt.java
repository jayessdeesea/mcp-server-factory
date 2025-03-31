package user.jakecarr.model;

import java.util.Map;

/**
 * Interface for MCP prompts.
 * Prompts are structured templates that generate responses based on parameters.
 */
public interface McpPrompt extends McpComponent {
    
    /**
     * Handles the prompt with the specified parameters.
     * 
     * @param parameters The parameters for the prompt
     * @return The prompt response
     */
    PromptResponse handle(Map<String, Object> parameters);
    
    /**
     * Gets the parameter schema for the prompt.
     * The schema defines the parameters that the prompt accepts.
     * 
     * @return The parameter schema
     */
    Map<String, Object> getParameterSchema();
    
    /**
     * Gets the type of the component.
     * For prompts, this is always ComponentType.PROMPT.
     * 
     * @return ComponentType.PROMPT
     */
    @Override
    default ComponentType getType() {
        return ComponentType.PROMPT;
    }
    
    /**
     * Class representing the response to a prompt.
     */
    class PromptResponse {
        private final String content;
        private final Map<String, Object> metadata;
        
        /**
         * Creates a new PromptResponse with the specified content.
         * 
         * @param content The response content
         */
        public PromptResponse(String content) {
            this(content, null);
        }
        
        /**
         * Creates a new PromptResponse with the specified content and metadata.
         * 
         * @param content The response content
         * @param metadata Additional metadata for the response
         */
        public PromptResponse(String content, Map<String, Object> metadata) {
            this.content = content;
            this.metadata = metadata;
        }
        
        /**
         * Gets the response content.
         * 
         * @return The content
         */
        public String getContent() {
            return content;
        }
        
        /**
         * Gets the response metadata.
         * 
         * @return The metadata, or null if none
         */
        public Map<String, Object> getMetadata() {
            return metadata;
        }
    }
}
