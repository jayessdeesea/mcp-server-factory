package user.jakecarr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.McpSyncServerExchange;
import io.modelcontextprotocol.spec.McpSchema;
import user.jakecarr.model.McpTool;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service class for managing MCP tools.
 * This class is responsible for creating, registering, and managing MCP tools.
 */
public class ToolService {
    private static final Logger LOGGER = Logger.getLogger(ToolService.class.getName());
    
    // Map of tool names to their implementations
    private final Map<String, Object> tools = new HashMap<>();
    
    /**
     * Creates a new ToolService.
     */
    public ToolService() {
        // Initialize tools
        initializeTools();
    }
    
    /**
     * Registers all tools with the MCP server.
     * 
     * @param mcpServer The MCP server to register tools with
     */
    public void registerTools(McpSyncServer mcpServer) {
        LOGGER.info("Registering MCP tools...");
        
        // Register each tool with the MCP server
        for (Map.Entry<String, Object> entry : tools.entrySet()) {
            if (entry.getValue() instanceof McpTool mcpTool) {
                LOGGER.info("Registering tool: " + mcpTool.getName());
                
                // Create a McpSchema.Tool from the McpTool
                McpSchema.JsonSchema inputSchema = new ObjectMapper().convertValue(
                        mcpTool.getInputSchema(), 
                        McpSchema.JsonSchema.class);
                
                McpSchema.Tool tool = new McpSchema.Tool(
                        mcpTool.getName(),
                        mcpTool.getDescription(),
                        inputSchema);
                
                // Create a tool specification
                McpServerFeatures.SyncToolSpecification toolSpec = 
                    new McpServerFeatures.SyncToolSpecification(
                        tool, 
                        (exchange, args) -> {
                            McpTool.ToolResult result = mcpTool.execute(args);
                            
                            // Convert the result to a CallToolResult
                            boolean isError = !result.isSuccess();
                            String message = result.getMessage();
                            Object data = result.getData();
                            
                            // Create a text content with the result
                            String responseText;
                            if (data != null) {
                                try {
                                    // Convert data to JSON string
                                    ObjectMapper objectMapper = new ObjectMapper();
                                    String jsonData = objectMapper.writeValueAsString(data);
                                    
                                    // Combine message and data
                                    responseText = message + "\n\n" + jsonData;
                                } catch (Exception e) {
                                    LOGGER.warning("Failed to convert data to JSON: " + e.getMessage());
                                    responseText = message + "\n\nError: Failed to convert data to JSON";
                                }
                            } else {
                                responseText = message;
                            }
                            
                            McpSchema.TextContent textContent = new McpSchema.TextContent(responseText);
                            
                            // Return the result
                            return new McpSchema.CallToolResult(
                                    java.util.List.of(textContent),
                                    isError);
                        }
                    );
                
                // Register the tool with the MCP server
                mcpServer.addTool(toolSpec);
            }
        }
        
        LOGGER.info("Registered " + tools.size() + " MCP tools");
    }
    
    /**
     * Gets a tool by name.
     * 
     * @param name The name of the tool
     * @return The tool, or null if not found
     */
    public Object getTool(String name) {
        return tools.get(name);
    }
    
    /**
     * Gets all registered tools.
     * 
     * @return A map of tool names to their implementations
     */
    public Map<String, Object> getAllTools() {
        return new HashMap<>(tools);
    }
    
    /**
     * Initializes all tools.
     */
    private void initializeTools() {
        LOGGER.info("Initializing MCP tools...");
        
        // Add the ExplainConceptTool
        user.jakecarr.model.impl.ExplainConceptTool explainConceptTool = new user.jakecarr.model.impl.ExplainConceptTool();
        tools.put(explainConceptTool.getName(), explainConceptTool);
        
        // Add the PingTool
        user.jakecarr.model.impl.PingTool pingTool = new user.jakecarr.model.impl.PingTool();
        tools.put(pingTool.getName(), pingTool);
        
        // Add the task planner tools
        initializeTaskPlannerTools();
        
        LOGGER.info("Initialized " + tools.size() + " MCP tools");
    }
    
    /**
     * Initializes the task planner tools.
     */
    private void initializeTaskPlannerTools() {
        LOGGER.info("Initializing task planner tools...");
        
        // Add the CodeCleanupPlannerTool
        user.jakecarr.model.impl.CodeCleanupPlannerTool codeCleanupPlannerTool = new user.jakecarr.model.impl.CodeCleanupPlannerTool();
        tools.put(codeCleanupPlannerTool.getName(), codeCleanupPlannerTool);
        
        // Add the FeatureImplementationPlannerTool
        user.jakecarr.model.impl.FeatureImplementationPlannerTool featureImplementationPlannerTool = new user.jakecarr.model.impl.FeatureImplementationPlannerTool();
        tools.put(featureImplementationPlannerTool.getName(), featureImplementationPlannerTool);
        
        // Add the GeneralTaskPlannerTool
        user.jakecarr.model.impl.GeneralTaskPlannerTool generalTaskPlannerTool = new user.jakecarr.model.impl.GeneralTaskPlannerTool();
        tools.put(generalTaskPlannerTool.getName(), generalTaskPlannerTool);
        
        // Add the CleanupTaskTool
        user.jakecarr.model.impl.CleanupTaskTool cleanupTaskTool = new user.jakecarr.model.impl.CleanupTaskTool();
        tools.put(cleanupTaskTool.getName(), cleanupTaskTool);
        
        // Add the LocalMcpDeploymentPlannerTool
        user.jakecarr.model.impl.LocalMcpDeploymentPlannerTool localMcpDeploymentPlannerTool = new user.jakecarr.model.impl.LocalMcpDeploymentPlannerTool();
        tools.put(localMcpDeploymentPlannerTool.getName(), localMcpDeploymentPlannerTool);
        
        LOGGER.info("Initialized task planner tools");
    }
}
