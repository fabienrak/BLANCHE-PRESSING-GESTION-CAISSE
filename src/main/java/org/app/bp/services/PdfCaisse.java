package org.app.bp.services;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.app.bp.models.SatistiqueParMoi;
import org.app.bp.models.StatStiqueAnnee;
import org.app.bp.models.Statisitique;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PdfCaisse {
    private static Font fontNomEntreprise = new Font(FontFamily.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLACK);
    private static Font fontNomPrenom = new Font(FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
    private static Font fontNumeroFac = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK);
    
    private static Font fontTitreTableau = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.WHITE);
    
    private static Font fontMontantFin = new Font(FontFamily.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLACK);
    
    private static BaseColor colorFont(){
        return BaseColor.BLUE;
    }
    
    private static PdfPCell aligementGauche(PdfPCell cell){
        cell.setHorizontalAlignment(Element.ALIGN_LEFT); 
        cell.setPadding(5);
        return cell;
    }
    private static PdfPCell aligementdROITE(PdfPCell cell){
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT); 
        cell.setPadding(5);
        return cell;
    }
    public static void generatePdf(String titre,TableView tableView){
        System.out.println(titre); 
        String pdfFilePath = "./caisse/"+titre.replace(" ", "")+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS"))+".pdf";
            Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            Image logo = Image.getInstance("E:\\STAGE\\BLANCHE_PRESSING_GESTION_CAISSE\\gestion-caisse-final\\src\\main\\resources\\img\\logo.png");
            logo.scaleAbsolute(90,90);
            logo.setBackgroundColor(colorFont());
            logo.setBorderColor(BaseColor.BLACK);
            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            float[] columnWidths = {1f, 1f};
            table.setWidths(columnWidths);
            Paragraph dateFacture = new Paragraph("Date : "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")),fontNomPrenom);
        
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
            
            Paragraph objet = new Paragraph(titre,fontNomEntreprise);
            document.add(objet);
            document.add(new Paragraph("\n"));
            
            PdfPTable tableCaisse = new PdfPTable(tableView.getColumns().size());
            tableCaisse.setWidthPercentage(100);
            float[] columnWid = {1f,1f};
            tableCaisse.setWidths(columnWid);
            int i = 0;
            
            PdfPCell cell = null;
            ObservableList titles = tableView.getColumns();
            for(i = 0 ; i < titles.size() ; i++){
                TableColumn column = (TableColumn)titles.get(i);
                cell = new PdfPCell(new Paragraph(column.getText(),fontTitreTableau));
                cell.setBackgroundColor(colorFont());
                cell.setPadding(5);
                tableCaisse.addCell(cell);
            }
            double total = 0;
            ObservableList list = tableView.getItems();
            for(i = 0 ; i < list.size() ; i++){
                Object o = list.get(i);
                PdfPCell cell1 = new PdfPCell();
                PdfPCell cell2 = new PdfPCell();
                if(o.getClass()== Statisitique.class){
                    Statisitique stat = (Statisitique)o;
                    if(stat.getDate() == null){
                        cell1.addElement(new Paragraph(String.valueOf(stat.getNum_semaine_moi()),fontNomPrenom));
                    }else{
                        cell1.addElement(new Paragraph(String.valueOf(stat.getDate().toString()),fontNomPrenom));
                    }   
                    cell2.addElement(new Paragraph(stat.getAffichePrix(),fontNomPrenom));
                }else if(o.getClass() == SatistiqueParMoi.class){
                    SatistiqueParMoi stm = (SatistiqueParMoi)o;
                    cell1.addElement(new Paragraph(stm.getMoi(),fontNomPrenom));
                    cell2.addElement(new Paragraph(stm.getAffichePrix(),fontNomPrenom));
                }else if(o.getClass() == StatStiqueAnnee.class){
                    StatStiqueAnnee stm = (StatStiqueAnnee)o;
                    cell1.addElement(new Paragraph(String.valueOf(stm.getAnnee()),fontNomPrenom));
                    cell2.addElement(new Paragraph(stm.getAffichePrix(),fontNomPrenom));
                }
                tableCaisse.addCell(cell1);
                tableCaisse.addCell(cell2);
            }
            document.add(tableCaisse);
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
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
