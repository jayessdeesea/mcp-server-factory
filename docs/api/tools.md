# MCP Tools

This document explains the concept of tools in the Model Context Protocol (MCP) and how they are implemented in the MCP Server Factory.

## What are MCP Tools?

In the context of the Model Context Protocol, tools are executable functions that an MCP server makes available to clients. Unlike resources (which provide data) or prompts (which generate responses based on templates), tools perform actions and can modify state.

Tools can be thought of as "action endpoints" that:
1. Accept parameters as input
2. Perform some operation or computation
3. Return a result indicating success/failure and any output data

## Types of MCP Tools

### 1. Generator Tools
These tools generate content or code based on input parameters.

Example: A tool that generates a basic MCP server template based on specified requirements.

### 2. Validator Tools
These tools validate configurations, code, or other inputs against best practices or requirements.

Example: A tool that checks if an MCP server implementation follows recommended patterns.

### 3. Utility Tools
These tools provide utility functions that help with common tasks.

Example: A tool that formats JSON schema definitions for MCP tool inputs.

### 4. Task Planner Tools
These tools analyze high-level objectives and generate actionable task plans.

Example: A tool that breaks down a "clean up the codebase" objective into specific steps with instructions.

## Implementing Tools in Your MCP Server

### Basic Structure

```java
public class MyTool implements Tool {
    @Override
    public ToolResponse execute(ToolRequest request) {
        // Parse the tool parameters
        Map<String, Object> params = request.getParameters();
        
        // Execute the tool logic
        Result result = performAction(params);
        
        // Return the result
        return new ToolResponse(result.isSuccess(), result.getMessage(), result.getData());
    }
    
    private Result performAction(Map<String, Object> params) {
        // Implementation specific to this tool
    }
}
```

### Tool Input Schema

Tools should define an input schema that specifies the parameters they accept:

```java
public JsonSchema getInputSchema() {
    return JsonSchema.builder()
        .type("object")
        .property("name", JsonSchema.builder().type("string").description("Name of the component").build())
        .property("options", JsonSchema.builder().type("object").description("Additional options").build())
        .required("name")
        .build();
}
```

### Registering Tools

Tools need to be registered with your MCP server to be accessible:

```java
server.registerTool("generate_template", new TemplateGeneratorTool());
server.registerTool("validate_config", new ConfigValidatorTool());
```

## Best Practices for MCP Tools

1. **Clear Documentation**: Each tool should have clear documentation about its purpose, parameters, and expected output.

2. **Input Validation**: Validate all input parameters before executing the tool logic.

3. **Error Handling**: Provide clear error messages when a tool fails to execute properly.

4. **Idempotency**: When possible, design tools to be idempotent (running the same tool with the same parameters multiple times has the same effect as running it once).

5. **Progress Reporting**: For long-running tools, consider providing progress updates.

## Examples

### Example 1: Template Generator Tool

```java
public class TemplateGeneratorTool implements Tool {
    @Override
    public ToolResponse execute(ToolRequest request) {
        Map<String, Object> params = request.getParameters();
        
        String templateType = (String) params.get("type");
        String name = (String) params.get("name");
        
        try {
            String template = generateTemplate(templateType, name);
            return new ToolResponse(true, "Template generated successfully", template);
        } catch (Exception e) {
            return new ToolResponse(false, "Failed to generate template: " + e.getMessage(), null);
        }
    }
    
    private String generateTemplate(String templateType, String name) {
        switch (templateType) {
            case "tool":
                return generateToolTemplate(name);
            case "resource":
                return generateResourceTemplate(name);
            case "prompt":
                return generatePromptTemplate(name);
            default:
                throw new IllegalArgumentException("Unknown template type: " + templateType);
        }
    }
    
    private String generateToolTemplate(String name) {
        // Implementation to generate a tool template
    }
    
    private String generateResourceTemplate(String name) {
        // Implementation to generate a resource template
    }
    
    private String generatePromptTemplate(String name) {
        // Implementation to generate a prompt template
    }
    
    @Override
    public JsonSchema getInputSchema() {
        return JsonSchema.builder()
            .type("object")
            .property("type", JsonSchema.builder()
                .type("string")
                .description("Type of template to generate (tool, resource, or prompt)")
                .enumValues(Arrays.asList("tool", "resource", "prompt"))
                .build())
            .property("name", JsonSchema.builder()
                .type("string")
                .description("Name of the component")
                .build())
            .required("type", "name")
            .build();
    }
}
```

### Example 2: Configuration Validator Tool

