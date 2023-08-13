package com.sijan.ticketbooking.utils;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class TicketPdfGenerator {

    public ByteArrayInputStream createPdf() {
        String title = "Ticket details";
        String content = " here we should pass the ticket details, total price and seat confirmed";

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();

        PdfWriter.getInstance(document, out);

        document.open();
        Paragraph titleText = new Paragraph(title);
        titleText.setAlignment(Element.ALIGN_CENTER);
        document.add(titleText);
//        this will add all the ticket details to the pdf
        Paragraph details = new Paragraph(content);
        document.add(details);
        document.close();
        return new ByteArrayInputStream(out.toByteArray());

    }
}
