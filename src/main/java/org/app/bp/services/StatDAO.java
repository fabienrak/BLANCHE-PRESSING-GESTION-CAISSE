/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.app.bp.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.app.bp.models.SatistiqueParMoi;
import org.app.bp.models.StatStiqueAnnee;
import org.app.bp.models.Statisitique;
import org.app.bp.utils.DBUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Cocone
 */
public class StatDAO {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();
    
    
    
    
    public int [][] getListe_semaine(SatistiqueParMoi moi){
        int [] numero_moi=getNombreSemaine(moi.getAnnee(), moi.getNum_moi());
        int [][] value=new int[numero_moi[1]-numero_moi[0]][2];
        for (int i = 0; i < value.length; i++) {
            value[i][0]=numero_moi[0]+i+1;
//            System.out.println(value[i][0]);
            value[i][1]=i+1;
//            System.out.println(value[i][1]);
        }
        return value;
    }
    public static int[] getDistinctNumbers(List<Integer> liste) {
        List<Integer> numbers=liste;
        Set<Integer> distinctSet = new HashSet<>(numbers);
        List<Integer> distinctList = new ArrayList<>(distinctSet);
        int [] value=new int[distinctList.size()];
         for (int i = 0; i < distinctList.size(); i++) {
             value[i]=distinctList.get(i);
         }
        return value;
    }
    public ObservableList<Statisitique> getParSemaine(SatistiqueParMoi moi) throws SQLException{
        ObservableList<Statisitique> liste=getParJOUR(moi);
        List<Integer>  listeSemaine=new ArrayList<>();
        for (int i = 0; i < liste.size(); i++) {
           listeSemaine.add(liste.get(i).getSemaine());
        }
        int [] num_semaine=getDistinctNumbers(listeSemaine);
        int [] nombreSemaine=getNombreSemaine(moi.getAnnee(), moi.getNum_moi());
        ObservableList<Statisitique> value=FXCollections.observableArrayList();
        for (int i = 0; i < num_semaine.length; i++) {
            Statisitique statistique=new Statisitique();
            statistique.setSemaine(num_semaine[i]);
            statistique.setAnnee(moi.getAnnee());
            statistique.setMoi(moi.getNum_moi());
            double prix=0;
            for (int j = 0; j < liste.size(); j++) {
                if(liste.get(j).getSemaine()==num_semaine[i]){
                    prix=prix+liste.get(j).getPrix();
                }
            }
            statistique.setPrix(prix);
            value.add(statistique);
        }
        return value;
    }
    public ObservableList<Statisitique> getStatParSemaine(SatistiqueParMoi moi) throws SQLException{
        ObservableList<Statisitique> liste=getParJOUR(moi);
        ObservableList<Statisitique> value=FXCollections.observableArrayList();
        int [][]listeSemaine=getListe_semaine(moi);
        for (int i = 0; i < listeSemaine.length; i++) {
            Statisitique statistique=new Statisitique();
            statistique.setSemaine(listeSemaine[i][0]);
            statistique.setNum_semaine_moi(listeSemaine[i][1]);
            statistique.setAnnee(moi.getAnnee());
            statistique.setMoi(moi.getNum_moi());
            double prix=0;
            for (int j = 0; j < liste.size(); j++) {
                if(liste.get(j).getSemaine()==listeSemaine[i][0]){
                    prix=prix+liste.get(j).getPrix();
                }
            }
            statistique.setPrix(prix);
            value.add(statistique);
        }
        return value;
    }

//    ----------------Par ANNEEE------------------
    public ObservableList<StatStiqueAnnee> getStatParANNEE() throws SQLException{
        
        String query = "select * from v_statistique_par_annee";
        System.out.println(query);
        ObservableList<StatStiqueAnnee> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StatStiqueAnnee commandeFinal = new StatStiqueAnnee();                
                commandeFinal.setAnnee(resultSet.getInt("annee"));
                commandeFinal.setPrix(Double.parseDouble(resultSet.getString("prix")));
                data.add(commandeFinal);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return data;
    }
    public int getNumeroSemaine(Date date){
        int value=0;
        LocalDate localdate=date.toLocalDate();
        value=localdate.getDayOfYear()/7;
        return value;
    }
    public static int getDernierJourMoiANNEE(int moi,int annee){
        YearMonth yearMonth = YearMonth.of(annee, moi);
        LocalDate dernierJourDuMois = yearMonth.atEndOfMonth();
        return dernierJourDuMois.getDayOfMonth();
    }
    public static int[] getNombreSemaine(int annee,int moi){
        int value=0;
        LocalDate timedateFin=LocalDate.of(annee, moi, getDernierJourMoiANNEE(moi, annee));
        LocalDate timedateDebut=LocalDate.of(annee, moi,1);
        int valFin = timedateFin.getDayOfYear()/7;
        int valDebut=timedateDebut.getDayOfYear()/7;
        value=valFin-valDebut;
        int [] val={valDebut,valFin };
        return val;
    }
    
