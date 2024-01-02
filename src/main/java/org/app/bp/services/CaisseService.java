package org.app.bp.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.app.bp.utils.DBUtils;

public class CaisseService {
    
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    Connection connection = DBUtils.getConnection();

    public double getTotalCaisse(LocalDate date, int type){
        double total = 0;
          String liste_site_query = "SELECT sum(prix) from facturation where etat=1 and type="+type+" and date_payement='"+date.format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))+"'";
        System.out.println(liste_site_query);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(liste_site_query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                total = resultSet.getDouble("sum(prix)");
                //System.out.println(t);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return total;
    }

    public double getTotalAvanceCaisse(LocalDate date){
        return getTotalCaisse(date, 0);
    }

    public double getTotalLivraisonCaisse(LocalDate date){
        return getTotalCaisse(date, 1);
    }

}
