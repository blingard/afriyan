package org.ligot.afriyan.controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.ligot.afriyan.Dto.SouscriptionDTO;
import org.ligot.afriyan.service.ISouscription;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/generate-pdf")
public class PdfController {

    @Value("${unfpaphoto}")
    private String unfpa;

    @Value("${young}")
    private String young;

    private final ISouscription service;

    public PdfController(ISouscription service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public void generatePdf(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        SouscriptionDTO rendezVousDTO = service.findById(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=abonnement"+rendezVousDTO.getService().getCentrePartenaire().getLibelle()+"-"+rendezVousDTO.getDatecreation());

        OutputStream out = response.getOutputStream();

        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, out);

        document.open();
        header(document);
        body(document, rendezVousDTO);
        document.close();
    }
    private void header(Document document) throws DocumentException, IOException {


        // Créer une table avec trois colonnes
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);

        // Logo à gauche
        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(Rectangle.NO_BORDER);

        Image leftLogo = Image.getInstance(young);
        leftLogo.scaleToFit(50, 50);
        cell1.addElement(leftLogo);
        table.addCell(cell1);

        // Texte au milieu
        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph paragraph = new Paragraph("Souscription");
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell2.addElement(paragraph);
        table.addCell(cell2);

        // Logo à droite
        PdfPCell cell3 = new PdfPCell();
        cell3.setBorder(Rectangle.NO_BORDER);

        Image rightLogo = Image.getInstance(unfpa);
        rightLogo.setAlignment(Element.ALIGN_RIGHT);
        rightLogo.scaleToFit(50, 50);
        cell3.addElement(rightLogo);
        table.addCell(cell3);

        document.add(table);
    }
    private void body(Document document, SouscriptionDTO rendezVousDTO) throws DocumentException, IOException {
        Paragraph paragraph = new Paragraph();
        leaveEmptyLine(paragraph, 2);
        document.add(paragraph);

        // Créer une table avec trois colonnes
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        CustomParagraph(table, "Service: ", rendezVousDTO.getService().getLibelle());
        leaveEmptyLine(paragraph, 1);
        CustomParagraph(table, "Centre Partenaire: ", rendezVousDTO.getService().getCentrePartenaire().getLibelle());
        leaveEmptyLine(paragraph, 1);
        String pattern = "dd/MM/yyyy hh-mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        CustomParagraph(table, "Date: ", simpleDateFormat.format(rendezVousDTO.getDatecreation()));
        leaveEmptyLine(paragraph, 1);
        CustomParagraph(table, "Name: ", rendezVousDTO.getUtilisateur().getNom()+" "+rendezVousDTO.getUtilisateur().getPrenom());
        leaveEmptyLine(paragraph, 1);
        CustomParagraph(table, "Phone: ", rendezVousDTO.getUtilisateur().getNumero_telephone());
        leaveEmptyLine(paragraph, 1);
        CustomParagraph(table, "Status: ", rendezVousDTO.getStatus().toString());

        document.add(table);
    }
    private static void leaveEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    private void CustomParagraph(PdfPTable pdfPTable, String title, String content){
        PdfPCell cell1 = new PdfPCell();
        cell1.setBorder(Rectangle.NO_BORDER);
        Paragraph paragraphTitle = new Paragraph(title);
        paragraphTitle.setAlignment(Element.ALIGN_LEFT);
        cell1.addElement(paragraphTitle);
        pdfPTable.addCell(cell1);

        PdfPCell cell2 = new PdfPCell();
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph paragraphContent = new Paragraph(content);
        paragraphContent.setAlignment(Element.ALIGN_CENTER);
        cell2.addElement(paragraphContent);
        pdfPTable.addCell(cell2);
    }
}
