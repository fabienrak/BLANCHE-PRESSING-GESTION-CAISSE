package org.app.bp.services;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.app.bp.models.Clients;
import org.app.bp.models.CommandeClient;
import org.app.bp.models.CommandeFinal;
import org.app.bp.models.FactureAvance;

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

public class PdfService {
    private static float marge = 10f;
    private static Font fontNomEntreprise = new Font(FontFamily.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLACK);
    private static Font fontNomPrenom = new Font(FontFamily.TIMES_ROMAN,12,Font.NORMAL,BaseColor.BLACK);
    private static Font fontNumeroFac = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK);
    
    private static Font fontTitreTableau = new Font(FontFamily.TIMES_ROMAN,12,Font.BOLD,BaseColor.WHITE);
    
    private static Font fontMontantFin = new Font(FontFamily.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLACK);
    
    public static void generationDeFactureFinal(CommandeFinal commande){
        String pdfFilePath = commande.getCode()+".pdf";
            Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            addDetailsBlanchePressing(document,commande.getClient(),LocalDate.now(),"FACTURE FINAL : "+commande.getCode(),"Facture final du commande "+commande.getCode());
            List<FactureAvance> list = commande.getListeFactureAvance();
            if(list == null){
                list = new ArrayList<>();
            }
            document.add(new Paragraph("\n"));
            addListeCommandeClient(document, commande.getListeCommandeClient());
            document.add(new Paragraph("\n"));
            addListeFactureAvance(document, list);
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            float[] columnWidths = {3f, 1f,1f};
            table.setWidths(columnWidths);

            PdfPCell inv = new PdfPCell();
            inv.setBorder(0);
            table.addCell(inv);
            PdfPCell lbl_montant = new PdfPCell(new Paragraph("Total Avance ",fontNomPrenom));
            aligementGauche(lbl_montant);
            table.addCell(lbl_montant);
            PdfPCell montant = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commande.getTotalAvance()) +" Ar ",fontNomPrenom));
            aligementdROITE(montant);
            table.addCell(montant); 


             inv = new PdfPCell();
            inv.setBorder(0);
            table.addCell(inv);
             lbl_montant = new PdfPCell(new Paragraph("Montant total",fontNomPrenom));
            aligementGauche(lbl_montant);
            table.addCell(lbl_montant);
             montant = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commande.getPrixTotal()) +" Ar ",fontNomPrenom));
            aligementdROITE(montant);
            table.addCell(montant);

             inv = new PdfPCell();
            inv.setBorder(0);
            table.addCell(inv);
             lbl_montant = new PdfPCell(new Paragraph("Montant total à payer ",fontMontantFin));
            aligementGauche(lbl_montant);
            lbl_montant.setBackgroundColor(BaseColor.GRAY);
            table.addCell(lbl_montant);
             montant = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(commande.getResteApayer()) +" Ar ",fontMontantFin));
            aligementdROITE(montant);
            montant.setBackgroundColor(BaseColor.GRAY);
            table.addCell(montant);
            document.add(table);
            document.close();
            writer.close();
            
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


    public static void generationFactureAccompte(FactureAvance facture){
        String pdfFilePath = facture.getNumeroFacture()+".pdf";
            Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFilePath));
            document.open();
            addDetailsBlanchePressing(document,facture.getCommande().getClient(),facture.getDateFacturation(),"FACTURE D'AVANCE : "+facture.getNumeroFacture(),"Avance du commande "+facture.getCommande().getCode());
            List<FactureAvance> list = new ArrayList<>();
            list.add(facture);
            document.add(new Paragraph("\n"));
            
            addListeFactureAvance(document, list);
            
            
            document.add(new Paragraph("\n"));
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            float[] columnWidths = {3f, 1f,1f};
            table.setWidths(columnWidths);
            PdfPCell inv = new PdfPCell();
            inv.setBorder(0);
            table.addCell(inv);
            PdfPCell lbl_montant = new PdfPCell(new Paragraph("Montant total à payer ",fontMontantFin));
            aligementGauche(lbl_montant);
            lbl_montant.setBackgroundColor(BaseColor.GRAY);
            table.addCell(lbl_montant);
            
            PdfPCell montant = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(facture.getPrixAvance()) +" Ar ",fontMontantFin));
            aligementdROITE(montant);
            montant.setBackgroundColor(BaseColor.GRAY);
            table.addCell(montant);
            document.add(table);
            document.close();
            writer.close();
            
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

    private static void addListeCommandeClient(Document document,ObservableList<CommandeClient> liste)throws Exception{
         PdfPTable table = new PdfPTable(5);
         table.setWidthPercentage(100);
        float[] columnWidths = {2f, 1f, 1f, 1f ,1f};
         table.setWidths(columnWidths);
        String[] titre = new String[]{"Article","Service","Quantité","Prix","Total"};
         int i = 0;
         PdfPCell cell = null;
         for(i = 0 ; i < titre.length ; i++){
            cell = new PdfPCell(new Paragraph(titre[i],fontTitreTableau));
            if(i == 0 || i == 1){
                cell.setBackgroundColor(BaseColor.GREEN);
                aligementGauche(cell);
            
            }else{
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
                aligementdROITE(cell);
            }
            cell.setBackgroundColor(BaseColor.GREEN);
            cell.setPadding(5);
            table.addCell(cell);
         }

         for(i = 0 ; i < liste.size() ; i++){
            CommandeClient commandeClient = liste.get(i);
            table.addCell(aligementGauche(new PdfPCell(new Paragraph(commandeClient.getArticle().getNom_article(),fontNomPrenom))));
            table.addCell(aligementGauche(new PdfPCell(new Paragraph(commandeClient.getService().toString(),fontNomPrenom))));
            table.addCell(aligementdROITE(new PdfPCell(new Paragraph(String.valueOf(commandeClient.getNombre()),fontNomPrenom))));
            table.addCell(aligementdROITE(new PdfPCell(new Paragraph(String.valueOf(commandeClient.getPrixUnitaire()+" Ar "),fontNomPrenom))));
            table.addCell(aligementdROITE(new PdfPCell(new Paragraph(String.valueOf(commandeClient.getPrixTotal()+" Ar "),fontNomPrenom))));
        }
        document.add(table);
    }

    private static void addListeFactureAvance(Document document,List<FactureAvance> listeFact)throws Exception{
         PdfPTable table = new PdfPTable(4);
         table.setWidthPercentage(100);
         float[] columnWidths = {4f, 1f,1f,1f};
         table.setWidths(columnWidths);
         String[] titre = new String[]{"Description avance","Quantité","Prix","Total"};
         int i = 0;
         PdfPCell cell = null;
         for(i = 0 ; i < titre.length ; i++){
            cell = new PdfPCell(new Paragraph(titre[i],fontTitreTableau));
            if(i == 0){
                cell.setBackgroundColor(BaseColor.GREEN);
                aligementGauche(cell);
            
            }else{
                cell.setHorizontalAlignment(Element.ALIGN_CENTER); 
                aligementdROITE(cell);
            }
            cell.setBackgroundColor(BaseColor.GREEN);
           cell.setPadding(5);
            table.addCell(cell);
         }
         PdfPCell desc = null;
         PdfPCell qt = null;
         PdfPCell pU = null;
         PdfPCell totalPU = null;
         for(i = 0 ; i < listeFact.size() ; i++){
            desc = new PdfPCell(new Paragraph("Avance pour le commande "+listeFact.get(i).getCommande().getCode()+" avec montant total de "+NumberFormat.getInstance(java.util.Locale.FRENCH).format(listeFact.get(i).getCommande().getPrixTotal()) + " Ar",fontNomPrenom));
            aligementGauche(cell);
            desc.setPadding(5);
            table.addCell(desc);
            qt = new PdfPCell(new Paragraph("1",fontNomPrenom));
            aligementdROITE(qt);
            qt.setPadding(5);
            table.addCell(qt);
            pU = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(listeFact.get(i).getPrixAvance())+" Ar ",fontNomPrenom));
            totalPU = new PdfPCell(new Paragraph(NumberFormat.getInstance(java.util.Locale.FRENCH).format(listeFact.get(i).getPrixAvance())+" Ar ",fontNomPrenom));
            pU.setPadding(5);
            totalPU.setPadding(5);
            aligementdROITE(pU);
            aligementdROITE(totalPU);
            table.addCell(pU);
            table.addCell(totalPU);
        }
        document.add(table);
    }


    private static void addDetailsBlanchePressing(Document document,Clients client,LocalDate dateFac,String numFac,String objetFac)throws Exception{
            
        // definition de logo
        Image logo = Image.getInstance("E:\\STAGE\\BLANCHE_PRESSING_GESTION_CAISSE\\gestion-caisse-final\\src\\main\\resources\\img\\logo.png");
            logo.scaleAbsolute(100,100);
            logo.setBackgroundColor(BaseColor.GREEN);
            logo.setBorderColor(BaseColor.BLACK);

        // definition du details
        Paragraph nomEntreprise = new Paragraph("BLANCHE PRESSING",fontNomEntreprise);
        Paragraph adresseEntreprise = new Paragraph("Analakely",fontNomPrenom);
        Paragraph telephoneEntreprise = new Paragraph("Tél : 034 89 009 01",fontNomPrenom);
        Paragraph mailEntreprise = new Paragraph("Email : blanchePressing@gmail.com",fontNomPrenom);
        
        // definition Numero facture
        Paragraph dateFacture = new Paragraph("Date : "+dateFac.toString(),fontNomPrenom);
        Paragraph numFacture = new Paragraph(numFac,fontNumeroFac);
        
        // Details client
        Paragraph nomPrenomClient = new Paragraph(client.getNom_client().toUpperCase() + " "+client.getPrenom_client().toLowerCase(),fontNomPrenom);
        String adresseCli = client.getAdresse_client_1();
        if(adresseCli.isEmpty() == true){
            adresseCli = client.getAdresse_client_2();
        }
        String telCli = client.getContact_client_1();
        if(telCli.isEmpty() == true){
            telCli = client.getContact_client_2();
        }
        Paragraph adresseClient = new Paragraph(adresseCli,fontNomPrenom);
        Paragraph telClient = new Paragraph("Tél : "+telCli,fontNomPrenom);

        // Construction
         PdfPTable table = new PdfPTable(2);
         table.setWidthPercentage(100);
         float[] columnWidths = {1f, 1f};
         table.setWidths(columnWidths);

         //Cell image et num facture
        PdfPCell cellImage = new PdfPCell();
        cellImage.addElement(logo);
        cellImage.setBorder(0);
        PdfPCell cellNumFacture = new PdfPCell();
        cellNumFacture.addElement(dateFacture);
        cellNumFacture.addElement(numFacture);
        cellNumFacture.setBorder(0);
        table.addCell(cellImage);
        table.addCell(cellNumFacture);
        
        // CeLL Entreprise et client
        PdfPCell cellEntreprise = new PdfPCell();
        cellEntreprise.addElement(nomEntreprise);
        cellEntreprise.addElement(adresseEntreprise);
        cellEntreprise.addElement(telephoneEntreprise);
        cellEntreprise.addElement(mailEntreprise);
        cellEntreprise.setVerticalAlignment(Element.ALIGN_TOP);
        cellEntreprise.setBorder(0);

        PdfPCell cellClient = new PdfPCell();
        cellClient.addElement(nomPrenomClient);
        cellClient.addElement(adresseClient);
        cellClient.addElement(telClient);
        cellClient.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellClient.setPadding(10f);
        table.addCell(cellEntreprise);
        table.addCell(cellClient);
        document.add(table);
        
        Paragraph codeClient = new Paragraph("Code Client : "+client.getId_client(),fontNomPrenom);
        codeClient.setAlignment(Element.ALIGN_RIGHT);
        document.add(codeClient);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        Paragraph objet = new Paragraph("Objet : "+objetFac,fontNomPrenom);
        document.add(objet);
    }

}
