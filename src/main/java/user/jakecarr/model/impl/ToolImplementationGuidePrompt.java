package user.jakecarr.model.impl;

import user.jakecarr.model.McpPrompt;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Prompt that provides a guide for implementing MCP tools.
 * This prompt generates a step-by-step guide for implementing an MCP tool.
 */
public class ToolImplementationGuidePrompt implements McpPrompt {
    private static final Logger LOGGER = Logger.getLogger(ToolImplementationGuidePrompt.class.getName());
    
    private static final String NAME = "tool_implementation_guide";
    private static final String DESCRIPTION = "Provides a step-by-step guide for implementing an MCP tool";
    
    /**
     * Creates a new ToolImplementationGuidePrompt.
     */
    public ToolImplementationGuidePrompt() {
        // No initialization needed
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
    public PromptResponse handle(Map<String, Object> parameters) {
        LOGGER.info("Handling tool_implementation_guide prompt with parameters: " + parameters);
        
        // Get the tool name parameter
        String toolName = (String) parameters.getOrDefault("toolName", "MyTool");
        
        // Get the tool description parameter
        String toolDescription = (String) parameters.getOrDefault("description", "A tool that does something useful");
        
        // Get the language parameter
        String language = (String) parameters.getOrDefault("language", "java");
        
        // Generate the guide based on the parameters
        String guide = generateGuide(toolName, toolDescription, language);
        
        // Return the guide
        return new PromptResponse(guide);
    }
    
    @Override
    public Map<String, Object> getParameterSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        
        Map<String, Object> toolNameProperty = new HashMap<>();
        toolNameProperty.put("type", "string");
        toolNameProperty.put("description", "The name of the tool to implement");
        
        Map<String, Object> descriptionProperty = new HashMap<>();
        descriptionProperty.put("type", "string");
        descriptionProperty.put("description", "A description of what the tool does");
        
        Map<String, Object> languageProperty = new HashMap<>();
        languageProperty.put("type", "string");
        languageProperty.put("description", "The programming language to use");
        languageProperty.put("enum", new String[]{"java", "typescript", "python"});
        
        properties.put("toolName", toolNameProperty);
        properties.put("description", descriptionProperty);
        properties.put("language", languageProperty);
        
        schema.put("properties", properties);
        
        return schema;
    }
    
    /**
     * Generates a guide for implementing an MCP tool.
     * 
     * @param toolName The name of the tool
     * @param toolDescription A description of what the tool does
     * @param language The programming language to use
     * @return A step-by-step guide for implementing the tool
     */
    private String generateGuide(String toolName, String toolDescription, String language) {
        switch (language.toLowerCase()) {
            case "java":
                return generateJavaGuide(toolName, toolDescription);
            case "typescript":
                return generateTypeScriptGuide(toolName, toolDescription);
            case "python":
                return generatePythonGuide(toolName, toolDescription);
            default:
                return "Unsupported language: " + language;
        }
    }
    
