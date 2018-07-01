package com.tiagohs.hqr.helpers.tools

import com.tiagohs.hqr.models.database.SourceDB
import io.reactivex.Observable
import org.apache.commons.collections4.ListUtils

class ListPaginator<T> {
    var originalList: List<T> = ArrayList()

    var comicsListsByPage: List<List<T>> = ArrayList()
    var comicsList: List<T> = ArrayList()

    var hasMorePages: Boolean = false

    var source: SourceDB? = null

    var currentPage: Int = 0
    var totalPage: Int = 0

    fun onCreatePagination(originalList: List<T>, size: Int = 20): List<T> {
        this.originalList = originalList

        comicsListsByPage = ListUtils.partition<T>(originalList, size)
        totalPage = comicsListsByPage.size
        hasMorePages = currentPage < totalPage - 1

        comicsList = comicsListsByPage.get(currentPage++)

        return comicsList
    }

    fun onGetNextPageComics(): Observable<List<T>> {
        return Observable
                .create<List<T>>({ emitter ->
                    if (currentPage < totalPage - 1) {
                        hasMorePages = ++currentPage < totalPage - 1
                        comicsList = ListUtils.union(comicsList, comicsListsByPage.get(currentPage))

                        emitter.onNext(comicsList)
                    }

                    emitter.onComplete()
                })
    }
}