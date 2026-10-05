# 📲 Guía definitiva: Instalar la app en tu celular personal

Esta guía explica **con todo detalle** cómo instalar **Android Demo Kotlin** en tu teléfono. Está escrita para **Windows + celular Android** (Samsung, Xiaomi, Motorola, Pixel, etc.).

---

## 🎯 ¿Qué vas a lograr?

1. Conectar tu celular a la PC por USB.
2. Compilar la app (generar el APK) en la PC.
3. Instalarla en tu teléfono.
4. Probar las **9 demos** directamente en tu dispositivo (cámara, acelerómetro, notificaciones…).

> 💡 La app **no estará en Play Store**: se instala vía **APK propio**, como se hace en desarrollo.

---

## 📋 Requisitos

| Requisito | Detalle | ¿Cómo lo verifico? |
|---|---|---|
| Celular Android | **Versión 8.0 (API 26) o superior** — revisa en *Ajustes → Acerca del teléfono → Versión de Android* | Ya está, casi todo teléfono moderno lo cumple |
| Cable USB | Que **transfiera datos** (no solo carga). Prueba el puerto con otro archivo si falla | Al conectar, el celular pregunta "¿Permitir acceso a datos?" |
| PC | Windows 10/11 | — |
| Espacio en disco | ~2.5 GB libres (JDK + SDK + Gradle) | — |
| Internet | Para el primer build (descarga Gradle/dependencias) y para el **Demo 5 (Red)** | — |

---

## 🧭 Vista general de los pasos

```
Paso 1  → Preparar el CELULAR (modo desarrollador + depuración USB)
Paso 2  → Preparar la PC (JDK 17 + Android Studio/SDK + variables)
Paso 3  → Conectar el celular y verificar que adb lo detecta
Paso 4  → Compilar la app (APK)
Paso 5  → Instalar la app en el celular
Paso 6  → Abrir y probar las 9 demos
Paso 7  → (Alternativa) Instalar el APK sin cable
```

---

## Paso 1 — Preparar el teléfono (10 minutos)

### 1.1 Activar "Opciones de desarrollador"

1. Abre **Ajustes** en tu teléfono.
2. Ve a **Acerca del teléfono** (en algunos: **Acerca del dispositivo** o **Ajustes → Sistema**).
3. Busca el campo **"Número de compilación"** (o *Build number*).
4. **Toca 7 veces seguidas** sobre él. Verás un mensaje: *"Ya estás en modo desarrollador"* / *"You are now a developer"*.

> 📌 Rutas por marca (si no la encuentras):
> - **Samsung:** Ajustes → Acerca del teléfono → Información de software → Número de compilación
> - **Xiaomi/Redmi (HyperOS/MIUI):** Ajustes → Sobre el teléfono → toca "Versión de HyperOS/MIUI" varias veces → pide tu cuenta Mi (a veces pide SIM)
> - **Motorola:** Ajustes → Acerca del teléfono → Número de compilación
> - **Pixel / Stock Android:** Ajustes → Acerca del teléfono → Número de compilación

### 1.2 Activar "Depuración USB"

1. En **Ajustes**, busca la nueva sección **"Opciones de desarrollador"** (o *Developer options*).
2. Activa el interruptor general si aparece (verás un aviso de advertencia → **Aceptar**).
3. Dentro, activa **"Depuración USB"** (*USB debugging*).
4. *(Opcional pero recomendado)* Deja desactivado **"Depuración USB (ajustes de seguridad)"** y **"Permanecer activo"** salvo que sepas lo que haces.

### 1.3 Tener en cuenta para marcas específicas

- **Xiaomi/Redmi/Poco:** además, en *Developer options*, activa **"Depuración por USB (Ajustes de seguridad)"** y asegúrate de iniciar sesión con tu cuenta Mi (en algunos modelos sin SIM cuesta).
- **Samsung:** si el PC no lo detecta, instala el driver **Samsung USB Driver** (o abre Samsung Smart Switch y cierra).
- **Oppo/Realme/Vivo:** pueden requerir **"Instalación vía USB"** activada y a veces un código/permiso especial el primer día.

### 1.4 (Opcional) Desbloquear y mantener encendido

- Con la pantalla **desbloqueada** y el celular **encendido** durante toda la instalación.
- En *Developer options* puedes activar **"Permanecer activo"** (Screen stay awake) para que no se apague mientras trabajas.

---

## Paso 2 — Preparar la PC

Necesitas dos cosas en la PC: **JDK 17** y **Android Studio (con SDK 35)**. Si ya instalaste todo y `java -version` y `adb version` responden, **salta a la Paso 3**.

### 2.1 Instalar JDK 17 (solo si no lo tienes)

