package com.example.lorempicsum

import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * BaseActivity for all child activity.
 */
abstract class BaseActivity: AppCompatActivity() {

    /**
     * Holds reference of the tag value to show activity name in log.
     */
    private val TAG = BaseActivity::class.java.name

    /**
     *  Progress dialog instance to show downloading progress.
     */
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(getLayoutResource())
        configureToolbar()
    }

    abstract fun getLayoutResource(): Int
    abstract fun getToolbarTitle(): String?

    /**
     * Configure toolbar for the activity.
     */
    private fun configureToolbar() {
        Log.v(TAG, "configureToolbar()")
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = getToolbarTitle()
    }

    /**
     * Display progress dialog.
     */
    fun showProgressDialog() {
        Log.v(TAG, "showProgressDialog()")
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        if (!progressDialog!!.isShowing) {
            progressDialog!!.setMessage("Please wait....")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }
    }

    /**
     * Dismiss progress dialog.
     */
    fun dismissProgressDialog() {
        Log.v(TAG, "dismissProgressDialog()")
        if (progressDialog != null) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }

    /**
     * Show toast message utility.
     */
    fun showToast(message: String) {
        Log.v(TAG, "showToast()")
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Check network availability.
     */
    fun isNetworkAvailable(): Boolean {
        Log.v(TAG, "isNetworkAvailable()")
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        Log.v(TAG, String.format("isNetworkAvailable() :: isConnected = %b", isConnected))
        return isConnected
    }
}