    /**
     * Generates a guide for implementing an MCP tool in Java.
     * 
     * @param toolName The name of the tool
     * @param toolDescription A description of what the tool does
     * @return A step-by-step guide for implementing the tool in Java
     */
    private String generateJavaGuide(String toolName, String toolDescription) {
        String className = toolName + "Tool";
        
        return """
            # Implementing the %s Tool in Java
            
            This guide will walk you through implementing the %s tool in Java.
            
            ## Step 1: Create the Tool Class
            
            Create a new Java class called `%s` that implements the `Tool` interface:
            
            ```java
            import org.modelcontextprotocol.Tool;
            import org.modelcontextprotocol.ToolRequest;
            import org.modelcontextprotocol.ToolResponse;
            
            import java.util.HashMap;
            import java.util.Map;
            
            public class %s implements Tool {
                // Implementation will go here
            }
            ```
            
            ## Step 2: Implement the Required Methods
            
            The `Tool` interface requires you to implement several methods:
            
            ```java
            public class %s implements Tool {
                @Override
                public String getName() {
                    return "%s";
                }
                
                @Override
                public String getDescription() {
                    return "%s";
                }
                
                @Override
                public ToolResponse execute(ToolRequest request) {
                    // Tool implementation will go here
                    return new ToolResponse(true, "Tool executed successfully", null);
                }
                
                @Override
                public Map<String, Object> getInputSchema() {
                    Map<String, Object> schema = new HashMap<>();
                    // Define the input schema
                    return schema;
                }
                
                @Override
                public Map<String, Object> getOutputSchema() {
                    Map<String, Object> schema = new HashMap<>();
                    // Define the output schema
                    return schema;
                }
            }
            ```
            
            ## Step 3: Implement the Tool Logic
            
            Now, implement the actual logic of your tool in the `execute` method:
            
            ```java
            @Override
            public ToolResponse execute(ToolRequest request) {
                try {
                    // Get parameters from the request
                    Map<String, Object> params = request.getParameters();
                    
                    // Validate parameters
                    if (!validateParameters(params)) {
                        return new ToolResponse(false, "Invalid parameters", null);
                    }
                    
                    // Perform the tool's action
                    Object result = performAction(params);
                    
                    // Return the result
                    return new ToolResponse(true, "Tool executed successfully", result);
                } catch (Exception e) {
                    return new ToolResponse(false, "Error executing tool: " + e.getMessage(), null);
                }
            }
            
            private boolean validateParameters(Map<String, Object> params) {
                // Implement parameter validation
                return true;
            }
            
            private Object performAction(Map<String, Object> params) {
                // Implement the tool's action
                return "Result of the action";
            }
            ```
            
            ## Step 4: Define the Input Schema
            
            Define the input schema to specify what parameters your tool accepts:
            
            ```java
            @Override
            public Map<String, Object> getInputSchema() {
                Map<String, Object> schema = new HashMap<>();
                schema.put("type", "object");
                
                Map<String, Object> properties = new HashMap<>();
                
                Map<String, Object> param1Property = new HashMap<>();
                param1Property.put("type", "string");
                param1Property.put("description", "Description of parameter 1");
                
                Map<String, Object> param2Property = new HashMap<>();
                param2Property.put("type", "number");
                param2Property.put("description", "Description of parameter 2");
                
                properties.put("param1", param1Property);
                properties.put("param2", param2Property);
                
                schema.put("properties", properties);
                schema.put("required", Arrays.asList("param1"));
                
                return schema;
            }
            ```
            
            ## Step 5: Define the Output Schema
            
            Define the output schema to specify what your tool returns:
            
            ```java
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
                dataProperty.put("description", "The result data");
                
                properties.put("success", successProperty);
                properties.put("message", messageProperty);
                properties.put("data", dataProperty);
                
                schema.put("properties", properties);
                
                return schema;
            }
            ```
            
            ## Step 6: Register the Tool with Your MCP Server
            
            Finally, register your tool with your MCP server:
            
            ```java
            import org.modelcontextprotocol.Server;
            
            public class MyMcpServer {
                public static void main(String[] args) {
                    Server server = new Server("my-server", "1.0.0");
                    
                    // Register your tool
                    server.registerTool(new %s());
                    
                    // ... other server setup
                }
            }
            ```
            
            ## Next Steps
            
            1. Implement the specific logic for your tool
            2. Add proper error handling
            3. Test your tool thoroughly
            4. Document your tool for users
            
            Congratulations! You've implemented the %s tool in Java.
            """.formatted(
                toolName, toolName, className, className, className,
                toolName, toolDescription, className, toolName
            );
    }
    
