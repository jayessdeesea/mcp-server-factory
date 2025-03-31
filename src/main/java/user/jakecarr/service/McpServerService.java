package user.jakecarr.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpSyncServer;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for managing the MCP server.
 * This class is responsible for starting and stopping the MCP server,
 * and coordinating between the different components (tools, resources, prompts).
 */
public class McpServerService {
    private static final Logger LOGGER = Logger.getLogger(McpServerService.class.getName());
    
    private final ToolService toolService;
    private final ResourceService resourceService;
    private final PromptService promptService;
    private final StdioServerTransportProvider transportProvider;
    
    private McpSyncServer mcpServer;
    
    /**
     * Creates a new McpServerService with the specified dependencies.
     * 
     * @param toolService The service for managing MCP tools
     * @param resourceService The service for managing MCP resources
     * @param promptService The service for managing MCP prompts
     * @param transportProvider The transport provider for the MCP server
     */
    public McpServerService(
            ToolService toolService,
            ResourceService resourceService,
            PromptService promptService,
            StdioServerTransportProvider transportProvider) {
        this.toolService = toolService;
        this.resourceService = resourceService;
        this.promptService = promptService;
        this.transportProvider = transportProvider;
    }
    
    /**
     * Initializes the MCP server when the bean is created.
     * This method is called automatically by Spring after the bean is constructed.
     */
    public void init() {
        LOGGER.info("Initializing MCP server bean...");
        startServer();
    }
    
    /**
     * Cleans up resources when the bean is destroyed.
     * This method is called automatically by Spring before the bean is destroyed.
     */
    public void destroy() {
        LOGGER.info("Destroying MCP server bean...");
        stopServer();
    }
    
    /**
     * Starts the MCP server.
     * This method initializes the MCP server, registers all tools, resources, and prompts,
     * and starts listening for client connections.
     */
    public void startServer() {
        LOGGER.info("Starting MCP server...");
        
        try {
            // Initialize the MCP server
            initializeServer();
            
            // Register tools, resources, and prompts
            registerComponents();
            
            // Start listening for client connections
            startListening();
            
            LOGGER.info("MCP server started successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to start MCP server", e);
            throw new RuntimeException("Failed to start MCP server", e);
        }
    }
    
    /**
     * Stops the MCP server.
     * This method gracefully shuts down the MCP server and releases any resources.
     */
    public void stopServer() {
        LOGGER.info("Stopping MCP server...");
        
        try {
            // Stop listening for client connections
            stopListening();
            
            // Clean up resources
            cleanup();
            
            LOGGER.info("MCP server stopped successfully");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to stop MCP server", e);
            throw new RuntimeException("Failed to stop MCP server", e);
        }
    }
    
    /**
     * Initializes the MCP server.
     */
    private void initializeServer() {
        LOGGER.info("Initializing MCP server...");
        
        // Create server capabilities using the builder pattern
        McpSchema.ServerCapabilities serverCapabilities = McpSchema.ServerCapabilities.builder()
                .tools(true) // Enable tools capability with list changed notifications
                .resources(true, true) // Enable resources capability with subscribe and list changed notifications
                .prompts(true) // Enable prompts capability with list changed notifications
                .build();
        
        // Create the MCP server
        this.mcpServer = McpServer.sync(this.transportProvider)
                .serverInfo("mcp-server-factory", "1.0.0")
                .capabilities(serverCapabilities)
                .build();
    }
    
    /**
     * Registers all tools, resources, and prompts with the MCP server.
     */
    private void registerComponents() {
        LOGGER.info("Registering MCP components...");
        
        // Register tools
        toolService.registerTools(this.mcpServer);
        
        // Register resources
        resourceService.registerResources(this.mcpServer);
        
        // Register prompts
        promptService.registerPrompts(this.mcpServer);
    }
    
    /**
     * Starts listening for client connections.
     */
    private void startListening() {
        LOGGER.info("Starting to listen for client connections...");
        
        // The server automatically starts listening when built
    }
    
    /**
     * Stops listening for client connections.
     */
    private void stopListening() {
        LOGGER.info("Stopping listening for client connections...");
        
        if (this.mcpServer != null) {
            this.mcpServer.closeGracefully();
        }
    }
    
    /**
     * Cleans up resources used by the MCP server.
     */
    private void cleanup() {
        LOGGER.info("Cleaning up resources...");
        
        // Resources are automatically cleaned up when the server is closed
        this.mcpServer = null;
    }
}
