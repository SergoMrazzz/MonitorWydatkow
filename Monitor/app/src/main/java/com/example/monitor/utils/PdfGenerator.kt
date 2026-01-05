package com.example.monitor.utils

import android.content.Context
import android.os.Environment
import com.example.monitor.data.model.Category
import com.example.monitor.data.model.Expense
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PdfGenerator(private val context: Context) {

    fun generateReport(
        expenses: List<Expense>,
        categories: Map<String, Category>,
        period: String
    ): String {
        val fileName = "raport_${period}_${System.currentTimeMillis()}.pdf"
        val directory = File(
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "MonitorReports"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val file = File(directory, fileName)
        val writer = PdfWriter(file)
        val pdfDocument = PdfDocument(writer)
        val document = Document(pdfDocument)

        // Tytuł
        val title = Paragraph("Raport Wydatków")
            .setFontSize(20f)
            .setBold()
            .setTextAlignment(TextAlignment.CENTER)
        document.add(title)

        val periodPara = Paragraph("Okres: $period")
            .setFontSize(12f)
            .setTextAlignment(TextAlignment.CENTER)
        document.add(periodPara)
        document.add(Paragraph("\n"))

        // Suma
        val total = expenses.sumOf { it.amount }
        val totalPara = Paragraph("Łączna suma: %.2f PLN".format(total))
            .setBold()
            .setFontSize(14f)
        document.add(totalPara)
        document.add(Paragraph("\n"))

        // Tabela wydatków
        val table = Table(UnitValue.createPercentArray(floatArrayOf(2f, 3f, 2f, 2f)))
            .useAllAvailableWidth()

        // Nagłówki
        table.addHeaderCell(
            Cell().add(Paragraph("Data")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold()
        )
        table.addHeaderCell(
            Cell().add(Paragraph("Kategoria")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold()
        )
        table.addHeaderCell(
            Cell().add(Paragraph("Kwota")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold()
        )
        table.addHeaderCell(
            Cell().add(Paragraph("Waluta")).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBold()
        )

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        expenses.forEach { expense ->
            table.addCell(Cell().add(Paragraph(dateFormat.format(expense.date))))
            table.addCell(Cell().add(Paragraph(categories[expense.categoryId]?.name ?: "Nieznana")))
            table.addCell(Cell().add(Paragraph("%.2f".format(expense.amount))))
            table.addCell(Cell().add(Paragraph(expense.currencyCode)))
        }

        document.add(table)
        document.close()

        return file.absolutePath
    }
}