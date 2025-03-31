# MCP Server Factory

A Java-based Model Context Protocol (MCP) server designed to help understand and build other MCP servers.

## Project Overview

This project serves as both a learning tool and a guide for building MCP servers. It focuses on providing:

1. **Understanding of MCP Components**:
   - Tools (executable functions that perform actions)
   - Resources (data sources that provide information)
   - Prompts (structured templates that generate responses)

2. **Templates and Best Practices** for building MCP servers

## Features

1. **Tools Section**:
   - `explain_concept` - Provides detailed explanations of MCP concepts (tool, resource, prompt, server, client)
   - **Task Planner Tools**:
     - `code_cleanup_planner` - Generates a task plan for code cleanup objectives
     - `feature_implementation_planner` - Generates a task plan for feature implementation objectives
     - `general_task_planner` - Generates a task plan for general objectives
     - `local_mcp_deployment_planner` - Generates a task plan for deploying local MCP servers with critical clean and test steps that abort deployment on failure

2. **Resources Section**:
   - `mcp://factory/documentation/{topic}` - Documentation on MCP topics (getting-started, best-practices, troubleshooting)

3. **Prompts Section**:
   - `tool_implementation_guide` - Step-by-step guide for implementing MCP tools in different languages

## Architecture

The MCP Server Factory is built using:
- Java with Spring Framework (not Spring Boot)
- log4j2 for logging implementation
- Maven for build management

Following the design principles:
- KISS (Keep It Simple, Stupid)
- DRY (Don't Repeat Yourself)
- YAGNI (You Aren't Gonna Need It)
- SOLID (Single responsibility, Open-closed, Liskov substitution, Interface segregation, Dependency inversion)

## Project Structure

- `src/main/java/user/jakecarr/` - Main source code
  - `.../config/` - Spring configuration
  - `.../main/` - Classes with main methods
  - `.../model/` - Domain models and interfaces
  - `.../model/impl/` - Implementations of MCP components
  - `.../service/` - Business logic and services

- `docs/api/` - API documentation
  - `prompts.md` - Documentation for supported prompts
  - `resources.md` - Documentation for supported resources
  - `tools.md` - Documentation for supported tools

## Code Quality

The codebase follows high-quality standards:

- **Well-structured code** with clear separation of concerns
- **Comprehensive documentation** with Javadoc comments for all classes and methods
- **Modular design** with abstract base classes and interfaces
- **Consistent error handling** with proper logging
- **Maintainable architecture** with extracted methods and helper functions
- **Clean code practices** including:
  - Descriptive naming conventions
  - Single responsibility principle
  - DRY (Don't Repeat Yourself) principle
  - Proper encapsulation

## Building and Running

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- MCP Java SDK 0.8.1 (located at `C:/Users/jayes/vscode/java-sdk-0.8.1/java-sdk-0.8.1.jar`)

### Building the Project

```bash
# Clone the repository
git clone https://github.com/yourusername/mcp-server-factory.git
cd mcp-server-factory

# Build the project
mvn clean package
```

### Running the Server

```bash
# Run the server
java -jar target/mcp-server-factory-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Running the Tests

```bash
# Run the tests
mvn test
```

## Development Guidelines

- Core business logic must not depend on Spring
- Spring annotations only in the config package
- No mocks in src/main/java
- No recursion
- Tests should include timeouts

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run the tests
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