1. Descarga **Eclipse Temurin 17 (.msi, Windows x64)**: https://adoptium.net/es/temurin/releases/?version=17
2. Instálalo dejando las opciones por defecto (marca también "Set JAVA_HOME variable" si te lo ofrece).
3. Abre **PowerShell** y verifica:

   ```powershell
   java -version
   ```

   Debes ver algo como: `openjdk version "17.0.x" ...`

### 2.2 Instalar Android Studio + SDK

1. Descarga desde **https://developer.android.com/studio** e instala.
2. En el primer arranque, el **SDK Manager** instalará lo básico. Para este proyecto necesitas **Platform 35** y **Build-Tools**: puedes añadirlos después desde **More Actions → SDK Manager**:
   - ✅ **Android SDK Platform 35**
   - ✅ **Android SDK Build-Tools 35.x**
   - ✅ **Android SDK Platform-Tools** (contiene `adb`)
3. Configura la variable de entorno `ANDROID_HOME` en PowerShell:

   ```powershell
   [Environment]::SetEnvironmentVariable("ANDROID_HOME", "C:\Users\darus\AppData\Local\Android\Sdk", "User")
   [Environment]::SetEnvironmentVariable("Path", $env:Path + ";%ANDROID_HOME%\platform-tools", "User")
   ```
4. Abre una **nueva** ventana de PowerShell y verifica:

   ```powershell
   adb version
   ```

   → Si responde con la versión de Android Debug Bridge, todo listo. ✅

> 📌 **Nota:** si ya usas Android Studio con otro SDK (ej. API 34), el proyecto tolera una bajada puntual de `compileSdk` (35→34) cambiando `app/build.gradle.kts`, pero lo ideal es instalar la 35.

---

## Paso 3 — Conectar el celular y verificar con `adb`

### 3.1 Conexión física

1. Conecta el celular a la PC con un **cable USB que transfiera datos**.
2. En el celular aparecerá una notificación tipo **"¿Permitir acceso a datos USB?" / "Usar USB para…"** → selecciona **"Transferencia de archivos / MTP"** (no "solo carga").
3. Con la Depuración USB ya activa, aparecerá un diálogo:
   > **"¿Permitir la depuración USB?"** con un *"Permite siempre desde este equipo"*.
   - ✅ Marca **"Permitir siempre desde este equipo"**
   - ✅ Pulsa **Permitir** (si lo cancelas, verás `unauthorized` y tendrás que repetir).

### 3.2 Verificar con adb

Abre **PowerShell** y ejecuta:

```powershell
adb devices
```

Resultados posibles:

| Salida | Significado | Qué hacer |
|---|---|---|
| `unauthorized` | El celular no ha aceptado la huella RSA | Desbloquea el celular y acepta "Permitir depuración USB" ahí |
| `offline` | Problema de cable/driver | Cambia de cable/puerto; reinicia `adb kill-server && adb start-server` |
| `R58Mxxxxxx  device` | ✅ **¡CONECTADO!** | Continúa al Paso 4 |
| *(lista vacía)* | El driver no reconoce el equipo | Revisa el Paso 3.3 y el Troubleshooting |
| `no devices/emulators found` | No hay conexión | Comprueba cable y paso 3.1 |

> 💡 Si tu teléfono pide **"¿Permitir instalar apps desconocidas?"** al instalar por USB, dale **Permitir** cuando aparezca.

### 3.3 Si la PC no reconoce el celular (drivers)

- **Windows 10/11** normalmente autodetecta. Si no:
  1. **Método rápido:** desinstala el dispositivo en *Administrador de dispositivos* → botón derecho → *Actualizar controlador* → *Buscar automáticamente*.
  2. **SDK Manager → SDK Tools → Google USB Driver** → instalar, e indicarlo manualmente en el Administrador de dispositivos.
  3. **Drivers del fabricante:**
     - Samsung → "Samsung Android USB Driver" (o instala Samsung Smart Switch una vez)
     - Xiaomi → instalar "Mi Phone Assistant" / herramienta PC Suite
     - Motorola → "Motorola Device Manager"
     - Huawei → "HiSuite"

### 3.4 Precauciones de seguridad (importante)

- El modo desarrollador con **Depuración USB activada** permite a la PC ejecutar comandos en tu teléfono.
- **Desactívalo cuando termines**: *Ajustes → Opciones de desarrollador → desactivar "Depuración USB"* (e incluso el interruptor general).
- Solo conecta a equipos de confianza. Si un día adb muestra un diálogo RSA que no esperabas, pulsa **Cancelar**.

---

## Paso 4 — Compilar la app (generar el APK)

Ruta del proyecto en la PC:

```
C:\Users\darus\proyectos\kotlin
```

