# 📱 Android Demo Kotlin

Proyecto **estándar de Android + Kotlin** con **9 demos básicos** para probar funcionalidades del sistema en tu dispositivo o emulador.

> ⚠️ **Importante:** este proyecto fue generado en una máquina **sin entorno de desarrollo instalado**. Todos los pasos necesarios (JDK, Android SDK, Android Studio, variables de entorno, comandos) están documentados en este README.

> 📲 **¿Quieres instalar la app en tu CELULAR PERSONAL?** Hay una guía dedicada y muy detallada en **[`INSTALAR_EN_CELULAR.md`](INSTALAR_EN_CELULAR.md)** (modo desarrollador, depuración USB, adb, instalación por APK y troubleshooting).

---

## ✨ ¿Qué incluye?

| # | Demo | Funcionalidad probada | Archivo principal |
|---|------|----------------------|-------------------|
| 1 | UI Básica | `TextView`, `EditText`, `Button`, `Snackbar`, `Toast` | `ui/DemoUiActivity.kt` |
| 2 | ViewModel + LiveData | Arquitectura MVVM, rotación de pantalla | `ui/DemoCounterActivity.kt` |
| 3 | Coroutines | Tareas en segundo plano (`Dispatchers.IO`) | `ui/DemoCoroutinesActivity.kt` |
| 4 | SharedPreferences | Almacenamiento local clave-valor | `ui/DemoPrefsActivity.kt` |
| 5 | Red (Retrofit) | API pública `jsonplaceholder.typicode.com` | `ui/DemoNetworkActivity.kt` |
| 6 | Cámara | Permiso `CAMERA` + `ACTION_IMAGE_CAPTURE` | `ui/DemoCameraActivity.kt` |
| 7 | Acelerómetro | Sensor `TYPE_ACCELEROMETER` en tiempo real | `ui/DemoSensorActivity.kt` |
| 8 | Notificaciones | Canal (`NotificationChannel`) + `POST_NOTIFICATIONS` | `ui/DemoNotificationActivity.kt` |
| 9 | Permisos runtime | `RequestMultiplePermissions` | `ui/DemoPermissionsActivity.kt` |

**Stack / librerías:** Kotlin 2.0.21 • AndroidX • Material 3 • ViewBinding • LiveData • StateFlow • Coroutines • Retrofit 2 • OkHttp 4 • Gson.

---

## 📋 Requisitos previos

| Dependencia | Versión mínima | Por qué |
|---|---|---|
| JDK | **17** (Temurin u Oracle) | Exigido por Android Gradle Plugin 8.x |
| Android Studio | **Hedgehog (2023.1.1) o superior** | La forma más fácil de build + emulador |
| Android SDK | API 35 | `compileSdk` / `targetSdk` del proyecto |
| Dispositivo / Emulador | Android **8.0 (API 26)** o superior | `minSdk` del proyecto |
| Conexión a internet | — | (1) primer build descarga Gradle + dependencias; (2) el Demo 5 usa una API pública |

Este README asume **Windows + PowerShell**, pero los pasos valen para cualquier SO (cambian las rutas).

---

## 🚀 Inicio rápido (resumen)

```powershell
cd C:\Users\darus\proyectos\kotlin

# 1. Build del APK de debug (solo primera vez descarga dependencias)
.\gradlew.bat assembleDebug

# 2. Con un dispositivo/emulador conectado, instalar directamente
.\gradlew.bat installDebug

# 3. O lanzar la app manualmente desde adb:
adb shell am start -n com.example.androiddemo/.MainActivity
```

El APK de debug se genera en:

```
app\build\outputs\apk\debug\app-debug.apk
```

> ⚠️ **Si no tienes JDK ni Android SDK instalados, primero sigue la sección siguiente "Instalación paso a paso".**

---

## 🛠️ Instalación paso a paso (desde cero)

### Paso 1 — Instalar JDK 17

1. Descarga **Eclipse Temurin 17** desde: **https://adoptium.net/es/temurin/releases/?version=17** (elige **Windows x64 .msi**).
2. Ejecuta el instalador `.msi`. Ruta típica:
   ```
   C:\Program Files\Eclipse Adoptium\jdk-17.0.x.x\
   ```
3. Configura las variables de entorno (PowerShell):

   ```powershell
   # JAVA_HOME apunta a la carpeta donde se instaló el JDK
   [Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Eclipse Adoptium\jdk-17.0.12_7", "User")

   # Añade el binario java al PATH
   [Environment]::SetEnvironmentVariable("Path", $env:Path + ";%JAVA_HOME%\bin", "User")
   ```

   > Edición manual: **Panel de control → Sistema → Configuración avanzada del sistema → Variables de entorno.**

