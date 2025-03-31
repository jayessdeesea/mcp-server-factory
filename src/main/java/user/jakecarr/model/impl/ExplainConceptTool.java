package user.jakecarr.model.impl;

import user.jakecarr.model.McpTool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tool that explains MCP concepts.
 * This tool provides detailed explanations of various MCP concepts.
 */
public class ExplainConceptTool implements McpTool {
    private static final Logger LOGGER = Logger.getLogger(ExplainConceptTool.class.getName());
    
    private static final String NAME = "explain_concept";
    private static final String DESCRIPTION = "Provides a detailed explanation of an MCP concept";
    
    private final Map<String, String> explanations = new HashMap<>();
    
    /**
     * Creates a new ExplainConceptTool.
     */
    public ExplainConceptTool() {
        // Initialize explanations
        initializeExplanations();
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    @Override
    public ToolResult execute(Map<String, Object> parameters) {
        LOGGER.info("Executing explain_concept tool with parameters: " + parameters);
        
        // Get the concept parameter
        String concept = (String) parameters.get("concept");
        if (concept == null || concept.isEmpty()) {
            return new ToolResult(false, "Missing required parameter: concept", null);
        }
        
        // Convert to lowercase for case-insensitive lookup
        concept = concept.toLowerCase();
        
        // Get the explanation for the concept
        String explanation = explanations.get(concept);
        if (explanation == null) {
            return new ToolResult(
                false,
                "Unknown concept: " + concept,
                "Available concepts: " + String.join(", ", explanations.keySet())
            );
        }
        
        // Return the explanation
        return new ToolResult(true, "Explanation retrieved successfully", explanation);
    }
    
    @Override
    public Map<String, Object> getInputSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        
        Map<String, Object> conceptProperty = new HashMap<>();
        conceptProperty.put("type", "string");
        conceptProperty.put("description", "The MCP concept to explain");
        conceptProperty.put("enum", explanations.keySet().toArray());
        
        properties.put("concept", conceptProperty);
        
        schema.put("properties", properties);
        schema.put("required", Arrays.asList("concept"));
        
        return schema;
    }
    
    @Override
    public Map<String, Object> getOutputSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        
        Map<String, Object> successProperty = new HashMap<>();
        successProperty.put("type", "boolean");
        successProperty.put("description", "Whether the tool execution was successful");
        
        Map<String, Object> messageProperty = new HashMap<>();
        messageProperty.put("type", "string");
        messageProperty.put("description", "A message describing the result");
        
        Map<String, Object> dataProperty = new HashMap<>();
        dataProperty.put("type", "string");
        dataProperty.put("description", "The explanation of the concept");
        
        properties.put("success", successProperty);
        properties.put("message", messageProperty);
        properties.put("data", dataProperty);
        
        schema.put("properties", properties);
        schema.put("required", Arrays.asList("success", "message", "data"));
        
