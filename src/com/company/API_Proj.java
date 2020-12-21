package com.company;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.Scanner;

import com.google.gson.*;

public class API_Proj
{


    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome!\nChoose from the menu.\n");
        System.out.println("- verge - to see the top three Verge articles.");
        System.out.println("- weather - to see weather in select areas.");
        System.out.println("- stop - to stop the program.\n");
        String string = input.nextLine();

        while(!string.contains("stop"))
        {


            if(string.contains("verge".toLowerCase()))
            {
                System.out.println("Thinking...\n");
                try {
                    startWebRequestNews();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(string.contains("weather") || string.contains("Weather"))
            {

                if(checkAl(string))
                {
                    if(string.contains("Dallas") || string.contains("dallas"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("Dallas");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if(string.contains("Dubai") || string.contains("dubai"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("Dubai");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if(string.contains("Bradenton") || string.contains("bradenton"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("Bradenton");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if(string.contains("Anchorage") || string.contains("anchorage"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("Anchorage");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if(string.contains("Seoul") || string.contains("seoul"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("Seoul");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        System.out.println("Sorry, the only cities I know are 'Dallas', 'Dubai', 'Bradenton', 'Seoul' and 'Anchorage'");
                    }
                }

                else if(checkAlNum(string))
                {
                    if(string.contains("75078"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("75078");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if (string.contains("75080"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("75080");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else if (string.contains("10013"))
                    {
                        System.out.println("Thinking...");
                        try {
                            startWebRequest("10013");
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        System.out.println("Only zip codes I know are '75078', '75080', and '10013'");
                    }
                }

                else
                {
                    System.out.println("Error, make sure your input is alphanumeric.");
                }
            }
            string = input.nextLine();
        }

        System.out.println("Bye");

    }


    static String startWebRequest(String city) throws IOException
    {
        String weatherURL = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=26d8029aee13140f465b51320e882121";             //please get your own token to use from API.Openweathermap

        StringBuilder result = new StringBuilder(); //this is going to hold the JSON Response from the server
        URL url = new URL(weatherURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        DecimalFormat numberFormat = new DecimalFormat("#.0");

        double temp = parseTemp(result.toString());
        double tempMax = parseMaxTemp(result.toString());
        double tempMin = parseMinTemp(result.toString());
        String location = parseLocation(result.toString());
        String disLocation = parseDisLocation(result.toString());

        System.out.print("Temperature in "+ disLocation +", " +location);
        System.out.print(" is " + numberFormat.format(temp));
        System.out.print(" with a high of " + numberFormat.format(tempMax));
        System.out.println(" and a low of " +  numberFormat.format(tempMin)+"\n");

        return result.toString();
    }

    static String startWebRequestNews() throws IOException
    {
        String newsURL = "https://newsapi.org/v1/articles?source=the-verge&sortBy=top&apiKey=67df9ee42ed14f048a3bf8559009ab2f";             //please get your own token to use from API.Openweathermap

        StringBuilder result = new StringBuilder(); //this is going to hold the JSON Response from the server
        URL url = new URL(newsURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();

        parseNews(result.toString());

        return result.toString();
    }

    static void parseNews(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject mainObj = jelement.getAsJsonObject();

        JsonArray newsObj = mainObj.getAsJsonArray("articles");

        JsonObject firstObj = newsObj.get(0).getAsJsonObject();
        String author1 = firstObj.get("author").getAsString();
        String title1 = firstObj.get("title").getAsString();

        JsonObject secondObj = newsObj.get(1).getAsJsonObject();
        String author2 = secondObj.get("author").getAsString();
        String title2 = secondObj.get("title").getAsString();

        JsonObject thirdObj = newsObj.get(2).getAsJsonObject();
        String author3 = thirdObj.get("author").getAsString();
        String title3 = thirdObj.get("title").getAsString();

        System.out.print("Top three articles on TheVerge.com are: \n\t'" + title1 + "' by " + author1);
        System.out.print("\n\t'" + title2 + "' by " + author2);
        System.out.println("\n\t'" + title3 + "' by " + author3+"\n");
    }

    static double parseTemp(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();

        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("main");
        double  temp = coordinateObject.get("temp").getAsDouble();
        temp = (temp*1.8) - 459.67;
        return temp;
    }

    static double parseMaxTemp(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("main");
        double  temp = coordinateObject.get("temp_max").getAsDouble();
        temp = (temp*1.8) - 459.67; //Converting to Fahrenheit
        return temp;
    }

    static double parseMinTemp(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("main");
        double  temp = coordinateObject.get("temp_min").getAsDouble();
        temp = (temp*1.8) - 459.67; //Converting to Fahrenheit
        return temp;
    }

    static String parseLocation(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
        JsonObject  coordinateObject = MasterWeatherObject.getAsJsonObject("sys");
        String  temp = coordinateObject.get("country").getAsString();
        return temp;
    }

    static String parseDisLocation(String json)
    {
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject  MasterWeatherObject = jelement.getAsJsonObject();
        String  temp = MasterWeatherObject.get("name").getAsString();
        return temp;
    }

    static boolean checkAlNum(String yea)
    {
        for(int i = 0; i < yea.length(); i++)
        {
            if(!Character.isLetterOrDigit(yea.charAt(i)))
            {
                if(yea.charAt(i) != ' ')
                {
                    return false;
                }
            }
        }

        return true;
    }

    static boolean checkAl(String yea)
    {
        for(int i = 0; i < yea.length(); i++)
        {
            if(!Character.isLetter(yea.charAt(i)))
            {
                if(yea.charAt(i) != ' ')
                {
                    return false;
                }
            }
        }

        return true;
    }

}

