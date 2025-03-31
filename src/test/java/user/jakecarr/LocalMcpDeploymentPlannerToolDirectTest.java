package user.jakecarr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import user.jakecarr.model.McpTool.ToolResult;
import user.jakecarr.model.impl.LocalMcpDeploymentPlannerTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the LocalMcpDeploymentPlannerTool using direct tool implementation.
 * This test demonstrates how to directly use the tool implementation to get the full task plan data.
 */
public class LocalMcpDeploymentPlannerToolDirectTest {

    /**
     * Tests the LocalMcpDeploymentPlannerTool by directly using the tool implementation.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testLocalMcpDeploymentPlannerToolDirect() {
        // Create the tool
        LocalMcpDeploymentPlannerTool tool = new LocalMcpDeploymentPlannerTool();
        
        // Test the tool metadata
        assertEquals("local_mcp_deployment_planner", tool.getName());
        assertEquals("Generates a task plan for deploying local MCP servers", tool.getDescription());
        
        // Create the parameters
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Deploy a weather MCP server");
        
        // Execute the tool
        ToolResult result = tool.execute(params);
        
        // Verify the result
        assertTrue(result.isSuccess());
        assertEquals("Task plan generated successfully", result.getMessage());
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Deploy a weather MCP server", data.get("objective"));
        assertNotNull(data.get("summary"));
        assertNotNull(data.get("steps"));
        assertTrue(data.get("steps") instanceof Object[]);
        
        Object[] steps = (Object[]) data.get("steps");
        assertTrue(steps.length > 0);
        
        // Verify the first step
        @SuppressWarnings("unchecked")
        Map<String, Object> firstStep = (Map<String, Object>) steps[0];
        
        assertEquals("Clean the project", firstStep.get("description"));
        assertNotNull(firstStep.get("instruction"));
        assertNotNull(firstStep.get("metadata"));
        
        // Print the detailed summary
        System.out.println("Task Plan Detailed Summary:");
        System.out.println(data.get("detailedSummary"));
        
        // Print all steps
        System.out.println("\nTask Plan Steps:");
        for (int i = 0; i < steps.length; i++) {
            @SuppressWarnings("unchecked")
            Map<String, Object> step = (Map<String, Object>) steps[i];
            System.out.println((i + 1) + ". " + step.get("description"));
        }
    }
}
