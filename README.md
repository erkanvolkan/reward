## Project Overview

This Android project is designed using Kotlin  to create a clean, maintainable, and efficient architecture for displaying a list of rewards. The project implements key Android architecture components, including `Hilt` for dependency injection, `ViewModel` for UI state management, `Flow` for reactive data streams, and `Retrofit` for network operations. Below is an overview of the architecture and components used.

### Key Components

#### 1. MainActivity
- The main entry point for the UI.
- Sets up a `RecyclerView` to display rewards and uses the `AppViewModel` to observe and update the UI based on different states: 
  - **Initial**: UI is hidden.
  - **Loading**: Shows a `ProgressBar` while data is being fetched.
  - **Success**: Displays a list of rewards fetched from the backend.
  - **Error**: Shows an error message if something goes wrong.
- The edge-to-edge display support is enabled, and window insets are adjusted dynamically to handle system bars.

#### 2. AppViewModel
- Manages the UI state and business logic for fetching rewards.
- Exposes a `StateFlow` to represent different UI states (`INITIAL`, `LOADING`, `SUCCESS`, and `ERROR`).
- The `getRewards()` function makes a network request using the repository and processes the rewards data.
  - Filters out items with null or blank names.
  - Sorts the items by `listId` and `name`.
  - Groups the data by `listId` for easy categorization and display in the UI.

#### 3. AppRepository
- Provides data to the `AppViewModel` by making network requests through `Retrofit`.
- Uses `AuthService`, which defines the API endpoint to fetch rewards.
- Returns the API response as a `Flow`, enabling reactive data streams.

#### 4. Hilt Dependency Injection
- The project uses Hilt for managing dependencies, ensuring that objects like `OkHttpClient`, `Retrofit`, and `AppRepository` are injected where needed.
- `AppModule` provides singleton instances of `OkHttpClient` (with logging interceptor) and `Retrofit` for network requests.

#### 5. ItemAdapter
- A custom `RecyclerView.Adapter` used to bind the rewards data to the views in the `RecyclerView`.
- Each item in the list displays the reward's name and ID.

### Network Layer
- The project uses `Retrofit` for making network calls.
- `AuthService` defines a `GET` request to retrieve data from a specified URL.
- The response is parsed using `GsonConverterFactory` and returned as a `Flow` for further processing.

