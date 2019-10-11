package com.example.openthedoor.view.fragments.dashboard


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openthedoor.R
import com.example.openthedoor.data.remote.openthedoor.model.data.GetChildrenData
import com.example.openthedoor.data.remote.openthedoor.model.params.SendCoordinatesParams
import com.example.openthedoor.utils.extensions.isLocationEnabled
import com.example.openthedoor.view.activities.base.BaseActivity
import com.example.openthedoor.view.adapters.ChildrenAdapter
import com.example.openthedoor.view.fragments.base.BaseFragment
import com.example.openthedoor.viewModel.UserViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    override fun getLayoutId(): Int = R.layout.fragment_home

    lateinit var childrenAdapter: ChildrenAdapter
    lateinit var userViewModel: UserViewModel
    var childrenList = ArrayList<GetChildrenData>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachViewModel()
        initChildrenAdapter()

        if (isLocationEnabled((activity as BaseActivity))) {
//            showToast("Enabled")
            userViewModel.startLocationUpdates(activity as BaseActivity)
        }
        userViewModel.getChildren(appPreferences.getUser().user.id.toString())


    }

    private fun initChildrenAdapter() {
        childrenAdapter = ChildrenAdapter(context as BaseActivity, childrenList, this)
        rvChildlist.layoutManager = LinearLayoutManager(context)
        rvChildlist.adapter = childrenAdapter
    }


    private fun attachViewModel() {
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        with(userViewModel) {
            snackbarMessage.observe(viewLifecycleOwner, Observer {
                val msg = it?.getContentIfNotHandled()
                if (!msg.isNullOrEmpty()) showToast(msg)
            })
            progressBar.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
                if (show != null)
                    showProgressDialog(show)
            })
            getChildrenResponse.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
                if (show?.data != null) {
                    if (show.data.isNotEmpty()) {
                        rvChildlist.visibility = View.VISIBLE
                        tvNotfound.visibility = View.GONE
                    } else {
                        rvChildlist.visibility = View.GONE
                        tvNotfound.visibility = View.VISIBLE
                    }
                    childrenList.clear()
                    for (temp in show.data.iterator()) {
                        childrenList.add(temp[0])
                    }
                    childrenAdapter.notifyDataSetChanged()
                }

            })

            updateLocationResponse.observe(viewLifecycleOwner, Observer {
                val show = it?.getContentIfNotHandled()
//                showToast(show.toString())
                if (show != null) {
                    val params = SendCoordinatesParams()
                    params.driver_id = appPreferences.getUser().user.id.toString()
                    params.latitude = show.latitude.toString()
                    params.longitude = show.longitude.toString()
                    userViewModel.sendCoordinates(
                        params.driver_id,
                        params.latitude,
                        params.longitude
                    )
                }
            })
        }
    }

    override fun onPause() {
        super.onPause()
        userViewModel.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        userViewModel.startLocationUpdates(activity as BaseActivity)
    }
}