4. Verifica en una ventana de PowerShell **nueva**:

   ```powershell
   java -version
   # Debe imprimir algo como: openjdk version "17.0.x" 2024-xx-xx
   ```

> 📌 **Alternativa:** si instalas Android Studio, puedes usar su **JBR (JetBrains Runtime, ya incluye JDK 17/21)** y omitir este paso. En ese caso, en Android Studio ve a `File → Project Structure → SDK Location` y marca la JDK incluida.

### Paso 2 — Instalar Android Studio y el SDK

1. Descarga e instala: **https://developer.android.com/studio**.
2. En el **primer arranque**, Android Studio abre el **SDK Manager**. Instala:
   - **Android SDK Platform 35** (Android 15) ✅
   - **Android SDK Build-Tools 35.x** ✅
   - **Android SDK Platform-Tools** (incluye `adb`) ✅
   - *(Opcional)* **Emulator** en la pestaña "SDK Tools" para crear un AVD.
3. Ruta típica del SDK:
   ```
   C:\Users\darus\AppData\Local\Android\Sdk
   ```
4. Para usar `adb` y Gradle desde la **línea de comandos**, define las variables:

   ```powershell
   [Environment]::SetEnvironmentVariable("ANDROID_HOME", "C:\Users\darus\AppData\Local\Android\Sdk", "User")
   [Environment]::SetEnvironmentVariable("Path", $env:Path + ";%ANDROID_HOME%\platform-tools", "User")
   ```
5. Verifica en una ventana **nueva**:

   ```powershell
   adb version
   # Android Debug Bridge version 1.0.4x
   ```

> 📌 Android Studio crea automáticamente el archivo `local.properties` con `sdk.dir=...`. **No lo subas a git** (está en `.gitignore`).

### Paso 3 — Preparar dónde ejecutar la app

**Opción A — Emulador (lo más cómodo):**
1. En Android Studio: **Device Manager** (icono de teléfono en la barra derecha).
2. **Create Virtual Device** → elige un modelo (p. ej. Pixel 7) → **Next**.
3. Elige una **imagen de sistema** con API **34 o 35** (p. ej. "UpsideDownCake" / "VanillaIceCream") → **Finish**.
4. Pulsa **▶** en el AVD para arrancarlo, o abre el emulador: también aparece como dispositivo en el desplegable de ejecución.

**Opción B — Teléfono físico con depuración USB:**
1. En el teléfono: **Ajustes → Acerca del teléfono** → toca **7 veces** sobre **"Número de compilación"**.
2. Ahora verás **Opciones de desarrollador** en **Ajustes → Sistema** → activa **"Depuración USB"**.
3. Conecta por USB y acepta el diálogo *"¿Permitir depuración USB?"*.
4. Verifica que el equipo lo detecta:

   ```powershell
   adb devices
   # Lista de dispositivos:
   # R58Mxxxxxxxx    device
   ```

   Si aparece `unauthorized`, acepta el diálogo en el teléfono y reintenta.

### Paso 4 — Abrir el proyecto en Android Studio

1. **File → Open…** y selecciona la carpeta:
   ```
   C:\Users\darus\proyectos\kotlin
   ```
2. Espera el **Gradle Sync** (barra inferior). La primera vez descarga Gradle 8.9 y todas las dependencias (varios minutos; si es lento, revisa **Troubleshooting**).
3. Si Android Studio muestra *"SDK location not found"*:
   **File → Project Structure → SDK Location** → pon `C:\Users\darus\AppData\Local\Android\Sdk`.

### Paso 5 — Ejecutar la app

1. En el desplegable superior elige tu **dispositivo/emulador** (o **Create New Virtual Device**).
2. Pulsa el botón verde **▶ Run** (atajo `Shift+F10`).
3. La app **Android Demo Kotlin** se instala y abre en la pantalla principal con las **9 demos**.
4. Para depurar (logs): la vista **Logcat** de Android Studio (`Alt+6`) o por consola:

   ```powershell
   adb logcat -s AndroidRuntime:E   # solo errores de la app
   adb logcat -s OkHttp:V           # ver peticiones del Demo 5 (Retrofit)
   ```

---

## 💻 Trabajar solo por línea de comandos (sin Android Studio)

El proyecto incluye el **Gradle Wrapper**, así que no necesitas Gradle global. Solo necesitas **JDK 17** + **ANDROID_HOME** (pasos 1 y 2).

