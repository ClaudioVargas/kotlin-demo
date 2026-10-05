package com.example.androiddemo.model

/**
 * Representa un demo de la lista principal.
 * @param id identifica el demo y se usa para navegar a la Activity correcta.
 * @param title resId del título.
 * @param description resId de la descripción.
 * @param icon drawable del sistema usado como icono.
 */
data class DemoItem(
    val id: Int,
    @androidx.annotation.StringRes val title: Int,
    @androidx.annotation.StringRes val description: Int,
    @androidx.annotation.DrawableRes val icon: Int
)