    /**
     * Generates a guide for implementing an MCP tool in TypeScript.
     * 
     * @param toolName The name of the tool
     * @param toolDescription A description of what the tool does
     * @return A step-by-step guide for implementing the tool in TypeScript
     */
    private String generateTypeScriptGuide(String toolName, String toolDescription) {
        String className = toolName + "Tool";
        
        return """
            # Implementing the %s Tool in TypeScript
            
            This guide will walk you through implementing the %s tool in TypeScript.
            
            ## Step 1: Create the Tool Class
            
            Create a new TypeScript file called `%s.ts` that implements the `Tool` interface:
            
            ```typescript
            import { Tool, ToolRequest, ToolResponse } from '@modelcontextprotocol/sdk';
            
            export class %s implements Tool {
                // Implementation will go here
            }
            ```
            
            ## Step 2: Implement the Required Methods
            
            The `Tool` interface requires you to implement several methods:
            
            ```typescript
            export class %s implements Tool {
                getName(): string {
                    return "%s";
                }
                
                getDescription(): string {
                    return "%s";
                }
                
                execute(request: ToolRequest): ToolResponse {
                    // Tool implementation will go here
                    return {
                        success: true,
                        message: "Tool executed successfully",
                        data: null
                    };
                }
                
                getInputSchema(): object {
                    // Define the input schema
                    return {};
                }
                
                getOutputSchema(): object {
                    // Define the output schema
                    return {};
                }
            }
            ```
            
            ## Step 3: Implement the Tool Logic
            
            Now, implement the actual logic of your tool in the `execute` method:
            
            ```typescript
            execute(request: ToolRequest): ToolResponse {
                try {
                    // Get parameters from the request
                    const params = request.parameters;
                    
                    // Validate parameters
                    if (!this.validateParameters(params)) {
                        return {
                            success: false,
                            message: "Invalid parameters",
                            data: null
                        };
                    }
                    
                    // Perform the tool's action
                    const result = this.performAction(params);
                    
                    // Return the result
                    return {
                        success: true,
                        message: "Tool executed successfully",
                        data: result
                    };
                } catch (error) {
                    return {
                        success: false,
                        message: `Error executing tool: ${error.message}`,
                        data: null
                    };
                }
            }
            
            private validateParameters(params: any): boolean {
                // Implement parameter validation
                return true;
            }
            
            private performAction(params: any): any {
                // Implement the tool's action
                return "Result of the action";
            }
            ```
            
            ## Step 4: Define the Input Schema
            
            Define the input schema to specify what parameters your tool accepts:
            
            ```typescript
            getInputSchema(): object {
                return {
                    type: "object",
                    properties: {
                        param1: {
                            type: "string",
                            description: "Description of parameter 1"
                        },
                        param2: {
                            type: "number",
                            description: "Description of parameter 2"
                        }
                    },
                    required: ["param1"]
                };
            }
            ```
            
            ## Step 5: Define the Output Schema
            
            Define the output schema to specify what your tool returns:
            
            ```typescript
            getOutputSchema(): object {
                return {
                    type: "object",
                    properties: {
                        success: {
                            type: "boolean",
                            description: "Whether the tool execution was successful"
                        },
                        message: {
                            type: "string",
                            description: "A message describing the result"
                        },
                        data: {
                            type: "string",
                            description: "The result data"
                        }
                    }
                };
            }
            ```
            
            ## Step 6: Register the Tool with Your MCP Server
            
            Finally, register your tool with your MCP server:
            
            ```typescript
            import { Server } from '@modelcontextprotocol/sdk';
            import { %s } from './%s';
            
            async function main() {
                const server = new Server({
                    name: "my-server",
                    version: "1.0.0"
                });
                
                // Register your tool
                server.registerTool(new %s());
                
                // ... other server setup
            }
            
            main().catch(console.error);
            ```
            
            ## Next Steps
            
            1. Implement the specific logic for your tool
            2. Add proper error handling
            3. Test your tool thoroughly
            4. Document your tool for users
            
            Congratulations! You've implemented the %s tool in TypeScript.
            """.formatted(
                toolName, toolName, className.toLowerCase(), className, className,
                toolName, toolDescription, className, className.toLowerCase(), className, toolName
            );
    }
    
