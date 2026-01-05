package com.example.monitor.ui.expenses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import com.example.monitor.data.model.Expense
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monitor.R
import com.example.monitor.adapter.ExpenseAdapter
import com.example.monitor.data.model.Category
import com.example.monitor.viewmodel.ExpenseViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ExpensesFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()
    private lateinit var expenseAdapter: ExpenseAdapter
    private var categoriesMap = mutableMapOf<String, Category>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyStateText: TextView
    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expenses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewExpenses)
        emptyStateText = view.findViewById(R.id.emptyStateText)
        fab = view.findViewById(R.id.fabAddExpense)

        setupRecyclerView()
        observeData()

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_expensesFragment_to_addExpenseFragment)
        }
    }
    private fun showExpenseOptionsDialog(expense: Expense) {
        val options = arrayOf("Edytuj", "Usuń", "Anuluj")

        AlertDialog.Builder(requireContext())
            .setTitle("Wydatek")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> editExpense(expense)
                    1 -> deleteExpense(expense)
                    2 -> dialog.dismiss()
                }
            }
            .show()
    }


    private fun setupRecyclerView() {
        expenseAdapter = ExpenseAdapter(categoriesMap) { expense ->
            showExpenseOptionsDialog(expense)
        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = expenseAdapter
    }

    private fun deleteExpense(expense: Expense) {
        viewModel.deleteExpense(expense)
        Toast.makeText(requireContext(), "Wydatek usunięty", Toast.LENGTH_SHORT).show()
    }

    private fun editExpense(expense: Expense) {
        val bundle = Bundle().apply {
            putString("expenseId", expense.expenseId)
        }
        findNavController().navigate(
            R.id.action_expensesFragment_to_addExpenseFragment,
            bundle
        )
    }

    private fun observeData() {
        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categoriesMap.clear()
            categories.forEach { category ->
                categoriesMap[category.categoryId] = category
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                viewModel.getExpensesByUserId(it.userId).observe(viewLifecycleOwner) { expenses ->
                    expenseAdapter.submitList(expenses)

                    if (expenses.isEmpty()) {
                        emptyStateText.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    } else {
                        emptyStateText.visibility = View.GONE
                        recyclerView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}