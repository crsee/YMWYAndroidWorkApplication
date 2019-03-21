package com.liaction.ymwy.myapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.AbstractCursor
import android.database.Cursor
import android.net.Uri
import android.os.Bundle

class YMWYContentProvider : ContentProvider() {

    override fun call(method: String, arg: String?, extras: Bundle?): Bundle? {
        if (method == "send") {
            return send(arg, extras)
        }
        return super.call(method, arg, extras)
    }

    private fun send(message: String?, extras: Bundle?): Bundle {
        val bundle = Bundle()
        bundle.putString("message", "收到消息:$message")
        bundle.putBundle("extras", extras)
        return bundle
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues): Uri? {
        return null
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}

@Deprecated(message = "暂不使用", level = DeprecationLevel.HIDDEN)
class YMWYCursor(val message: String) : AbstractCursor() {
    override fun getLong(column: Int): Long {
        return column.toLong()
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getColumnNames(): Array<String> {
        return arrayOf("message")
    }

    override fun getShort(column: Int): Short {
        return column.toShort()
    }

    override fun getFloat(column: Int): Float {
        return column.toFloat()
    }

    override fun getDouble(column: Int): Double {
        return column.toDouble()
    }

    override fun isNull(column: Int): Boolean {
        return column != 0
    }

    override fun getInt(column: Int): Int {
        return column
    }

    override fun getString(column: Int): String {
        return if (column != 0) "无效请求" else message
    }
}
