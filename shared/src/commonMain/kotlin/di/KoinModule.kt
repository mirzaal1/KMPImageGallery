package di

import data.repository.ImagesRepository
import data.source.ImagesDataSource
import data.source.remote.ImagesRemoteDataSource
import data.source.remote.ImagesService
import domain.mapper.ImageMapper
import domain.usecase.ImagesFetchUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import utils.getDispatcherProvider


fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    modules(sharedModules)
    appDeclaration()
}

fun initKoin() = initKoin { }

private val sharedModules: List<Module>
    get() = listOf(useCaseModule, repositoryModule, apiModule, utilityModule)


private val useCaseModule = module {
    factory { ImageMapper(get()) }
    factory { ImagesFetchUseCase() }
}

private val repositoryModule = module {
    single { ImagesRepository() }
    factory<ImagesDataSource.Remote> { ImagesRemoteDataSource(get(), get()) }
}

private val apiModule = module {
    factory { ImagesService() }
}

private val utilityModule = module {
    factory { getDispatcherProvider() }
}