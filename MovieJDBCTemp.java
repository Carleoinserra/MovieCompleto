package com.example.demo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

@Component
public class MovieJDBCTemp {
	 private JdbcTemplate jdbcTemplateObject;
	
	 @Autowired
	public MovieJDBCTemp(JdbcTemplate jdbcTemplateObject){
		
		 this.jdbcTemplateObject = jdbcTemplateObject;
	}
	 

	    public int insertMovie(String titolo , String regista, String attori,String url) {
	        String query = "INSERT INTO movies (titolo, regista, attori, url) VALUES (?, ?, ?, ?)";
	        return jdbcTemplateObject.update(query, titolo, regista, attori,  url);
	    }
	    
	    public ArrayList<film> ritornaFilms(){
	    	ResultSet rs1 = null;
	    	
	    	
	            String query = "SELECT * FROM movies";
	            return jdbcTemplateObject.query(query, new ResultSetExtractor<ArrayList<film>>() {
	                @Override
	                public ArrayList<film> extractData(ResultSet rs) throws SQLException {
	                	ArrayList <film> listaFilm = new ArrayList<>();
	                    while (rs.next()) {
	                    	film o1 = new film();
	                        
	                        o1.setTitolo(rs.getString("titolo"));
	                        o1.setRegista(rs.getString("regista"));
	                        o1.setAttori(rs.getString("attori"));
	                        o1.setUrl(rs.getString("url"));
	                      
	                        
	                        listaFilm.add(o1);
	                    }
	                    return listaFilm;
	                }
	            });
	        }

}
