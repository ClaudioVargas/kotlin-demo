// Archivo de build raíz: aquí solo se DECLARAN los plugins.
// Se aplican luego en cada módulo (app/build.gradle.kts).
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}