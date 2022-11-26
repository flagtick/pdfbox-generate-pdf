package com.ap.pdfboxgenerate;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class App 
{
    public static void main( String[] args ) throws IOException {
        PDDocument document = new PDDocument();

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDType0Font font = PDType0Font.load(document, new File("src/main/resources/font/Helvetica.ttf"));
        float pdfMarginLeft = page.getCropBox().getLowerLeftX();

        float fontSize = 14;
        float scaleLogo = 0.5f;
        float leading = 20;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
        contentStream.setFont(font, fontSize);
        PDRectangle mediaBox = page.getMediaBox();

        float yCordinate = page.getCropBox().getUpperRightY() - 180;
        float endX = page.getCropBox().getUpperRightX() - 60;

        // Flagtick Logo
        PDImageXObject pdImage = PDImageXObject.createFromFile("src/main/resources/img/logo.png", document);
        contentStream.drawImage(pdImage, 56, mediaBox.getHeight() - 2 * 70, pdImage.getWidth() * scaleLogo, pdImage.getHeight() * scaleLogo);

        contentStream.beginText();

        contentStream.newLineAtOffset(pdfMarginLeft + 60, yCordinate - 15);
        contentStream.showText("Flagtick Portal Information");

        contentStream.setLeading(leading * 2);
        contentStream.newLine();
        contentStream.showText("Date: " + dateFormat.format(date));
        contentStream.endText();

        contentStream.moveTo(pdfMarginLeft + 60, yCordinate - 30);
        contentStream.lineTo(endX, yCordinate - 30);
        contentStream.stroke();

        contentStream.beginText();
        contentStream.newLineAtOffset(pdfMarginLeft + 60, yCordinate - 90);
        contentStream.showText("Your Name: Luis Nguyen");
        contentStream.endText();

        contentStream.close();
        try {
            document.save("C:/Users/admin/Documents/example_pdf_doc.pdf");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("PDF created");
        document.close();
    }
}
