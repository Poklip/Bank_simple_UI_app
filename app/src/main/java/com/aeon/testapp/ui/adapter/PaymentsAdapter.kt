package com.aeon.testapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aeon.testapp.R
import com.aeon.testapp.data.models.Payment

class PaymentsAdapter : RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder>() {

    private var paymentsData: List<Payment> = emptyList()

    class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvId: TextView
        val tvTitle: TextView
        val tvAmount: TextView
        val tvCreated: TextView

        init {
            tvId = view.findViewById(R.id.tvId)
            tvTitle = view.findViewById(R.id.tvTitle)
            tvAmount = view.findViewById(R.id.tvAmount)
            tvCreated = view.findViewById(R.id.tvCreated)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PaymentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_payment, viewGroup, false)

        return PaymentViewHolder(view)
    }

    override fun getItemCount() = paymentsData.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        val tempAmount = paymentsData[position].paymentAmount
        val amount = if (tempAmount.toString().contains("E")) {
            convertDouble(tempAmount.toString())
        } else if (tempAmount != null && tempAmount.toString().isNotEmpty()) {
            tempAmount.toString()
        } else {
            ""
        }
        holder.tvId.text = paymentsData[position].paymentId.toString()
        holder.tvTitle.text = paymentsData[position].paymentTitle
        holder.tvAmount.text = amount
        holder.tvCreated.text = (paymentsData[position].paymentCreated ?: "0").toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(payments: List<Payment>) {
        paymentsData = payments
        notifyDataSetChanged()
    }

    private fun convertDouble(value: String): String {
        val index = value.substringAfter("E").toInt() + 1
        val resultString = (value.substringBefore("E").replace(".", ""))
        return try {
            resultString.substring(0, index) + "." + resultString.substring(index)
        } catch (_: IndexOutOfBoundsException) {
            resultString
        }
    }
}