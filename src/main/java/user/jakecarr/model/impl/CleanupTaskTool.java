package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool.TaskPlan;
import user.jakecarr.model.TaskPlannerTool.TaskStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tool that generates task plans for cleanup objectives.
 * This tool is specifically designed for cleanup tasks requested by users.
 */
public class CleanupTaskTool extends AbstractTaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(CleanupTaskTool.class.getName());
    
    private static final String NAME = "cleanup_task";
    private static final String DESCRIPTION = "Generates a task plan for cleanup objectives";
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    @Override
    public TaskPlan analyzeObjective(String objective, Map<String, Object> context) {
        LOGGER.info("Analyzing cleanup objective: " + objective);
        
        // Create a list of task steps for cleanup
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Analyze the current state
        steps.add(createAnalysisStep());
        
        // Step 2: Identify items to clean up
        steps.add(createIdentificationStep());
        
        // Step 3: Prioritize cleanup tasks
        steps.add(createPrioritizationStep());
        
        // Step 4: Execute cleanup tasks
        steps.add(createExecutionStep());
        
        // Step 5: Verify cleanup
        steps.add(createVerificationStep());
        
        // Create a summary of the task plan
        String summary = "This task plan provides a systematic approach to cleaning up. " +
                "It starts with analyzing the current state, then identifies items to clean up, " +
                "prioritizes the cleanup tasks, executes them, and finally verifies that the cleanup was successful.";
        
        return new TaskPlan(objective, steps, summary);
    }
    
    private TaskStep createAnalysisStep() {
        String description = "Analyze the current state";
        String instruction = """
            Analyze the current state to understand what needs to be cleaned up:
            
            1. Take inventory of the current state
            2. Identify areas that need attention
            3. Document the current state for reference
            4. Determine the scope of the cleanup
            
            This step is crucial for understanding the extent of the cleanup needed and establishing a baseline.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", new ArrayList<>());
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createIdentificationStep() {
        String description = "Identify items to clean up";
        String instruction = """
            Identify specific items that need to be cleaned up:
            
            1. Create a list of items that need attention
            2. Categorize items by type or area
            3. Note any special requirements for each item
            4. Identify any dependencies between items
            
            Be thorough in your identification to ensure nothing is missed during the cleanup process.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Analyze the current state"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createPrioritizationStep() {
        String description = "Prioritize cleanup tasks";
        String instruction = """
            Prioritize the cleanup tasks to ensure efficient execution:
            
            1. Assess the importance of each item
            2. Consider dependencies between items
            3. Evaluate the effort required for each item
            4. Create a prioritized list of cleanup tasks
            
            Prioritization helps focus efforts on the most important items first and ensures that dependencies are respected.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Low");
        metadata.put("priority", "Medium");
        metadata.put("dependencies", List.of("Identify items to clean up"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createExecutionStep() {
        String description = "Execute cleanup tasks";
        String instruction = """
            Execute the cleanup tasks according to the prioritized list:
            
            1. Follow the prioritized list of tasks
            2. Address each item thoroughly
            3. Document the actions taken for each item
            4. Adjust the plan as needed based on findings during execution
            
            Be methodical in your execution to ensure that all items are properly addressed.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "High");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Prioritize cleanup tasks"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createVerificationStep() {
        String description = "Verify cleanup";
        String instruction = """
            Verify that the cleanup was successful:
            
            1. Review each item to ensure it was properly addressed
            2. Compare the current state to the desired state
            3. Address any remaining issues
            4. Document the final state for reference
            
            Verification ensures that the cleanup was thorough and effective.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Execute cleanup tasks"));
        
        return new TaskStep(description, instruction, metadata);
    }
}
