# MCP Prompts

This document explains the concept of prompts in the Model Context Protocol (MCP) and how they are implemented in the MCP Server Factory.

## What are MCP Prompts?

In the context of the Model Context Protocol, prompts are structured requests that can be sent to an MCP server to retrieve specific information or guidance. Unlike tools (which execute actions) and resources (which provide data), prompts are designed to generate contextual responses based on predefined templates and patterns.

Prompts can be thought of as "smart templates" that:
1. Accept parameters or context
2. Process this input according to defined rules
3. Return a formatted response that helps guide the user

## Types of MCP Prompts

### 1. Guidance Prompts
These prompts provide step-by-step instructions or best practices for specific tasks.

Example: A prompt that guides users through setting up a new MCP server with the correct configuration.

### 2. Template Prompts
These prompts generate code templates or boilerplate for common MCP server components.

Example: A prompt that generates the basic structure for a new MCP tool implementation.

### 3. Explanation Prompts
These prompts provide detailed explanations of MCP concepts or components.

Example: A prompt that explains how MCP resources work and when to use them.

## Implementing Prompts in Your MCP Server

### Basic Structure

```java
public class MyPromptHandler implements PromptHandler {
    @Override
    public PromptResponse handlePrompt(PromptRequest request) {
        // Parse the prompt parameters
        Map<String, Object> params = request.getParameters();
        
        // Process the prompt based on the parameters
        String response = generateResponse(params);
        
        // Return the formatted response
        return new PromptResponse(response);
    }
    
    private String generateResponse(Map<String, Object> params) {
        // Implementation specific to this prompt
    }
}
```

### Registering Prompts

Prompts need to be registered with your MCP server to be accessible:

```java
server.registerPrompt("create_tool_template", new CreateToolTemplatePrompt());
server.registerPrompt("mcp_resource_explanation", new ResourceExplanationPrompt());
```

## Best Practices for MCP Prompts

1. **Clear Documentation**: Each prompt should have clear documentation about its purpose, parameters, and expected output.

2. **Consistent Formatting**: Maintain consistent formatting in prompt responses to make them easy to read and use.

3. **Parameterization**: Make prompts flexible by accepting parameters that can customize the response.

4. **Error Handling**: Include robust error handling to provide helpful feedback when prompts are used incorrectly.

5. **Versioning**: Consider versioning your prompts to maintain backward compatibility as your server evolves.

## Examples

### Example 1: Tool Template Prompt

```java
public class ToolTemplatePrompt implements PromptHandler {
    @Override
    public PromptResponse handlePrompt(PromptRequest request) {
        String toolName = request.getParameters().get("toolName").toString();
        String toolDescription = request.getParameters().get("description").toString();
        
        StringBuilder template = new StringBuilder();
        template.append("public class ").append(toolName).append("Tool implements Tool {\n\n");
        template.append("    @Override\n");
        template.append("    public ToolResponse execute(ToolRequest request) {\n");
        template.append("        // TODO: Implement tool logic\n");
        template.append("        return new ToolResponse(\"Tool executed successfully\");\n");
        template.append("    }\n\n");
        template.append("    @Override\n");
        template.append("    public ToolMetadata getMetadata() {\n");
        template.append("        return new ToolMetadata(\"").append(toolName).append("\", \"");
        template.append(toolDescription).append("\");\n");
        template.append("    }\n");
        template.append("}\n");
        
        return new PromptResponse(template.toString());
    }
}
```

### Example 2: MCP Concept Explanation Prompt

```java
public class ConceptExplanationPrompt implements PromptHandler {
    private Map<String, String> explanations = new HashMap<>();
    
    public ConceptExplanationPrompt() {
        explanations.put("tool", "MCP Tools are executable functions that perform actions...");
        explanations.put("resource", "MCP Resources provide data that can be accessed by the client...");
        explanations.put("prompt", "MCP Prompts are structured templates that generate responses...");
    }
    
    @Override
    public PromptResponse handlePrompt(PromptRequest request) {
        String concept = request.getParameters().get("concept").toString().toLowerCase();
        
        if (explanations.containsKey(concept)) {
            return new PromptResponse(explanations.get(concept));
        } else {
            return new PromptResponse("Concept not found. Available concepts: " + 
                                     String.join(", ", explanations.keySet()));
        }
    }
}
```

## Supported Prompts in MCP Server Factory

The MCP Server Factory supports the following prompts:

1. `bootstrap_mcp_server` - Provides a template for creating a Java-based MCP server
2. `explain_mcp_concept` - Provides detailed explanations of MCP concepts
3. `tool_implementation_guide` - Guides through implementing an MCP tool
4. `resource_implementation_guide` - Guides through implementing an MCP resource
5. `prompt_implementation_guide` - Guides through implementing an MCP prompt

### bootstrap_mcp_server

This prompt generates a complete template for a Java-based MCP server using Spring Framework. It provides all the necessary code and configuration files to get started with a new MCP server project.

**Parameters:**
- `serverName` (string): The name of the MCP server
- `packageName` (string): The Java package name for the server code
- `description` (string): A description of the MCP server
- `buildSystem` (string): The build system to use (maven, gradle, or detect)

**Example Usage:**
```java
Map<String, Object> parameters = new HashMap<>();
parameters.put("serverName", "weather-mcp-server");
parameters.put("packageName", "com.example.weather");
parameters.put("description", "An MCP server that provides weather information");
parameters.put("buildSystem", "maven");

McpPrompt.PromptResponse response = promptService.getPrompt("bootstrap_mcp_server").handle(parameters);
String template = response.getContent();
```

The generated template includes:
- Project structure
- Build configuration (Maven or Gradle)
- Spring configuration
- MCP server setup
- Basic tool implementation (PingTool)
- Instructions for running and extending the server
