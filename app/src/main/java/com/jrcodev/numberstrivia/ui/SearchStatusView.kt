package com.jrcodev.numberstrivia.ui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.jrcodev.numberstrivia.R
import com.jrcodev.numberstrivia.Status
import com.jrcodev.numberstrivia.databinding.ViewStatusSearchBinding
import com.jrcodev.numberstrivia.mapIntToEnum


class SearchStatusView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    var binding: ViewStatusSearchBinding

    private var startView: TextView
    private var loadingView: LinearLayout
    private var errorView: TextView
    private var resultView: TextView

    var status: Status = Status.Start
        set(value) {
            Log.i("TESTE", "$value")
            field = value

            when (status) {
                Status.Loading -> {
                    loadingView.visibility = VISIBLE
                    makeInvisible(resultView, errorView, startView)
                }
                Status.Error -> {
                    errorView.visibility = VISIBLE
                    makeInvisible(resultView, loadingView, startView)
                }
                Status.Result -> {
                    resultView.visibility = VISIBLE
                    makeInvisible(errorView, loadingView, startView)
                }
                else -> {
                    startView.visibility = VISIBLE
                    makeInvisible(resultView, errorView, loadingView)
                }
            }

            invalidate()
            requestLayout()
        }
    var label: String = ""
        set(value) {
            field = value
            binding.resultSearchStatus.text =
                context.resources.getString(R.string.search_result_label, label.replace('.', '?'))
            invalidate()
            requestLayout()
        }


    init {
        val layoutInflater = LayoutInflater.from(context)
        binding = ViewStatusSearchBinding.inflate(layoutInflater, this)
        startView = binding.startSearchStatus
        errorView = binding.errorSearchStatus
        resultView = binding.resultSearchStatus
        loadingView = binding.loadingSearchStatus

        attrs?.run {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchStatusView)
            val statusResource = typedArray.getInt(R.styleable.SearchStatusView_status, 0)
            val labelResource = typedArray.getString(R.styleable.SearchStatusView_label)
            Log.i("TESTE", "$statusResource and $labelResource")

            status = mapIntToEnum(statusResource)
            labelResource?.let { label = labelResource }

            typedArray.recycle()
        }
    }


    private fun makeInvisible(vararg views: View) {
        for (view: View in views) {
            view.visibility = View.GONE
        }
    }
}