package com.example.demo;




import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MyController {
	
	private MovieJDBCTemp movieJDBCTemp;
	
	@Autowired
    public MyController(MovieJDBCTemp movieJDBCTemp) {
		
    	this.movieJDBCTemp = movieJDBCTemp;
	}

	
    @Autowired
    private OmdbService omdbService;

    @GetMapping("/")
    public String getMovieForm(Model model) {
    	ArrayList <film> lista = new ArrayList<>();
    	lista = movieJDBCTemp.ritornaFilms();
    	model.addAttribute("lista", lista);
    	
    	 return "getMovieForm";
    }

    @PostMapping("/getMovie")
    public String getMovie(@RequestParam("title") String title, Model model) {
        String movieDetails = omdbService.getMovieDetails(title);
        
        // Parse JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(movieDetails);
            model.addAttribute("title", jsonNode.get("Title").asText());
            model.addAttribute("director", jsonNode.get("Director").asText());
            model.addAttribute("actors", jsonNode.get("Actors").asText());
            model.addAttribute("poster", jsonNode.get("Poster").asText());
            
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return "movieDetails";
    }
    
    @PostMapping("/add")
    public ResponseEntity<String> addMovie(@RequestParam("tit") String s) {
    	System.out.println(s);
    	String movieDetails = omdbService.getMovieDetails(s);
    	// Parse JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(movieDetails);
           String titolo =  jsonNode.get("Title").asText();
            String regista =  jsonNode.get("Director").asText();
           String attori = jsonNode.get("Actors").asText();
            String url =  jsonNode.get("Poster").asText();
            movieJDBCTemp.insertMovie(titolo, regista, attori, url);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
        
    	
    	
    	  return ResponseEntity.ok("Film aggiunto con successo");
    }

}


