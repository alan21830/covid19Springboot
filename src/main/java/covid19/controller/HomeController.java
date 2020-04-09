package covid19.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import covid19.model.Countries;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public String home(Model model) throws IOException {
	
		String response = esegui();
		Gson gson = new Gson();
		ArrayList<Countries> listcovid = gson.fromJson(response, new TypeToken<ArrayList<Countries>>() {}.getType());
		listcovid = (ArrayList<Countries>) listcovid.stream().sorted(Comparator.comparingInt(Countries::getCases).reversed()).collect(Collectors.toList());
		String data="";
		String labelLine="";
		
		model.addAttribute("listacovid", listcovid);
		ArrayList<Countries> listcovidRicovery = new ArrayList<Countries>();
		String dataLine="";
		String label="";
		listcovidRicovery =   (ArrayList<Countries>) listcovid.stream().sorted(Comparator.comparingInt(Countries::getRecovered)).collect(Collectors.toList());
		//listcovid.stream().sorted(Comparator.comparingInt(Countries::getTodayCases)).collect(Collectors.toList());
		System.out.print(listcovidRicovery.size());
		for (int i = 200; i <= 209; i++)
		{
			dataLine+=listcovidRicovery.get(i).getRecovered()+",";
			label+="'"+listcovidRicovery.get(i).getCountry()+"',";
		}
		dataLine = dataLine.substring(0,dataLine.length()-1);
		label = label.substring(0, label.length()-1);
		model.addAttribute("data",dataLine);
		model.addAttribute("label",label);
		return "home";
	}

	private String esegui() throws IOException {
	
	 String responseLine = null;
		URL url = new URL("https://corona.lmao.ninja/countries?sort=country");
	 HttpURLConnection con = (HttpURLConnection) url.openConnection();	
	 con.setRequestMethod("GET");
	  con.setRequestProperty("Content-Type", "application/json");
	 con.setDoOutput(true);
	 con.addRequestProperty("User-Agent", "Mozilla");
	  BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
	  StringBuilder response = new StringBuilder();
	 
	  while ((responseLine = br.readLine()) != null) {
	  response.append(responseLine.trim());
	   }
      return  response.toString();

	
	
	
	}
}
