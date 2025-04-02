package cz.pacmanplus.di

import cz.pacmanplus.Navigator
import org.koin.dsl.module

val accessories = module {
    single<Navigator> {
        Navigator()
    }
}

