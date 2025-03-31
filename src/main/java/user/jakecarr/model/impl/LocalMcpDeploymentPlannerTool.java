package user.jakecarr.model.impl;

import user.jakecarr.model.TaskPlannerTool.TaskPlan;
import user.jakecarr.model.TaskPlannerTool.TaskStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tool that generates task plans for deploying local MCP servers.
 * This tool is specifically designed for deploying MCP servers to a local environment.
 */
public class LocalMcpDeploymentPlannerTool extends AbstractTaskPlannerTool {
    private static final Logger LOGGER = Logger.getLogger(LocalMcpDeploymentPlannerTool.class.getName());
    
    private static final String NAME = "local_mcp_deployment_planner";
    private static final String DESCRIPTION = "Generates a task plan for deploying local MCP servers";
    
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
     * Analyzes a high-level local MCP deployment objective and generates a task plan.
     * 
     * @param objective The high-level objective to analyze
     * @param context Additional context for the task planning
     * @return A task plan with actionable steps
     */
    @Override
    public TaskPlan analyzeObjective(final String objective, final Map<String, Object> context) {
        LOGGER.info("Analyzing local MCP deployment objective: " + objective);
        
        // Create a list of task steps for local MCP server deployment
        List<TaskStep> steps = createDeploymentSteps();
        
        // Create a summary of the task plan
        String summary = createSummary();
        
        return new TaskPlan(objective, steps, summary);
    }
    
    /**
     * Creates the list of task steps for local MCP server deployment.
     * 
     * @return The list of task steps
     */
    private List<TaskStep> createDeploymentSteps() {
        List<TaskStep> steps = new ArrayList<>();
        
        // Step 1: Clean
        steps.add(createCleanStep());
        
        // Step 2: Build
        steps.add(createBuildStep());
        
        // Step 3: Test
        steps.add(createTestStep());
        
        // Step 4: Package
        steps.add(createPackageStep());
        
        // Step 5: Deploy
        steps.add(createDeployStep());
        
        // Step 6: Update MCP Settings
        steps.add(createUpdateMcpSettingsStep());
        
        // Step 7: Verify with Ping
        steps.add(createVerifyWithPingStep());
        
        return steps;
    }
    
    /**
     * Creates a summary of the task plan.
     * 
     * @return The summary
     */
    private String createSummary() {
        return "This task plan provides a streamlined approach to deploying a local MCP server. " +
                "It covers cleaning, building, testing, packaging, deployment to a standardized location, " +
                "and verification. Each step includes error checking to abort the process if any step fails. " +
                "Following this plan will result in a properly deployed MCP server that can be used with AI assistants.";
    }
    
    /**
     * Creates a task step with the specified parameters.
     * 
     * @param description The description of the step
     * @param instruction The instruction for the step
     * @param estimatedEffort The estimated effort for the step
     * @param priority The priority of the step
     * @param dependencies The dependencies of the step
     * @param isCritical Whether the step is critical
     * @param abortOnFailure Whether to abort on failure
     * @return The created task step
     */
    private TaskStep createTaskStep(final String description, final String instruction, final String estimatedEffort, 
                                   final String priority, final List<String> dependencies, final boolean isCritical, 
                                   final boolean abortOnFailure) {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", estimatedEffort);
        metadata.put("priority", priority);
        metadata.put("dependencies", dependencies);
        metadata.put("isCritical", isCritical);
        metadata.put("abortOnFailure", abortOnFailure);
        
        return new TaskStep(description, instruction, metadata);
    }
    
