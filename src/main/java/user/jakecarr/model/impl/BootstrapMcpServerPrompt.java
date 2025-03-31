package user.jakecarr.model.impl;

import user.jakecarr.model.McpPrompt;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Prompt that provides a template for bootstrapping a Java-based MCP server.
 * This prompt generates a complete project structure and code for a Spring-based MCP server.
 */
public class BootstrapMcpServerPrompt implements McpPrompt {
    private static final Logger LOGGER = Logger.getLogger(BootstrapMcpServerPrompt.class.getName());
    
    private static final String NAME = "bootstrap_mcp_server";
    private static final String DESCRIPTION = "Provides a template for creating a Java-based MCP server";
    
    /**
     * Creates a new BootstrapMcpServerPrompt.
     */
    public BootstrapMcpServerPrompt() {
        // No initialization needed
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
    
    @Override
    public PromptResponse handle(Map<String, Object> parameters) {
        LOGGER.info("Handling bootstrap_mcp_server prompt with parameters: " + parameters);
        
        // Extract parameters with defaults
        String serverName = (String) parameters.getOrDefault("serverName", "my-mcp-server");
        String packageName = (String) parameters.getOrDefault("packageName", "com.example.mcp");
        String serverDescription = (String) parameters.getOrDefault("description", "A Model Context Protocol server");
        String buildSystem = (String) parameters.getOrDefault("buildSystem", "detect");
        
        // Detect or validate build system
        if ("detect".equals(buildSystem)) {
            // Check for Maven (pom.xml)
            if (new File("pom.xml").exists()) {
                buildSystem = "maven";
            }
            // Check for Gradle (build.gradle or build.gradle.kts)
            else if (new File("build.gradle").exists() || new File("build.gradle.kts").exists()) {
                buildSystem = "gradle";
            }
            else {
                // Neither Maven nor Gradle detected
                return new PromptResponse(
                    "Error: No build system detected. This prompt requires either Maven (pom.xml) or Gradle (build.gradle) to be present in the project."
                );
            }
        }
        
        // Generate the template based on the detected/specified build system
        String template = generateServerTemplate(serverName, packageName, serverDescription, buildSystem);
        
        return new PromptResponse(template);
    }
    
    @Override
    public Map<String, Object> getParameterSchema() {
        Map<String, Object> schema = new HashMap<>();
        schema.put("type", "object");
        
        Map<String, Object> properties = new HashMap<>();
        
        // Add properties using helper method to reduce duplication
        properties.put("serverName", createStringProperty("The name of the MCP server"));
        properties.put("packageName", createStringProperty("The Java package name for the server code"));
        properties.put("description", createStringProperty("A description of the MCP server"));
        
        // Build system property has an enum, so create it separately
        Map<String, Object> buildSystemProperty = createStringProperty("The build system to use (maven, gradle, or detect)");
        buildSystemProperty.put("enum", new String[]{"maven", "gradle", "detect"});
        properties.put("buildSystem", buildSystemProperty);
        
        schema.put("properties", properties);
        
        return schema;
    }
    
    /**
     * Helper method to create a string property for the parameter schema.
     * 
     * @param description The description of the property
     * @return A map representing a string property
     */
    private Map<String, Object> createStringProperty(String description) {
        Map<String, Object> property = new HashMap<>();
        property.put("type", "string");
        property.put("description", description);
        return property;
    }
    
    /**
     * Generates a complete template for a Java-based MCP server.
     * 
     * @param serverName The name of the server
     * @param packageName The Java package name for the server code
     * @param serverDescription A description of the server
     * @param buildSystem The build system to use (maven or gradle)
     * @return A complete template for a Java-based MCP server
     */
    private String generateServerTemplate(String serverName, String packageName, String serverDescription, String buildSystem) {
        StringBuilder template = new StringBuilder();
        
        // Add project overview
        addProjectOverview(template, serverName, serverDescription, packageName);
        
        // Add build configuration
        addBuildConfiguration(template, buildSystem, serverName, packageName);
        
        // Add Java code examples
        addJavaCodeExamples(template, packageName, serverName, serverDescription);
        
        // Add running instructions
        addRunningInstructions(template, buildSystem, serverName);
        
        // Add extension instructions
        addExtensionInstructions(template);
        
        return template.toString();
    }
    
    /**
     * Adds project overview to the template.
     * 
     * @param template The template builder
     * @param serverName The name of the server
     * @param serverDescription A description of the server
     * @param packageName The Java package name for the server code
     */
    private void addProjectOverview(StringBuilder template, String serverName, String serverDescription, String packageName) {
        // Add project structure overview
        template.append("# ").append(serverName).append("\n\n");
        template.append(serverDescription).append("\n\n");
        template.append("## Project Structure\n\n");
        
        // Add directory structure
        template.append("```\n");
        template.append(generateDirectoryStructure(packageName));
        template.append("```\n\n");
    }
    
    /**
     * Adds build configuration to the template.
     * 
     * @param template The template builder
     * @param buildSystem The build system to use (maven or gradle)
     * @param serverName The name of the server
     * @param packageName The Java package name for the server code
     */
    private void addBuildConfiguration(StringBuilder template, String buildSystem, String serverName, String packageName) {
        // Add build file (Maven or Gradle)
        if ("maven".equals(buildSystem)) {
            template.append("## pom.xml\n\n");
            template.append("```xml\n");
            template.append(generatePomXml(serverName, packageName));
            template.append("```\n\n");
        } else {
            template.append("## build.gradle\n\n");
            template.append("```groovy\n");
            template.append(generateBuildGradle(serverName, packageName));
            template.append("```\n\n");
        }
    }
    
    /**
     * Adds Java code examples to the template.
     * 
     * @param template The template builder
     * @param packageName The Java package name for the server code
     * @param serverName The name of the server
     * @param serverDescription A description of the server
     */
    private void addJavaCodeExamples(StringBuilder template, String packageName, String serverName, String serverDescription) {
        // Add main application class
        template.append("## Main Application Class\n\n");
        template.append("```java\n");
        template.append(generateMainClass(packageName));
        template.append("```\n\n");
        
        // Add Spring configuration
        template.append("## Spring Configuration\n\n");
        template.append("```java\n");
        template.append(generateAppConfig(packageName));
        template.append("```\n\n");
        
        // Add MCP server service
        template.append("## MCP Server Service\n\n");
        template.append("```java\n");
        template.append(generateMcpServerService(packageName, serverName, serverDescription));
        template.append("```\n\n");
        
        // Add a basic tool implementation
        template.append("## Basic Tool Implementation\n\n");
        template.append("```java\n");
        template.append(generatePingTool(packageName));
        template.append("```\n\n");
    }
    
    /**
     * Adds running instructions to the template.
     * 
     * @param template The template builder
     * @param buildSystem The build system to use (maven or gradle)
     * @param serverName The name of the server
     */
    private void addRunningInstructions(StringBuilder template, String buildSystem, String serverName) {
        template.append("## Running the Server\n\n");
        template.append("To build and run the server:\n\n");
        template.append("```bash\n");
        
        if ("maven".equals(buildSystem)) {
            template.append("mvn clean package\n");
            template.append("java -jar target/").append(serverName).append("-1.0-SNAPSHOT-jar-with-dependencies.jar\n");
        } else {
            template.append("./gradlew build\n");
            template.append("java -jar build/libs/").append(serverName).append("-1.0-SNAPSHOT-all.jar\n");
        }
        
        template.append("```\n\n");
    }
    
    /**
     * Adds extension instructions to the template.
     * 
     * @param template The template builder
     */
    private void addExtensionInstructions(StringBuilder template) {
        template.append("## Extending the Server\n\n");
        
        template.append("### Adding a New Tool\n\n");
        template.append("1. Create a new class that implements the `McpTool` interface\n");
        template.append("2. Register the tool in the `ToolService` class\n\n");
        
        template.append("### Adding a New Resource\n\n");
        template.append("1. Create a new class that implements the `McpResource` interface\n");
        template.append("2. Register the resource in the `ResourceService` class\n\n");
        
        template.append("### Adding a New Prompt\n\n");
        template.append("1. Create a new class that implements the `McpPrompt` interface\n");
        template.append("2. Register the prompt in the `PromptService` class\n\n");
    }
    
    /**
     * Generates a directory structure for a Java-based MCP server.
     * 
     * @param packageName The Java package name for the server code
     * @return A directory structure for a Java-based MCP server
     */
    private String generateDirectoryStructure(String packageName) {
        // Convert package name to directory structure
        String packagePath = packageName.replace('.', '/');
        
        return String.format("""
            src/
            ├── main/
            │   ├── java/
            │   │   └── %s/
            │   │       ├── config/
            │   │       │   ├── AppConfig.java
            │   │       │   ├── McpServerServiceConfig.java
            │   │       │   ├── PromptServiceConfig.java
            │   │       │   ├── ResourceServiceConfig.java
            │   │       │   ├── ToolServiceConfig.java
            │   │       │   └── TransportProviderConfig.java
            │   │       ├── main/
            │   │       │   └── McpServerMain.java
            │   │       ├── model/
            │   │       │   ├── McpComponent.java
            │   │       │   ├── McpPrompt.java
            │   │       │   ├── McpResource.java
            │   │       │   ├── McpTool.java
            │   │       │   └── impl/
            │   │       │       └── PingTool.java
            │   │       └── service/
            │   │           ├── McpServerService.java
            │   │           ├── PromptService.java
            │   │           ├── ResourceService.java
            │   │           └── ToolService.java
            │   └── resources/
            │       └── log4j2.xml
            └── test/
                └── java/
                    └── %s/
                        └── McpServerTest.java
            """, packagePath, packagePath);
    }
    
    /**
     * Generates a Maven POM file for a Java-based MCP server.
     * 
     * @param serverName The name of the server
     * @param packageName The Java package name for the server code
     * @return A Maven POM file for a Java-based MCP server
     */
    private String generatePomXml(String serverName, String packageName) {
        return String.format("""
            <?xml version="1.0" encoding="UTF-8"?>
            <project xmlns="http://maven.apache.org/POM/4.0.0"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
                <modelVersion>4.0.0</modelVersion>
            
                <groupId>%s</groupId>
                <artifactId>%s</artifactId>
                <version>1.0-SNAPSHOT</version>
            
                <properties>
                    <maven.compiler.source>21</maven.compiler.source>
                    <maven.compiler.target>21</maven.compiler.target>
                    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    <spring.version>5.3.27</spring.version>
                    <log4j.version>2.20.0</log4j.version>
                    <junit.version>5.9.2</junit.version>
                    <mockito.version>5.2.0</mockito.version>
                    <modelcontextprotocol.version>0.8.1</modelcontextprotocol.version>
                </properties>
            
                <dependencies>
                    <!-- MCP SDK -->
                    <dependency>
                        <groupId>io.modelcontextprotocol.sdk</groupId>
                        <artifactId>mcp</artifactId>
                        <version>${modelcontextprotocol.version}</version>
                    </dependency>
            
                    <!-- Spring Framework -->
                    <dependency>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-core</artifactId>
                        <version>${spring.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring-context</artifactId>
                        <version>${spring.version}</version>
                    </dependency>
                    
                    <!-- Logging -->
                    <dependency>
                        <groupId>org.apache.logging.log4j</groupId>
                        <artifactId>log4j-api</artifactId>
                        <version>${log4j.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.logging.log4j</groupId>
                        <artifactId>log4j-core</artifactId>
                        <version>${log4j.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.logging.log4j</groupId>
                        <artifactId>log4j-jul</artifactId>
                        <version>${log4j.version}</version>
                    </dependency>
                    <dependency>
                        <groupId>org.apache.logging.log4j</groupId>
                        <artifactId>log4j-slf4j-impl</artifactId>
                        <version>${log4j.version}</version>
                    </dependency>
            
                    <!-- Testing -->
                    <dependency>
                        <groupId>org.junit.jupiter</groupId>
                        <artifactId>junit-jupiter-api</artifactId>
                        <version>${junit.version}</version>
                        <scope>test</scope>
                    </dependency>
                    <dependency>
                        <groupId>org.junit.jupiter</groupId>
                        <artifactId>junit-jupiter-engine</artifactId>
                        <version>${junit.version}</version>
                        <scope>test</scope>
                    </dependency>
                    <dependency>
                        <groupId>org.mockito</groupId>
                        <artifactId>mockito-core</artifactId>
                        <version>${mockito.version}</version>
                        <scope>test</scope>
                    </dependency>
                    <dependency>
                        <groupId>org.mockito</groupId>
                        <artifactId>mockito-junit-jupiter</artifactId>
                        <version>${mockito.version}</version>
                        <scope>test</scope>
                    </dependency>
                </dependencies>
            
                <build>
                    <plugins>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-compiler-plugin</artifactId>
                            <version>3.11.0</version>
                            <configuration>
                                <source>${maven.compiler.source}</source>
                                <target>${maven.compiler.target}</target>
                            </configuration>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-surefire-plugin</artifactId>
                            <version>3.1.0</version>
                        </plugin>
                        <plugin>
                            <groupId>org.apache.maven.plugins</groupId>
                            <artifactId>maven-assembly-plugin</artifactId>
                            <version>3.5.0</version>
                            <configuration>
                                <descriptorRefs>
                                    <descriptorRef>jar-with-dependencies</descriptorRef>
                                </descriptorRefs>
                                <archive>
                                    <manifest>
                                        <mainClass>%s.main.McpServerMain</mainClass>
                                    </manifest>
                                </archive>
                            </configuration>
                            <executions>
                                <execution>
                                    <id>make-assembly</id>
                                    <phase>package</phase>
                                    <goals>
                                        <goal>single</goal>
                                    </goals>
                                </execution>
                            </executions>
                        </plugin>
                    </plugins>
                </build>
            </project>
            """, packageName, serverName, packageName);
    }
    
    /**
     * Generates a Gradle build file for a Java-based MCP server.
     * 
     * @param serverName The name of the server
     * @param packageName The Java package name for the server code
     * @return A Gradle build file for a Java-based MCP server
     */
    private String generateBuildGradle(String serverName, String packageName) {
        return String.format("""
            plugins {
                id 'java'
                id 'application'
            }
            
            group = '%s'
            version = '1.0-SNAPSHOT'
            
            repositories {
                mavenCentral()
            }
            
            dependencies {
                // MCP SDK
                implementation 'io.modelcontextprotocol.sdk:mcp:0.8.1'
                
                // Spring Framework
                implementation 'org.springframework:spring-core:5.3.27'
                implementation 'org.springframework:spring-context:5.3.27'
                
                // Logging
                implementation 'org.apache.logging.log4j:log4j-api:2.20.0'
                implementation 'org.apache.logging.log4j:log4j-core:2.20.0'
                implementation 'org.apache.logging.log4j:log4j-jul:2.20.0'
                implementation 'org.apache.logging.log4j:log4j-slf4j-impl:2.20.0'
                
                // Testing
                testImplementation 'org.junit.jupiter:junit-jupiter-api:5.9.2'
                testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.9.2'
                testImplementation 'org.mockito:mockito-core:5.2.0'
                testImplementation 'org.mockito:mockito-junit-jupiter:5.2.0'
            }
            
            application {
                mainClass = '%s.main.McpServerMain'
            }
            
            java {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }
            
            test {
                useJUnitPlatform()
            }
            
            // Create a single JAR with all dependencies
            tasks.register('fatJar', Jar) {
                archiveBaseName.set('%s')
                archiveClassifier.set('all')
                
                duplicatesStrategy = DuplicatesStrategy.EXCLUDE
                
                manifest {
                    attributes 'Main-Class': '%s.main.McpServerMain'
                }
                
                from sourceSets.main.output
                
                dependsOn configurations.runtimeClasspath
                from {
                    configurations.runtimeClasspath.collect { it.isDirectory() ? it : zipTree(it) }
                }
            }
            
            // Make the jar task depend on the fatJar task
            jar.finalizedBy(fatJar)
            """, packageName, packageName, serverName, packageName);
    }
    
    /**
     * Generates a main application class for a Java-based MCP server.
     * 
     * @param packageName The Java package name for the server code
     * @return A main application class for a Java-based MCP server
     */
    private String generateMainClass(String packageName) {
        return String.format("""
            package %s.main;
            
            import org.springframework.context.annotation.AnnotationConfigApplicationContext;
            import %s.config.AppConfig;
            
            import java.util.logging.Level;
            import java.util.logging.Logger;
            
            /**
             * Main entry point for the MCP Server application.
             * This class initializes the Spring context.
             * The MCP server is started automatically by the init method in McpServerService,
             * which is configured as the initMethod in the @Bean definition.
             */
            public class McpServerMain {
                private static final Logger LOGGER = Logger.getLogger(McpServerMain.class.getName());
            
                public static void main(String[] args) {
                    LOGGER.info("Starting MCP Server...");
                    
                    try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
                        LOGGER.info("MCP Server started successfully. Press Ctrl+C to stop.");
                        
                        // Keep the application running until terminated
                        Thread.currentThread().join();
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Failed to start MCP Server", e);
                        System.exit(1);
                    }
                }
            }
            """, packageName, packageName);
    }
    
    /**
     * Generates an AppConfig class for a Java-based MCP server.
     * 
     * @param packageName The Java package name for the server code
     * @return An AppConfig class for a Java-based MCP server
     */
    private String generateAppConfig(String packageName) {
        return String.format("""
            package %s.config;
            
            import org.springframework.context.annotation.Configuration;
            import org.springframework.context.annotation.Import;
            
            /**
             * Main Spring configuration class.
             * This class imports all other configuration classes.
             */
            @Configuration
            @Import({
                McpServerServiceConfig.class,
                ToolServiceConfig.class,
                ResourceServiceConfig.class,
                PromptServiceConfig.class,
                TransportProviderConfig.class
            })
            public class AppConfig {
                // No additional beans needed here
            }
            """, packageName);
    }
    
    /**
     * Generates an MCP server service class for a Java-based MCP server.
     * 
     * @param packageName The Java package name for the server code
     * @param serverName The name of the server
     * @param serverDescription A description of the server
     * @return An MCP server service class for a Java-based MCP server
     */
    private String generateMcpServerService(String packageName, String serverName, String serverDescription) {
        return String.format("""
            package %s.service;
            
            import io.modelcontextprotocol.server.McpServerFeatures;
            import io.modelcontextprotocol.server.McpSyncServer;
            import io.modelcontextprotocol.server.transport.StdioTransport;
            import io.modelcontextprotocol.spec.McpSchema;
            
            import java.util.logging.Level;
            import java.util.logging.Logger;
            
            /**
             * Service class for managing the MCP server.
             * This class is responsible for creating, starting, and stopping the MCP server.
             */
            public class McpServerService {
                private static final Logger LOGGER = Logger.getLogger(McpServerService.class.getName());
                
                private final ToolService toolService;
                private final ResourceService resourceService;
                private final PromptService promptService;
                private final StdioTransport transportProvider;
                
                private McpSyncServer mcpServer;
                
                /**
                 * Creates a new McpServerService.
                 * 
                 * @param toolService The tool service
                 * @param resourceService The resource service
                 * @param promptService The prompt service
                 * @param transportProvider The transport provider
                 */
                public McpServerService(
                        ToolService toolService,
                        ResourceService resourceService,
                        PromptService promptService,
                        StdioTransport transportProvider) {
                    this.toolService = toolService;
                    this.resourceService = resourceService;
                    this.promptService = promptService;
                    this.transportProvider = transportProvider;
                }
                
                /**
                 * Initializes the MCP server.
                 * This method is called automatically by Spring as the initMethod.
                 */
                public void init() {
                    LOGGER.info("Initializing MCP server...");
                    
                    try {
                        // Create the MCP server
                        mcpServer = new McpSyncServer(
                                new McpSchema.ServerInfo("%s", "1.0.0", "%s"),
                                McpServerFeatures.builder().build());
                        
                        // Register tools, resources, and prompts
                        toolService.registerTools(mcpServer);
                        resourceService.registerResources(mcpServer);
                        promptService.registerPrompts(mcpServer);
                        
                        // Start the server
                        mcpServer.start(transportProvider);
                        
                        LOGGER.info("MCP server initialized successfully");
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Failed to initialize MCP server", e);
                        throw new RuntimeException("Failed to initialize MCP server", e);
                    }
                }
                
                /**
                 * Destroys the MCP server.
                 * This method is called automatically by Spring as the destroyMethod.
                 */
                public void destroy() {
                    LOGGER.info("Shutting down MCP server...");
                    
                    try {
                        if (mcpServer != null) {
                            mcpServer.stop();
                        }
                        
                        LOGGER.info("MCP server shut down successfully");
                    } catch (Exception e) {
                        LOGGER.log(Level.SEVERE, "Failed to shut down MCP server", e);
                    }
                }
                
                /**
                 * Gets the MCP server.
                 * 
                 * @return The MCP server
                 */
                public McpSyncServer getMcpServer() {
                    return mcpServer;
                }
            }
            """, packageName, serverName, serverDescription);
    }
    
    /**
     * Generates a PingTool class for a Java-based MCP server.
     * 
     * @param packageName The Java package name for the server code
     * @return A PingTool class for a Java-based MCP server
     */
    private String generatePingTool(String packageName) {
        return String.format("""
            package %s.model.impl;
            
            import %s.model.McpTool;
            import io.modelcontextprotocol.spec.McpSchema;
            
            import java.util.HashMap;
            import java.util.Map;
            import java.util.logging.Logger;
            
            /**
             * A simple ping tool that responds to ping requests.
             * This tool is useful for testing the MCP server.
             */
            public class PingTool implements McpTool {
                private static final Logger LOGGER = Logger.getLogger(PingTool.class.getName());
                
                private static final String NAME = "ping";
                private static final String DESCRIPTION = "Responds to ping requests to check server availability";
                
                @Override
                public String getName() {
                    return NAME;
                }
                
                @Override
                public String getDescription() {
                    return DESCRIPTION;
                }
                
                @Override
                public McpSchema.ToolResult execute(Map<String, Object> parameters) {
                    LOGGER.info("Executing ping tool with parameters: " + parameters);
                    
                    // Get the message parameter, if any
                    String message = (String) parameters.getOrDefault("message", "pong");
                    
                    // Create a response
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", message);
                    response.put("timestamp", System.currentTimeMillis());
                    
                    // Return the result
                    return new McpSchema.ToolResult(response);
                }
                
                @Override
                public Map<String, Object> getInputSchema() {
                    Map<String, Object> schema = new HashMap<>();
                    schema.put("type", "object");
                    
                    Map<String, Object> properties = new HashMap<>();
                    
                    Map<String, Object> messageProperty = new HashMap<>();
                    messageProperty.put("type", "string");
                    messageProperty.put("description", "Optional message to include in the response");
                    
                    properties.put("message", messageProperty);
                    
                    schema.put("properties", properties);
                    
                    return schema;
                }
                
                @Override
                public Map<String, Object> getOutputSchema() {
                    Map<String, Object> schema = new HashMap<>();
                    schema.put("type", "object");
                    
                    Map<String, Object> properties = new HashMap<>();
                    
                    Map<String, Object> messageProperty = new HashMap<>();
                    messageProperty.put("type", "string");
                    messageProperty.put("description", "The response message");
                    
                    Map<String, Object> timestampProperty = new HashMap<>();
                    timestampProperty.put("type", "number");
                    timestampProperty.put("description", "The timestamp of the response");
                    
                    properties.put("message", messageProperty);
                    properties.put("timestamp", timestampProperty);
                    
                    schema.put("properties", properties);
                    
                    return schema;
                }
            }
            """, packageName, packageName);
    }
}
