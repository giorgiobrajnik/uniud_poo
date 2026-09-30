# POO 2026 - Multi-Project Java Workspace

This is a multi-project Java workspace organized with Maven for the Programming course 2026.

## Project Structure

```
poo-2026/
├── esempio-pizzeria/          # First project: Pizza manager application
│   ├── src/
│   │   ├── main/java/        # Production source code
│   │   └── test/java/        # Test source code
│   ├── pom.xml               # Maven configuration
│   └── target/               # Build output (auto-generated)
├── pom.xml                   # Root POM (optional, for multi-module)
├── .gitignore                # Git ignore rules
├── README.md                 # This file
└── poo-2026.code-workspace   # VS Code workspace file
```

## Getting Started

### 1. Open the Workspace in VS Code

```bash
code poo-2026.code-workspace
```

Or in VS Code: `File` → `Open Workspace from File` → Select `poo-2026.code-workspace`

### 2. Install Required Extensions

When opening the workspace, VS Code will recommend installing:
- **Extension Pack for Java** (Red Hat)
- **Maven for Java** (Microsoft)
- **Test Runner for Java** (Microsoft)
- **Debugger for Java** (Microsoft)

### 3. Build the Project

```bash
mvn clean compile
```

### 4. Run Tests

```bash
mvn test
```

Or use the VS Code Test Explorer (in the left sidebar).

### 5. Run the Application

```bash
mvn exec:java -Dexec.mainClass="PizzaManagerDemo"
```

## Adding New Projects

To add a new independent project:

1. Create a new directory in the root (e.g., `progetto-2`)
2. Create the Maven structure:
   ```bash
   mkdir -p progetto-2/src/{main/java,test/java}
   ```
3. Create a `pom.xml` file (use [pom.xml](esempio-pizzeria/pom.xml) as a template)
4. Update `poo-2026.code-workspace` to include the new folder:
   ```json
   {
     "folders": [
       { "path": "esempio-pizzeria", "name": "Esempio Pizzeria" },
       { "path": "progetto-2", "name": "Progetto 2" }
     ]
   }
   ```

## Maven Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Remove build artifacts |
| `mvn compile` | Compile source code |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn install` | Install to local repository |
| `mvn clean install` | Full build cycle |

## Java Version

This project uses Java 11. Ensure you have JDK 11 or later installed:

```bash
java -version
javac -version
```

## Testing

Tests are located in `src/test/java/`. They use JUnit 5.

Run specific test:
```bash
mvn test -Dtest=MenuItemTest
```

Run all tests:
```bash
mvn test
```

## Troubleshooting

### Java Extension Not Found
Make sure to install the Extension Pack for Java. Go to Extensions (Ctrl+Shift+X) and search for "Extension Pack for Java".

### Maven Not Found
Install Maven:
```bash
# Linux/macOS
brew install maven  # or apt-get install maven

# Windows
choco install maven
```

Verify: `mvn -version`

### Classes Not Found During Compilation
Run `mvn clean compile` to rebuild the entire project.

### Tests Not Running
1. Ensure test files are in `src/test/java/`
2. Test class names should end with `Test`
3. Test methods should be annotated with `@Test` (JUnit 5)

## Useful Links

- [Maven Documentation](https://maven.apache.org/)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [VS Code Java Extension](https://code.visualstudio.com/docs/languages/java)
