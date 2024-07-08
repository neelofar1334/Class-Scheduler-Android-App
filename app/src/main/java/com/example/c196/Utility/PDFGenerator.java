package com.example.c196.Utility;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PDFGenerator {

    public static void generateSchedulePDF(Context context, List<String> scheduleItems) {
        try {
            File pdfFolder = new File(context.getExternalFilesDir(null), "MyApp");
            if (!pdfFolder.exists()) {
                pdfFolder.mkdirs();
            }

            File pdfFile = new File(pdfFolder, "Schedule.pdf");
            FileOutputStream fos = new FileOutputStream(pdfFile);

            PdfWriter writer = new PdfWriter(fos);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            document.add(new Paragraph("Student Schedule"));
            document.add(new Paragraph(" ")); // empty line

            for (String item : scheduleItems) {
                document.add(new Paragraph(item));
            }

            document.close();

            // Inform the user that the schedule was created
            Toast.makeText(context, "Schedule generated: " + pdfFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

            // Open the generated PDF
            openPDF(context, pdfFile);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Failed to generate schedule", Toast.LENGTH_LONG).show();
        }
    }

    private static void openPDF(Context context, File pdfFile) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", pdfFile);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        context.startActivity(Intent.createChooser(intent, "Open Schedule"));
    }
}
