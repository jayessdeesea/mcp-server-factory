package user.jakecarr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import user.jakecarr.model.McpComponent;
import user.jakecarr.model.McpTool;
import user.jakecarr.model.impl.ExplainConceptTool;
import user.jakecarr.service.ToolService;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the MCP Server Factory.
 */
public class McpServerFactoryTest {
    
    /**
     * Tests the ExplainConceptTool.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testExplainConceptTool() {
        // Create the tool
        ExplainConceptTool tool = new ExplainConceptTool();
        
        // Test the tool metadata
        assertEquals("explain_concept", tool.getName());
        assertEquals("Provides a detailed explanation of an MCP concept", tool.getDescription());
        assertEquals(McpTool.ComponentType.TOOL, tool.getType());
        
        // Test the tool execution with a valid concept
        Map<String, Object> params = new HashMap<>();
        params.put("concept", "tool");
        
        McpTool.ToolResult result = tool.execute(params);
        
        assertTrue(result.isSuccess());
        assertEquals("Explanation retrieved successfully", result.getMessage());
        assertNotNull(result.getData());
        assertTrue(result.getData().toString().contains("MCP Tool"));
        
        // Test the tool execution with an invalid concept
        params.put("concept", "invalid_concept");
        
        result = tool.execute(params);
        
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Unknown concept"));
    }
    
    /**
     * Tests the ToolService.
     */
    @Test
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    public void testToolService() {
        // Create the service
        ToolService service = new ToolService();
        
        // Test getting a tool by name
        Object tool = service.getTool("explain_concept");
        
        assertNotNull(tool);
        assertTrue(tool instanceof ExplainConceptTool);
        
        // Test getting all tools
        Map<String, Object> tools = service.getAllTools();
        
        assertNotNull(tools);
        assertEquals(7, tools.size());
        assertTrue(tools.containsKey("explain_concept"));
        assertTrue(tools.containsKey("ping"));
        assertTrue(tools.containsKey("code_cleanup_planner"));
        assertTrue(tools.containsKey("feature_implementation_planner"));
        assertTrue(tools.containsKey("general_task_planner"));
        assertTrue(tools.containsKey("cleanup_task"));
        assertTrue(tools.containsKey("local_mcp_deployment_planner"));
    }
}
