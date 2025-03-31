package user.jakecarr.service;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.spec.McpSchema;
import user.jakecarr.model.McpPrompt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service class for managing MCP prompts.
 * This class is responsible for creating, registering, and managing MCP prompts.
 */
public class PromptService {
    private static final Logger LOGGER = Logger.getLogger(PromptService.class.getName());
    
    // Map of prompt names to their handlers
    private final Map<String, Object> prompts = new HashMap<>();
    
    /**
     * Creates a new PromptService.
     */
    public PromptService() {
        // Initialize prompts
        initializePrompts();
    }
    
    /**
     * Registers all prompts with the MCP server.
     * 
     * @param mcpServer The MCP server to register prompts with
     */
    public void registerPrompts(McpSyncServer mcpServer) {
        LOGGER.info("Registering MCP prompts...");
        
        // Register each prompt with the MCP server
        for (Map.Entry<String, Object> entry : prompts.entrySet()) {
            if (entry.getValue() instanceof McpPrompt mcpPrompt) {
                LOGGER.info("Registering prompt: " + mcpPrompt.getName());
                
                // Create a list of prompt arguments
                List<McpSchema.PromptArgument> arguments = new ArrayList<>();
                
                // Get the parameter schema
                Map<String, Object> parameterSchema = mcpPrompt.getParameterSchema();
                
                // Extract properties and required fields
                @SuppressWarnings("unchecked")
                Map<String, Object> properties = (Map<String, Object>) parameterSchema.getOrDefault("properties", new HashMap<>());
                @SuppressWarnings("unchecked")
                List<String> required = (List<String>) parameterSchema.getOrDefault("required", new ArrayList<>());
                
                // Create prompt arguments from the properties
                for (Map.Entry<String, Object> property : properties.entrySet()) {
                    String name = property.getKey();
                    @SuppressWarnings("unchecked")
                    Map<String, Object> propertyDetails = (Map<String, Object>) property.getValue();
                    String description = (String) propertyDetails.getOrDefault("description", "");
                    boolean isRequired = required.contains(name);
                    
                    // Create a prompt argument
                    McpSchema.PromptArgument argument = new McpSchema.PromptArgument(name, description, isRequired);
                    arguments.add(argument);
                }
                
                // Create a McpSchema.Prompt from the McpPrompt
                McpSchema.Prompt prompt = new McpSchema.Prompt(
                        mcpPrompt.getName(),
                        mcpPrompt.getDescription(),
                        arguments);
                
                // Create a prompt specification
                McpServerFeatures.SyncPromptSpecification promptSpec = 
                    new McpServerFeatures.SyncPromptSpecification(
                        prompt, 
                        (exchange, request) -> {
                            // Handle the prompt
                            McpPrompt.PromptResponse response = mcpPrompt.handle(request.arguments());
                            
                            // Create a prompt message
                            McpSchema.PromptMessage message = new McpSchema.PromptMessage(
                                    McpSchema.Role.ASSISTANT,
                                    new McpSchema.TextContent(response.getContent()));
                            
                            // Return the result
                            return new McpSchema.GetPromptResult(
                                    mcpPrompt.getDescription(),
                                    List.of(message));
                        }
                    );
                
                // Register the prompt with the MCP server
                mcpServer.addPrompt(promptSpec);
            }
        }
        
        LOGGER.info("Registered " + prompts.size() + " MCP prompts");
    }
    
    /**
     * Gets a prompt handler by name.
     * 
     * @param name The name of the prompt
     * @return The prompt handler, or null if not found
     */
    public Object getPrompt(String name) {
        return prompts.get(name);
    }
    
    /**
     * Gets all registered prompts.
     * 
     * @return A map of prompt names to their handlers
     */
    public Map<String, Object> getAllPrompts() {
        return new HashMap<>(prompts);
    }
    
    /**
     * Initializes all prompts.
     */
    private void initializePrompts() {
        LOGGER.info("Initializing MCP prompts...");
        
        // Add the ToolImplementationGuidePrompt
        user.jakecarr.model.impl.ToolImplementationGuidePrompt toolImplementationGuidePrompt = 
            new user.jakecarr.model.impl.ToolImplementationGuidePrompt();
        prompts.put(toolImplementationGuidePrompt.getName(), toolImplementationGuidePrompt);
        
        // Add the BootstrapMcpServerPrompt
        user.jakecarr.model.impl.BootstrapMcpServerPrompt bootstrapMcpServerPrompt = 
            new user.jakecarr.model.impl.BootstrapMcpServerPrompt();
        prompts.put(bootstrapMcpServerPrompt.getName(), bootstrapMcpServerPrompt);
        
        LOGGER.info("Initialized " + prompts.size() + " MCP prompts");
    }
}
