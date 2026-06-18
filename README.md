# PawVet - Gestión Veterinaria 🐾

Aplicación Android desarrollada con **Jetpack Compose** bajo la arquitectura **MVVM**, diseñada para la gestión de mascotas, citas médicas y consultas inteligentes.

## 🚀 Requisitos del Proyecto (Semana 14)
- **Interfaz:** 100% Jetpack Compose (Lista, Detalle, Formulario, Asistente).
- **Navegación:** Navigation Compose con paso de argumentos (ID).
- **Arquitectura:** MVVM (Model-View-ViewModel).
- **Persistencia:** Base de datos local con **Room** (CRUD completo).
- **API:** Consumo de API externa de razas con **Retrofit**.
- **Patrón Repository:** Única fuente de datos para los ViewModels.

## 📂 Estructura del Proyecto

### 1. Data Layer (`com.example.pawvet_1.data`)
- **`local/`**: Configuración de Room.
    - `model/`: Entidades de la base de datos (`Mascota`, `Cita`).
    - `dao/`: Interfaces con las consultas SQL.
    - `PawVetDatabase.kt`: Singleton de la base de datos.
- **`remote/`**: Consumo de API con Retrofit (Dog API para el Asistente).
- **`repository/`**: Clases que conectan los datos (local/remoto) con el ViewModel.

### 2. UI Layer (`com.example.pawvet_1.ui`)
- **`viewmodel/`**: Lógica de negocio y manejo de `UiState` usando `StateFlow`.
- **`screens/`**: Vistas (Composables) que observan el estado.
    - `home/`: Pantalla principal de navegación.
    - `mascotas/`: CRUD de mascotas (Lista, Detalle, Registro).
    - `citas/`: Gestión de citas veterinarias.
    - `consultas/`: Asistente Virtual (PawBot AI) que consume la API.
- **`components/`**: Componentes visuales reutilizables.

### 3. Navigation (`com.example.pawvet_1.navigation`)
- **`PawVetNavGraph.kt`**: Grafo de navegación que define las rutas y argumentos de la app.

## 🛠️ Tecnologías Usadas
- **Kotlin** & **Coroutines**
- **Jetpack Compose** (UI moderna)
- **Room** (Base de datos local)
- **Retrofit** & **Gson** (Consumo de API)
- **Navigation Compose**
- **Material 3** (Diseño)

---
Desarrollado para el curso de Desarrollo de Aplicaciones Móviles.
