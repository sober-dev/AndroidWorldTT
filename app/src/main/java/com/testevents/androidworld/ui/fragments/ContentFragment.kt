package com.testevents.androidworld.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testevents.androidworld.R
import com.testevents.androidworld.models.ContentData
import com.testevents.androidworld.network.SmartBoxApiService
import com.testevents.androidworld.ui.activities.MainActivity
import com.testevents.androidworld.ui.adapters.ContentAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_content.*

class ContentFragment : Fragment() {
    private val contentData: ArrayList<ContentData> = ArrayList()
    private val events: ArrayList<ContentData> = ArrayList()
    private val shops: ArrayList<ContentData> = ArrayList()
    private val contentAdapter = ContentAdapter(contentData) { postItem: ContentData -> onItemClicked(postItem) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_tab_content.layoutManager = LinearLayoutManager(activity)
        rv_tab_content.adapter = contentAdapter

        tabhost.setup()
        tabhost.addTab(
            tabhost.newTabSpec(getString(R.string.events))
                .setContent(R.id.rv_tab_content)
                .setIndicator(getString(R.string.events))
        )
        tabhost.addTab(
            tabhost.newTabSpec(getString(R.string.shops))
                .setContent(R.id.rv_tab_content)
                .setIndicator(getString(R.string.shops))
        )
        tabhost.setOnTabChangedListener {
            showContent(it)
        }

        val tabWidget = tabhost.tabWidget
        var i = 0
        val childCount = tabWidget.childCount
        while (i < childCount) {
            tabWidget.getChildAt(i).setBackgroundResource(R.drawable.tab_bg_selector)
            i++
        }

        loadContent()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).fullScreenMode(false)
        (activity as MainActivity).setActionBarTitle(getString(R.string.app_name))
    }

    @SuppressLint("CheckResult")
    private fun loadContent() {
        SmartBoxApiService.create().getContent()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                initContent(result)
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun initContent(result: List<ContentData>) {
        events.clear()
        shops.clear()

        result.forEach {
            if (it.type == "event")
                events.add(it)
            else if (it.type == "shop")
                shops.add(it)
        }
        tabhost.currentTab = 1
        tabhost.currentTab = 0
    }

    private fun showContent(tabId: String) {
        contentData.clear()
        if (tabId == getString(R.string.events))
            contentData.addAll(events)
        else if (tabId == getString(R.string.shops))
            contentData.addAll(shops)
        contentAdapter.notifyDataSetChanged()
        rv_tab_content.scrollToPosition(0)
    }

    private fun onItemClicked(item: ContentData) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putString("type", item.type)
        bundle.putString("title", item.title)
        bundle.putString("shortDescription", item.shortDescription)
        bundle.putString("description", item.description)
        bundle.putString("bigImage", item.bigImage)
        bundle.putString("latitude", item.latitude)
        bundle.putString("longitude", item.longitude)
        detailsFragment.arguments = bundle

        fragmentManager!!.beginTransaction()
            .replace(R.id.fragment_container, detailsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }
}
