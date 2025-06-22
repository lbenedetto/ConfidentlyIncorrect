# Project Guidelines for Confidently Incorrect

## Project Overview
Confidently Incorrect is a multiplayer web-based game that tests players' knowledge and confidence in numerical estimates. The game works as follows:

1. Players create or join lobbies
2. The host starts the game, presenting questions to all players
3. Players submit numerical estimates with confidence intervals (lower and upper bounds)
4. The system scores answers based on the accuracy of estimates and the width of confidence intervals
5. Results are shown after each question, including individual and team scores
6. The game continues through multiple questions until it ends with final results

The name "Confidently Incorrect" refers to the cognitive bias where people tend to be overconfident in their estimates.

## Project Structure
- **Backend**: Kotlin with Spring Boot
- **Frontend**: TypeScript with jQuery
- **Database**: MySQL
- **Real-time Communication**: Server-Sent Events (SSE)

### Key Components:
- **Domain**: Core entities and business logic
- **Usecases**: Application services implementing game mechanics
- **Web**: Controllers and API endpoints
- **Gateway**: Data access layer

## Development Guidelines
- There are no tests to run to verify functionality, so do not bother looking for them or attempting to test your code. 
- This project is currently in YOLO mode, which means we'll worry about correctness later. 
- As long as the project compiles, we can assume it is correct enough.
- Build the project before submitting to ensure TypeScript compilation works correctly
- Follow existing code style with proper Kotlin conventions
- When modifying the frontend, make sure to run the TypeScript build process

## Running the Application
1. Start MySQL with `docker compose up`
2. For frontend development, follow the npm setup in the readme.md
3. Run the Spring Boot application