    /**
     * Generates a guide for implementing an MCP tool in Python.
     * 
     * @param toolName The name of the tool
     * @param toolDescription A description of what the tool does
     * @return A step-by-step guide for implementing the tool in Python
     */
    private String generatePythonGuide(String toolName, String toolDescription) {
        String className = toolName + "Tool";
        String fileName = toolName.toLowerCase() + "_tool.py";
        
        return """
            # Implementing the %s Tool in Python
            
            This guide will walk you through implementing the %s tool in Python.
            
            ## Step 1: Create the Tool Class
            
            Create a new Python file called `%s` that implements the `Tool` interface:
            
            ```python
            from modelcontextprotocol import Tool, ToolRequest, ToolResponse
            
            class %s(Tool):
                # Implementation will go here
                pass
            ```
            
            ## Step 2: Implement the Required Methods
            
            The `Tool` interface requires you to implement several methods:
            
            ```python
            class %s(Tool):
                def get_name(self):
                    return "%s"
                
                def get_description(self):
                    return "%s"
                
                def execute(self, request):
                    # Tool implementation will go here
                    return ToolResponse(success=True, message="Tool executed successfully", data=None)
                
                def get_input_schema(self):
                    # Define the input schema
                    return {}
                
                def get_output_schema(self):
                    # Define the output schema
                    return {}
            ```
            
            ## Step 3: Implement the Tool Logic
            
            Now, implement the actual logic of your tool in the `execute` method:
            
            ```python
            def execute(self, request):
                try:
                    # Get parameters from the request
                    params = request.parameters
                    
                    # Validate parameters
                    if not self._validate_parameters(params):
                        return ToolResponse(success=False, message="Invalid parameters", data=None)
                    
                    # Perform the tool's action
                    result = self._perform_action(params)
                    
                    # Return the result
                    return ToolResponse(success=True, message="Tool executed successfully", data=result)
                except Exception as e:
                    return ToolResponse(success=False, message=f"Error executing tool: {str(e)}", data=None)
            
            def _validate_parameters(self, params):
                # Implement parameter validation
                return True
            
            def _perform_action(self, params):
                # Implement the tool's action
                return "Result of the action"
            ```
            
            ## Step 4: Define the Input Schema
            
            Define the input schema to specify what parameters your tool accepts:
            
            ```python
            def get_input_schema(self):
                return {
                    "type": "object",
                    "properties": {
                        "param1": {
                            "type": "string",
                            "description": "Description of parameter 1"
                        },
                        "param2": {
                            "type": "number",
                            "description": "Description of parameter 2"
                        }
                    },
                    "required": ["param1"]
                }
            ```
            
            ## Step 5: Define the Output Schema
            
            Define the output schema to specify what your tool returns:
            
            ```python
            def get_output_schema(self):
                return {
                    "type": "object",
                    "properties": {
                        "success": {
                            "type": "boolean",
                            "description": "Whether the tool execution was successful"
                        },
                        "message": {
                            "type": "string",
                            "description": "A message describing the result"
                        },
                        "data": {
                            "type": "string",
                            "description": "The result data"
                        }
                    }
                }
            ```
            
            ## Step 6: Register the Tool with Your MCP Server
            
            Finally, register your tool with your MCP server:
            
            ```python
            from modelcontextprotocol import Server
            from %s import %s
            
            def main():
                server = Server(name="my-server", version="1.0.0")
                
                # Register your tool
                server.register_tool(%s())
                
                # ... other server setup
            
            if __name__ == "__main__":
                main()
            ```
            
            ## Next Steps
            
            1. Implement the specific logic for your tool
            2. Add proper error handling
            3. Test your tool thoroughly
            4. Document your tool for users
            
            Congratulations! You've implemented the %s tool in Python.
            """.formatted(
                toolName, toolName, fileName, className, className,
                toolName, toolDescription, fileName.replace(".py", ""), className, className, toolName
            );
    }
}
