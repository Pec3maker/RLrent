package ru.rlrent.application.projects.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.surfstudio.android.dagger.scope.PerApplication
import ru.surfstudio.practice.i_projects.ProjectsApi

@Module
class ProjectsModule {

    @Provides
    @PerApplication
    internal fun provideProjectsApi(retrofit: Retrofit): ProjectsApi {
        return retrofit.create(ProjectsApi::class.java)
    }
}
