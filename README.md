# Satellite Tracker App

## Architecture

- **Presentation Layer**: Comprises Jetpack Compose UI components and ViewModels handling UI logic.
- **Domain Layer**: Contains business logic, repository interfaces.
- **Data Layer**: Implements data sources, repositories, and handles data fetching.
- **Dependency Injection**: Managed using Hilt to provide dependencies across different layers efficiently.
