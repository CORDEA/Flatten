package jp.cordea.flatten.ui

import app.cash.molecule.RecompositionClock
import app.cash.molecule.moleculeFlow
import org.koin.dsl.module

val presenterModule = module {
    factory {
        moleculeFlow(clock = RecompositionClock.Immediate) {
            homePresenter(get(), get())
        }
    }
}