| Comando | Qué hace | Dónde queda el resultado |
|---|---|---|
| `.\gradlew.bat tasks` | Lista todas las tareas disponibles | — |
| `.\gradlew.bat assembleDebug` | Compila el APK de **debug** | `app\build\outputs\apk\debug\app-debug.apk` |
| `.\gradlew.bat assembleRelease` | Compila el APK de **release** | `app\build\outputs\apk\release\app-release-unsigned.apk` |
| `.\gradlew.bat installDebug` | Compila **e instala** en el dispositivo/emulador conectado | — |
| `.\gradlew.bat clean` | Borra las carpetas `build/` | — |
| `.\gradlew.bat --stop` | Detiene el daemon de Gradle | — |

Instalar/desinstalar con `adb` directamente:

```powershell
adb install -r app\build\outputs\apk\debug\app-debug.apk   # -r = reinstalar conservando datos
adb uninstall com.example.androiddemo
adb shell am start -n com.example.androiddemo/.MainActivity  # abrir la app
adb shell am force-stop com.example.androiddemo              # cerrar la app
adb shell pm list packages | Select-String androiddemo       # confirmar si está instalada
```

Forzar un build limpio desde cero (recomendado si algo falla raro):

```powershell
.\gradlew.bat clean assembleDebug
```

> **Variable importante:** si `gradlew.bat` falla con *"SDK location not found"*, crea el archivo `local.properties` junto a `settings.gradle.kts`:
> ```properties
> sdk.dir=C\:\\Users\\darus\\AppData\\Local\\Android\\Sdk
> ```

---

## 📂 Estructura del proyecto

```
kotlin/
├── README.md                       ← este archivo
├── build.gradle.kts                ← plugins (raíz)
├── settings.gradle.kts             ← nombre del proyecto y módulos
├── gradle.properties               ← flags de la JVM / AndroidX
├── gradlew + gradlew.bat           ← Gradle Wrapper (no requieren Gradle global)
├── gradle/
│   ├── libs.versions.toml          ← CATÁLOGO DE VERSIONES (dependencias)
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties  ← fija la versión de Gradle (8.9)
└── app/
    ├── build.gradle.kts            ← configuración del módulo (SDKs, deps, viewBinding)
    ├── proguard-rules.pro
    └── src/main/
        ├── AndroidManifest.xml      ← permisos + Activities declaradas
        ├── java/com/example/androiddemo/
        │   ├── MainActivity.kt              ← pantalla principal (menú de demos)
        │   ├── model/DemoItem.kt            ← modelo de la lista principal
        │   ├── adapter/DemoAdapter.kt       ← RecyclerView de la lista principal
        │   ├── adapter/PostAdapter.kt       ← RecyclerView del Demo 5
        │   ├── network/                     ← Retrofit (ApiService, Post, RetrofitClient)
        │   ├── viewmodel/                   ← CounterViewModel, PostViewModel
        │   ├── util/NotificationHelper.kt   ← canal + notificación
        │   └── ui/                          ← las 9 Activities de demo
        └── res/
            ├── layout/              ← 12 layouts XML
            ├── values/
            │   ├── strings.xml      ← TODOS los textos de la app
            │   ├── colors.xml
            │   └── themes.xml       ← Tema Material 3 DayNight
            ├── drawable/            ← icono vectorial de la app
            └── mipmap-anydpi-v26/   ← icono adaptativo
```

---

## 🧪 Guía rápida de cada demo

1. **UI Básica** → Escribe tu nombre, pulsa "Mostrar saludo". Verás `TextView` actualizado, un `Toast` y un `Snackbar`. El botón se convierte en "Reiniciar".
2. **ViewModel + LiveData** → Pulsa `+1` / `-1`, luego **gira la pantalla**. El contador NO se reinicia porque vive en el `ViewModel`. 👈 El mejor demo para entender MVVM.
3. **Coroutines** → Pulsa "Ejecutar tarea": la UI sigue respondiendo mientras el `delay(3000)` simula trabajo en `Dispatchers.IO`. Observa la barra de progreso.
4. **SharedPreferences** → Guarda un texto, cierra la app por completo y vuelve: los datos persisten en el dispositivo.
5. **Red (Retrofit)** → Descarga ~100 posts de `https://jsonplaceholder.typicode.com/posts`. Pulsa "Reintentar" si no hay red. Los logs HTTP se ven en Logcat (tag `OkHttp`).
6. **Cámara** → Pide permiso `CAMERA` (runtime), abre la cámara del sistema y muestra la miniatura de la foto. Prueba también "denegar" la primera vez.
7. **Acelerómetro** → Mueve el teléfono y mira los valores X/Y/Z. 👉 **Recomendado en dispositivo físico** (el emulador lo simula mal).
8. **Notificaciones** → En Android 13+ pedirá permiso `POST_NOTIFICATIONS`; después creará un canal y lanzará una notificación tocable.
9. **Permisos runtime** → Solicita 3 permisos a la vez y muestra el estado de cada uno con ✅/❌.

