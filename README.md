# Taiga Studio 3.0

A Java Swing-based Agile project management application inspired by Taiga.io. Taiga Studio provides a comprehensive platform for managing projects, user stories, sprints, and tasks with an intuitive desktop interface.

## 📋 Features

### Project Management
- **Create and manage multiple projects** with titles and descriptions
- **Switch between projects** with an easy-to-use home panel
- **Edit and delete projects** on the fly

### Agile Workflow
- **Product Backlog**: Manage user stories with drag-and-drop prioritization
- **Sprint Planning**: Create and manage sprints with start and end dates
- **Story Management**: 
  - Create user stories with titles, descriptions, and story points
  - Assign priorities and status to stories
  - Move stories between backlog and sprints
- **Task Tracking**:
  - Break down stories into tasks
  - Drag-and-drop task reordering
  - Edit and manage task details

### Visualization
- **Burndown Charts**: Track sprint progress with visual burndown charts
- **Sprint Overview**: View and manage active sprints
- **Status Tracking**: Monitor story and task statuses


## 🏗️ Architecture

The application follows a modular architecture organized into distinct packages:

### Core Packages

- **`data`**: Domain model classes
  - `Project`: Project entity with backlog and sprints
  - `Story`: User story with points, priority, and tasks
  - `Task`: Individual task within a story
  - `Sprint`: Time-boxed iteration containing stories
  - `Backlog`: Collection of user stories
  - `Blackboard`: Singleton pattern for managing application state
  - `Developer`: Developer entity

- **`home`**: Main application entry point and home screen
  - `Main`: Application entry point and main window
  - `MainManager`: View manager for switching between panels
  - `HomePanel`: Home screen with project list
  - `StatusBarPanel`: Application status bar

- **`project`**: Project and sprint management UI
  - `ProjectPanel`: Main project view
  - `BacklogPanel`: Product backlog management
  - `SprintPanel`: Sprint creation and management
  - `BurndownChart`: Sprint burndown chart visualization
  - Listeners for project and backlog interactions

- **`stories`**: User story management
  - `NewStoryPanel`: Create new user stories
  - `EditStoryPanel`: Edit existing stories
  - `BacklogStoryTransferable`: Drag-and-drop support for stories
  - `StoryReorderHandler`: Reordering stories in backlog

- **`tasks`**: Task management
  - `TaskPanel`: Create and edit tasks
  - `TaskListPanel`: Display list of tasks
  - `TaskTransferHandler`: Drag-and-drop task reordering
  - `TaskListener`: Task interaction handling

## 🧪 Testing

The project includes two types of tests:

### Automated Unit & Integration Tests (No UI Required)
Perfect for CI/CD pipelines and automated validation:

- `TaskUnitTest`: Task creation, modification, completion tracking
- `StoryUnitTest`: Story lifecycle, task management, validation
- `BacklogUnitTest`: Backlog operations, prioritization, reordering
- `SprintUnitTest`: Sprint management, story handling, date validation
- `ProjectUnitTest`: Project management, sprint and backlog operations
- `DeveloperUnitTest`: Developer entity management
- `BlackboardUnitTest`: Singleton pattern, project management, state handling
- `ProjectWorkflowIntegrationTest`: End-to-end workflow testing

### Manual UI Tests
For visual testing and demonstrations:
- `TestHomePanel`: Home panel features

**📊 Test Reports:** Generated in `target/surefire-reports/` after running tests

## 📦 Dependencies

The project uses:
- **Java Swing**: For GUI components
- **JUnit**: For testing (via Maven Surefire)
- **Maven**: For build management

No external third-party libraries required beyond Java SE standard library.

## 👥 Authors

- **Dr. Gonzalez-Sanchez** - Project Lead & Instructor
- **Johnny Parini** - Core Development
- **Owen Sam** - Project Management Features
- **Owen Ledrick** - Project Management Features
- **Jonathan Garcia** - Story Management
- **Collin Howard** - Story & Task Features
- **Alexander Bloomer** - Task Management
- **Kevin Mokarapiromya** - Task Management
- **Finley Room** - Sprint Management

## 🔧 Development

### Project Structure

```
TaigaStudioThree/
├── pom.xml                 # Maven configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── data/       # Domain model
│   │   │   ├── home/       # Main application & home screen
│   │   │   ├── project/    # Project & sprint UI
│   │   │   ├── stories/    # Story management
│   │   │   └── tasks/      # Task management
│   │   └── resources/      # Application resources
│   └── test/
│       └── java/           # Test classes
└── target/                 # Build output
```

### Coding Guidelines

- Java 21 features and syntax
- Package-private visibility where appropriate
- Null-safe programming with `Objects.requireNonNull()`
- Singleton pattern for global state (`Blackboard`)
- Listener pattern for UI interactions
- TransferHandler for drag-and-drop functionality

## 🐛 Known Issues

Please report issues to the project maintainers.

## 🚧 Future Enhancements

Potential areas for improvement:
- Persistence layer (database or file-based storage)
- User authentication and multi-user support
- Advanced reporting and analytics
- Export functionality (PDF, CSV)
- REST API for remote access
- Customizable themes and layouts


**Version**: 3.0  
**Last Updated**: 2026

