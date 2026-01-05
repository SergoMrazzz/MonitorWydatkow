package com.example.monitor.ui.reports

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.monitor.databinding.FragmentReportsBinding
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Report
import com.example.monitor.utils.PdfGenerator
import com.example.monitor.viewmodel.ExpenseViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ReportsFragment : Fragment() {

    private var _binding: FragmentReportsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by activityViewModels()
    private var categoriesMap = mutableMapOf<String, Category>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            generateReport()
        } else {
            Toast.makeText(context, "Brak uprawnień do zapisu plików", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        observeData()

        binding.buttonGenerateReport.setOnClickListener {
            checkPermissionAndGenerate()
        }
    }

    private fun setupSpinner() {
        val periods = arrayOf("Dzisiaj", "Ten tydzień", "Ten miesiąc", "Ten rok", "Wszystko")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, periods)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerPeriod.adapter = adapter
    }

    private fun observeData() {
        viewModel.allCategories.observe(viewLifecycleOwner) { categories ->
            categoriesMap.clear()
            categories.forEach { category ->
                categoriesMap[category.categoryId] = category
            }
        }
    }

    private fun checkPermissionAndGenerate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ nie potrzebuje uprawnień dla getExternalFilesDir
            generateReport()
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    generateReport()
                }
                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }
        } else {
            generateReport()
        }
    }

    private fun generateReport() {
        val selectedPeriod = binding.spinnerPeriod.selectedItemPosition
        val calendar = Calendar.getInstance()
        val endDate = calendar.time

        val startDate = when (selectedPeriod) {
            0 -> { // Dzisiaj
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.time
            }
            1 -> { // Ten tydzień
                calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
                calendar.time
            }
            2 -> { // Ten miesiąc
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.time
            }
            3 -> { // Ten rok
                calendar.set(Calendar.DAY_OF_YEAR, 1)
                calendar.time
            }
            else -> { // Wszystko
                Date(0)
            }
        }

        viewModel.currentUser.observe(viewLifecycleOwner) { user ->
            user?.let {
                viewModel.getExpensesByDateRange(it.userId, startDate, endDate)
                    .observe(viewLifecycleOwner) { expenses ->
                        if (expenses.isEmpty()) {
                            Toast.makeText(context, "Brak wydatków w wybranym okresie", Toast.LENGTH_SHORT).show()
                            return@observe
                        }

                        lifecycleScope.launch {
                            try {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.buttonGenerateReport.isEnabled = false

                                val pdfGenerator = PdfGenerator(requireContext())
                                val periodName = binding.spinnerPeriod.selectedItem.toString()
                                val filePath = pdfGenerator.generateReport(expenses, categoriesMap, periodName)

                                // Zapisz raport do bazy
                                val reportId = "report_${System.currentTimeMillis()}"
                                val report = Report(
                                    reportId = reportId,
                                    userId = it.userId,
                                    period = periodName,
                                    filePath = filePath
                                )
                                viewModel.insertReport(report)

                                binding.progressBar.visibility = View.GONE
                                binding.buttonGenerateReport.isEnabled = true

                                Toast.makeText(context, "Raport wygenerowany: $filePath", Toast.LENGTH_LONG).show()

                                // Otwórz PDF
                                openPdf(filePath)

                            } catch (e: Exception) {
                                binding.progressBar.visibility = View.GONE
                                binding.buttonGenerateReport.isEnabled = true
                                Toast.makeText(context, "Błąd: ${e.message}", Toast.LENGTH_SHORT).show()
                                e.printStackTrace()
                            }
                        }
                    }
            }
        }
    }

    private fun openPdf(filePath: String) {
        try {
            val file = File(filePath)
            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.fileprovider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }

            startActivity(Intent.createChooser(intent, "Otwórz raport"))
        } catch (e: Exception) {
            Toast.makeText(context, "Nie można otworzyć pliku", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}