---

## 📦 Versiones del proyecto

Todas centralizadas en `gradle/libs.versions.toml` (Gradle Version Catalog):

| Componente | Versión | Dónde se declara |
|---|---|---|
| Gradle | 8.9 | `gradle/wrapper/gradle-wrapper.properties` |
| Android Gradle Plugin | 8.7.3 | `libs.versions.toml` → `agp` |
| Kotlin | 2.0.21 | `libs.versions.toml` → `kotlin` |
| compileSdk / targetSdk | 35 | `app/build.gradle.kts` |
| minSdk | 26 | `app/build.gradle.kts` |
| JDK (jvmTarget) | 17 | `app/build.gradle.kts` |
| Material (Material 3) | 1.12.0 | `libs.versions.toml` |
| AndroidX core-ktx | 1.15.0 | `libs.versions.toml` |
| Lifecycle (ViewModel/LiveData) | 2.8.7 | `libs.versions.toml` |
| Coroutines | 1.9.0 | `libs.versions.toml` |
| Retrofit / Converter Gson | 2.11.0 | `libs.versions.toml` |
| OkHttp / Logging | 4.12.0 | `libs.versions.toml` |

> Paneles clave: **File → Project Structure** (SDK/AGP) y `libs.versions.toml` (resto de librerías).

---

## 🐛 Troubleshooting (errores comunes)

| Síntoma | Causa probable | Solución |
|---|---|---|
| `SDK location not found. Define a valid SDK location` | Falta `local.properties` o `ANDROID_HOME` | Crea `local.properties` con `sdk.dir=C:\\Users\\darus\\AppData\\Local\\Android\\Sdk` o define `ANDROID_HOME`. |
| `Unsupported class file major version 65` | Usas un JDK > 17 para Gradle | Forzar JDK 17: **File → Project Structure → SDK Location → JDK**. |
| `Could not find com.android.application:...` | Repositorios o caché corruptos | `File → Invalidate Caches / Restart` y luego `.\gradlew.bat clean`. |
| La descarga de Gradle/dependencias es muy lenta | Red/proxy | Repetir el sync; Gradle reanuda descargas. Si usas proxy, configúralo en `gradle.properties` (`systemProp.http.proxyHost=...`, `systemProp.http.proxyPort=...`). |
| `adb devices` lo muestra como `offline` | Cable/controladores | Prueba otro cable, activa "Depuración USB" de nuevo y reinicia `adb kill-server && adb start-server`. |
| El emulador no aparece | Falta imagen de sistema o HAXM/WHPX | SDK Manager → instalar imagen API 34/35; activa **Windows Hypervisor Platform** (configuración de Windows). |
| La notificación no aparece (Android 13+) | Permiso denegado | Ajustes → Apps → Android Demo Kotlin → Notificaciones → activar. |
| El Demo 5 da error de red | Sin internet, DNS o proxy | Verifica conexión; el retrofit loguea el error en Logcat. |
| Permiso de cámara denegado para siempre | "No volver a preguntar" marcado | Ajustes → Apps → Android Demo Kotlin → Permisos → habilitar manualmente. |

---

## 🚧 Siguientes pasos / ideas para ampliar

- 🔄 Migrar a **Jetpack Compose** (UI declarativa moderna).
- 🗄️ Reemplazar SharedPreferences por **DataStore** o añadir **Room** (base de datos local).
- 🖼️ Guardar la foto completa del demo 6 usando `FileProvider` + `Uri`.
- 📶 Probar **BroadcastReceiver** (detectar batería o internet).
- 📡 Añadir **WorkManager** para tareas programadas.
- 🧪 Escribir **test unitarios** (JUnit) para los ViewModels y **UI tests** (Espresso).

---

## ✅ Checklist final

- [ ] JDK 17 instalado y `java -version` funciona
- [ ] Android Studio instalado con Platform 35 + Build-Tools
- [ ] `adb devices` detecta tu teléfono o creaste un AVD
- [ ] Proyecto abierto y **Gradle Sync** completado
- [ ] `.\gradlew.bat assembleDebug` genera el APK
- [ ] `▶ Run` instala la app y ves el menú con las 9 demos

¡Disfruta probando! Si algo falla, abre un issue con el error exacto de la consola y el mensaje de Logcat. 🚀