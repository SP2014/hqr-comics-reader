package com.tiagohs.hqr.ui.contracts

import com.tiagohs.hqr.models.view_models.ChapterViewModel
import com.tiagohs.hqr.ui.presenter.config.IPresenter
import com.tiagohs.hqr.ui.views.config.IView

class ReaderContract {

    interface IReaderView: IView {

        fun onBindChapter(ch: ChapterViewModel?)
    }

    interface IReaderPresenter: IPresenter<IReaderView> {

        fun onGetChapterDetails(chapterPath: String, chapterName: String?)
    }
}