package com.tiagohs.hqr.ui.presenter

import android.util.Log
import com.tiagohs.hqr.R
import com.tiagohs.hqr.database.IComicsRepository
import com.tiagohs.hqr.helpers.tools.PreferenceHelper
import com.tiagohs.hqr.helpers.tools.getOrDefault
import com.tiagohs.hqr.interceptors.config.Contracts
import com.tiagohs.hqr.models.view_models.ComicViewModel
import com.tiagohs.hqr.models.view_models.FETCH_ALL
import com.tiagohs.hqr.models.view_models.FETCH_BY_PUBLISHERS
import com.tiagohs.hqr.models.view_models.FETCH_BY_SCANLATORS
import com.tiagohs.hqr.ui.adapters.comics.ComicItem
import com.tiagohs.hqr.ui.contracts.ListComicsContract
import com.tiagohs.hqr.ui.presenter.config.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListComicsPresenter(
        private val interceptor: Contracts.IListComicsInterceptor,
        private val comicRepository: IComicsRepository,
        private val preferenceHelper: PreferenceHelper
): BasePresenter<ListComicsContract.IListComicsView>(), ListComicsContract.IListComicsPresenter {

    override fun onBindView(view: ListComicsContract.IListComicsView) {
        super.onBindView(view)

        interceptor.onBind()
        mSubscribers.add(interceptor.subscribeComicDetailSubject()
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.toModel() }
                .subscribe({ comic ->
                    Log.d("LIST_COMICS", "Inicialização: " + comic?.comic?.name)

                    mView?.onBindItem(comic!!)
                }, { error ->
                    Log.e("LIST_COMICS", "Inicialização Falhou ", error)
                }))
    }

    override fun onGetComics(listType: String, flag: String) {

        mSubscribers.add(onCheckListType(listType, flag)
               .map { it.map { it.toModel() } }
              .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { mView!!.onBindComics(it) },
                        { error -> Log.e("List", "Error", error) }
                ))
    }

    override fun hasPagesSupport(): Boolean {
        return interceptor.hasPageSuport()
    }

    override fun getOriginalList(): List<ComicViewModel> {
        return interceptor.getOriginalList()
    }

    override fun addOrRemoveFromFavorite(comic: ComicViewModel) {
        val sourceId = preferenceHelper.currentSource().getOrDefault()

        mSubscribers.add(comicRepository.addOrRemoveFromFavorite(comic, sourceId)
                        .subscribeOn(Schedulers.io())
                        .map { it.toModel() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ mView?.onBindItem(it) },
                                { error -> Log.e("LIST", "addOrRemoveFromFavorite Falhou ", error) }))
    }

    fun onCheckListType(listType: String, flag: String): Observable<List<ComicViewModel>> {

        when (listType) {
            FETCH_ALL -> return interceptor.onGetAllByLetter(flag)
            FETCH_BY_PUBLISHERS -> return interceptor.onGetAllByPublisher(flag)
            FETCH_BY_SCANLATORS -> return interceptor.onGetAllByScanlator(flag)
        }

        return interceptor.onGetAllByLetter(flag)
    }

    override fun hasMoreComics(): Boolean {
        return interceptor.hasMoreComics()
    }

    override fun onGetMoreComics() {

        if (interceptor.hasMoreComics()) {
            mSubscribers.add(interceptor.onGetMore()
                    .subscribeOn(Schedulers.io())
                    .map { it.map { it.toModel() } }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { mView!!.onBindMoreComics(it) },
                            { error -> Log.e("List", "Error", error) }
                    ))
        }

    }

    private fun ComicViewModel.toModel(): ComicItem {
        this.favorite = comicRepository.isFavorite(pathLink!!)

        return ComicItem(this, R.layout.item_comic)
    }

}