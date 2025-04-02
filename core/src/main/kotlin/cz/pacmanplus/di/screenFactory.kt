package cz.pacmanplus.di

import cz.pacmanplus.Navigator
import cz.pacmanplus.RootUI
import cz.pacmanplus.screens.GameScreen
import cz.pacmanplus.screens.IntroScreen
import cz.pacmanplus.screens.MenuScreen
import org.koin.dsl.module

val screenFactory = module {
    single<RootUI> { RootUI() }
    single<Navigator> { Navigator() }
    single<IntroScreen> {
        IntroScreen()
    }
    single<GameScreen> {
        GameScreen()
    }
    single<MenuScreen> { MenuScreen() }
}

