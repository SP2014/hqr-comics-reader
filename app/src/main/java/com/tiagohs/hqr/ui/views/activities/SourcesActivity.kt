package com.tiagohs.hqr.ui.views.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.tiagohs.hqr.R
import com.tiagohs.hqr.models.database.CatalogueSource
import com.tiagohs.hqr.models.database.SourceDB
import com.tiagohs.hqr.ui.adapters.SourceSection
import com.tiagohs.hqr.ui.callbacks.ISourcesCallback
import com.tiagohs.hqr.ui.contracts.SourcesContract
import com.tiagohs.hqr.ui.views.config.BaseActivity
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_sources.*
import javax.inject.Inject


class SourcesActivity: BaseActivity(), ISourcesCallback, SourcesContract.ISourcesView {

    companion object {

        fun newIntent(context: Context?): Intent {
            return Intent(context, SourcesActivity::class.java)
        }

    }

    override fun onGetLayoutViewId(): Int = R.layout.activity_sources
    override fun onGetMenuLayoutId(): Int  = 0

    @Inject
    lateinit var presenter: SourcesContract.ISourcesPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getApplicationComponent()!!.inject(this)

        setScreenTitle(getString(R.string.sources_activity_title))

        presenter.onBindView(this)
        presenter.getAllSources()
    }

    override fun onErrorAction() {
        presenter.getAllSources()

        snack?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onUnbindView()
    }

    override fun onBindSources(catalogueSources: List<CatalogueSource>) {
        val sectionAdapter = SectionedRecyclerViewAdapter()

        catalogueSources.forEach { catalogueSource: CatalogueSource ->
            sectionAdapter.addSection(SourceSection(this, catalogueSource.language, catalogueSource.sourceDBS , this))
        }

        sourcesList.adapter = sectionAdapter
        sourcesList.layoutManager = LinearLayoutManager(this)
    }

    override fun onSourceSelect(sourceDB: SourceDB) {
        finish()
    }

}