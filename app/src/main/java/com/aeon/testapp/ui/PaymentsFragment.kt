package com.aeon.testapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aeon.testapp.App
import com.aeon.testapp.R
import com.aeon.testapp.data.models.Payment
import com.aeon.testapp.ui.adapter.PaymentsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PaymentsFragment : Fragment(R.layout.fragment_payments) {


    private val rvPayments: RecyclerView by lazy { requireActivity().findViewById(R.id.rvPayments) }
    private val btnLogout: Button by lazy {requireActivity().findViewById(R.id.btnLogout)}

    private val adapter: PaymentsAdapter by lazy { PaymentsAdapter() }
    private var paymentsData: List<Payment> = emptyList()

    private val networkScope = CoroutineScope(Dispatchers.IO)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPrefs = requireActivity().getSharedPreferences("SP", Context.MODE_PRIVATE)
        val token = arguments?.getString("token") ?: ""

        super.onViewCreated(view, savedInstanceState)
        rvPayments.layoutManager = LinearLayoutManager(context)
        rvPayments.adapter = adapter

        btnLogout.setOnClickListener {
            sharedPrefs.edit().putString("token", "").apply()
            requireActivity().recreate()
        }

        CoroutineScope(Dispatchers.Main).launch {
            networkScope.async {
                paymentsData = async {
                    getPaymentsData(token)
                }.await()
            }.join()
            adapter.setData(paymentsData)
        }
    }

    private fun getPaymentsData(token: String) : List<Payment> {
        Log.d("KVS_DEBUG", token)
        val errorResponse = listOf(Payment(0, "error", null, null))
        var response: List<Payment>
        try {
            with(App().getApi().getPayments(token).execute()) {
                response = if (body()?.paymentsList != null) {
                    body()?.paymentsList ?: errorResponse
                } else {
                    errorResponse
                }
            }
        } catch (e: Exception) {
            response = errorResponse
        }
        return response
    }
}