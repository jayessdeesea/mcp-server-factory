# MCP Resources

This document explains the concept of resources in the Model Context Protocol (MCP) and how they are implemented in the MCP Server Factory.

## What are MCP Resources?

In the context of the Model Context Protocol, resources are data sources that an MCP server makes available to clients. Unlike tools (which execute actions) or prompts (which generate responses based on templates), resources represent static or dynamic data that can be accessed but not modified by the client.

Resources can be thought of as "data endpoints" that:
1. Have a unique URI (Uniform Resource Identifier)
2. Provide data in a specific format (usually JSON or plain text)
3. Can be static (unchanging) or dynamic (generated on request)

## Types of MCP Resources

### 1. Static Resources
These resources provide fixed data that doesn't change between requests.

Example: Documentation, reference materials, or configuration templates.

### 2. Dynamic Resources
These resources generate data on-the-fly when requested.

Example: Current system status, real-time metrics, or data that depends on parameters in the request.

### 3. Parameterized Resources
These resources accept parameters as part of the URI to customize the response.

Example: A resource that provides information about a specific MCP component based on its name.

## Implementing Resources in Your MCP Server

### Basic Structure

```java
public class MyResourceProvider implements ResourceProvider {
    @Override
    public Resource getResource(ResourceRequest request) {
        // Parse the resource URI
        String uri = request.getUri();
        
        // Generate or retrieve the resource data
        String data = generateResourceData(uri);
        
        // Return the resource with appropriate metadata
        return new Resource(uri, "application/json", data);
    }
    
    private String generateResourceData(String uri) {
        // Implementation specific to this resource
    }
}
```

### Resource URIs

Resources are identified by URIs that follow a specific pattern:

```
scheme://authority/path?query
```

For example:
- `mcp://server/tools/list` - List all available tools
- `mcp://server/resources/documentation?topic=resources` - Get documentation about resources

### Registering Resources

Resources need to be registered with your MCP server to be accessible:

```java
server.registerResourceProvider("mcp://server/tools", new ToolsResourceProvider());
server.registerResourceProvider("mcp://server/resources", new DocumentationResourceProvider());
```

## Best Practices for MCP Resources

1. **Consistent URI Structure**: Use a consistent and intuitive URI structure for your resources.

2. **Clear Documentation**: Document each resource's URI pattern, parameters, and response format.

3. **Appropriate Content Types**: Use appropriate MIME types for your resources (e.g., application/json, text/plain).

4. **Error Handling**: Provide clear error messages when a resource cannot be found or accessed.

5. **Caching**: Consider implementing caching for resources that are expensive to generate but don't change frequently.

## Examples

### Example 1: Static Documentation Resource

```java
public class DocumentationResourceProvider implements ResourceProvider {
    private Map<String, String> documentation = new HashMap<>();
    
    public DocumentationResourceProvider() {
        documentation.put("tools", "MCP Tools are executable functions that perform actions...");
        documentation.put("resources", "MCP Resources provide data that can be accessed by the client...");
        documentation.put("prompts", "MCP Prompts are structured templates that generate responses...");
    }
    
    @Override
    public Resource getResource(ResourceRequest request) {
        String uri = request.getUri();
        URI parsedUri = URI.create(uri);
        
        // Parse query parameters
        Map<String, String> queryParams = parseQueryParams(parsedUri.getQuery());
        String topic = queryParams.getOrDefault("topic", "");
        
        if (documentation.containsKey(topic)) {
            return new Resource(uri, "text/plain", documentation.get(topic));
        } else {
            return new Resource(uri, "text/plain", 
                "Topic not found. Available topics: " + String.join(", ", documentation.keySet()));
        }
    }
    
    private Map<String, String> parseQueryParams(String query) {
        // Implementation to parse query parameters
    }
}
```

### Example 2: Dynamic System Status Resource

```java
public class SystemStatusResourceProvider implements ResourceProvider {
    @Override
    public Resource getResource(ResourceRequest request) {
        String uri = request.getUri();
        
        // Generate system status information
        JsonObject status = new JsonObject();
        status.addProperty("timestamp", System.currentTimeMillis());
        status.addProperty("uptime", getSystemUptime());
        status.addProperty("memoryUsage", getMemoryUsage());
        status.addProperty("activeConnections", getActiveConnections());
        
        return new Resource(uri, "application/json", status.toString());
    }
    
    private long getSystemUptime() {
        // Implementation to get system uptime
    }
    
    private double getMemoryUsage() {
        // Implementation to get memory usage
    }
    
    private int getActiveConnections() {
        // Implementation to get active connections
    }
}
```

## Supported Resources in MCP Server Factory

The MCP Server Factory supports the following resources:

1. `mcp://factory/templates/list` - List all available MCP server templates
2. `mcp://factory/templates/{templateName}` - Get a specific MCP server template
3. `mcp://factory/documentation/{topic}` - Get documentation on a specific MCP topic
4. `mcp://factory/examples/{component}/{exampleName}` - Get example code for MCP components
5. `mcp://factory/best-practices/{component}` - Get best practices for implementing MCP components
