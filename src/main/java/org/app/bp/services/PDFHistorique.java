package org.app.bp.services;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javafx.collections.ObservableList;

public class PDFHistorique {
    private static Font fontNomEntreprise = new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
    private static Font fontNomPrenom = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
    private static Font fontNumeroFac = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);

    private static Font fontTitreTableau = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.WHITE);

    private static Font fontMontantFin = new Font(FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);

    private static BaseColor colorFont() {
        return BaseColor.BLUE;
    }

    private static PdfPCell aligementGauche(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        return cell;
    }

    private static PdfPCell aligementdROITE(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPadding(5);
        return cell;
    }

    public void generatePdf(String titre, ObservableList<CommandeFinal> commandeFinal) {
        String pdfFilePath = "./commande/" + titre.replace(" ", "")
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + ".pdf";
        Document document = new Document();
        HistoriqueService historiqueService = new HistoriqueService();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            Image logo = Image.getInstance("src\\main\\resources\\img\\logo.png");
            logo.scaleAbsolute(90, 90);
            logo.setBackgroundColor(colorFont());
            logo.setBorderColor(BaseColor.BLACK);

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            float[] columnWidths = { 1f, 1f };
            table.setWidths(columnWidths);
            Paragraph dateFacture = new Paragraph(
                    "Date : " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")),
                    fontNomPrenom);

            PdfPCell cellImage = new PdfPCell();
            cellImage.addElement(logo);
            cellImage.setBorder(0);
            PdfPCell cellNumFacture = new PdfPCell();
            cellNumFacture.addElement(dateFacture);
            cellNumFacture.setBorder(0);
            table.addCell(cellImage);
            table.addCell(cellNumFacture);
            document.add(table);
            document.add(new Paragraph("\n"));
            int j = 0;
            String[] titreColumns = new String[] { "Nom", "Nombre" };
            PdfPTable tabledernier = new PdfPTable(2);
            PdfPCell cellDernierTable = null;
            // int j=0;
            PdfPCell cell3 = new PdfPCell(new Paragraph("", fontNumeroFac));
            cell3.setBorderWidthLeft(0);
            Paragraph paragrapheTitres = new Paragraph("Tous les Commandes ", fontNumeroFac);

            PdfPCell cell34 = new PdfPCell(paragrapheTitres);
            cell34.setBorderWidthRight(0);
            tabledernier.addCell(cell34);
            tabledernier.addCell(cell3);
            for (j = 0; j < titreColumns.length; j++) {
                cellDernierTable = new PdfPCell(new Paragraph(titreColumns[j], fontTitreTableau));
                if (j == 0 || j == 1) {
                    cellDernierTable.setBackgroundColor(colorFont());
                    aligementGauche(cellDernierTable);

                } else {
                    cellDernierTable.setHorizontalAlignment(Element.ALIGN_CENTER);
                    aligementdROITE(cellDernierTable);
                }
                cellDernierTable.setBackgroundColor(colorFont());
                cellDernierTable.setPadding(5);
                tabledernier.addCell(cellDernierTable);
            }

            // document.add(paragrapheTitre);
            ObservableList<CommandeClient> listes = historiqueService.getTousLesCommandeDunDateDonnee(commandeFinal);
            for (j = 0; j < listes.size(); j++) {
                CommandeClient commandeClient = listes.get(j);
                tabledernier.addCell(aligementGauche(
                        new PdfPCell(new Paragraph(commandeClient.getArticle().getNom_article(), fontNomPrenom))));
                tabledernier.addCell(aligementdROITE(
                        new PdfPCell(new Paragraph(String.valueOf(commandeClient.getNombre()), fontNomPrenom))));
            }
            document.add(tabledernier);
            document.add(new Paragraph("\n"));
            PdfPTable tablesGand=new PdfPTable(2);
            tablesGand.setWidths(new float[] { 1f, 1f });
            
            for (int i = 0; i < commandeFinal.size(); i++) {
                PdfPCell cell = null;
                PdfPTable tables = new PdfPTable(2);
                tables.setWidths(new float[] { 1f, 1f });
                PdfPCell cell4 = null;
                Paragraph paragrapheTitre = new Paragraph("Commande : " , fontNumeroFac);
                cell4 = new PdfPCell(paragrapheTitre);
                cell4.setBorderWidthRight(0);
                PdfPCell cell45 = new PdfPCell(new Paragraph( ""+commandeFinal.get(i).getCode(), fontNumeroFac));

                tables.addCell(cell4);
                cell45.setBorderWidthLeft(0);
                tables.addCell(cell45);

                for (j = 0; j < titreColumns.length; j++) {
                    cell = new PdfPCell(new Paragraph(titreColumns[j], fontTitreTableau));
                    // if(j== 0 || j== 1){
                    cell.setBackgroundColor(colorFont());
                    aligementGauche(cell);

                    // }
                    cell.setBackgroundColor(colorFont());
                    cell.setPadding(5);
                    System.out.println(cell + " " + j);
                    tables.addCell(cell);
                }
                System.out.println(i);
                PdfPCell celltables=new PdfPCell();
                // document.add(paragrapheTitre);
                ObservableList<CommandeClient> liste = historiqueService.getListCommandeClient(commandeFinal.get(i));
                // int x=liste.size();
                for (j = 0; j < liste.size(); j++) {
                    CommandeClient commandeClient = liste.get(j);
                    tables.addCell(aligementGauche(
                            new PdfPCell(new Paragraph(commandeClient.getArticle().getNom_article(), fontNomPrenom))));
                    tables.addCell(aligementdROITE(
                            new PdfPCell(new Paragraph(String.valueOf(commandeClient.getNombre()), fontNomPrenom))));
                }
                // document.add(tables);
                celltables.addElement(tables);
                celltables.setBorder(0);
                tablesGand.addCell(celltables);
            }
            int x=commandeFinal.size();
            PdfPCell tableCell3=new PdfPCell(new Paragraph(""));
            if (x % 2 == 0) {}
            else{
                tableCell3.setBorder(0);
                tablesGand.addCell(tableCell3);
            }
            document.add(tablesGand);

            document.close();
            writer.close();
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                Desktop.getDesktop().open(new File(pdfFilePath));
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}