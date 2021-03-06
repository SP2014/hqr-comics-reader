package com.tiagohs.hqr.ui.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.tiagohs.hqr.R
import com.tiagohs.hqr.models.view_models.*
import com.tiagohs.hqr.ui.adapters.SimpleItemAdapter
import com.tiagohs.hqr.ui.callbacks.ISimpleItemCallback
import com.tiagohs.hqr.ui.views.activities.ListComicsActivity
import com.tiagohs.hqr.ui.views.config.BaseFragment
import kotlinx.android.synthetic.main.fragment_comic_resume.*

private const val COMIC = "comic_link"

class ComicResumeFragment: BaseFragment() {

    companion object {
        fun newFragment(comic: ComicViewModel): ComicResumeFragment {
            val bundle = Bundle()
            bundle.putParcelable(COMIC, comic)

            val fragment = ComicResumeFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    lateinit var comic: ComicViewModel

    override fun getViewID(): Int {
        return R.layout.fragment_comic_resume
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        comic = arguments!!.getParcelable(COMIC)
    }
    override fun onErrorAction() {
        dismissSnack()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!comic.publicationDate.isNullOrEmpty()) datePublish.text = comic.publicationDate
        else datePublishContainer.visibility = View.GONE

        onBindList(comic.authors, authorsList, onAuthorSelect(), authorsContainer)
        onBindList(comic.genres, genresList, onGenreSelect())
        onBindList(comic.scanlators, scanlatorsList, onScanlatorSelect(), scanlatorsContainer)

        if (!comic.summary.isNullOrEmpty() && !comic.summary!!.equals("...")) summary.text = comic.summary
        else summary.text = getString(R.string.no_synopse)
    }

    private fun onBindList(list: List<DefaultModelView>?, listItem: RecyclerView, callback: ISimpleItemCallback, container: LinearLayout? = null) {
        if (list != null && list.isNotEmpty()) {
            listItem.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            listItem.adapter = SimpleItemAdapter(list, context, callback)
        } else {
            if (container != null)
                container.visibility = View.GONE
        }
    }

    fun onAuthorSelect(): ISimpleItemCallback {
        return object : ISimpleItemCallback {
            override fun onClick(item: DefaultModelView) {

            }
        }
    }

    fun onGenreSelect(): ISimpleItemCallback {
        return object : ISimpleItemCallback {
            override fun onClick(item: DefaultModelView) {
                startActivity(ListComicsActivity.newIntent(context, ListComicsModel(FETCH_BY_PUBLISHERS, item.name!!, item.pathLink!!)))
            }
        }
    }

    fun onScanlatorSelect(): ISimpleItemCallback {
        return object : ISimpleItemCallback {
            override fun onClick(item: DefaultModelView) {
                startActivity(ListComicsActivity.newIntent(context, ListComicsModel(FETCH_BY_SCANLATORS, item.name!!, item.pathLink!!)))
            }
        }
    }
}