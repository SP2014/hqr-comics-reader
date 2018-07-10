package com.tiagohs.hqr.factory

import com.tiagohs.hqr.helpers.tools.RealmUtils
import com.tiagohs.hqr.models.database.comics.Chapter
import com.tiagohs.hqr.models.database.comics.Comic
import com.tiagohs.hqr.models.view_models.ChapterViewModel
import io.realm.Realm
import io.realm.RealmList

object ChapterFactory {

    fun createChapterForRealm(chapterViewModel: ChapterViewModel, comic: Comic, realm: Realm): Chapter {
        val realmObject = realm.createObject(Chapter::class.java, RealmUtils.getDataId<Chapter>(realm))

        realmObject.apply {
            copyFromChapterViewModel(this, chapterViewModel, comic, realm)
        }

        return realmObject
    }

    fun copyFromChapterViewModel(chapter: Chapter, chapterViewModel: ChapterViewModel, comic: Comic, realm: Realm): Chapter {

        if (chapterViewModel.chapterName != null) {
            chapter.chapterName = chapterViewModel.chapterName
        }

        if (chapterViewModel.chapterPath != null) {
            chapter.chapterPath = chapterViewModel.chapterPath
        }

        chapter.comic = realm.copyToRealmOrUpdate(comic)
        chapter.lastPageRead = chapterViewModel.lastPageRead

        return chapter
    }

    fun createListOfChaptersFormRealm(listNetwork: List<ChapterViewModel>?, comic: Comic,  realm: Realm): RealmList<Chapter> {
        val list = RealmList<Chapter>()

        listNetwork?.forEach {
            val chapterLocal = realm.where(Chapter::class.java)
                    .equalTo("comic.pathLink", comic.pathLink)
                    .equalTo("chapterPath", it.chapterPath)
                    .findFirst()

            if (chapterLocal == null) {
                list.add( createChapterForRealm(it, comic, realm) )
            } else {
                list.add( realm.copyToRealmOrUpdate(chapterLocal) )
            }
        }

        return list
    }

    fun createListOfChapterViewModel(comicsDb: List<Chapter>): List<ChapterViewModel> {
        val chapterDbList = ArrayList<ChapterViewModel>()
        chapterDbList.addAll(comicsDb.map { ChapterViewModel().create(it) })

        return chapterDbList
    }

}