```java
public class ConfigValidatorTool implements Tool {
    @Override
    public ToolResponse execute(ToolRequest request) {
        Map<String, Object> params = request.getParameters();
        
        String configJson = (String) params.get("config");
        
        try {
            List<ValidationIssue> issues = validateConfig(configJson);
            
            if (issues.isEmpty()) {
                return new ToolResponse(true, "Configuration is valid", Collections.emptyList());
            } else {
                return new ToolResponse(false, "Configuration has issues", issues);
            }
        } catch (Exception e) {
            return new ToolResponse(false, "Failed to validate configuration: " + e.getMessage(), null);
        }
    }
    
    private List<ValidationIssue> validateConfig(String configJson) {
        // Implementation to validate the configuration
    }
    
    @Override
    public JsonSchema getInputSchema() {
        return JsonSchema.builder()
            .type("object")
            .property("config", JsonSchema.builder()
                .type("string")
                .description("JSON configuration to validate")
                .build())
            .required("config")
            .build();
    }
}
```

## Supported Tools in MCP Server Factory

The MCP Server Factory supports the following tools:

1. `generate_mcp_server` - Generates a complete MCP server template based on specified requirements
2. `validate_mcp_server` - Validates an MCP server implementation against best practices
3. `generate_component` - Generates a specific MCP component (tool, resource, or prompt)
4. `explain_concept` - Provides a detailed explanation of an MCP concept
5. `format_schema` - Formats a JSON schema for MCP tool inputs
6. `code_cleanup_planner` - Generates a task plan for code cleanup objectives
7. `feature_implementation_planner` - Generates a task plan for feature implementation objectives
8. `general_task_planner` - Generates a task plan for general objectives
9. `local_mcp_deployment_planner` - Generates a task plan for deploying local MCP servers

## Task Planner Tools

Task planner tools are a special type of tool that analyze high-level objectives and generate actionable task plans. These tools help break down complex tasks into manageable steps with detailed instructions.

### Structure of a Task Plan

A task plan consists of:

1. **Objective**: The original high-level objective
2. **Summary**: A summary of the task plan
3. **Steps**: A list of actionable steps to accomplish the objective

Each step includes:
- **Description**: A short description of the step
- **Instruction**: Detailed instructions for completing the step
- **Metadata**: Additional information such as estimated effort, priority, and dependencies

### Example: Code Cleanup Planner

The `code_cleanup_planner` tool generates a task plan for cleaning up a codebase. It breaks down the objective into steps such as:

1. Analyze the codebase
2. Fix code style issues
3. Refactor duplicate code
4. Improve naming conventions
5. Add missing documentation
6. Fix code smells
7. Optimize performance
8. Verify changes

Each step includes detailed instructions on how to accomplish it, including example commands and code snippets.

### Example: Feature Implementation Planner

The `feature_implementation_planner` tool generates a task plan for implementing a new feature. It breaks down the objective into steps such as:

1. Analyze requirements
2. Design the feature
3. Implement the feature
4. Write tests
5. Integrate with existing code
6. Document the feature
7. Review and refine

### Example: General Task Planner

The `general_task_planner` tool is a versatile tool that can generate task plans for various types of objectives. It analyzes the objective and either delegates to a specialized planner or generates a generic task plan with steps such as:

1. Analyze the objective
2. Research and gather information
3. Plan the approach
4. Implement the solution
5. Test and verify
6. Document the changes

### Example: Local MCP Deployment Planner

The `local_mcp_deployment_planner` tool generates a streamlined task plan for deploying MCP servers to a local environment. It breaks down the deployment process into these essential steps:

1. **Clean the project (CRITICAL)** - Remove any previous build artifacts to ensure a fresh build. This step is critical and will abort the entire deployment process if it fails for any reason.
2. Build the project - Compile the Java source code into class files
3. **Run tests (CRITICAL)** - Execute all tests as a quality gate. If any test fails, the deployment process is immediately aborted. This step ensures that only properly functioning code is deployed.
4. Package the project - Create an executable JAR file with all dependencies
5. Deploy the MCP server - Copy the JAR to the standard location and update MCP settings
6. Verify the deployment - Test the server with the ping tool to ensure it's working correctly

Each step includes detailed cross-platform instructions (for both Windows and Unix-based systems) with error checking to abort the process if any step fails. The clean step is particularly critical - if it fails for any reason, the entire deployment process will be immediately aborted to prevent issues with stale or incompatible artifacts. The tool is specifically designed for deploying MCP servers to the `{HOME}/mcp-server/mcp-server-factory` directory structure, making it easy to manage and update the MCP server.