    /**
     * Creates the clean step.
     * 
     * @return The clean step
     */
    private TaskStep createCleanStep() {
        String description = "Clean the project";
        String instruction = """
            CRITICAL STEP: Clean the project to remove any previous build artifacts.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Clean the project
            Write-Host "Starting critical clean step..." -ForegroundColor Yellow
            mvn clean
            
            # Check if the command was successful
            if ($LASTEXITCODE -ne 0) {
                Write-Error "CRITICAL FAILURE: Clean step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the clean step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            
            Write-Host "Project cleaned successfully." -ForegroundColor Green
            ```
            
            # For Unix bash
            ```bash
            # Clean the project
            echo -e "\033[33mStarting critical clean step...\033[0m"
            mvn clean
            
            # Check if the command was successful
            if [ $? -ne 0 ]; then
                echo -e "\033[31mCRITICAL FAILURE: Clean step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mFix the issues with the clean step before attempting deployment again.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            echo -e "\033[32mProject cleaned successfully.\033[0m"
            ```
            
            The clean step removes any previous build artifacts to ensure a fresh build.
            This prevents issues that might arise from stale or incompatible artifacts.
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if the clean operation fails.
            """;
        
        return createTaskStep(description, instruction, "Low", "Critical", new ArrayList<>(), true, true);
    }
    
    /**
     * Creates the build step.
     * 
     * @return The build step
     */
    private TaskStep createBuildStep() {
        String description = "Build the project";
        String instruction = """
            CRITICAL STEP: Compile the project code.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Compile the code
            Write-Host "Starting critical build step..." -ForegroundColor Yellow
            mvn compile
            
            # Check if the command was successful
            if ($LASTEXITCODE -ne 0) {
                Write-Error "CRITICAL FAILURE: Build step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the build step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            
            Write-Host "Project compiled successfully." -ForegroundColor Green
            ```
            
            # For Unix bash
            ```bash
            # Compile the code
            echo -e "\033[33mStarting critical build step...\033[0m"
            mvn compile
            
            # Check if the command was successful
            if [ $? -ne 0 ]; then
                echo -e "\033[31mCRITICAL FAILURE: Build step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mFix the issues with the build step before attempting deployment again.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            echo -e "\033[32mProject compiled successfully.\033[0m"
            ```
            
            The build step compiles the Java source code into class files.
            This step verifies that the code is syntactically correct and can be compiled.
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if the build operation fails.
            """;
        
        return createTaskStep(description, instruction, "Low", "Critical", 
                             List.of("Clean the project"), true, true);
    }
    
