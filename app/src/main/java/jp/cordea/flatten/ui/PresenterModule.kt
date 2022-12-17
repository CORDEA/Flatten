package jp.cordea.flatten.ui

import app.cash.molecule.RecompositionClock
import app.cash.molecule.moleculeFlow
import kotlinx.coroutines.flow.Flow
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presenterModule = module {
    factory(named(TAG_HOME)) {
        moleculeFlow(clock = RecompositionClock.Immediate) {
            homePresenter(get(), get())
        }
    }
    factory(named(TAG_USER)) {
        moleculeFlow(clock = RecompositionClock.Immediate) {
            userPresenter(get())
        }
    }
}
