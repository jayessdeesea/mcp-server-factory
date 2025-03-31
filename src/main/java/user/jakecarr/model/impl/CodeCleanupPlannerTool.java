package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool.TaskPlan;
import user.jakecarr.model.TaskPlannerTool.TaskStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tool that generates task plans for code cleanup objectives.
 * This tool analyzes a high-level code cleanup objective and generates
 * a detailed task plan with actionable steps to improve code quality.
 */
public class CodeCleanupPlannerTool extends AbstractTaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(CodeCleanupPlannerTool.class.getName());
    
    private static final String NAME = "code_cleanup_planner";
    private static final String DESCRIPTION = "Generates a task plan for code cleanup objectives";
    
    /**
     * Gets the name of the task planner tool.
     * 
     * @return The tool name
     */
    @Override
    public String getName() {
        return NAME;
    }
    
    /**
     * Gets the description of the task planner tool.
     * 
     * @return The tool description
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    /**
     * Analyzes a high-level code cleanup objective and generates a task plan.
     * 
     * @param objective The high-level objective to analyze
     * @param context Additional context for the task planning
     * @return A task plan with actionable steps
     */
    @Override
    public TaskPlan analyzeObjective(final String objective, final Map<String, Object> context) {
        LOGGER.info("Analyzing code cleanup objective: " + objective);
        
        // Create a list of task steps for code cleanup
        List<TaskStep> steps = createCodeCleanupSteps();
        
        // Create a summary of the task plan
        String summary = createSummary();
        
        return new TaskPlan(objective, steps, summary);
    }
    
    /**
     * Creates the list of task steps for code cleanup.
     * 
     * @return The list of task steps
     */
    private List<TaskStep> createCodeCleanupSteps() {
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Analyze the codebase
        steps.add(createAnalysisStep());
        
        // Step 2: Fix code style issues
        steps.add(createCodeStyleStep());
        
        // Step 3: Refactor duplicate code
        steps.add(createDuplicateCodeStep());
        
        // Step 4: Improve naming conventions
        steps.add(createNamingConventionsStep());
        
        // Step 5: Add missing documentation
        steps.add(createDocumentationStep());
        
        // Step 6: Fix code smells
        steps.add(createCodeSmellsStep());
        
        // Step 7: Optimize performance
        steps.add(createPerformanceStep());
        
        // Step 8: Verify changes
        steps.add(createVerificationStep());
        
        return steps;
    }
    
    /**
     * Creates a summary of the task plan.
     * 
     * @return The summary
     */
    private String createSummary() {
        return "This task plan provides a systematic approach to cleaning up the codebase. " +
                "It starts with analyzing the current state of the code, then addresses various aspects " +
                "of code quality including style, duplication, naming, documentation, code smells, and performance. " +
                "Finally, it includes a verification step to ensure that the changes don't break existing functionality.";
    }
    
    /**
     * Creates a task step with the specified parameters.
     * 
     * @param description The description of the step
     * @param instruction The instruction for the step
     * @param estimatedEffort The estimated effort for the step
     * @param priority The priority of the step
     * @param dependencies The dependencies of the step
     * @return The created task step
     */
    private TaskStep createTaskStep(final String description, final String instruction, 
                                   final String estimatedEffort, final String priority, 
                                   final List<String> dependencies) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", estimatedEffort);
        metadata.put("priority", priority);
        metadata.put("dependencies", dependencies);
        
        return new TaskStep(description, instruction, metadata);
    }
    
    /**
     * Creates the analysis step.
     * 
     * @return The analysis step
     */
    private TaskStep createAnalysisStep() {
        String description = "Analyze the codebase";
        String instruction = """
            Analyze the current state of the codebase to identify areas that need improvement:
            
            1. Use the `list_files` tool to get a list of all source files
            2. Use the `read_file` tool to examine key files
            3. Look for the following issues:
               - Inconsistent code style
               - Duplicate code
               - Poor naming conventions
               - Missing or inadequate documentation
               - Code smells (long methods, large classes, etc.)
               - Performance bottlenecks
            4. Create a list of specific issues to address
            
            Example command:
            ```
            list_files --path src/main/java --recursive true
            ```
            """;
        
        return createTaskStep(description, instruction, "Medium", "High", new ArrayList<>());
    }
    
    /**
     * Creates the code style step.
     * 
     * @return The code style step
     */
    private TaskStep createCodeStyleStep() {
        String description = "Fix code style issues";
        String instruction = """
            Address code style issues to ensure consistency throughout the codebase:
            
            1. Check for and fix the following code style issues:
               - Inconsistent indentation
               - Inconsistent brace placement
               - Inconsistent spacing
               - Line length exceeding limits
               - Inconsistent import ordering
               - Inconsistent comment style
            2. Use the `replace_in_file` tool to make the necessary changes
            3. Consider using a code formatter if available
            
            Example:
            ```
            replace_in_file --path src/main/java/example/File.java --search "public void method() {\\n    // Inconsistent indentation\\n  doSomething();\\n}" --replace "public void method() {\\n    // Fixed indentation\\n    doSomething();\\n}"
            ```
            """;
        
        return createTaskStep(description, instruction, "Medium", "Medium", List.of("Analyze the codebase"));
    }
    
    /**
     * Creates the duplicate code step.
     * 
     * @return The duplicate code step
     */
    private TaskStep createDuplicateCodeStep() {
        String description = "Refactor duplicate code";
        String instruction = """
            Identify and refactor duplicate code to improve maintainability:
            
            1. Look for code that appears in multiple places
            2. Extract common functionality into shared methods or classes
            3. Use the `replace_in_file` tool to refactor the code
            4. Ensure that the refactored code maintains the original behavior
            
            Example:
            ```
            // Before: Duplicate code in multiple files
            // File1.java
            public void processData(Data data) {
                data.validate();
                data.normalize();
                data.transform();
                // ...
            }
            
            // File2.java
            public void handleData(Data data) {
                data.validate();
                data.normalize();
                data.transform();
                // ...
            }
            
            // After: Extract common functionality
            // DataProcessor.java
            public void standardProcess(Data data) {
                data.validate();
                data.normalize();
                data.transform();
            }
            
            // File1.java
            public void processData(Data data) {
                dataProcessor.standardProcess(data);
                // ...
            }
            
            // File2.java
            public void handleData(Data data) {
                dataProcessor.standardProcess(data);
                // ...
            }
            ```
            """;
        
        return createTaskStep(description, instruction, "High", "High", List.of("Analyze the codebase"));
    }
    
    /**
     * Creates the naming conventions step.
     * 
     * @return The naming conventions step
     */
    private TaskStep createNamingConventionsStep() {
        String description = "Improve naming conventions";
        String instruction = """
            Improve naming conventions to enhance code readability:
            
            1. Identify variables, methods, and classes with unclear or inconsistent names
            2. Rename them to follow consistent naming conventions:
               - Classes: PascalCase (e.g., DataProcessor)
               - Methods and variables: camelCase (e.g., processData)
               - Constants: UPPER_SNAKE_CASE (e.g., MAX_RETRY_COUNT)
            3. Use descriptive names that indicate purpose
            4. Use the `replace_in_file` tool to make the changes
            5. Ensure that all references are updated
            
            Example:
            ```
            // Before: Poor naming
            public void proc(Obj o) {
                int x = o.calc();
                if (x > 10) {
                    o.upd(x);
                }
            }
            
            // After: Improved naming
            public void processObject(DataObject dataObject) {
                int calculatedValue = dataObject.calculateValue();
                if (calculatedValue > MAX_THRESHOLD) {
                    dataObject.updateValue(calculatedValue);
                }
            }
            ```
            """;
        
        return createTaskStep(description, instruction, "Medium", "Medium", List.of("Analyze the codebase"));
    }
    
    /**
     * Creates the documentation step.
     * 
     * @return The documentation step
     */
    private TaskStep createDocumentationStep() {
        String description = "Add missing documentation";
        String instruction = """
            Add or improve documentation to make the code more maintainable:
            
            1. Add Javadoc comments to classes, methods, and fields
            2. Include the following in method documentation:
               - Purpose of the method
               - Parameters and their meaning
               - Return value and its meaning
               - Exceptions that may be thrown
            3. Add inline comments for complex logic
            4. Update or create README files for important components
            5. Use the `replace_in_file` tool to add documentation
            
            Example:
            ```
            // Before: No documentation
            public int calculate(int a, int b) {
                if (b == 0) {
                    throw new IllegalArgumentException();
                }
                return a / b;
            }
            
            // After: Added documentation
            /**
             * Divides two integers and returns the result.
             *
             * @param a The dividend
             * @param b The divisor
             * @return The result of dividing a by b
             * @throws IllegalArgumentException If b is zero
             */
            public int calculate(int a, int b) {
                if (b == 0) {
                    throw new IllegalArgumentException("Divisor cannot be zero");
                }
                return a / b;
            }
            ```
            """;
        
        return createTaskStep(description, instruction, "High", "Medium", List.of("Analyze the codebase"));
    }
    
    /**
     * Creates the code smells step.
     * 
     * @return The code smells step
     */
    private TaskStep createCodeSmellsStep() {
        String description = "Fix code smells";
        String instruction = """
            Identify and fix code smells to improve code quality:
            
            1. Look for the following code smells:
               - Long methods (more than 30 lines)
               - Large classes (more than 300 lines)
               - Deep nesting (more than 3 levels)
               - High cyclomatic complexity
               - God classes (classes that do too much)
               - Feature envy (methods that use more features of other classes than their own)
            2. Refactor the code to address these issues:
               - Extract methods to break down long methods
               - Extract classes to break down large classes
               - Simplify nested conditions
               - Use polymorphism instead of switch statements
            3. Use the `replace_in_file` tool to make the changes
            
            Example:
            ```
            // Before: Long method with deep nesting
            public void processOrder(Order order) {
                if (order.isValid()) {
                    if (order.hasItems()) {
                        for (Item item : order.getItems()) {
                            if (item.isInStock()) {
                                // Process item
                                // ...
                            } else {
                                // Handle out of stock
                                // ...
                            }
                        }
                    } else {
                        // Handle empty order
                        // ...
                    }
                } else {
                    // Handle invalid order
                    // ...
                }
            }
            
            // After: Refactored with extracted methods
            public void processOrder(Order order) {
                if (!order.isValid()) {
                    handleInvalidOrder(order);
                    return;
                }
                
                if (!order.hasItems()) {
                    handleEmptyOrder(order);
                    return;
                }
                
                processOrderItems(order);
            }
            
            private void processOrderItems(Order order) {
                for (Item item : order.getItems()) {
                    if (item.isInStock()) {
                        processInStockItem(item);
                    } else {
                        handleOutOfStockItem(item);
                    }
                }
            }
            
            private void processInStockItem(Item item) {
                // Process item
                // ...
            }
            
            private void handleOutOfStockItem(Item item) {
                // Handle out of stock
                // ...
            }
            
            private void handleEmptyOrder(Order order) {
                // Handle empty order
                // ...
            }
            
            private void handleInvalidOrder(Order order) {
                // Handle invalid order
                // ...
            }
            ```
            """;
        
        return createTaskStep(description, instruction, "High", "High", List.of("Analyze the codebase", "Refactor duplicate code"));
    }
    
    /**
     * Creates the performance step.
     * 
     * @return The performance step
     */
    private TaskStep createPerformanceStep() {
        String description = "Optimize performance";
        String instruction = """
            Identify and address performance issues in the code:
            
            1. Look for the following performance issues:
               - Inefficient algorithms
               - Unnecessary object creation
               - Excessive database queries
               - Unoptimized loops
               - Memory leaks
            2. Optimize the code to address these issues:
               - Use more efficient algorithms
               - Reduce object creation
               - Batch database operations
               - Optimize loops
               - Fix memory leaks
            3. Use the `replace_in_file` tool to make the changes
            
            Example:
            ```
            // Before: Inefficient string concatenation in a loop
            String result = "";
            for (int i = 0; i < 1000; i++) {
                result += data[i];
            }
            
            // After: Optimized with StringBuilder
            StringBuilder resultBuilder = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                resultBuilder.append(data[i]);
            }
            String result = resultBuilder.toString();
            ```
            """;
        
        return createTaskStep(description, instruction, "Medium", "Medium", List.of("Analyze the codebase", "Fix code smells"));
    }
    
    /**
     * Creates the verification step.
     * 
     * @return The verification step
     */
    private TaskStep createVerificationStep() {
        String description = "Verify changes";
        String instruction = """
            Verify that the changes don't break existing functionality:
            
            1. Run unit tests if available
            2. Manually test key functionality
            3. Check for compilation errors
            4. Review the changes for unintended side effects
            5. Fix any issues found during verification
            
            Example command:
            ```
            execute_command --command "mvn test" --requires_approval false
            ```
            """;
        
        return createTaskStep(description, instruction, "Medium", "High", List.of(
            "Fix code style issues",
            "Refactor duplicate code",
            "Improve naming conventions",
            "Add missing documentation",
            "Fix code smells",
            "Optimize performance"
        ));
    }
}
