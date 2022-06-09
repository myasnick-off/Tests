package com.geekbrains.tests.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.geekbrains.tests.databinding.ListItemBinding
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.view.search.SearchResultAdapter.SearchResultViewHolder

internal class SearchResultAdapter : RecyclerView.Adapter<SearchResultViewHolder>() {

    private var results: List<SearchResult> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultViewHolder {
        return SearchResultViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: SearchResultViewHolder,
        position: Int
    ) {
        holder.bind(results[position])
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun updateResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    internal class SearchResultViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(searchResult: SearchResult) {
            binding.repositoryName.text = searchResult.fullName
            binding.repositoryName.setOnClickListener {
                Toast.makeText(itemView.context, searchResult.fullName, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
