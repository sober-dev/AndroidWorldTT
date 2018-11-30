package com.testevents.androidworld.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.testevents.androidworld.R
import com.testevents.androidworld.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {
    private var type: String = ""
    private var title: String = ""
    private var shortDescription: String = ""
    private var description: String = ""
    private var bigImage: String = ""
    private var latitude: String = ""
    private var longitude: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

        val bundle = arguments
        if (bundle != null) {
            type = bundle.getString("type", "")
            title = bundle.getString("title", "")
            shortDescription = bundle.getString("shortDescription", "")
            description = bundle.getString("description", "")
            bigImage = bundle.getString("bigImage", "")
            latitude = bundle.getString("latitude", "")
            longitude = bundle.getString("longitude", "")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Picasso.get().load(bigImage).into(iv_big_image)
        tv_title.text = title
        tv_short_description.text = shortDescription
        tv_description.text = description
        btn_show_on_map.setOnClickListener {
            navigateToMapFragment()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).fullScreenMode(false)
        if (type == "event")
            (activity as MainActivity).setActionBarTitle(getString(R.string.events))
        else if (type == "shop")
            (activity as MainActivity).setActionBarTitle(getString(R.string.shops))
    }

    private fun navigateToMapFragment() {
        val mapFragment = MapFragment()
        val bundle = Bundle()
        bundle.putString("latitude", latitude)
        bundle.putString("longitude", longitude)
        mapFragment.arguments = bundle

        fragmentManager!!.beginTransaction()
            .replace(R.id.fragment_container, mapFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null)
            .commit()
    }
}
