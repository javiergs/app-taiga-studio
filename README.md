# Taiga Studio 3.0

**A Java Swing application for Agile project management, inspired by Taiga.**

Taiga Studio is a desktop application for exploring Agile project workflows, including projects, backlogs, user stories, sprints, and tasks. The codebase is also a useful starting point for **CSC 3100 software design, requirements, refactoring, and testing exercises**.

> **Educational project:** This repository is a Java desktop application and a potential teaching template. It is not affiliated with or endorsed by Taiga. Functionality and setup should be verified before use in a course.

## Features

- **Projects:** Create, select, edit, and delete projects.
- **Product backlog:** Create and prioritize user stories, including drag-and-drop interactions.
- **Sprint planning:** Organize stories into time-boxed sprints.
- **Tasks:** Manage tasks associated with stories and reorder them through the UI.
- **Progress views:** Explore sprint information and burndown-chart functionality.

## Requirements

- **JDK 21**
- **Apache Maven 3.x**
- A desktop environment capable of displaying **Java Swing** windows

The project uses Swing from the Java standard library and JUnit 5 for tests. The Maven configuration does not declare an external charting or AI API library.

## Build, test, and run

From the repository root:

```bash
mvn clean test
mvn package
```

Launch the desktop application after compilation:

```bash
java -cp target/classes home.Main
```

The application entry point is `home.Main`. The launch command assumes compilation succeeded and is based on the current source structure; it has **not been independently executed** as part of this documentation update. The application requires a graphical desktop session.

JUnit test reports are generated under `target/surefire-reports/`. GitHub Actions runs `mvn -B test` on pushes and pull requests to `main`, `master`, and `develop`.

## Code organization

| Package | Responsibility | Representative classes |
| --- | --- | --- |
| `data` | Project and Agile domain models; shared application state | `Project`, `Backlog`, `Sprint`, `Story`, `Task`, `Developer`, `Blackboard` |
| `home` | Application entry point, project selection, and view navigation | `Main`, `MainManager`, `HomePanel` |
| `project` | Project, backlog, sprint, and burndown views | `ProjectPanel`, `BacklogPanel`, `SprintPanel`, `BurndownChart` |
| `stories` | Story creation, editing, and drag-and-drop | `NewStoryPanel`, `EditStoryPanel`, `StoryReorderHandler` |
| `tasks` | Task views, listeners, and drag-and-drop | `TaskPanel`, `TaskListPanel`, `TaskTransferHandler` |

The `data.Blackboard` class centralizes project state using a Singleton and JavaBeans property-change notifications. Swing listeners and transfer handlers connect user interactions to application behavior. These are useful patterns to examine critically, rather than assumptions that every design choice is ideal.

## Testing

The repository includes:

- **Unit tests** for `Project`, `Sprint`, `Backlog`, `Story`, `Task`, `Developer`, and `Blackboard`.
- **Integration tests** in `src/test/java/integration/`, including `ProjectWorkflowIntegrationTest`.
- **UI-related test code**, including `TestHomePanel`, which may require a graphical environment.

The presence of tests does not imply full coverage or guarantee that every UI interaction is automated.

## Suggested CSC 3100 exercises

This application can be used as a **starting point** for project-based work, with scope adjusted to the course:

1. **Requirements:** Specify a new user story, acceptance criteria, and observable behavior.
2. **Design review:** Model selected classes and interactions in UML; identify coupling and responsibilities.
3. **Design patterns:** Evaluate the Singleton and Observer-like notification mechanisms; propose improvements with justification.
4. **Refactoring:** Remove dead/commented-out code, improve separation of concerns, and preserve behavior with regression tests.
5. **Testing:** Add JUnit tests for domain invariants, boundary cases, and cross-object workflows.
6. **Feature extension:** Prototype persistence, backlog filtering, or export functionality through a reviewed pull request.

These are **suggested activities**, not existing assignments or guaranteed implemented features.

## Development notes

- Prefer small pull requests with a clear motivation, acceptance criteria, and relevant tests.
- Keep changes to domain logic separate from Swing presentation changes when practical.
- Run `mvn test` before submitting.
- Do not assume that in-memory project data persists after the application exits; a persistence layer is a potential extension.

