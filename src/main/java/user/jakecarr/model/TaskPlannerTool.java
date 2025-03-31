package user.jakecarr.model;

import java.util.List;
import java.util.Map;

/**
 * Interface for MCP task planner tools.
 * Task planner tools analyze high-level objectives and generate actionable plans.
 */
public interface TaskPlannerTool extends McpTool {
    
    /**
     * Analyzes a high-level objective and generates a task plan.
     * 
     * @param objective The high-level objective to analyze
     * @param context Additional context for the task planning
     * @return A task plan with actionable steps
     */
    TaskPlan analyzeObjective(String objective, Map<String, Object> context);
    
    /**
     * Class representing a task plan with actionable steps.
     */
    class TaskPlan {
        private final String objective;
        private final List<TaskStep> steps;
        private final String summary;
        
        /**
         * Creates a new TaskPlan.
         * 
         * @param objective The original high-level objective
         * @param steps The actionable steps to accomplish the objective
         * @param summary A summary of the task plan
         */
        public TaskPlan(String objective, List<TaskStep> steps, String summary) {
            this.objective = objective;
            this.steps = steps;
            this.summary = summary;
        }
        
        /**
         * Gets the original high-level objective.
         * 
         * @return The objective
         */
        public String getObjective() {
            return objective;
        }
        
        /**
         * Gets the actionable steps to accomplish the objective.
         * 
         * @return The steps
         */
        public List<TaskStep> getSteps() {
            return steps;
        }
        
        /**
         * Gets a summary of the task plan.
         * 
         * @return The summary
         */
        public String getSummary() {
            return summary;
        }
    }
    
    /**
     * Class representing a single step in a task plan.
     */
    class TaskStep {
        private final String description;
        private final String instruction;
        private final Map<String, Object> metadata;
        
        /**
         * Creates a new TaskStep.
         * 
         * @param description A short description of the step
         * @param instruction Detailed instructions for completing the step
         * @param metadata Additional metadata for the step
         */
        public TaskStep(String description, String instruction, Map<String, Object> metadata) {
            this.description = description;
            this.instruction = instruction;
            this.metadata = metadata;
        }
        
        /**
         * Gets a short description of the step.
         * 
         * @return The description
         */
        public String getDescription() {
            return description;
        }
        
        /**
         * Gets detailed instructions for completing the step.
         * 
         * @return The instruction
         */
        public String getInstruction() {
            return instruction;
        }
        
        /**
         * Gets additional metadata for the step.
         * 
         * @return The metadata
         */
        public Map<String, Object> getMetadata() {
            return metadata;
        }
    }
}
