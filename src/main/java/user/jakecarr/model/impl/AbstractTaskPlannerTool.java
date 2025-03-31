package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Abstract base class for task planner tools.
 * Provides common functionality for all task planner tools.
 */
public abstract class AbstractTaskPlannerTool implements TaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(AbstractTaskPlannerTool.class.getName());
    
    /**
     * Gets the name of the task planner tool.
     * 
     * @return The tool name
     */
    @Override
    public abstract String getName();
    
    /**
     * Gets the description of the task planner tool.
     * 
     * @return The tool description
     */
    @Override
    public abstract String getDescription();
    
    /**
     * Executes the task planner tool with the specified parameters.
     * 
     * @param parameters The parameters for the tool execution
     * @return The result of the tool execution
     */
    /**
     * Executes the task planner tool with the specified parameters.
     * 
     * @param parameters The parameters for the tool execution
     * @return The result of the tool execution
     */
    @Override
    public ToolResult execute(Map<String, Object> parameters) {
        LOGGER.info("Executing task planner tool: " + getName() + " with parameters: " + parameters);
        
        // Validate the objective parameter
        String objective = validateObjectiveParameter(parameters);
        if (objective == null) {
            return new ToolResult(false, "Missing required parameter: objective", null);
        }
        
        // Get the context parameter (optional)
        @SuppressWarnings("unchecked")
        Map<String, Object> context = (Map<String, Object>) parameters.getOrDefault("context", new HashMap<>());
        
        try {
            // Analyze the objective and generate a task plan
            TaskPlan taskPlan = analyzeObjective(objective, context);
            
            // Convert the task plan to a result
            Map<String, Object> result = convertTaskPlanToResult(taskPlan);
            
            return new ToolResult(true, "Task plan generated successfully", result);
        } catch (Exception e) {
            LOGGER.warning("Failed to generate task plan: " + e.getMessage());
            return new ToolResult(false, "Failed to generate task plan: " + e.getMessage(), null);
        }
    }
    
    /**
     * Validates the objective parameter.
     * 
     * @param parameters The parameters to validate
     * @return The validated objective, or null if invalid
     */
    private String validateObjectiveParameter(final Map<String, Object> parameters) {
        final String objective = (String) parameters.get("objective");
        if (objective == null || objective.isEmpty()) {
            return null;
        }
        return objective;
    }
    
    /**
     * Converts a TaskPlan to a result map.
     * 
     * @param taskPlan The task plan to convert
     * @return The result map
     */
    private Map<String, Object> convertTaskPlanToResult(final TaskPlan taskPlan) {
        Map<String, Object> result = new HashMap<>();
        result.put("objective", taskPlan.getObjective());
        result.put("summary", taskPlan.getSummary());
        
        // Convert the task steps to maps
        result.put("steps", taskPlan.getSteps().stream()
            .map(this::convertTaskStepToMap)
            .toArray());
        
        // Create a detailed summary of the task plan
        result.put("detailedSummary", createDetailedSummary(taskPlan));
        
        return result;
    }
    
    /**
     * Converts a TaskStep to a map.
     * 
     * @param step The task step to convert
     * @return The map representation of the task step
     */
    private Map<String, Object> convertTaskStepToMap(final TaskStep step) {
        Map<String, Object> stepMap = new HashMap<>();
        stepMap.put("description", step.getDescription());
        stepMap.put("instruction", step.getInstruction());
        stepMap.put("metadata", step.getMetadata());
        return stepMap;
    }
    
    /**
     * Creates a detailed summary of the task plan.
     * 
     * @param taskPlan The task plan
     * @return The detailed summary
     */
    private String createDetailedSummary(final TaskPlan taskPlan) {
        StringBuilder detailedSummary = new StringBuilder();
        detailedSummary.append("Objective: ").append(taskPlan.getObjective()).append("\n\n");
        detailedSummary.append("Summary: ").append(taskPlan.getSummary()).append("\n\n");
        detailedSummary.append("Steps:\n");
        
        for (int i = 0; i < taskPlan.getSteps().size(); i++) {
            TaskStep step = taskPlan.getSteps().get(i);
            detailedSummary.append(i + 1).append(". ").append(step.getDescription()).append("\n");
        }
        
        return detailedSummary.toString();
    }
    
    /**
     * Gets the input schema for the task planner tool.
     * 
     * @return The input schema
     */
    @Override
    public Map<String, Object> getInputSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        
        Map<String, Object> objectiveProperty = new HashMap<>();
        objectiveProperty.put("type", "string");
        objectiveProperty.put("description", "The high-level objective to analyze");
        
        Map<String, Object> contextProperty = new HashMap<>();
        contextProperty.put("type", "object");
        contextProperty.put("description", "Additional context for the task planning");
        
        properties.put("objective", objectiveProperty);
        properties.put("context", contextProperty);
        
        schema.put("properties", properties);
        schema.put("required", Arrays.asList("objective"));
        
        return schema;
    }
    
    /**
     * Gets the output schema for the task planner tool.
     * 
     * @return The output schema
     */
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
        dataProperty.put("type", "object");
        dataProperty.put("description", "The generated task plan");
        
        Map<String, Object> dataProperties = new HashMap<>();
        
        Map<String, Object> objectiveProperty = new HashMap<>();
        objectiveProperty.put("type", "string");
        objectiveProperty.put("description", "The original high-level objective");
        
        Map<String, Object> summaryProperty = new HashMap<>();
        summaryProperty.put("type", "string");
        summaryProperty.put("description", "A summary of the task plan");
        
        Map<String, Object> stepsProperty = new HashMap<>();
        stepsProperty.put("type", "array");
        stepsProperty.put("description", "The actionable steps to accomplish the objective");
        
        Map<String, Object> stepItems = new HashMap<>();
        stepItems.put("type", "object");
        
        Map<String, Object> stepProperties = new HashMap<>();
        
        Map<String, Object> descriptionProperty = new HashMap<>();
        descriptionProperty.put("type", "string");
        descriptionProperty.put("description", "A short description of the step");
        
        Map<String, Object> instructionProperty = new HashMap<>();
        instructionProperty.put("type", "string");
        instructionProperty.put("description", "Detailed instructions for completing the step");
        
        Map<String, Object> metadataProperty = new HashMap<>();
        metadataProperty.put("type", "object");
        metadataProperty.put("description", "Additional metadata for the step");
        
        stepProperties.put("description", descriptionProperty);
        stepProperties.put("instruction", instructionProperty);
        stepProperties.put("metadata", metadataProperty);
        
        stepItems.put("properties", stepProperties);
        stepItems.put("required", Arrays.asList("description", "instruction", "metadata"));
        
        stepsProperty.put("items", stepItems);
        
        dataProperties.put("objective", objectiveProperty);
        dataProperties.put("summary", summaryProperty);
        dataProperties.put("steps", stepsProperty);
        
        dataProperty.put("properties", dataProperties);
        dataProperty.put("required", Arrays.asList("objective", "summary", "steps"));
        
        properties.put("success", successProperty);
        properties.put("message", messageProperty);
        properties.put("data", dataProperty);
        
        schema.put("properties", properties);
        schema.put("required", Arrays.asList("success", "message", "data"));
        
        return schema;
    }
    
    /**
     * Analyzes a high-level objective and generates a task plan.
     * This method must be implemented by subclasses.
     * 
     * @param objective The high-level objective to analyze
     * @param context Additional context for the task planning
     * @return A task plan with actionable steps
     */
    @Override
    public abstract TaskPlan analyzeObjective(String objective, Map<String, Object> context);
}
