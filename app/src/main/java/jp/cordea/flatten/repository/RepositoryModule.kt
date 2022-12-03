package jp.cordea.flatten.repository

import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepository(get()) }
    single { ScoreRepository(get()) }
}
