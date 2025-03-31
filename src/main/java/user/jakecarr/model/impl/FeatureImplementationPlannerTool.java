package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool.TaskPlan;
import user.jakecarr.model.TaskPlannerTool.TaskStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tool that generates task plans for feature implementation objectives.
 */
public class FeatureImplementationPlannerTool extends AbstractTaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(FeatureImplementationPlannerTool.class.getName());
    
    private static final String NAME = "feature_implementation_planner";
    private static final String DESCRIPTION = "Generates a task plan for feature implementation objectives";
    
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
        LOGGER.info("Analyzing feature implementation objective: " + objective);
        
        // Create a list of task steps for feature implementation
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Analyze requirements
        steps.add(createRequirementsStep());
        
        // Step 2: Design the feature
        steps.add(createDesignStep());
        
        // Step 3: Implement the feature
        steps.add(createImplementationStep());
        
        // Step 4: Write tests
        steps.add(createTestingStep());
        
        // Step 5: Integrate with existing code
        steps.add(createIntegrationStep());
        
        // Step 6: Document the feature
        steps.add(createDocumentationStep());
        
        // Step 7: Review and refine
        steps.add(createReviewStep());
        
        // Create a summary of the task plan
        String summary = "This task plan provides a systematic approach to implementing a new feature. " +
                "It starts with analyzing requirements and designing the feature, then moves on to implementation, " +
                "testing, integration, and documentation. Finally, it includes a review step to ensure the feature " +
                "meets the requirements and follows best practices.";
        
        return new TaskPlan(objective, steps, summary);
    }
    
    private TaskStep createRequirementsStep() {
        String description = "Analyze requirements";
        String instruction = """
            Analyze the requirements for the feature:
            
            1. Identify the core functionality of the feature
            2. Determine the inputs and outputs
            3. Identify any constraints or limitations
            4. Determine how the feature should integrate with existing code
            5. Identify any dependencies on other features or components
            
            Example questions to answer:
            - What problem does this feature solve?
            - Who will use this feature?
            - What inputs does the feature need?
            - What outputs should the feature produce?
            - Are there any performance requirements?
            - Are there any security considerations?
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", new ArrayList<>());
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createDesignStep() {
        String description = "Design the feature";
        String instruction = """
            Design the feature based on the requirements:
            
            1. Identify the classes and methods needed
            2. Design the data structures
            3. Define the interfaces
            4. Create sequence diagrams or flowcharts if helpful
            5. Consider design patterns that might be applicable
            
            Example design considerations:
            - How will the feature be structured?
            - What design patterns might be useful?
            - How will the feature interact with existing code?
            - What are the key components and their responsibilities?
            - How will the data flow through the system?
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Analyze requirements"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createImplementationStep() {
        String description = "Implement the feature";
        String instruction = """
            Implement the feature according to the design:
            
            1. Create the necessary classes and methods
            2. Implement the core functionality
            3. Handle edge cases and error conditions
            4. Follow coding standards and best practices
            5. Use the `write_to_file` tool to create new files
            6. Use the `replace_in_file` tool to modify existing files
            
            Example implementation approach:
            1. Start with interfaces or abstract classes
            2. Implement concrete classes
            3. Implement unit tests alongside the code
            4. Use test-driven development if appropriate
            5. Commit changes incrementally
            
            Example:
            ```
            // Create a new file for the feature
            write_to_file --path src/main/java/example/Feature.java --content "package example;\\n\\npublic class Feature {\\n    // Implementation\\n}"
            
            // Modify an existing file to use the feature
            replace_in_file --path src/main/java/example/Main.java --search "// TODO: Add feature" --replace "Feature feature = new Feature();\\nfeature.execute();"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "High");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Design the feature"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createTestingStep() {
        String description = "Write tests";
        String instruction = """
            Write tests for the feature:
            
            1. Write unit tests for individual components
            2. Write integration tests for component interactions
            3. Write end-to-end tests for the complete feature
            4. Test edge cases and error conditions
            5. Use the `write_to_file` tool to create test files
            
            Example testing approach:
            1. Use JUnit for unit tests
            2. Use Mockito for mocking dependencies
            3. Test happy paths and error paths
            4. Test edge cases and boundary conditions
            5. Aim for high test coverage
            
            Example:
            ```
            // Create a test file
            write_to_file --path src/test/java/example/FeatureTest.java --content "package example;\\n\\nimport org.junit.Test;\\nimport static org.junit.Assert.*;\\n\\npublic class FeatureTest {\\n    @Test\\n    public void testFeature() {\\n        Feature feature = new Feature();\\n        assertEquals(expected, feature.execute());\\n    }\\n}"
            
            // Run the tests
            execute_command --command "mvn test" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Implement the feature"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createIntegrationStep() {
        String description = "Integrate with existing code";
        String instruction = """
            Integrate the feature with existing code:
            
            1. Identify the integration points
            2. Modify existing code to use the new feature
            3. Ensure backward compatibility if needed
            4. Update configuration files if needed
            5. Use the `replace_in_file` tool to modify existing files
            
            Example integration approach:
            1. Identify where the feature should be used
            2. Update existing code to call the new feature
            3. Ensure error handling is consistent
            4. Update any configuration or dependency injection
            
            Example:
            ```
            // Update a service to use the new feature
            replace_in_file --path src/main/java/example/Service.java --search "// Process data\\nprocessData(data);" --replace "// Process data using the new feature\\nFeature feature = new Feature();\\nfeature.process(data);"
            
            // Update configuration
            replace_in_file --path src/main/resources/application.properties --search "# Feature configuration" --replace "# Feature configuration\\nfeature.enabled=true\\nfeature.maxItems=100"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of("Implement the feature", "Write tests"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createDocumentationStep() {
        String description = "Document the feature";
        String instruction = """
            Document the feature:
            
            1. Add Javadoc comments to classes and methods
            2. Update README files or other documentation
            3. Create usage examples
            4. Document any configuration options
            5. Use the `replace_in_file` tool to update documentation files
            
            Example documentation approach:
            1. Add Javadoc comments to all public classes and methods
            2. Update README.md with feature description and usage examples
            3. Create a separate documentation file if the feature is complex
            
            Example:
            ```
            // Add Javadoc to a class
            replace_in_file --path src/main/java/example/Feature.java --search "public class Feature {" --replace "/**\\n * A feature that does something useful.\\n * <p>\\n * This feature provides functionality for...\\n */\\npublic class Feature {"
            
            // Update README.md
            replace_in_file --path README.md --search "## Features\\n\\n" --replace "## Features\\n\\n### New Feature\\n\\nThis feature provides... Usage example:\\n\\n```java\\nFeature feature = new Feature();\\nfeature.execute();\\n```\\n\\n"
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "Medium");
        metadata.put("dependencies", List.of("Implement the feature"));
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createReviewStep() {
        String description = "Review and refine";
        String instruction = """
            Review the implementation and refine as needed:
            
            1. Review the code for quality and best practices
            2. Ensure all tests pass
            3. Verify that the feature meets the requirements
            4. Check for any performance issues
            5. Refine the implementation based on the review
            
            Example review checklist:
            - Does the code follow the design?
            - Does the feature meet the requirements?
            - Is the code well-structured and maintainable?
            - Are there adequate tests?
            - Is the documentation clear and complete?
            - Are there any performance concerns?
            - Are there any security concerns?
            
            Example:
            ```
            // Run tests to verify the feature
            execute_command --command "mvn test" --requires_approval false
            
            // Check code coverage
            execute_command --command "mvn jacoco:report" --requires_approval false
            ```
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "High");
        metadata.put("dependencies", List.of(
            "Implement the feature",
            "Write tests",
            "Integrate with existing code",
            "Document the feature"
        ));
        
        return new TaskStep(description, instruction, metadata);
    }
}
