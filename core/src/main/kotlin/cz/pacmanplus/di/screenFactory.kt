package cz.pacmanplus.di

import cz.pacmanplus.Navigator
import cz.pacmanplus.RootUI
import cz.pacmanplus.screens.EditorScreen
import cz.pacmanplus.screens.GameScreen
import cz.pacmanplus.screens.IntroScreen
import cz.pacmanplus.screens.LoadingScreen
import cz.pacmanplus.screens.MenuScreen
import org.koin.dsl.module

val screenFactory = module {
    single<RootUI> { RootUI() }
    single<Navigator> { Navigator() }
    single<IntroScreen> {
        IntroScreen()
    }
    single<LoadingScreen> {
        LoadingScreen()
    }
    single<MenuScreen> {
        MenuScreen()
    }

    single<GameScreen> {
        GameScreen()
    }

    single<EditorScreen> {
        EditorScreen()
    }
}
