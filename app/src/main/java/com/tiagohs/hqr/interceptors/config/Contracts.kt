package com.tiagohs.hqr.interceptors.config

import android.content.Context
import com.tiagohs.hqr.models.view_models.ComicViewModel
import com.tiagohs.hqr.models.view_models.DefaultModelView
import com.tiagohs.hqr.ui.adapters.comics_details.ComicDetailsListItem
import io.reactivex.Observable


object Contracts {

    interface IBaseInterceptor {
        fun onUnbind()
        fun onBind()
    }

    interface IBaseComicsInterceptor {

        fun subscribeComicDetailSubject(): Observable<ComicViewModel>
    }

    interface ISourceInterceptor: IBaseInterceptor {

    }

    interface IHomeInterceptor: IBaseInterceptor, IBaseComicsInterceptor {

        fun onGetLastestComics(): Observable<List<ComicViewModel>>
        fun onGetPopularComics(): Observable<List<ComicViewModel>>

        fun onGetPublishers(): Observable<List<DefaultModelView>>

        fun onGetMorePublishers(): Observable<List<DefaultModelView>>
        fun onGetMorePopularComics(): Observable<List<ComicViewModel>>
        fun onGetMoreLastestComics(): Observable<List<ComicViewModel>>

        fun hasMorePublishers(): Boolean
        fun hasMorePopularComics(): Boolean
        fun hasMoreLastestComics(): Boolean
    }

    interface IListComicsInterceptor: IBaseInterceptor, IBaseComicsInterceptor {

        fun onGetAllByLetter(letter: String): Observable<List<ComicViewModel>>
        fun onGetAllByScanlator(scanlator: String): Observable<List<ComicViewModel>>
        fun onGetAllByPublisher(publisher: String): Observable<List<ComicViewModel>>
        fun onGetAllByGenres(genre: String): Observable<List<ComicViewModel>>

        fun onGetMore(flag: String): Observable<List<ComicViewModel>>

        fun hasMoreComics(): Boolean
        fun hasPageSuport(): Boolean
        fun getOriginalList(): List<ComicViewModel>
    }

    interface IComicsDetailsInterceptor: IBaseInterceptor, IBaseComicsInterceptor {

        fun onGetComicData(comicPath: String, sourceId: Long): Observable<ComicViewModel?>
    }

    interface ISearchInterceptor: IBaseInterceptor, IBaseComicsInterceptor {

        fun onSearchComics(query: String): Observable<List<ComicViewModel>>

        fun onGetMore(): Observable<List<ComicViewModel>>

        fun hasMoreComics(): Boolean
        fun hasPageSuport(): Boolean
        fun getOriginalList(): List<ComicViewModel>
    }

    interface IFavoritesInterceptor: IBaseInterceptor, IBaseComicsInterceptor {

        fun onSubscribeInitializa(context: Context): Observable<ComicDetailsListItem>

        fun onGetFavorites(context: Context): Observable<List<ComicDetailsListItem>>
        fun onGetMore(): Observable<List<ComicDetailsListItem>>

        fun hasMoreComics(): Boolean
        fun getOriginalList(): List<ComicDetailsListItem>
    }

}