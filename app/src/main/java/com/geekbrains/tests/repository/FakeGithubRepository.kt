package com.geekbrains.tests.repository

import com.geekbrains.tests.model.SearchResponse
import retrofit2.Response

internal class FakeGithubRepository: RepositoryContract {
    override fun searchGithub(query: String, callback: RepositoryCallback) {
        callback.handleGitHubResponse(Response.success(SearchResponse(20, listOf())))
    }
}