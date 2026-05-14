# Arogya-Nidhi: Comprehensive Healthcare Access Portal

**Arogya-Nidhi** is a modern Android application designed to bridge the gap between citizens and critical healthcare services. By leveraging real time location data and a centralized repository of healthcare benefits, the platform empowers users to make informed medical decisions instantly.

##  Key Features

* **Smart Hospital Locator:** Integrates Google Maps SDK to provide real-time navigation and detailed information for nearby medical facilities.
* **Healthcare Schemes Catalog:** A dynamic, searchable repository of government healthcare benefits and insurance programs.
* **Advanced Eligibility Engine:** Custom-built logic module that filters healthcare schemes based on user-specific demographics and socioeconomic data.
* **Secure User Management:** Robust authentication and profile management powered by Firebase and Firestore.
* **Offline-First Architecture:** Utilizes Room Database for local caching, ensuring access to critical data even in low-connectivity areas.

##  System Architecture

The project is built using the **MVVM (Model-View-ViewModel)** architectural pattern. This ensures a clean separation of concerns, making the codebase scalable and maintainable.



* **UI Layer:** Developed entirely with **Jetpack Compose** for a fluid, reactive user interface following Material 3 design guidelines.
* **Domain Layer:** Contains the business logic for eligibility checking and hospital data processing.
* **Data Layer:** Implements the **Repository Pattern** to manage data flow between Firebase, local SQLite (Room), and remote APIs.


## 🛠️ Technical Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose
* **Dependency Injection:** Hilt (Dagger)
* **Local Database:** Room
* **Backend Services:** Firebase (Auth, Firestore, Storage)
* **API Integration:** Google Maps SDK, Fused Location Provider
* **Asynchronous Programming:** Kotlin Coroutines & Flow

## ⚙️ Installation & Setup

To run this project locally, you will need **Android Studio** (Ladybug or newer) and an active Firebase project.

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/sufyanmd/Arogya-Nidhi.git
    ```

2.  **Firebase Configuration:**
    * Place your `google-services.json` file in the `app/` directory.

3.  **API Key Configuration:**
    * This project uses a secure secrets management system. Create a `secrets.properties` file in the root directory:
    ```properties
    FIREBASE_API_KEY=your_actual_api_key_here
    ```

4.  **Build & Run:**
    * Sync the project with Gradle files and deploy to an emulator or physical device (Min API: 26).

---
## Download App
Click the badge below to download the latest stable APK:

[![Download APK] 📥](https://github.com/Sufyanmd/Arogya-Nidhi/releases/tag/v1.0.0)

> **Note:** You may need to enable "Install from Unknown Sources" in your Android settings to install this build.
