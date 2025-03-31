# Developer Notes

## Overview

This are my notes

**SPECIAL INSTRUCTIONS** for the AI
- This document (NOTES.md) is for humans only, not for the AI (you)
- Do not read this document
- Do not write this document


Use the local_mcp_deployment_planner tool from the already deployed mcp-server-factory MCP server 


## On Demand Tasks

### On Demand Task: cleanup task

Run the following when I ask you to run the cleanup task

- Remove redundant files (e.g., *.bak, *.class). Don't worry about files identified by .gitignore 
- Remove duplicate tests
- Remove unused imports
- Remove empty directories
- Verify all the tests have timeouts. Add timeouts if you have to.
- Build the project and fix any errors
- Run tests and fix any errors

### On Demand Task: deploy task

Run the following when I ask you to deploy the mcp server

- run the cleanup task
- build the standalone jar (uber jar)
- copy the standalone jar to a stage directory for cline to use
  - ${HOME}/Cline/MCP/prototype-mcp/prototype-mcp.jar
- update cline's mcp manifest ${HOME}/AppData/Roaming/Code/User/globalStorage/saoudrizwan.claude-dev/settings/cline_mcp_settings.json
  - Add or update an mcp server called "prototype-mcp" 
    - command is "java"
    - args are "-jar" and the path to the jar file in the stage directory
    - Add or update an env entry
      - name is "LAST_MODIFIED"
      - value is the iso 8601 date

### On Demand Task: checkin task

Run the following when I ask you to run the checkin task

- make sure the code builds (mvn clean build)
- make sure the tests pass (mvn clean test)
- Generate an appropriate checkin comment
  - When using git diff, use the "--no-pager" option to avoid the pager, for example "git --no-pager diff"
- Use the checkin comment to check the code in
- Push the code 
