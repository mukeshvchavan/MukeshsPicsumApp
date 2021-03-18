package com.example.lorempicsum

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {

    /**
     * Holds reference of the tag value to show activity name in log.
     */
    private val TAG = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG, "onCreate()")
        inItUI()
    }

    /**
     * Initialize UI.
     */
    private fun inItUI() {
        Log.v(TAG, "inItUI()")

        /**
         * Check network availability.
         */
        if (isNetworkAvailable()) {
            Log.v(TAG, "inItUI() :: Network available")
            downloadData()
        } else {
            Log.v(TAG, "inItUI() :: Network not available")
            showToast(getString(R.string.internet_not_available))
        }
    }

    override fun getLayoutResource(): Int {
        Log.v(TAG, "getLayoutResource()")
        return R.layout.activity_main
    }

    override fun getToolbarTitle(): String? {
        Log.v(TAG, "getToolbarTitle()")
        return getString(R.string.toolbar_title)
    }

    /**
     * Download data from server.
     */
    private fun downloadData() {
        Log.v(TAG, "downloadData()")
        val request = ServiceBuilder.buildService(PicsumInterface::class.java)
        showProgressDialog()
        val call = request.getImages()
        call.enqueue(object : Callback<List<ImageData>> {
            override fun onResponse(
                call: Call<List<ImageData>>,
                response: Response<List<ImageData>>
            ) {
                Log.v(
                    TAG,
                    String.format(
                        "downloadData() :: onResponse() :: response.isSuccessful = %b",
                        response.isSuccessful
                    )
                )
                dismissProgressDialog()

                if (response.isSuccessful) {
                    recyclerView?.apply {
                        setHasFixedSize(true)
                        layoutManager = GridLayoutManager(this@MainActivity, 2)
                        adapter = ImageAdapter(response.body()!!.toList())
                    }
                }
            }

            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                Log.v(TAG, "onFailure()")
                dismissProgressDialog()
                showToast(t.message.toString())
            }
        })
    }
}