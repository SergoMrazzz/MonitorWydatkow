package com.example.monitor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.monitor.R
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Expense
import java.text.SimpleDateFormat
import java.util.*

class ExpenseAdapter(
    private val categories: Map<String, Category>,
    private val onItemClick: (Expense) -> Unit
) : ListAdapter<Expense, ExpenseAdapter.ExpenseViewHolder>(ExpenseDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = getItem(position)
        holder.bind(expense, categories[expense.categoryId], onItemClick)
    }

    class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryIcon: TextView = itemView.findViewById(R.id.categoryIcon)
        private val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        private val expenseAmount: TextView = itemView.findViewById(R.id.expenseAmount)
        private val expenseDate: TextView = itemView.findViewById(R.id.expenseDate)

        fun bind(expense: Expense, category: Category?, onItemClick: (Expense) -> Unit) {
            categoryIcon.text = category?.icon ?: "📦"
            categoryName.text = category?.name ?: "Nieznana"
            expenseAmount.text = "%.2f %s".format(expense.amount, expense.currencyCode)

            val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            expenseDate.text = dateFormat.format(expense.date)

            itemView.setOnClickListener { onItemClick(expense) }
        }
    }

    class ExpenseDiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem.expenseId == newItem.expenseId
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }
    }
}