    public ObservableList<SatistiqueParMoi> getParMoi(int annee) throws SQLException{
     
        String query = "select * from v_statistique_par_moi where annee='"+annee+"'";
        System.out.println(query);
        ObservableList<SatistiqueParMoi> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("commande = "+ resultSet.getRow());
            while (resultSet.next()) {
                SatistiqueParMoi commandeFinal = new SatistiqueParMoi();
                commandeFinal.setAnnee(resultSet.getInt("annee"));
                commandeFinal.setNum_moi(resultSet.getInt("moi"));
                commandeFinal.setPrix(Double.parseDouble(resultSet.getString("prix")));
                data.add(commandeFinal);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return data;
    }
    public ObservableList<Statisitique> getParJOUR(SatistiqueParMoi moi) throws SQLException{
       System.out.println(moi);
        String valMois = moi.getNum_moi() +"";;
        if(moi.getNum_moi() < 10){
            valMois = "0"+moi.getNum_moi() +"";
        }
        
        String query = "select * from v_payement_par_jour where annee='"+moi.getAnnee()+"' and moi ='"+valMois+"'";
        System.out.println(query);
        ObservableList<Statisitique> data = FXCollections.observableArrayList();
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            System.out.println("commande = "+ resultSet.getRow());
//            int i=0;
            while (resultSet.next()) {
//                i=i+1;
//                System.out.println("iiiiiiiii==="+i);
                Statisitique commandeFinal = new Statisitique();
                commandeFinal.setAnnee(resultSet.getInt("annee"));
                commandeFinal.setMoi(resultSet.getInt("moi"));
                commandeFinal.setDate(Date.valueOf(resultSet.getString("date_payement")));
                commandeFinal.setPrix(Double.parseDouble(resultSet.getString("prix")));
                commandeFinal.setSemaine(getNumeroSemaine(commandeFinal.getDate()));
                data.add(commandeFinal);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
        return data;
    }
  
    
    public ObservableList<SatistiqueParMoi>  getStatParMOI(int annee) throws SQLException{
        ObservableList<SatistiqueParMoi> value=FXCollections.observableArrayList();
        ObservableList<SatistiqueParMoi> STatparMoi=getParMoi(annee);
        String [] moi=DateFormatSymbols.getInstance(Locale.FRANCE).getMonths();
        int count=0;
//        System.out.println(STatparMoi);
        for (int i = 0; i < moi.length-1; i++) {
            count=0;
            for (int j = 0; j < STatparMoi.size(); j++) {
                if((i+1==STatparMoi.get(j).getNum_moi())){
                    count++;
                    SatistiqueParMoi get = STatparMoi.get(j);
                    get.setMoi(moi[i]);
                    value.add(get);
                    break;
                }
            }
            if (count==0){
                    SatistiqueParMoi get=new SatistiqueParMoi();
                    get.setAnnee(annee);
                    get.setPrix(0);
                    get.setNum_moi(i+1);
                    get.setMoi(moi[i]);
                    value.add(get);
                    
            }
        }
        return value;
    }
       public static void main(String[] args) throws SQLException, Exception {
        StatDAO dao=new StatDAO();
        ObservableList<SatistiqueParMoi> liste=dao.getStatParMOI(2023);
        for (int i = 0; i < liste.size(); i++) {
            System.out.println(i+"   ertyuj" +liste.get(i));
            System.out.println(dao.getParSemaine(liste.get(i)));
            
        }
//        System.out.println(getDernierJourMoiANNEE(2, 2023));
    }
    
    
}
