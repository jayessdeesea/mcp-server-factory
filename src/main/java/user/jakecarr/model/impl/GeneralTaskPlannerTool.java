package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool.TaskPlan;
import user.jakecarr.model.TaskPlannerTool.TaskStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * Tool that generates task plans for general objectives.
 * This tool analyzes the objective and generates a generic task plan.
 */
public class GeneralTaskPlannerTool extends AbstractTaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(GeneralTaskPlannerTool.class.getName());
    
    private static final String NAME = "general_task_planner";
    private static final String DESCRIPTION = "Generates a task plan for general objectives";
    
    // Patterns for identifying different types of objectives
    private static final Pattern CODE_CLEANUP_PATTERN = Pattern.compile(
            "(?i).*(clean|refactor|improve|optimize|fix).*code.*");
    private static final Pattern FEATURE_IMPLEMENTATION_PATTERN = Pattern.compile(
            "(?i).*(implement|add|create|develop).*feature.*");
    private static final Pattern BUG_FIX_PATTERN = Pattern.compile(
            "(?i).*(fix|resolve|debug|troubleshoot).*bug.*");
    
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
        LOGGER.info("Analyzing general objective: " + objective);
        
        // Try to categorize the objective
        if (CODE_CLEANUP_PATTERN.matcher(objective).matches()) {
            LOGGER.info("Categorized as code cleanup objective");
            return new CodeCleanupPlannerTool().analyzeObjective(objective, context);
        } else if (FEATURE_IMPLEMENTATION_PATTERN.matcher(objective).matches()) {
            LOGGER.info("Categorized as feature implementation objective");
            return new FeatureImplementationPlannerTool().analyzeObjective(objective, context);
        } else if (BUG_FIX_PATTERN.matcher(objective).matches()) {
            LOGGER.info("Categorized as bug fix objective");
            return createBugFixPlan(objective);
        } else {
            LOGGER.info("Using generic task plan");
            return createGenericTaskPlan(objective);
        }
    }
    
    /**
     * Creates a task plan for bug fixing.
     * 
     * @param objective The bug fix objective
     * @return A task plan for bug fixing
     */
    private TaskPlan createBugFixPlan(String objective) {
        // Create a list of task steps for bug fixing
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Reproduce the bug
        steps.add(createReproduceStep());
        
        // Step 2: Analyze the bug
        steps.add(createAnalyzeStep());
        
        // Step 3: Fix the bug
        steps.add(createFixStep());
        
        // Step 4: Test the fix
        steps.add(createTestStep());
        
        // Step 5: Document the fix
        steps.add(createDocumentStep());
        
        // Create a summary of the task plan
        String summary = "This task plan provides a systematic approach to fixing a bug. " +
                "It starts with reproducing and analyzing the bug, then moves on to implementing a fix, " +
                "testing the fix, and documenting the changes.";
        
        return new TaskPlan(objective, steps, summary);
    }
    
    /**
     * Creates a generic task plan for objectives that don't fit into specific categories.
     * 
     * @param objective The general objective
     * @return A generic task plan
     */
    private TaskPlan createGenericTaskPlan(String objective) {
        // Create a list of task steps for the generic task
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Analyze the objective
        steps.add(createAnalyzeObjectiveStep());
        
        // Step 2: Research and gather information
        steps.add(createResearchStep());
        
        // Step 3: Plan the approach
        steps.add(createPlanStep());
        
        // Step 4: Implement the solution
        steps.add(createImplementStep());
        
        // Step 5: Test and verify
        steps.add(createVerifyStep());
        
        // Step 6: Document the changes
        steps.add(createDocumentChangesStep());
        
        // Create a summary of the task plan
        String summary = "This task plan provides a general approach to accomplishing the objective. " +
                "It starts with analyzing the objective and gathering information, then moves on to planning, " +
                "implementation, testing, and documentation.";
        
        return new TaskPlan(objective, steps, summary);
    }
    
    private TaskStep createReproduceStep() {
        String description = "Reproduce the bug";
        String instruction = """
            Reproduce the bug to understand its behavior:
            
            1. Identify the steps to reproduce the bug
            2. Document the expected behavior
            3. Document the actual behavior
            4. Identify any error messages or logs
            5. Determine the conditions under which the bug occurs
            
            Example approach:
            1. Use the `execute_command` tool to run the application
            2. Follow the steps that trigger the bug
            3. Capture any error messages or logs
            4. Document the exact steps to reproduce the bug
            
            Example:
            ```
            execute_command --command "mvn exec:java -Dexec.mainClass=user.jakecarr.main.McpServerFactoryMain" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", new ArrayList<>());
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createAnalyzeStep() {
        String description = "Analyze the bug";
        String instruction = """
            Analyze the bug to identify its root cause:
            
            1. Examine the code related to the bug
            2. Use debugging techniques to trace the execution
            3. Identify the root cause of the bug
            4. Understand the impact of the bug
            5. Consider potential fixes
            
            Example approach:
            1. Use the `read_file` tool to examine relevant files
            2. Use the `search_files` tool to find related code
            3. Add debug logging if needed
            4. Trace the execution path
            5. Identify the specific line or condition causing the bug
            
            Example:
            ```
            read_file --path src/main/java/user/jakecarr/service/McpServerService.java
            
            search_files --path src/main/java --regex "registerTools" --file_pattern "*.java"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "High");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Reproduce the bug"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createFixStep() {
        String description = "Fix the bug";
        String instruction = """
            Implement a fix for the bug:
            
            1. Develop a solution based on the root cause analysis
            2. Make the necessary code changes
            3. Ensure the fix addresses the root cause, not just the symptoms
            4. Consider any potential side effects
            5. Use the `replace_in_file` tool to modify the code
            
            Example approach:
            1. Identify the specific changes needed
            2. Make minimal changes to fix the bug
            3. Ensure the fix follows coding standards
            4. Add comments explaining the fix
            
            Example:
            ```
            replace_in_file --path src/main/java/user/jakecarr/service/McpServerService.java --search "// Register tools\\ntoolService.registerTools();" --replace "// Register tools\\ntry {\\n    toolService.registerTools();\\n} catch (Exception e) {\\n    LOGGER.log(Level.SEVERE, \"Failed to register tools\", e);\\n}"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Analyze the bug"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createTestStep() {
        String description = "Test the fix";
        String instruction = """
            Test the fix to ensure it resolves the bug:
            
            1. Verify that the bug is fixed
            2. Run regression tests to ensure no new bugs were introduced
            3. Test edge cases and boundary conditions
            4. Verify that the application works as expected
            5. Use the `execute_command` tool to run tests
            
            Example approach:
            1. Run unit tests if available
            2. Manually test the specific scenario that triggered the bug
            3. Test related functionality to ensure no regressions
            
            Example:
            ```
            execute_command --command "mvn test" --requires_approval false
            
            execute_command --command "mvn exec:java -Dexec.mainClass=user.jakecarr.main.McpServerFactoryMain" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Fix the bug"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createDocumentStep() {
        String description = "Document the fix";
        String instruction = """
            Document the bug fix:
            
            1. Update comments in the code to explain the fix
            2. Update any relevant documentation
            3. Document the root cause and solution
            4. Document any lessons learned
            5. Use the `replace_in_file` tool to update documentation
            
            Example approach:
            1. Add comments explaining the bug and fix
            2. Update README or other documentation if needed
            3. Document any configuration changes
            
            Example:
            ```
            replace_in_file --path src/main/java/user/jakecarr/service/McpServerService.java --search "try {\\n    toolService.registerTools();\\n} catch (Exception e) {\\n    LOGGER.log(Level.SEVERE, \"Failed to register tools\", e);\\n}" --replace "// Fix for bug #123: Added exception handling to prevent crashes when tools fail to register\\ntry {\\n    toolService.registerTools();\\n} catch (Exception e) {\\n    LOGGER.log(Level.SEVERE, \"Failed to register tools\", e);\\n}"
            
            replace_in_file --path README.md --search "## Known Issues\\n\\n" --replace "## Known Issues\\n\\n- Fixed: Application crash when registering tools\\n\\n"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Low");
        metadata.put("priority", "Medium");
        metadata.put("dependencies", List.of("Test the fix"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createAnalyzeObjectiveStep() {
        String description = "Analyze the objective";
        String instruction = """
            Analyze the objective to understand what needs to be accomplished:
            
            1. Break down the objective into smaller, manageable tasks
            2. Identify the key requirements and constraints
            3. Determine what resources and information are needed
            4. Identify any dependencies or prerequisites
            5. Define success criteria for the objective
            
            Example approach:
            1. Clearly state the objective in your own words
            2. List the key components or aspects of the objective
            3. Identify any ambiguities or uncertainties
            4. Determine what information you need to gather
            
            Example questions to answer:
            - What is the main goal of this objective?
            - What are the key components or tasks involved?
            - What resources or information do I need?
            - What are the constraints or limitations?
            - How will I know when the objective is successfully completed?
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", new ArrayList<>());
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createResearchStep() {
        String description = "Research and gather information";
        String instruction = """
            Research and gather information needed to accomplish the objective:
            
            1. Identify the information needed
            2. Use appropriate tools to gather information
            3. Analyze the codebase or documentation
            4. Research best practices or solutions
            5. Organize the gathered information
            
            Example approach:
            1. Use the `list_files` tool to explore the project structure
            2. Use the `read_file` tool to examine key files
            3. Use the `search_files` tool to find relevant code
            4. Use the `execute_command` tool to gather system information
            
            Example:
            ```
            list_files --path src/main/java --recursive true
            
            read_file --path README.md
            
            search_files --path src/main/java --regex "interface.*McpComponent" --file_pattern "*.java"
            
            execute_command --command "mvn dependency:tree" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Analyze the objective"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createPlanStep() {
        String description = "Plan the approach";
        String instruction = """
            Plan the approach to accomplish the objective:
            
            1. Define the steps needed to accomplish the objective
            2. Identify the tools and resources required
            3. Determine the order of steps
            4. Identify potential challenges or risks
            5. Create a timeline or estimate for each step
            
            Example approach:
            1. Break down the objective into specific tasks
            2. Determine the dependencies between tasks
            3. Identify the tools needed for each task
            4. Consider alternative approaches
            5. Choose the most appropriate approach
            
            Example planning questions:
            - What are the specific steps needed to accomplish this objective?
            - What is the most efficient order for these steps?
            - What tools or resources are needed for each step?
            - What are the potential challenges or risks?
            - How can I mitigate these risks?
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Research and gather information"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createImplementStep() {
        String description = "Implement the solution";
        String instruction = """
            Implement the solution to accomplish the objective:
            
            1. Follow the planned approach
            2. Create or modify files as needed
            3. Execute commands as needed
            4. Adapt the plan as necessary based on new information
            5. Track progress and address any issues that arise
            
            Example approach:
            1. Use the `write_to_file` tool to create new files
            2. Use the `replace_in_file` tool to modify existing files
            3. Use the `execute_command` tool to run commands
            4. Check the results of each step before proceeding
            
            Example:
            ```
            write_to_file --path src/main/java/user/jakecarr/model/NewComponent.java --content "package user.jakecarr.model;\\n\\npublic class NewComponent implements McpComponent {\\n    // Implementation\\n}"
            
            replace_in_file --path src/main/java/user/jakecarr/service/ComponentService.java --search "// Register components" --replace "// Register components\\nregisterComponent(new NewComponent());"
            
            execute_command --command "mvn compile" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "High");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Plan the approach"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createVerifyStep() {
        String description = "Test and verify";
        String instruction = """
            Test and verify that the objective has been accomplished:
            
            1. Test the implemented solution
            2. Verify that it meets the requirements
            3. Check for any issues or bugs
            4. Ensure that the solution works as expected
            5. Address any issues found during testing
            
            Example approach:
            1. Run unit tests if available
            2. Manually test the functionality
            3. Verify against the success criteria
            4. Check for any regressions or side effects
            
            Example:
            ```
            execute_command --command "mvn test" --requires_approval false
            
            execute_command --command "mvn exec:java -Dexec.mainClass=user.jakecarr.main.McpServerFactoryMain" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Implement the solution"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createDocumentChangesStep() {
        String description = "Document the changes";
        String instruction = """
            Document the changes made to accomplish the objective:
            
            1. Update comments in the code
            2. Update README or other documentation
            3. Document any configuration changes
            4. Document any known issues or limitations
            5. Provide examples or usage instructions if applicable
            
            Example approach:
            1. Add Javadoc comments to new or modified classes and methods
            2. Update README.md with information about the changes
            3. Create or update other documentation as needed
            
            Example:
            ```
            replace_in_file --path src/main/java/user/jakecarr/model/NewComponent.java --search "public class NewComponent implements McpComponent {" --replace "/**\\n * A component that does something useful.\\n * <p>\\n * This component provides functionality for...\\n */\\npublic class NewComponent implements McpComponent {"
            
            replace_in_file --path README.md --search "## Components\\n\\n" --replace "## Components\\n\\n### NewComponent\\n\\nThis component provides... Usage example:\\n\\n```java\\nNewComponent component = new NewComponent();\\ncomponent.doSomething();\\n```\\n\\n"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "Medium");
        metadata.put("dependencies", List.of("Test and verify"));
        
        return new TaskStep(description, instruction, metadata);
    }
}