### 4.1 Método A — Compilar con Android Studio (recomendado)

1. Abre **Android Studio** → **File → Open…** → selecciona `C:\Users\darus\proyectos\kotlin`.
2. Espera el **Gradle Sync** (barra inferior; la primera vez puede tardar 5–15 min descargando Gradle 8.9 y dependencias).
3. Cuando termine el sync, el proyecto estará listo. No pulses aún Run: pasamos por la consola para que entiendas cada paso (o ve directo al Paso 5).

### 4.2 Método B — Compilar por línea de comandos

1. Abre **PowerShell** en la carpeta del proyecto:

   ```powershell
   cd C:\Users\darus\proyectos\kotlin
   ```

2. Compila el APK de debug:

   ```powershell
   .\gradlew.bat assembleDebug
   ```

   - La primera vez descargará **Gradle 8.9** + librerías (10–20 min). No interrumpas.
   - Verás al final: **BUILD SUCCESSFUL**.

3. El APK generado queda en:

   ```
   C:\Users\darus\proyectos\kotlin\app\build\outputs\apk\debug\app-debug.apk
   ```

> 🔎 **Cómo comprobar que el APK existe sin buscar en carpetas:**
> ```powershell
> Get-Item app\build\outputs\apk\debug\app-debug.apk | Select-Object FullName, Length, LastWriteTime
> ```

### 4.3 Errores frecuentes al compilar

| Error | Solución |
|---|---|
| `SDK location not found` | Crea `local.properties` junto a `settings.gradle.kts` con: `sdk.dir=C:\\Users\\darus\\AppData\\Local\\Android\\Sdk` |
| `Unsupported class file major version` | Usa JDK 17 (`File → Project Structure → SDK Location → JDK`) |
| `Could not find ... edition` | Repite `.\gradlew.bat --stop` y vuelve a ejecutar, o `.\gradlew.bat clean assembleDebug` |
| Build lento en la primera vez | Normal: está descargando. Vigila la barra de progreso. |

---

## Paso 5 — Instalar la app en el celular

Elige **una** de estas 3 vías (todas instalan lo mismo):

### 5.1 Vía Android Studio (la más cómoda, botón ▶)

1. Con el proyecto abierto y sincronizado, **conecta el celular** y verifica que aparece en el desplegable superior (junto a ▶). Si no aparece, `adb devices` (Paso 3).
2. Pulsa el botón verde **▶ Run** (atajo `Shift+F10`).
3. Android Studio **compila e instala automáticamente**, abriendo la app en tu teléfono. 🎉
4. De ahora en adelante, para **reinstalar/actualizar** tras cambios: vuelve a pulsar **▶ Run**.
   - Para un redespliegue rápido sin reiniciar la Activity: usa **Apply Changes (⚡)**.

### 5.2 Vía línea de comandos (`gradlew installDebug`)

```powershell
cd C:\Users\darus\proyectos\kotlin
.\gradlew.bat installDebug
```

- Compila e instala en el único dispositivo conectado.
- Si tienes **varios dispositivos** conectados, mira el serial con `adb devices` y apunta a uno:

  ```powershell
  .\gradlew.bat installDebug -Pandroid.injected.androidSerial=SERIAL_ADB_DEVICE_001
  ```
  *(Sustituye `SERIAL_ADB_DEVICE_001` por el código que te dio `adb devices`.)*

### 5.3 Vía `adb install` manual (tú controlas el APK)

1. Compila como en el Paso 4.
2. Instala indicando la ruta:

   ```powershell
   adb install -r C:\Users\darus\proyectos\kotlin\app\build\outputs\apk\debug\app-debug.apk
   ```
   - `-r` = reinstalar/actualizar conservando datos.
3. Debes ver: **Success**.

### 5.4 Comprobar que quedó instalada y abrirla

```powershell
adb shell pm list packages | Select-String androiddemo   # lista el paquete
adb shell am start -n com.example.androiddemo/.MainActivity  # abre la app
```

---

## Paso 6 — Probar las 9 demos en tu celular

| # | Demo | Qué verás / necesitas |
|---|---|---|
| 1 | **UI Básica** | Escribe tu nombre → saludo + Toast + Snackbar |
| 2 | **ViewModel + LiveData** | Gira el teléfono: el contador **no** se reinicia |
| 3 | **Coroutines** | Tarea con barra de progreso, la UI no se congela |
| 4 | **SharedPreferences** | Guarda datos, cierra la app y vuelve: siguen ahí |
| 5 | **Red (Retrofit)** | Descarga posts de internet (activa datos/WiFi) |
| 6 | **Cámara** 📷 | Pedirá permiso de cámara → abre la cámara → muestra la miniatura |
| 7 | **Acelerómetro** 📈 | Ideal en tu celular (mejor que el emulador): mueve el teléfono y mira X/Y/Z |
| 8 | **Notificaciones** 🔔 | En Android 13+ pedirá permiso; luego verás la notificación y podrás tocarla |
| 9 | **Permisos runtime** | Te pide cámara + ubicación + audio de una vez y muestra ✅/❌ |

