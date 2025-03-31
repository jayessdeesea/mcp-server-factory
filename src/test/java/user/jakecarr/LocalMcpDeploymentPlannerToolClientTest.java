package user.jakecarr;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.ServerParameters;
import io.modelcontextprotocol.client.transport.StdioClientTransport;
import io.modelcontextprotocol.spec.McpSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the LocalMcpDeploymentPlannerTool using an MCP client to query the server.
 * This test demonstrates how to create an MCP client that connects to the server
 * and calls the local_mcp_deployment_planner tool.
 */
public class LocalMcpDeploymentPlannerToolClientTest {

    /**
     * Tests the LocalMcpDeploymentPlannerTool by creating an MCP client that connects to the server
     * and calls the tool.
     */
    @Test
    @Timeout(value = 10, unit = TimeUnit.SECONDS)
    public void testLocalMcpDeploymentPlannerToolWithClient() {
        // Create server parameters for the MCP server
        ServerParameters serverParams = ServerParameters.builder("java")
                .args("-jar", "target/mcp-server-factory-1.0-SNAPSHOT-jar-with-dependencies.jar")
                .build();

        // Create the client transport
        StdioClientTransport transport = new StdioClientTransport(serverParams);

        // Create the MCP client
        try (McpSyncClient client = McpClient.sync(transport).build()) {
            // Initialize the client
            McpSchema.InitializeResult initResult = client.initialize();
            
            // Verify the server capabilities
            assertNotNull(initResult.capabilities());
            assertNotNull(initResult.capabilities().tools(), "Tools capability should be enabled");
            
            // Verify the server info
            assertEquals("mcp-server-factory", initResult.serverInfo().name());
            
            // List available tools
            McpSchema.ListToolsResult toolsResult = client.listTools();
            assertNotNull(toolsResult.tools());
            
            // Find the local_mcp_deployment_planner tool
            boolean foundTool = false;
            for (McpSchema.Tool tool : toolsResult.tools()) {
                if ("local_mcp_deployment_planner".equals(tool.name())) {
                    foundTool = true;
                    break;
                }
            }
            assertTrue(foundTool, "local_mcp_deployment_planner tool not found");
            
            // Create the tool request
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("objective", "Deploy a weather MCP server");
            
            McpSchema.CallToolRequest toolRequest = new McpSchema.CallToolRequest(
                    "local_mcp_deployment_planner", 
                    arguments
            );
            
            // Call the tool
            McpSchema.CallToolResult toolResult = client.callTool(toolRequest);
            
            // Verify the result
            assertNotNull(toolResult);
            assertFalse(toolResult.isError());
            assertNotNull(toolResult.content());
            assertFalse(toolResult.content().isEmpty());
            
            // Verify the content
            McpSchema.Content content = toolResult.content().get(0);
            assertEquals("text", content.type());
            
            // Cast to TextContent and get the text
            McpSchema.TextContent textContent = (McpSchema.TextContent) content;
            assertNotNull(textContent.text());
            
            // Parse the JSON result
            String resultText = textContent.text();
            
            // Verify the result contains the expected text
            assertTrue(resultText.startsWith("Task plan generated successfully"), 
                    "Response should start with 'Task plan generated successfully'");
            
            // Verify the result contains the full task plan data
            assertTrue(resultText.contains("\"objective\":\"Deploy a weather MCP server\""), 
                    "Response should contain the objective");
            assertTrue(resultText.contains("\"summary\":"), 
                    "Response should contain the summary");
            assertTrue(resultText.contains("\"steps\":"), 
                    "Response should contain the steps");
            
            // Verify the result contains the expected steps
            assertTrue(resultText.contains("\"description\":\"Clean the project\""), 
                    "Response should contain the 'Clean the project' step");
            assertTrue(resultText.contains("\"description\":\"Build the project\""), 
                    "Response should contain the 'Build the project' step");
            assertTrue(resultText.contains("\"description\":\"Run tests\""), 
                    "Response should contain the 'Run tests' step");
            assertTrue(resultText.contains("\"description\":\"Package the project\""), 
                    "Response should contain the 'Package the project' step");
            assertTrue(resultText.contains("\"description\":\"Deploy the MCP server\""), 
                    "Response should contain the 'Deploy the MCP server' step");
            assertTrue(resultText.contains("\"description\":\"Verify the deployment\""), 
                    "Response should contain the 'Verify the deployment' step");
            
            // Print the first 100 characters of the response for debugging
            System.out.println("Response starts with: " + 
                    resultText.substring(0, Math.min(100, resultText.length())));
        }
    }
}
