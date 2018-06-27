package com.tiagohs.hqr.models.viewModels

import android.os.Parcel
import android.os.Parcelable
import com.tiagohs.hqr.helpers.tools.RealmUtils
import com.tiagohs.hqr.helpers.utils.ScreenUtils
import com.tiagohs.hqr.models.base.IComic
import com.tiagohs.hqr.models.database.DefaultModel
import com.tiagohs.hqr.models.database.SourceDB
import com.tiagohs.hqr.models.database.comics.Comic

class ComicViewModel() : Parcelable {
    var id: Long = -1L
    var name: String? = ""
    var pathLink: String? = ""
    var posterPath: String? = ""
    var summary: String? = ""
    var publicationDate: String? = ""

    var publisher: List<DefaultModel>? = null
    var genres: List<DefaultModel>? = null
    var authors: List<DefaultModel>? = null
    var scanlators: List<DefaultModel>? = null
    var chapters: List<ChapterViewModel>? = null

    var inicialized: Boolean = false
    var favorite: Boolean = false
    var lastUpdate: String? = ""

    var status: String? = ""
        set(value) {
            field = ScreenUtils.getStatusConstant(value)
        }

    var tags: List<String>? = null
    var source: SourceDB? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        name = parcel.readString()
        pathLink = parcel.readString()
        posterPath = parcel.readString()
        summary = parcel.readString()
        publicationDate = parcel.readString()
        inicialized = parcel.readByte() != 0.toByte()
        favorite = parcel.readByte() != 0.toByte()
        lastUpdate = parcel.readString()
        tags = parcel.createStringArrayList()
    }

    fun create(other: Comic): ComicViewModel {
        return ComicViewModel().apply {
            copyFrom(other)
        }
    }

    fun create(name: String?, posterPath: String?): Comic {
        return Comic().apply {
            this.id = RealmUtils.getDataId<Comic>()
            this.name = name
            this.posterPath = posterPath
        }
    }

    fun createList(others: List<Comic>): List<ComicViewModel> {
        val finalList = ArrayList<ComicViewModel>()

        others.forEach {
            finalList.add(ComicViewModel().create(it))
        }

        return finalList.toList()
    }

    fun copyFrom(other: IComic) {

        if (other.id != -1L) {
            this.id = other.id
        }

        if (other.name != null) {
            this.name = other.name
        }

        if (other.pathLink != null) {
            this.pathLink = other.pathLink
        }

        if (other.source != null) {
            this.source = other.source
        }

        if (other.posterPath != null) {
            this.posterPath = other.posterPath
        }

        if (other.summary != null) {
            this.summary = other.summary
        }

        if (other.publicationDate != null) {
            this.publicationDate = other.publicationDate
        }

        if (other.publisher != null) {
            this.publisher = other.publisher!!.toList()
        }

        if (other.genres != null) {
            this.genres = other.genres!!.toList()
        }

        if (other.authors != null) {
            this.authors = other.authors!!.toList()
        }

        if (other.scanlators != null) {
            this.scanlators = other.scanlators!!.toList()
        }

        if (other.chapters != null) {
            this.chapters = other.chapters!!.toList().map { ChapterViewModel().create(it) }
        }

        if (other.tags != null) {
            this.tags = other.tags!!.toList()
        }

        if (other.lastUpdate != null) {
            this.lastUpdate = other.lastUpdate
        }

        this.inicialized = other.inicialized
        this.favorite = other.favorite

    }

    fun copyFrom(other: ComicViewModel) {

        if (other.id != -1L) {
            this.id = other.id
        }

        if (other.name != null) {
            this.name = other.name
        }

        if (other.pathLink != null) {
            this.pathLink = other.pathLink
        }

        if (other.source != null) {
            this.source = other.source
        }

        if (other.posterPath != null) {
            this.posterPath = other.posterPath
        }

        if (other.summary != null) {
            this.summary = other.summary
        }

        if (other.publicationDate != null) {
            this.publicationDate = other.publicationDate
        }

        if (other.publisher != null) {
            this.publisher = other.publisher!!.toList()
        }

        if (other.genres != null) {
            this.genres = other.genres!!.toList()
        }

        if (other.authors != null) {
            this.authors = other.authors!!.toList()
        }

        if (other.scanlators != null) {
            this.scanlators = other.scanlators!!.toList()
        }

        if (other.chapters != null) {
            this.chapters = other.chapters!!.toList()
        }

        if (other.lastUpdate != null) {
            this.lastUpdate = other.lastUpdate
        }

        if (other.tags != null) {
            this.tags = other.tags!!.toList()
        }

        this.inicialized = other.inicialized
        this.favorite = other.favorite

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(pathLink)
        parcel.writeString(posterPath)
        parcel.writeString(summary)
        parcel.writeString(publicationDate)
        parcel.writeByte(if (inicialized) 1 else 0)
        parcel.writeByte(if (favorite) 1 else 0)
        parcel.writeString(lastUpdate)
        parcel.writeStringList(tags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ComicViewModel> {
        override fun createFromParcel(parcel: Parcel): ComicViewModel {
            return ComicViewModel(parcel)
        }

        override fun newArray(size: Int): Array<ComicViewModel?> {
            return arrayOfNulls(size)
        }
    }

}