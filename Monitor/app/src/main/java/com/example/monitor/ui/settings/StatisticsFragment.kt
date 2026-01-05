package com.example.monitor.ui.statistics

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.monitor.R
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Expense
import com.example.monitor.viewmodel.ExpenseViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.*

class StatisticsFragment : Fragment() {

    private val viewModel: ExpenseViewModel by activityViewModels()
    private var categoriesMap = mutableMapOf<String, Category>()
    private lateinit var pieChart: PieChart
    private lateinit var totalExpensesText: TextView
    private lateinit var emptyStateText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart = view.findViewById(R.id.pieChart)
        totalExpensesText = view.findViewById(R.id.totalExpensesText)
        emptyStateText = view.findViewById(R.id.emptyStateText)

        observeData()
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
                // Statystyki za bieżący miesiąc
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                val startDate = calendar.time

                calendar.add(Calendar.MONTH, 1)
                calendar.add(Calendar.DAY_OF_MONTH, -1)
                val endDate = calendar.time

                viewModel.getExpensesByDateRange(it.userId, startDate, endDate)
                    .observe(viewLifecycleOwner) { expenses ->
                        updateStatistics(expenses)
                    }
            }
        }
    }

    private fun updateStatistics(expenses: List<Expense>) {
        if (expenses.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            pieChart.visibility = View.GONE
            totalExpensesText.text = "Łączne wydatki: 0.00 PLN"
            return
        }

        emptyStateText.visibility = View.GONE
        pieChart.visibility = View.VISIBLE

        // Suma wydatków
        val total = expenses.sumOf { it.amount }
        totalExpensesText.text = "Łączne wydatki: %.2f PLN".format(total)

        // Grupowanie po kategoriach
        val categoryExpenses = mutableMapOf<String, Double>()
        expenses.forEach { expense ->
            val current = categoryExpenses[expense.categoryId] ?: 0.0
            categoryExpenses[expense.categoryId] = current + expense.amount
        }

        // Tworzenie wykresu kołowego
        val entries = mutableListOf<PieEntry>()
        val colors = mutableListOf<Int>()

        categoryExpenses.forEach { (categoryId, amount) ->
            val category = categoriesMap[categoryId]
            val label = category?.name ?: "Nieznana"
            entries.add(PieEntry(amount.toFloat(), label))

            val color = try {
                Color.parseColor(category?.color ?: "#9E9E9E")
            } catch (e: Exception) {
                Color.GRAY
            }
            colors.add(color)
        }

        val dataSet = PieDataSet(entries, "Wydatki według kategorii")
        dataSet.colors = colors
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.WHITE

        val pieData = PieData(dataSet)
        pieChart.data = pieData
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(true)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setEntryLabelTextSize(10f)
        pieChart.animateY(1000)
        pieChart.invalidate()
    }
}