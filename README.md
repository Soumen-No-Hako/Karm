# Karm

A Java application to create work items and project structures, and to keep track of projects and progress. Karm (derived from the Sanskrit word "Karam") is a lightweight productivity solution for organizing and finishing left-behind work.

- Homepage: https://github.com/Soumen-No-Hako/Karm
- License: Apache License 2.0

## Table of Contents

- [About](#about)
- [Key Features](#key-features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Clone & Build](#clone--build)
  - [Run](#run)
- [Usage Examples](#usage-examples)
- [Controller and URLs](#controller-and-urls)
- [Sponsors](#sponsors)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [Roadmap](#roadmap)
- [License](#license)
- [Contact](#contact)

## About

Karm helps you define projects, break them into work items, track progress, and keep a simple historical record of work. The goal is to be a minimal, practical tool to reduce context-switching and keep momentum on tasks.

## Key Features

- Create and organize projects
- Create, edit and track work items (tasks)
- Mark progress and status (e.g., todo, in-progress, done)
- Simple project structure and navigation
- Export or backup project data (planned)
- Extensible architecture to add more features (timers, priorities, integrations)

## Tech Stack

- Core: Java (JDK 11+ recommended)
- Build tools: Maven or Gradle (project-specific files determine which)
- Storage: local file-based store or embedded database (see Configuration below)
- License: Apache 2.0

> Note: The repository currently contains web/HTML resources as well. Check the code layout to identify the exact build/run steps (Maven/Gradle/IDE).

## Getting Started

These steps assume a standard Java-based project layout. If your project uses a different build system, adjust accordingly.

### Prerequisites

- Java 11 or newer (Java 17 recommended)
- Maven or Gradle (if used by project)
- Git

### Clone & Build

```bash
git clone https://github.com/Soumen-No-Hako/Karm.git
cd Karm
```

project uses Maven:

```bash
mvn clean package
```

If there is no assembled jar, open the project in your IDE (IntelliJ IDEA, Eclipse) and run the main application class.

### Run

If a runnable JAR is produced:

```bash
java -jar target/karm.jar
```

Or run from your IDE using the project's main class.

## Usage Examples

- Create a new project: use the UI
- Add work items: add tasks under a project with description
- Update status: mark tasks as in-progress or done to track progress.
- View dashboard: see a concise list of projects and outstanding tasks.

## Sponsors

If you'd like to support the development of Karm, thank you — every contribution helps.

- PayPal (PayPal.Me link): https://paypal.me/SoumenMitra2

## Configuration

Configuration options (storage backend, ports, file paths) should be located in a config file (e.g., application.properties / config.yml) or environment variables. If you prefer a specific persistence mechanism (JSON files, SQLite, H2 database), document it here once implemented.

## Contributing

Contributions are welcome! Here are suggested steps:

1. Fork the repository
2. Create a feature branch: git checkout -b feat/my-feature
3. Make your changes and add tests where applicable
4. Run existing tests: mvn test or ./gradlew test
5. Commit and push your branch, then open a Pull Request

Please follow the existing code style and add clear commit messages. For larger changes, open an issue first to discuss the approach.

## Roadmap

Planned improvements:

- Import/Export project data (JSON, CSV)
- Basic analytics / progress charts
- User accounts and multi-user collaboration
- Integrations: calendar, issue trackers
- Mobile-friendly UI

If you'd like to prioritize items, open issues or comment on existing ones.

## Issues and Support

If you run into problems or have feature requests, please open an issue on GitHub:
https://github.com/Soumen-No-Hako/Karm/issues

There are currently open issues in the repository — please check them before filing duplicates.

## License

This project is licensed under the Apache License 2.0. See the LICENSE file for details.

## Contact

Project maintained by Soumen-No-Hako — https://github.com/Soumen-No-Hako

Thank you for using Karm. Contributions and feedback are highly appreciated.
