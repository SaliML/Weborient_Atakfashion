package com.weborient.atakfashion.handlers.pdf

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.events.PdfDocumentEvent
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.*
import com.itextpdf.layout.property.*
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.api.getdata.ProductData
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object PDFHandler {
    fun generatePDF(activity: Activity, productList: ArrayList<ProductData>, date: Date) {
        try{
            //PDF könyvtár útvonal kinyerése
            val pdfFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString())

            //Fájlnév beállítása és hozzáfűzése a könyvtárhoz
            val tempPDF = "${pdfFolder}/Kiadott_termekek_${SimpleDateFormat(AppConfig.DATE_FORMAT).format(date)}_temp.pdf"
            val finalPDF = "${pdfFolder}/Kiadott_termekek_${SimpleDateFormat(AppConfig.DATE_FORMAT).format(date)}.pdf"

            val pdfWriter = PdfWriter(tempPDF)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            pdfDocument.addNewPage(PageSize.A4)
            document.topMargin *= 2f
            document.bottomMargin *= 2f

            val leading = 9f
            val newLineLeading = 0.5f
            val valueFontSize = 11f

            //Dokumentum kódolás, betűtípus beállítás
            val font: PdfFont
            activity.applicationContext.assets.open("calibri_regular.ttf").use { inStream ->
                val fontData = inStream.readBytes()
                font = PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H)
            }

            document.setFont(font)

            productList.forEach {product ->

                //Termék táblázat beállítása
                val productTable = Table(3)
                productTable.setMarginTop(10f)
                productTable.width = UnitValue.createPercentValue(100f)
                productTable.isKeepTogether = true

                val newLineParagraph = Paragraph(" ")
                newLineParagraph.setFixedLeading(newLineLeading)
                document.add(newLineParagraph)

                //Termék adatainak hozzáadása
                val productIDCell = Cell(0,1)
                val productNameCell = Cell(0,1)
                val productQuantityCell = Cell(0,1)
                val productCategoryNameCell = Cell(0,3)
                val productDescriptionCell = Cell(0,3)

                //Azonosító
                val productIDTitle = Text("Azonosító: ").setBold().setFontSize(valueFontSize)
                val productIDValue = Text(product.id).setFontSize(valueFontSize)
                val productIDParagraph = Paragraph()
                productIDParagraph.add(productIDTitle)
                productIDParagraph.add(productIDValue)
                productIDParagraph.setFixedLeading(leading)

                productIDCell.add(productIDParagraph)
                productTable.addCell(productIDCell)

                //Megnevezés
                val productNameTitle = Text("Terméknév: ").setBold().setFontSize(valueFontSize)
                val productNameValue = Text(product.name).setFontSize(valueFontSize)
                val productNameParagraph = Paragraph()
                productNameParagraph.add(productNameTitle)
                productNameParagraph.add(productNameValue)
                productNameParagraph.setFixedLeading(leading)

                productNameCell.add(productNameParagraph)
                productTable.addCell(productNameCell)

                //Mennyiség
                val productQuantityTitle = Text("Mennyiség: ").setBold().setFontSize(valueFontSize)
                val productQuantityValue = Text(product.quantity.toString()).setFontSize(valueFontSize)
                val productQuantityParagraph = Paragraph()
                productQuantityParagraph.add(productQuantityTitle)
                productQuantityParagraph.add(productQuantityValue)
                productQuantityParagraph.setFixedLeading(leading)

                productQuantityCell.add(productQuantityParagraph)
                productTable.addCell(productQuantityCell)

                //Kategória
                val productCategoryTitle = Text("Kategória: ").setBold().setFontSize(valueFontSize)
                val productCategoryValue = Text(product.categoryName).setFontSize(valueFontSize)
                val productCategoryParagraph = Paragraph()
                productCategoryParagraph.add(productCategoryTitle)
                productCategoryParagraph.add(productCategoryValue)
                productCategoryParagraph.setFixedLeading(leading)

                productCategoryNameCell.add(productCategoryParagraph)
                productTable.addCell(productCategoryNameCell)

                //Kategória
                val productDescriptionTitle = Text("Leírás: ").setBold().setFontSize(valueFontSize)
                val productDescriptionValue = Text(product.description).setFontSize(valueFontSize)
                val productDescriptionParagraph = Paragraph()
                productDescriptionParagraph.add(productDescriptionTitle)
                productDescriptionParagraph.add(productDescriptionValue)
                productDescriptionParagraph.setFixedLeading(leading)

                productDescriptionCell.add(productDescriptionParagraph)
                productTable.addCell(productDescriptionCell)

                document.add(productTable)
            }

            document.close()

            manipulatePdf(activity, tempPDF, finalPDF, date)
        }
        catch (e: Exception){
            DialogHandler.showInformationDialog(activity, "PDF létrehozása sikertelen volt!", DialogTypeEnums.Error)
        }
    }
    private fun manipulatePdf(activity: Activity, tempFile: String, finalFile: String, date: Date) {
        val documentIDSize = 11f
        val documentTitleSize = 11f
        val pageNumberSize = 11f

        val pdfDoc = PdfDocument(PdfReader(tempFile), PdfWriter(finalFile))
        val doc = Document(pdfDoc)
        val numberOfPages = pdfDoc.numberOfPages

        pdfDoc.defaultPageSize.width/2

        for (i in 1..numberOfPages) {

            //Dokumentum cím hozzáadása
            val documentTitle = Text("Atakfashion, kiadott termékek, ${SimpleDateFormat(AppConfig.DATE_FORMAT2).format(date)}")
                .setFontSize(documentTitleSize)
                .setBold()
            val titleParagraph = Paragraph(documentTitle)

            doc.showTextAligned(titleParagraph, pdfDoc.defaultPageSize.width/2, pdfDoc.defaultPageSize.height - doc.topMargin*1.5f, i, TextAlignment.CENTER, VerticalAlignment.TOP, 0f)
            // Write aligned text to the specified by parameters point
            doc.showTextAligned(
                Paragraph(String.format("%s/%s", i, numberOfPages)),
                pdfDoc.defaultPageSize.width/2,
                doc.bottomMargin/2,
                i,
                TextAlignment.CENTER,
                VerticalAlignment.BOTTOM,
                0f
            ).setFontSize(pageNumberSize)
        }
        doc.close()

        deleteTempPDFFile(tempFile)
        DialogHandler.showInformationDialog(activity, "PDF létrehozva a letöltések mappába!", DialogTypeEnums.Successful)
    }

    private fun deleteTempPDFFile(fileName: String){
        val pdfFile = File(fileName)

        if(pdfFile.exists()){
            pdfFile.delete()
        }
    }

}