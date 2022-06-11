package com.geekbrains.tests.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.tests.R
import com.geekbrains.tests.databinding.ActivityMainBinding
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.presenter.search.ScreenState
import com.geekbrains.tests.presenter.search.SearchViewModel
import com.geekbrains.tests.view.details.DetailsActivity
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private val adapter = SearchResultAdapter()
    private var totalCount: Int = 0

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setUI()
        initViewModel()
    }

    override fun setUI() {
        binding?.toDetailsActivityButton?.setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setSearchButtonListener()
        setRecyclerView()
    }

    private fun initViewModel() {
        viewModel.subscribeToLiveData().observe(this) { onStateChange(it) }
    }

    private fun onStateChange(state: ScreenState) {
        when(state) {
            is ScreenState.Working -> {
                displayLoading(false)
                state.searchResponse.apply {
                    displaySearchResults(searchResults!!, totalCount!!)
                }
            }
            is ScreenState.Error -> {
                displayLoading(false)
                displayError(state.error.message)
            }
            ScreenState.Loading -> displayLoading(true)
        }
    }

    private fun setRecyclerView() {
        binding?.let { binding ->
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = adapter
        }
    }

    private fun setSearchButtonListener() {
        binding?.searchButton?.setOnClickListener {
            val query =  binding?.searchEditText?.text.toString()
            if (query.isNotBlank()) {
                viewModel.searchGitHub(query)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.enter_search_word),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setQueryListener() {
        binding?.let { binding ->
            binding.searchEditText.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = binding.searchEditText.text.toString()
                    if (query.isNotBlank()) {
                        viewModel.searchGitHub(query)
                        return@OnEditorActionListener true
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            getString(R.string.enter_search_word),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@OnEditorActionListener false
                    }
                }
                false
            })
        }
    }


    override fun displaySearchResults(searchResults: List<SearchResult>, totalCount: Int) {
        binding?.let { binding ->
            binding.totalCountTextView.visibility = View.VISIBLE
            binding.totalCountTextView.text =
                String.format(Locale.getDefault(), getString(R.string.results_count), totalCount)
        }
        this.totalCount = totalCount
        adapter.updateResults(searchResults)
    }

    override fun displayError(error: String?) {
        Toast.makeText(this, error ?: getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(isVisible: Boolean) {
        binding?.let { it.progressBar.isVisible = isVisible }
    }

    companion object {
        const val FAKE = "FAKE"
    }
}
