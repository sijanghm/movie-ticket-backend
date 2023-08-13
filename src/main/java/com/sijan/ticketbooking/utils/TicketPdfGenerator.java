package com.sijan.ticketbooking.utils;


import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public class TicketPdfGenerator {

    public byte[] createPdf(Map<String, String> ticketData) {
        String title = "Ticket Confirmation";
        String content = " Thank you for booking ticket. Enjoy Movie !!!!";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A5);

        PdfWriter.getInstance(document, out);

        document.open();
        Paragraph titleText = new Paragraph(title);
        titleText.setAlignment(Element.ALIGN_CENTER);
        document.add(titleText);
//        this will add all the ticket details to the pdf
        Paragraph details = new Paragraph(content);
        document.add(details);

        // Creating a table of 2 columns
        PdfPTable table = new PdfPTable(2);

        // Setting width of table, its columns and spacing
        table.setWidthPercentage(100f);
        table.setWidths(new int[] { 3, 3 });
        table.setSpacingBefore(5);

        // Create Table Cells for table header
        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.MAGENTA);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("Item", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Details", font));
        table.addCell(cell);

        //Iterate over each data set
        ticketData.entrySet().forEach(entry -> {
            table.addCell(entry.getKey());
            table.addCell(entry.getValue());
        });
        // Adding the created table to document
        document.add(table);
        document.close();
        return out.toByteArray();
    }
}
