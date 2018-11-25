package com.domfeltl.json;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;

public class vatParser {

	private static OkHttpClient client = new OkHttpClient();
	
	
	public static void main(String[] args) {

		
		String json = null; // initializing the variable
		try
		{
			json = getEuroJSON("http://jsonvat.com/");
			
			JSONObject obj_JSONObject = new JSONObject(json);
			JSONArray obj_JSONArray = obj_JSONObject.getJSONArray("rates");
			
			/*
			System.out.println(" ");
			System.out.println("Printing \"rates\" array"); 
				System.out.println(obj_JSONArray);
				System.out.println("--" + obj_JSONArray.length());
				
			System.out.println(" ");
			System.out.println("Printing 6th element of \"rates\" array");
				JSONObject obj_JSONObject2 = obj_JSONArray.getJSONObject(5);
				System.out.println(obj_JSONObject2);
				
			System.out.println(" ");
			System.out.println("Printing contents of 1st object of \"periods\" array (newest VAT rates)");
				JSONArray periodsArray = obj_JSONObject2.getJSONArray("periods");
				JSONObject obj_JSONObject3 = periodsArray.getJSONObject(0);
				System.out.println(obj_JSONObject3);
				System.out.println("Effective from " + obj_JSONObject3.getString("effective_from"));
				
			System.out.println(" ");
			System.out.println("Printing standard VAT");	
				JSONObject ratesObject = obj_JSONObject3.getJSONObject("rates");
				System.out.println(ratesObject);
				System.out.println("VAT rate: " + ratesObject.getDouble("standard"));
			*/		
				
			JSONObject obj_JSONObject2 = obj_JSONArray.getJSONObject(5);
			JSONArray periodsArray = obj_JSONObject2.getJSONArray("periods");
			JSONObject obj_JSONObject3 = periodsArray.getJSONObject(0);
			JSONObject ratesObject = obj_JSONObject3.getJSONObject("rates");

		   
		   /* Defining variables */	
			
		   List<Double> CountryRates = new ArrayList<Double>();
		   Double CountryRate = 0.0;   
		   Double [] CountryRates2 = new Double[obj_JSONArray.length()];
		   String [] CountryNames = new String[obj_JSONArray.length()];
		   Double tempVar = 0.0;
		   String tempVarName;
				
		      
		   /* Creating an array of VAT rates */
		   
		   for(int i = 0; i< obj_JSONArray.length(); i++){
	              obj_JSONObject2 = obj_JSONArray.getJSONObject(i);
	              periodsArray = obj_JSONObject2.getJSONArray("periods");
				  obj_JSONObject3 = periodsArray.getJSONObject(0);
				  ratesObject = obj_JSONObject3.getJSONObject("rates");
				  CountryRate = ratesObject.getDouble("standard");
				  CountryRates2[i] = CountryRate;
				  CountryNames[i] = obj_JSONObject2.getString("name");
			}		
		    

		   /* Sorting vat array and swapping indexes on country names array */	
		    
			for(int i = 0; i< obj_JSONArray.length(); i++){
				
				for (int j = i; j < obj_JSONArray.length(); j++) {
			        if (CountryRates2[i] < CountryRates2[j]) {
			            tempVar = CountryRates2[i];
			            tempVarName = CountryNames [i];
			            CountryRates2[i] = CountryRates2[j];
			            CountryNames [i] = CountryNames [j];
			            CountryRates2[j] = tempVar;
			            CountryNames [j] = tempVarName;
			        }
			    }				  
			}
			
			
			/* Printing results */
		    
		    System.out.println("Country with highest VAT is " + CountryNames[0] + " with " + CountryRates2[0] + "%");
		    System.out.println("Country with 2nd highest VAT is " + CountryNames[1] + " with " + CountryRates2[1] + "%");
		    System.out.println("Country with 3rd highest VAT is " + CountryNames[2] + " with " + CountryRates2[2] + "%");
		    
		    System.out.println("Country with lowest VAT is " + CountryNames[obj_JSONArray.length()-1] + " with " + CountryRates2[obj_JSONArray.length()-1] + "%");
		    System.out.println("Country with 2nd lowest VAT is " + CountryNames[obj_JSONArray.length()-2] + " with " + CountryRates2[obj_JSONArray.length()-2] + "%");
		    System.out.println("Country with 3rd lowest VAT is " + CountryNames[obj_JSONArray.length()-3] + " with " + CountryRates2[obj_JSONArray.length()-3] + "%");		    
		    

		    /* Tracing exceptions */
		    
		} catch (Exception e)
		{
			e.printStackTrace();
		}		
		

	}
	
	/* Fetching data method */
	public static String getEuroJSON(String url) throws IOException {
		  Request request = new Request.Builder()
		      .url(url)
		      .build();

		  Response response = client.newCall(request).execute();
		  return response.body().string();
		}	
	
	

	
  
	

}


   
	   
   

	   
	   
	   

