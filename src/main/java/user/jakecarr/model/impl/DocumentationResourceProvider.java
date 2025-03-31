package user.jakecarr.model.impl;

import user.jakecarr.model.McpResource;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Resource provider for MCP documentation.
 * This provider serves documentation on various MCP topics.
 */
public class DocumentationResourceProvider implements McpResource.Provider {
    private static final Logger LOGGER = Logger.getLogger(DocumentationResourceProvider.class.getName());
    
    private static final String URI_PATTERN = "mcp://factory/documentation/([^/]+)";
    private static final Pattern URI_REGEX = Pattern.compile(URI_PATTERN);
    private static final String DESCRIPTION = "Provides documentation on MCP topics";
    
    private final Map<String, String> documentation = new HashMap<>();
    
    /**
     * Creates a new DocumentationResourceProvider.
     */
    public DocumentationResourceProvider() {
        // Initialize documentation
        initializeDocumentation();
    }
    
    @Override
    public String getUriPattern() {
        return URI_PATTERN;
    }
    
    @Override
    public McpResource getResource(String uri) {
        LOGGER.info("Getting resource for URI: " + uri);
        
        // Parse the URI to extract the topic
        Matcher matcher = URI_REGEX.matcher(uri);
        if (!matcher.matches()) {
            LOGGER.warning("URI does not match pattern: " + uri);
            return null;
        }
        
        String topic = matcher.group(1);
        
        // Get the documentation for the topic
        String content = documentation.get(topic);
        if (content == null) {
            LOGGER.warning("Documentation not found for topic: " + topic);
            return new DocumentationResource(
                uri,
                "Unknown topic: " + topic + ". Available topics: " + String.join(", ", documentation.keySet())
            );
        }
        
        // Return the documentation resource
        return new DocumentationResource(uri, content);
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    /**
     * Initializes the documentation for various MCP topics.
     */
    private void initializeDocumentation() {
        // Getting started
        documentation.put("getting-started", """
            # Getting Started with MCP
            
            This guide will help you get started with the Model Context Protocol (MCP).
            
            ## What is MCP?
            
            The Model Context Protocol (MCP) is a protocol that enables AI assistants to communicate with external systems.
            It allows AI assistants to:
            
            - Execute tools to perform actions
            - Access resources to get data
            - Use prompts to generate responses
            
            ## Setting Up an MCP Server
            
            To set up an MCP server, you need to:
            
            1. Choose a programming language and MCP SDK
            2. Create a server instance
            3. Implement tools, resources, and prompts
            4. Connect the server to a transport
            5. Start the server
            
            ## Example: Hello World MCP Server
            
            Here's a simple example of an MCP server in Java:
            
            ```java
            import org.modelcontextprotocol.Server;
            import org.modelcontextprotocol.transport.StdioTransport;
            
            public class HelloWorldServer {
                public static void main(String[] args) {
                    // Create a server instance
                    Server server = new Server("hello-world", "1.0.0");
                    
                    // Register a simple tool
                    server.registerTool("hello", (request) -> {
                        String name = request.getParameters().getOrDefault("name", "World").toString();
                        return "Hello, " + name + "!";
                    });
                    
                    // Connect to stdio transport
                    server.connect(new StdioTransport());
                    
                    // Start the server
                    server.start();
                }
            }
            ```
            
            ## Next Steps
            
            Once you have a basic MCP server running, you can:
            
            1. Implement more complex tools
            2. Add resources to provide data
            3. Create prompts to generate responses
            4. Connect your server to an AI assistant
            
            For more information, see the MCP documentation.
            """);
        
        // Best practices
        documentation.put("best-practices", """
            # MCP Best Practices
            
            This guide provides best practices for implementing MCP servers.
            
            ## General Best Practices
            
            1. **Clear Documentation**: Document your server, tools, resources, and prompts.
            2. **Error Handling**: Handle errors gracefully and provide clear error messages.
            3. **Security**: Consider security implications and validate all inputs.
            4. **Performance**: Optimize performance for long-running operations.
            5. **Testing**: Test your server thoroughly before deployment.
            
            ## Tool Best Practices
            
            1. **Input Validation**: Validate all input parameters before executing the tool logic.
            2. **Idempotency**: When possible, design tools to be idempotent.
            3. **Progress Reporting**: For long-running tools, provide progress updates.
            4. **Clear Results**: Return clear and structured results.
            
            ## Resource Best Practices
            
            1. **Consistent URI Structure**: Use a consistent and intuitive URI structure.
            2. **Appropriate MIME Types**: Use appropriate MIME types for your resources.
            3. **Caching**: Consider caching for resources that are expensive to generate.
            4. **Error Responses**: Provide clear error messages when a resource cannot be found.
            
            ## Prompt Best Practices
            
            1. **Parameterization**: Make prompts flexible by accepting parameters.
            2. **Consistent Formatting**: Maintain consistent formatting in prompt responses.
            3. **Versioning**: Consider versioning your prompts for backward compatibility.
            4. **Clear Instructions**: Provide clear instructions in your prompts.
            
            ## Implementation Best Practices
            
            1. **Modular Design**: Design your server with modularity in mind.
            2. **Dependency Injection**: Use dependency injection to manage dependencies.
            3. **Configuration**: Make your server configurable.
            4. **Logging**: Implement comprehensive logging.
            5. **Monitoring**: Add monitoring to track server health and usage.
            
            By following these best practices, you can create robust and maintainable MCP servers.
            """);
        
        // Troubleshooting
        documentation.put("troubleshooting", """
            # MCP Troubleshooting Guide
            
            This guide provides solutions to common issues with MCP servers.
            
            ## Connection Issues
            
            ### Server Not Starting
            
            If your MCP server is not starting, check:
            
            1. Are there any errors in the logs?
            2. Is the port already in use (for network transports)?
            3. Do you have the correct permissions?
            
            ### Client Cannot Connect
            
            If a client cannot connect to your MCP server, check:
            
            1. Is the server running?
            2. Are the client and server using the same transport?
            3. Is there a firewall blocking the connection?
            
            ## Tool Issues
            
            ### Tool Not Found
            
            If a tool is not found, check:
            
            1. Is the tool registered with the server?
            2. Is the tool name correct (case-sensitive)?
            
            ### Tool Execution Fails
            
            If a tool execution fails, check:
            
            1. Are the input parameters valid?
            2. Is there an error in the tool implementation?
            3. Are there any external dependencies that are not available?
            
            ## Resource Issues
            
            ### Resource Not Found
            
            If a resource is not found, check:
            
            1. Is the resource provider registered with the server?
            2. Does the URI match the provider's pattern?
            3. Is the resource available?
            
            ### Resource Content Issues
            
            If there are issues with resource content, check:
            
            1. Is the MIME type correct?
            2. Is the content properly encoded?
            3. Is there an error in the resource provider implementation?
            
            ## Prompt Issues
            
            ### Prompt Not Found
            
            If a prompt is not found, check:
            
            1. Is the prompt registered with the server?
            2. Is the prompt name correct (case-sensitive)?
            
            ### Prompt Execution Fails
            
            If a prompt execution fails, check:
            
            1. Are the parameters valid?
            2. Is there an error in the prompt implementation?
            
            ## General Troubleshooting
            
            1. **Check Logs**: Look for error messages in the logs.
            2. **Enable Debug Logging**: Enable debug logging for more detailed information.
            3. **Check Configuration**: Verify that the server is configured correctly.
            4. **Test Components Individually**: Test tools, resources, and prompts individually.
            5. **Update SDK**: Make sure you're using the latest version of the MCP SDK.
            
            If you're still having issues, consult the MCP documentation or seek help from the community.
            """);
    }
    
    /**
     * Implementation of McpResource for documentation.
     */
    private static class DocumentationResource implements McpResource {
        private final String uri;
        private final String content;
        
        /**
         * Creates a new DocumentationResource.
         * 
         * @param uri The URI of the resource
         * @param content The content of the resource
         */
        public DocumentationResource(String uri, String content) {
            this.uri = uri;
            this.content = content;
        }
        
        @Override
        public String getName() {
            // Extract the topic from the URI
            Matcher matcher = URI_REGEX.matcher(uri);
            if (matcher.matches()) {
                return "Documentation: " + matcher.group(1);
            } else {
                return "Documentation";
            }
        }
        
        @Override
        public String getDescription() {
            return "Documentation on an MCP topic";
        }
        
        @Override
        public String getUri() {
            return uri;
        }
        
        @Override
        public String getMimeType() {
            return "text/markdown";
        }
        
        @Override
        public String getContent() {
            return content;
        }
    }
}
