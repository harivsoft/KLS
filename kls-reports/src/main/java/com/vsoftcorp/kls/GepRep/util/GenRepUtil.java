package com.vsoftcorp.kls.GepRep.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.vsoftcorp.kls.GepRep.data.UserInputs;
import com.vsoftcorp.kls.util.EntityManagerUtil;

public class GenRepUtil {
	
	public static ResultSet resultSet = null;
	
	public static ResultSet getResultSet(String sqlQuery) {
		final String query = sqlQuery;
		try {
			// For Pacs Suvikas Call
			/*EntityManagerFactory emf = Persistence.createEntityManagerFactory("PACSPersistenceUnit");
			EntityManager em = emf.createEntityManager();*/
			
			EntityManager em = EntityManagerUtil.getEntityManager();
			
			Session session = em.unwrap(Session.class);
			
			session.doWork(new Work() {
				@Override
				public void execute(Connection con) throws SQLException {
					Statement st = con.createStatement();
					resultSet = st.executeQuery(query);
				}
			});
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resultSet;
	}
	
	
	
	
	public static String getGeneratedQuery(StringBuffer query,List<UserInputs> userInputs){
		for(UserInputs userInput : userInputs){
			query.append(" and "+userInput.getColumnName()+"'"+userInput.getValue()+"'");
		}
		
		return query.toString();
	}
	
	public static String ConvertingRomanNumbers(int number){
    	String roman="";
         
         
        while(number<=0 || number>3999){
            System.out.println("Invalid input.  You must enter a number between 1 and 3999");
            System.out.print("Please enter another number now: ");
            //number = scan.nextInt();
            }
         
        while(number>=1000){
            roman += "M";
            number-=1000;
        }
         
        while(number>=900){
            roman += "CM";
            number-=900;
        }
         
        while(number>=500){
            roman += "D";
            number-=500;
        }
         
        while(number>=400){
            roman += "CD";
            number-=400;
        }
         
        while(number>=100){
            roman += "C";
            number-=100;
        }
         
        while(number>=90){
            roman += "XC";
            number-=90;
        }
         
        while(number>=50){
            roman += "L";
            number-=50;
        }
         
        while(number>=40){
            roman += "XL";
            number-=40;
        }
         
        while(number>=10){
            roman += "X";
            number-=10;
        }
         
        while(number>=9){
            roman += "IX";
            number-=9;
        }
         
        while(number>=5){
            roman += "V";
            number-=5;
        }
         
        while(number>=4){
            roman += "IV";
            number-=4;
        }
         
        while(number>=1){
            roman += "I";
            number-=1;
        }
         
        return roman;
    }
	

}