    /**
     * Creates the test step.
     * 
     * @return The test step
     */
    private TaskStep createTestStep() {
        String description = "Run tests";
        String instruction = """
            CRITICAL STEP: Run the project tests to ensure everything is working correctly.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Run the tests
            Write-Host "Starting critical test step..." -ForegroundColor Yellow
            Write-Host "Running all tests - this is a quality gate that must pass to proceed with deployment."
            mvn test
            
            # Check if the command was successful
            if ($LASTEXITCODE -ne 0) {
                Write-Error "CRITICAL FAILURE: Test step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "One or more tests have failed. Fix the failing tests before attempting deployment again."
                Write-Error "Review the test output above to identify the specific test failures."
                exit 1  # This will terminate the entire script execution
            }
            
            Write-Host "SUCCESS: All tests passed successfully." -ForegroundColor Green
            Write-Host "Quality gate passed - proceeding to next step." -ForegroundColor Green
            ```
            
            # For Unix bash
            ```bash
            # Run the tests
            echo -e "\033[33mStarting critical test step...\033[0m"
            echo -e "Running all tests - this is a quality gate that must pass to proceed with deployment."
            mvn test
            
            # Check if the command was successful
            if [ $? -ne 0 ]; then
                echo -e "\033[31mCRITICAL FAILURE: Test step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mOne or more tests have failed. Fix the failing tests before attempting deployment again.\033[0m" >&2
                echo -e "\033[31mReview the test output above to identify the specific test failures.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            echo -e "\033[32mSUCCESS: All tests passed successfully.\033[0m"
            echo -e "\033[32mQuality gate passed - proceeding to next step.\033[0m"
            ```
            
            The test step runs all the unit and integration tests to ensure that the code works as expected.
            This is a critical quality gate to catch any regressions or issues before deployment.
            
            Success criteria:
            - All tests must pass (zero test failures)
            - The Maven test command must complete with exit code 0
            
            Failure criteria:
            - Any test failure (even a single test) will cause this step to fail
            - Any error in the test execution process will cause this step to fail
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if any test fails.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "Critical");
        metadata.put("dependencies", List.of("Build the project"));
        metadata.put("isCritical", true);
        metadata.put("abortOnFailure", true);
        metadata.put("successCriteria", "All tests pass with zero failures");
        metadata.put("qualityGate", true);
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createPackageStep() {
        String description = "Package the project";
        String instruction = """
            CRITICAL STEP: Package the project into a JAR file.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Package the project (skip tests as they were already run)
            Write-Host "Starting critical package step..." -ForegroundColor Yellow
            mvn package -DskipTests
            
            # Check if the command was successful
            if ($LASTEXITCODE -ne 0) {
                Write-Error "CRITICAL FAILURE: Package step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the package step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            
            # Verify the JAR file exists
            if (-not (Test-Path -Path "target\\*-with-dependencies.jar")) {
                Write-Error "CRITICAL FAILURE: JAR file not found. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the package step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            
            $jarFile = Get-ChildItem -Path "target\\*-with-dependencies.jar" | Select-Object -First 1
            Write-Host "Successfully packaged: $($jarFile.Name)" -ForegroundColor Green
            Write-Host "File size: $([math]::Round($jarFile.Length / 1MB, 2)) MB"
            ```
            
            # For Unix bash
            ```bash
            # Package the project (skip tests as they were already run)
            echo -e "\033[33mStarting critical package step...\033[0m"
            mvn package -DskipTests
            
            # Check if the command was successful
            if [ $? -ne 0 ]; then
                echo -e "\033[31mCRITICAL FAILURE: Package step failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mFix the issues with the package step before attempting deployment again.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            # Verify the JAR file exists
            jar_file=$(find target -name "*-with-dependencies.jar" | head -1)
            if [ -z "$jar_file" ]; then
                echo -e "\033[31mCRITICAL FAILURE: JAR file not found. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mFix the issues with the package step before attempting deployment again.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            echo -e "\033[32mSuccessfully packaged: $(basename "$jar_file")\033[0m"
            size=$(du -h "$jar_file" | cut -f1)
            echo "File size: $size"
            ```
            
            The package step creates an executable JAR file that includes all dependencies.
            This creates a self-contained executable that can be deployed to the target environment.
            The step also verifies that the JAR file was created successfully.
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if the packaging operation fails.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "Critical");
        metadata.put("dependencies", List.of("Run tests"));
        metadata.put("isCritical", true);  // Added to indicate this step is critical
        metadata.put("abortOnFailure", true);  // Added to indicate the process should abort on failure
        
        return new TaskStep(description, instruction, metadata);
    }
    
    private TaskStep createDeployStep() {
        String description = "Deploy the MCP server";
        String instruction = """
            CRITICAL STEP: Deploy the MCP server to the standard location.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Get home directory and server name
            Write-Host "Starting critical deploy step..." -ForegroundColor Yellow
            $homeDir = $env:USERPROFILE
            $serverName = "mcp-server-factory"
            Write-Host "Home directory: $homeDir"
            Write-Host "Server name: $serverName"
            
            # Verify the uber JAR exists
            $jarFile = Get-ChildItem -Path "target\\*-with-dependencies.jar" | Select-Object -First 1
            if (-not $jarFile) {
                Write-Error "CRITICAL FAILURE: Uber JAR not found. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the deploy step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            Write-Host "Found uber JAR file: $($jarFile.Name)"
            
            # Create the deployment directory structure
            $mcpServerDir = Join-Path -Path $homeDir -ChildPath "mcp-server"
            if (-not (Test-Path -Path $mcpServerDir)) {
                New-Item -Path $mcpServerDir -ItemType Directory | Out-Null
                Write-Host "Created directory: $mcpServerDir"
            }
            
            $serverDir = Join-Path -Path $mcpServerDir -ChildPath $serverName
            if (-not (Test-Path -Path $serverDir)) {
                New-Item -Path $serverDir -ItemType Directory | Out-Null
                Write-Host "Created directory: $serverDir"
            }
            
            $libDir = Join-Path -Path $serverDir -ChildPath "lib"
            if (-not (Test-Path -Path $libDir)) {
                New-Item -Path $libDir -ItemType Directory | Out-Null
                Write-Host "Created directory: $libDir"
            }
            
            # Copy the uber JAR to the deployment location
            $jarPath = Join-Path -Path $libDir -ChildPath "$serverName.jar"
            Copy-Item -Path $jarFile.FullName -Destination $jarPath -Force
            Write-Host "Copied JAR to: $jarPath"
            
            # Verify the deployment
            if (Test-Path -Path $jarPath) {
                Write-Host "✓ JAR file deployed successfully to: $jarPath" -ForegroundColor Green
            } else {
                Write-Error "CRITICAL FAILURE: JAR file deployment failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED."
                Write-Error "Fix the issues with the deploy step before attempting deployment again."
                exit 1  # This will terminate the entire script execution
            }
            ```
            
            # For Unix bash
            ```bash
            # Get home directory and server name
            home_dir="$HOME"
            server_name="mcp-server-factory"
            echo "Home directory: $home_dir"
            echo "Server name: $server_name"
            
            # Verify the uber JAR exists
            jar_file=$(find target -name "*-with-dependencies.jar" | head -1)
            if [ -z "$jar_file" ]; then
                echo "Error: Uber JAR not found. Aborting deployment." >&2
                exit 1
            fi
            echo "Found uber JAR file: $(basename "$jar_file")"
            
            # Create the deployment directory structure
            mcp_server_dir="$home_dir/mcp-server"
            if [ ! -d "$mcp_server_dir" ]; then
                mkdir -p "$mcp_server_dir"
                echo "Created directory: $mcp_server_dir"
            fi
            
            server_dir="$mcp_server_dir/$server_name"
            if [ ! -d "$server_dir" ]; then
                mkdir -p "$server_dir"
                echo "Created directory: $server_dir"
            fi
            
            lib_dir="$server_dir/lib"
            if [ ! -d "$lib_dir" ]; then
                mkdir -p "$lib_dir"
                echo "Created directory: $lib_dir"
            fi
            
            # Copy the uber JAR to the deployment location
            jar_path="$lib_dir/$server_name.jar"
            cp "$jar_file" "$jar_path"
            echo "Copied JAR to: $jar_path"
            
            # Verify the deployment
            if [ -f "$jar_path" ]; then
                echo -e "\033[32m✓ JAR file deployed successfully to: $jar_path\033[0m"
            else
                echo -e "\033[31mCRITICAL FAILURE: JAR file deployment failed. THE ENTIRE DEPLOYMENT PROCESS IS BEING ABORTED.\033[0m" >&2
                echo -e "\033[31mFix the issues with the deploy step before attempting deployment again.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            ```
            
            The deployment step copies the JAR file to the standard location ({HOME}/mcp-server/mcp-server-factory/lib/).
            This creates a self-contained executable that can be used by the MCP system.
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if the deployment operation fails.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Medium");
        metadata.put("priority", "Critical");
        metadata.put("dependencies", List.of("Package the project"));
        metadata.put("isCritical", true);
        metadata.put("abortOnFailure", true);
        
        return new TaskStep(description, instruction, metadata);
    }
    
    /**
     * Creates the update MCP settings step.
     * 
     * @return The update MCP settings step
     */
    private TaskStep createUpdateMcpSettingsStep() {
        String description = "Update MCP settings";
        String instruction = """
            CRITICAL STEP: Update the MCP settings to register the server with Claude.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE ABORTED.
            
            # For Windows PowerShell
            ```powershell
            # Get home directory and server name
            Write-Host "Starting critical update MCP settings step..." -ForegroundColor Yellow
            $homeDir = $env:USERPROFILE
            $serverName = "mcp-server-factory"
            
            # Get the JAR path
            $jarPath = Join-Path -Path $homeDir -ChildPath "mcp-server\\$serverName\\lib\\$serverName.jar"
            if (-not (Test-Path -Path $jarPath)) {
                Write-Error "CRITICAL FAILURE: JAR file not found at: $jarPath"
                Write-Error "The deployment step must be completed successfully before updating MCP settings."
                exit 1  # This will terminate the entire script execution
            }
            
            # Update MCP settings
            $settingsDir = "$env:APPDATA\\Code\\User\\globalStorage\\saoudrizwan.claude-dev\\settings"
            $settingsPath = "$settingsDir\\cline_mcp_settings.json"
            
            if (Test-Path -Path $settingsPath) {
                Write-Host "Found MCP settings file, updating..."
                $settings = Get-Content -Path $settingsPath | ConvertFrom-Json
                
                # Add or update server entry
                if (-not $settings.mcpServers) {
                    $settings | Add-Member -MemberType NoteProperty -Name "mcpServers" -Value @{}
                }
                
                $serverEntry = @{
                    command = "java"
                    args = @("-jar", $jarPath)
                    env = @{
                        LAST_MODIFIED = (Get-Date).ToString("o")
                    }
                    disabled = $false
                    autoApprove = @()
                }
                
                $settings.mcpServers | Add-Member -MemberType NoteProperty -Name $serverName -Value $serverEntry -Force
                
                # Save updated settings
                $settings | ConvertTo-Json -Depth 10 | Set-Content -Path $settingsPath
                Write-Host "✓ Updated MCP settings file successfully" -ForegroundColor Green
            } else {
                Write-Error "CRITICAL FAILURE: MCP settings file not found at: $settingsPath"
                Write-Error "You'll need to manually add this server to your MCP settings."
                exit 1  # This will terminate the entire script execution
            }
            ```
            
            # For Unix bash
            ```bash
            # Get home directory and server name
            home_dir="$HOME"
            server_name="mcp-server-factory"
            
            # Get the JAR path
            jar_path="$home_dir/mcp-server/$server_name/lib/$server_name.jar"
            if [ ! -f "$jar_path" ]; then
                echo -e "\033[31mCRITICAL FAILURE: JAR file not found at: $jar_path\033[0m" >&2
                echo -e "\033[31mThe deployment step must be completed successfully before updating MCP settings.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            
            # Update MCP settings
            settings_dir="$HOME/.config/Code/User/globalStorage/saoudrizwan.claude-dev/settings"
            settings_path="$settings_dir/cline_mcp_settings.json"
            
            if [ -f "$settings_path" ]; then
                echo "Found MCP settings file, updating..."
                # This requires jq for JSON manipulation
                if command -v jq >/dev/null 2>&1; then
                    # Create server entry
                    last_modified=$(date -u +"%Y-%m-%dT%H:%M:%S.0000000Z")
                    
                    # Update settings file
                    jq --arg name "$server_name" \\
                       --arg jar "$jar_path" \\
                       --arg modified "$last_modified" \\
                       '.mcpServers[$name] = {
                           "command": "java",
                           "args": ["-jar", $jar],
                           "env": {
                               "LAST_MODIFIED": $modified
                           },
                           "disabled": false,
                           "autoApprove": []
                       }' "$settings_path" > "${settings_path}.tmp" && mv "${settings_path}.tmp" "$settings_path"
                    
                    echo -e "\033[32m✓ Updated MCP settings file successfully\033[0m"
                else
                    echo -e "\033[31mCRITICAL FAILURE: jq not found, cannot update settings file automatically\033[0m" >&2
                    echo -e "\033[31mYou'll need to manually add this server to your MCP settings.\033[0m" >&2
                    exit 1  # This will terminate the entire script execution
                fi
            else
                echo -e "\033[31mCRITICAL FAILURE: MCP settings file not found at: $settings_path\033[0m" >&2
                echo -e "\033[31mYou'll need to manually add this server to your MCP settings.\033[0m" >&2
                exit 1  # This will terminate the entire script execution
            fi
            ```
            
            The update MCP settings step registers the server with Claude by updating the MCP settings file.
            This allows Claude to discover and use the server's tools and resources.
            
            IMPORTANT: This step is a critical prerequisite for all subsequent steps.
            If this step fails for ANY reason, the entire deployment process MUST be aborted immediately.
            Do not proceed with any further steps if the update MCP settings operation fails.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Low");
        metadata.put("priority", "Critical");
        metadata.put("dependencies", List.of("Deploy the MCP server"));
        metadata.put("isCritical", true);
        metadata.put("abortOnFailure", true);
        
        return new TaskStep(description, instruction, metadata);
    }
    
    /**
     * Creates the verify with ping step.
     * 
     * @return The verify with ping step
     */
    private TaskStep createVerifyWithPingStep() {
        String description = "Verify the deployment";
        String instruction = """
            CRITICAL STEP: Verify the MCP server deployment by testing it with the ping tool.
            THIS STEP MUST SUCCEED OR THE ENTIRE DEPLOYMENT PROCESS WILL BE CONSIDERED FAILED.
            
            # For Windows PowerShell
            ```powershell
            # Wait a moment for the server to be registered
            Write-Host "Starting critical verification step..." -ForegroundColor Yellow
            Write-Host "Waiting for the server to be registered..."
            Start-Sleep -Seconds 5
            
            # Test the server with the ping tool
            Write-Host "Testing the server with the ping tool..."
            
            # This would be done through Claude using the use_mcp_tool command
            # use_mcp_tool mcp-server-factory ping {"message": "Deployment verification check"}
            
            # For manual testing, you can use curl if the server uses WebSocket transport
            # or start the server and interact with it directly if it uses stdio transport
            
            Write-Host "Deployment verification complete." -ForegroundColor Green
            Write-Host "To use this server with Claude, ensure it's properly configured in your MCP settings."
            Write-Host "You can test it by asking Claude to use the ping tool: 'use the ping tool from mcp-server-factory'"
            
            # If you need to verify the server is actually working, you can add this check
            # If verification fails, abort the process
            # $verificationResult = ... # Some verification logic
            # if (-not $verificationResult) {
            #     Write-Error "CRITICAL FAILURE: Verification failed. THE DEPLOYMENT IS CONSIDERED UNSUCCESSFUL."
            #     Write-Error "Fix the issues with the server before attempting to use it."
            #     exit 1  # This will terminate the entire script execution
            # }
            ```
            
            # For Unix bash
            ```bash
            # Wait a moment for the server to be registered
            echo "Waiting for the server to be registered..."
            sleep 5
            
            # Test the server with the ping tool
            echo "Testing the server with the ping tool..."
            
            # This would be done through Claude using the use_mcp_tool command
            # use_mcp_tool mcp-server-factory ping {"message": "Deployment verification check"}
            
            # For manual testing, you can use curl if the server uses WebSocket transport
            # or start the server and interact with it directly if it uses stdio transport
            
            echo -e "\033[32mDeployment verification complete.\033[0m"
            echo "To use this server with Claude, ensure it's properly configured in your MCP settings."
            echo "You can test it by asking Claude to use the ping tool: 'use the ping tool from mcp-server-factory'"
            
            # If you need to verify the server is actually working, you can add this check
            # If verification fails, abort the process
            # verification_result=... # Some verification logic
            # if [ "$verification_result" != "success" ]; then
            #     echo -e "\033[31mCRITICAL FAILURE: Verification failed. THE DEPLOYMENT IS CONSIDERED UNSUCCESSFUL.\033[0m" >&2
            #     echo -e "\033[31mFix the issues with the server before attempting to use it.\033[0m" >&2
            #     exit 1  # This will terminate the entire script execution
            # fi
            ```
            
            The verification step tests the deployed MCP server to ensure it's working correctly.
            This is done by using the ping tool, which is a simple way to verify that the server is responsive.
            The actual verification would be done through Claude using the use_mcp_tool command.
            
            IMPORTANT: This step is the final validation of the deployment process.
            If this step fails, the entire deployment should be considered unsuccessful.
            The server should not be used until verification passes successfully.
            """;
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("estimatedEffort", "Low");
        metadata.put("priority", "Critical");
        metadata.put("dependencies", List.of("Update MCP settings"));
        metadata.put("isCritical", true);  // Added to indicate this step is critical
        metadata.put("abortOnFailure", true);  // Added to indicate the process should abort on failure
        
        return new TaskStep(description, instruction, metadata);
    }
}
