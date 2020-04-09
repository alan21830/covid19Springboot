package covid19.model;

import lombok.Data;

@Data
public class Countries {

	 

	public String id;
    public String country;
	public int cases,todayCases,deaths,todayDeaths,recovered,active,critical;
 
	
}