        return schema;
    }
    
    /**
     * Initializes the explanations for various MCP concepts.
     */
    private void initializeExplanations() {
        // Tool concept
        explanations.put("tool", """
            # MCP Tool
            
            In the Model Context Protocol (MCP), a tool is an executable function that performs an action and returns a result.
            
            Tools are used to extend the capabilities of AI assistants by allowing them to perform actions that they couldn't otherwise do, such as:
            - Generating code or content
            - Validating configurations
            - Performing calculations
            - Interacting with external systems
            
            ## Key Characteristics of MCP Tools
            
            1. **Input Parameters**: Tools accept parameters that control their behavior.
            2. **Execution Logic**: Tools perform some action or computation based on the input parameters.
            3. **Result**: Tools return a result indicating success/failure and any output data.
            
            ## Implementing MCP Tools
            
            To implement an MCP tool, you need to:
            
            1. Create a class that implements the Tool interface
            2. Define the input schema (what parameters the tool accepts)
            3. Implement the execution logic
            4. Define the output schema (what the tool returns)
            5. Register the tool with your MCP server
            
            ## Best Practices for MCP Tools
            
            - Provide clear documentation for your tool
            - Validate all input parameters
            - Handle errors gracefully
            - Make tools idempotent when possible
            - Consider performance implications for long-running tools
            """);
        
        // Resource concept
        explanations.put("resource", """
            # MCP Resource
            
            In the Model Context Protocol (MCP), a resource is a data source that provides information to clients.
            
            Resources are used to make data available to AI assistants, such as:
            - Documentation
            - Reference materials
            - Templates
            - Configuration examples
            - Status information
            
            ## Key Characteristics of MCP Resources
            
            1. **URI**: Resources are identified by a Uniform Resource Identifier (URI).
            2. **MIME Type**: Resources have a MIME type that indicates the format of the data.
            3. **Content**: Resources provide data in a specific format (usually JSON or plain text).
            
            ## Types of MCP Resources
            
            1. **Static Resources**: Fixed data that doesn't change between requests.
            2. **Dynamic Resources**: Data that is generated on-the-fly when requested.
            3. **Parameterized Resources**: Resources that accept parameters as part of the URI.
            
            ## Implementing MCP Resources
            
            To implement an MCP resource, you need to:
            
            1. Create a class that implements the ResourceProvider interface
            2. Define the URI pattern for the resources
            3. Implement the logic to generate or retrieve the resource data
            4. Register the resource provider with your MCP server
            
            ## Best Practices for MCP Resources
            
            - Use a consistent and intuitive URI structure
            - Provide clear documentation for your resources
            - Use appropriate MIME types
            - Handle errors gracefully
            - Consider caching for expensive resources
            """);
        
        // Prompt concept
        explanations.put("prompt", """
            # MCP Prompt
            
            In the Model Context Protocol (MCP), a prompt is a structured template that generates a response based on parameters.
            
            Prompts are used to provide guidance, instructions, or templates to AI assistants, such as:
            - Step-by-step guides for specific tasks
            - Code templates for common patterns
            - Explanations of concepts or components
            
            ## Key Characteristics of MCP Prompts
            
            1. **Parameters**: Prompts accept parameters that customize the response.
            2. **Template**: Prompts have a template that defines the structure of the response.
            3. **Response**: Prompts generate a formatted response based on the parameters.
            
            ## Types of MCP Prompts
            
            1. **Guidance Prompts**: Provide step-by-step instructions for specific tasks.
            2. **Template Prompts**: Generate code templates or boilerplate.
            3. **Explanation Prompts**: Provide detailed explanations of concepts or components.
            
            ## Implementing MCP Prompts
            
            To implement an MCP prompt, you need to:
            
            1. Create a class that implements the PromptHandler interface
            2. Define the parameter schema (what parameters the prompt accepts)
            3. Implement the logic to generate the response
            4. Register the prompt with your MCP server
            
            ## Best Practices for MCP Prompts
            
            - Provide clear documentation for your prompts
            - Validate all parameters
            - Use consistent formatting in responses
            - Handle errors gracefully
            - Consider versioning for backward compatibility
            """);
        
        // MCP server concept
        explanations.put("server", """
            # MCP Server
            
            In the Model Context Protocol (MCP), a server is a program that provides tools, resources, and prompts to clients.
            
            MCP servers are used to extend the capabilities of AI assistants by providing:
            - Tools that perform actions
            - Resources that provide data
            - Prompts that generate responses
            
            ## Key Components of MCP Servers
            
            1. **Server Configuration**: Defines the server's name, version, and capabilities.
            2. **Tools**: Executable functions that perform actions.
            3. **Resources**: Data sources that provide information.
            4. **Prompts**: Templates that generate responses.
            
            ## Implementing an MCP Server
            
            To implement an MCP server, you need to:
            
            1. Create a server instance with a name and version
            2. Define the server's capabilities
            3. Implement and register tools, resources, and prompts
            4. Connect the server to a transport (e.g., stdio, WebSocket)
            5. Start the server and handle requests
            
            ## Best Practices for MCP Servers
            
            - Provide clear documentation for your server
            - Organize tools, resources, and prompts logically
            - Handle errors gracefully
            - Consider security implications
            - Test thoroughly before deployment
            """);
        
        // MCP client concept
        explanations.put("client", """
            # MCP Client
            
            In the Model Context Protocol (MCP), a client is a program that connects to MCP servers and uses their tools, resources, and prompts.
            
            MCP clients are typically AI assistants or applications that need to extend their capabilities by:
            - Executing tools to perform actions
            - Accessing resources to get data
            - Using prompts to generate responses
            
            ## Key Components of MCP Clients
            
            1. **Client Configuration**: Defines how the client connects to servers.
            2. **Server Discovery**: Finds and connects to available MCP servers.
            3. **Request Handling**: Sends requests to servers and processes responses.
            
            ## Implementing an MCP Client
            
            To implement an MCP client, you need to:
            
            1. Create a client instance
            2. Connect to one or more MCP servers
            3. Discover the servers' capabilities
            4. Send requests to execute tools, access resources, or use prompts
            5. Process the responses
            
            ## Best Practices for MCP Clients
            
            - Handle server disconnections gracefully
            - Validate responses from servers
            - Implement timeouts for requests
            - Consider caching for frequently used resources
            - Provide clear error messages to users
            """);
    }
}
