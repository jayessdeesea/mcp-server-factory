package user.jakecarr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import user.jakecarr.model.McpTool;
import user.jakecarr.model.TaskPlannerTool;
import user.jakecarr.model.impl.CleanupTaskTool;
import user.jakecarr.model.impl.CodeCleanupPlannerTool;
import user.jakecarr.model.impl.LocalMcpDeploymentPlannerTool;
import user.jakecarr.model.impl.FeatureImplementationPlannerTool;
import user.jakecarr.model.impl.GeneralTaskPlannerTool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Task Planner Tools.
 */
public class TaskPlannerToolTest {
    
    /**
     * Tests the CodeCleanupPlannerTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCodeCleanupPlannerTool() {
        // Create the tool
        CodeCleanupPlannerTool tool = new CodeCleanupPlannerTool();
        
        // Test the tool metadata
        assertEquals("code_cleanup_planner", tool.getName());
        assertEquals("Generates a task plan for code cleanup objectives", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Clean up the codebase");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Clean up the codebase", data.get("objective"));
        assertNotNull(data.get("summary"));
        assertNotNull(data.get("steps"));
        assertTrue(data.get("steps") instanceof Object[]);
        
        Object[] steps = (Object[]) data.get("steps");
        assertTrue(steps.length > 0);
        
        // Verify the first step
        @SuppressWarnings("unchecked")
        Map<String, Object> firstStep = (Map<String, Object>) steps[0];
        
        assertEquals("Analyze the codebase", firstStep.get("description"));
        assertNotNull(firstStep.get("instruction"));
        assertNotNull(firstStep.get("metadata"));
    }
    
    /**
     * Tests the FeatureImplementationPlannerTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testFeatureImplementationPlannerTool() {
        // Create the tool
        FeatureImplementationPlannerTool tool = new FeatureImplementationPlannerTool();
        
        // Test the tool metadata
        assertEquals("feature_implementation_planner", tool.getName());
        assertEquals("Generates a task plan for feature implementation objectives", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Implement a new feature");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Implement a new feature", data.get("objective"));
        assertNotNull(data.get("summary"));
        assertNotNull(data.get("steps"));
        assertTrue(data.get("steps") instanceof Object[]);
        
        Object[] steps = (Object[]) data.get("steps");
        assertTrue(steps.length > 0);
        
        // Verify the first step
        @SuppressWarnings("unchecked")
        Map<String, Object> firstStep = (Map<String, Object>) steps[0];
        
        assertEquals("Analyze requirements", firstStep.get("description"));
        assertNotNull(firstStep.get("instruction"));
        assertNotNull(firstStep.get("metadata"));
    }
    
    /**
     * Tests the GeneralTaskPlannerTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testGeneralTaskPlannerTool() {
        // Create the tool
        GeneralTaskPlannerTool tool = new GeneralTaskPlannerTool();
        
        // Test the tool metadata
        assertEquals("general_task_planner", tool.getName());
        assertEquals("Generates a task plan for general objectives", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution with a general objective
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Improve the project");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Improve the project", data.get("objective"));
        assertNotNull(data.get("summary"));
        assertNotNull(data.get("steps"));
        assertTrue(data.get("steps") instanceof Object[]);
        
        Object[] steps = (Object[]) data.get("steps");
        assertTrue(steps.length > 0);
        
        // Test the tool execution with a code cleanup objective
        params.put("objective", "Clean up the code");
        
        result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        
        // Test the tool execution with a feature implementation objective
        params.put("objective", "Implement a feature");
        
        result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        
        // Test the tool execution with a bug fix objective
        params.put("objective", "Fix a bug");
        
        result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
    }
    
    /**
     * Tests the CleanupTaskTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testCleanupTaskTool() {
        // Create the tool
        CleanupTaskTool tool = new CleanupTaskTool();
        
        // Test the tool metadata
        assertEquals("cleanup_task", tool.getName());
        assertEquals("Generates a task plan for cleanup objectives", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Clean up the workspace");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Clean up the workspace", data.get("objective"));
        assertNotNull(data.get("summary"));
        assertNotNull(data.get("steps"));
        assertTrue(data.get("steps") instanceof Object[]);
        
        Object[] steps = (Object[]) data.get("steps");
        assertTrue(steps.length > 0);
        
        // Verify the first step
        @SuppressWarnings("unchecked")
        Map<String, Object> firstStep = (Map<String, Object>) steps[0];
        
        assertEquals("Analyze the current state", firstStep.get("description"));
        assertNotNull(firstStep.get("instruction"));
        assertNotNull(firstStep.get("metadata"));
    }
    
    /**
     * Tests the LocalMcpDeploymentPlannerTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testLocalMcpDeploymentPlannerTool() {
        // Create the tool
        LocalMcpDeploymentPlannerTool tool = new LocalMcpDeploymentPlannerTool();
        
        // Test the tool metadata
        assertEquals("local_mcp_deployment_planner", tool.getName());
        assertEquals("Generates a task plan for deploying local MCP servers", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution
        Map<String, Object> params = new HashMap<>();
        params.put("objective", "Deploy the application");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertTrue(result.getMessage().startsWith("Task plan generated successfully"), "Message should start with 'Task plan generated successfully'");
        assertNotNull(result.getData());
        
        // Verify the result structure
        @SuppressWarnings("unchecked")
        Map<String, Object> data = (Map<String, Object>) result.getData();
        
        assertEquals("Deploy the application", data.get("objective"));
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
    }
    
}
