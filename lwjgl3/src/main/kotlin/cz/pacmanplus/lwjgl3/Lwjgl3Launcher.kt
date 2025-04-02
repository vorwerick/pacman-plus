@file:JvmName("Lwjgl3Launcher")

package cz.pacmanplus.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import cz.pacmanplus.ApplicationCore

/** Launches the desktop (LWJGL3) application. */
fun main() {
    // This handles macOS support and helps on Windows.
    if (StartupHelper.startNewJvmIfRequired())
      return
    Lwjgl3Application(ApplicationCore(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("pacman-plus")
        setWindowedMode(800, 600)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