> ⚠️ **Importante:** en **Android 6.0+** los permisos se piden en tiempo real. Dale **Permitir** cuando la app lo solicite; si te equivocas, ve a *Ajustes → Apps → Android Demo Kotlin → Permisos* y actívalos manualmente.

---

## Paso 7 — Alternativa: instalar el APK SIN cable (por archivo)

Ideal si quieres la app en otro teléfono o no puedes conectar por USB.

1. **Compila el APK** (Paso 4) en:
   ```
   C:\Users\darus\proyectos\kotlin\app\build\outputs\apk\debug\app-debug.apk
   ```
2. **Pasa el archivo al teléfono** por el método que prefieras:
   - **USB** (modo "Transferencia de archivos"), **Google Drive / cualquier nube**, **WhatsApp / Telegram** (te lo envías a ti mismo/a), **bluetooth**, o un **pendrive OTG**.
3. En el teléfono, **toca el archivo `app-debug.apk`** descargado.
4. Android te pedirá permiso: **"¿Permitir instalar apps desconocidas?"** → abre los **Ajustes** del sistema y activa **"Permitir desde esta fuente"** para la app desde la que abres el APK (navegador, WhatsApp, etc.).
5. Vuelve al instalador → **Instalar** → **Listo / Hecho**.
6. Aparecerá el icono **🤖 Android Demo Kotlin** (carita azul) en el cajón de apps.

> 📌 El APK de **debug** funciona sin problema para uso personal. No requiere root ni nada especial.

---

## 🧹 Desinstalar la app

- **Desde el teléfono:** mantén pulsado el icono → *Desinstalar*. O *Ajustes → Apps → Android Demo Kotlin → Desinstalar*.
- **Desde la PC:**
  ```powershell
  adb uninstall com.example.androiddemo
  ```

---

## 🐛 Troubleshooting (solo la parte de instalación)

| Problema | Causa | Solución |
|---|---|---|
| `adb devices` muestra `unauthorized` | No aceptaste el diálogo RSA en el celular | Desbloquear el teléfono y pulsar "Permitir"; si no aparece, desconecta y reconecta |
| `adb devices` no muestra nada | Cable solo de carga / driver | Cable de datos; prueba otro puerto; Paso 3.3 (drivers) |
| `INSTALL_FAILED_UPDATE_INCOMPATIBLE` | Había una versión con otra firma instalada | `adb uninstall com.example.androiddemo` y vuelve a instalar |
| `INSTALL_FAILED_INSUFFICIENT_STORAGE` | Teléfono sin espacio | Libera espacio; la app ocupa ~10–20 MB |
| `INSTALL_FAILED_USER_RESTRICTED` | "Instalar vía USB" bloqueado (típico Xiaomi/Oppo) | En Developer options activa **"Instalación vía USB"** y confirma con cuenta/contraseña |
| `INSTALL_FAILED_OLDER_SDK` | El teléfono tiene Android < 8.0 | Esta app no es compatible con ese teléfono |
| `Failure [INSTALL_PARSE_FAILED...]` | APK corrupto / transferencia incompleta | Vuelve a copiar el APK (Paso 7) y reintenta |
| La PC "no lee" el teléfono | Drivers | Administrador de dispositivos → Actualizar → Google USB Driver / driver del fabricante |
| La app pide permisos y no aparecen | Ya concedidos previamente | Ajustes → Apps → Android Demo Kotlin → Permisos |

---

## ✅ Resumen final (checklist)

- [ ] Celular: Opciones de desarrollador activadas + Depuración USB ✅
- [ ] Diálogo "Permitir depuración USB" aceptado ✅
- [ ] `adb devices` muestra `device` ✅
- [ ] `.\gradlew.bat assembleDebug` → **BUILD SUCCESSFUL** ✅
- [ ] `.\gradlew.bat installDebug` (o ▶ Run) → **Success** ✅
- [ ] Icono **Android Demo Kotlin** visible en el teléfono ✅
- [ ] Las 9 demos funcionan (cámara, sensor, red, notificaciones…) ✅
- [ ] *(Opcional)* Desactivar Depuración USB al terminar ✅

¡Eso es todo! Si algo falla, revisa la fila correspondiente del Troubleshooting o consulta el `README.md` principal para montar el entorno desde cero. 🚀