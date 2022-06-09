package com.geekbrains.tests.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.tests.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.detailsFragmentContainer,
                DetailsFragment.newInstance(intent.getIntExtra(TOTAL_COUNT_EXTRA, DEFAULT_VAL))
            )
            .commitAllowingStateLoss()
    }

    companion object {

        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"
        const val DEFAULT_VAL = -1

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }
}
