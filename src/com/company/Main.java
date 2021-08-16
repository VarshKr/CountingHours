package com.company;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static final String SERVICE_FILE_PATH = "C:\\Users\\varsh\\Documents\\SUMMER_2021\\Zero Hunger Internship\\ServiceFile.csv";
    public static final String HOURS_FILE_PATH = "C:\\Users\\varsh\\Documents\\SUMMER_2021\\Zero Hunger Internship\\Hours.csv";
    public static final String FILE_DELIMINATOR = ",";
    public static final String HEADER = "monHours, tuesHours, wedHours, thursHours, friHours, satHours, sunHours\n";


    public static void main(String [] args) throws Exception {
        InputStream inputStream = new FileInputStream(SERVICE_FILE_PATH);
        List<List<String>> services = readCSV(inputStream);
        inputStream.close();
        inputStream = new FileInputStream(HOURS_FILE_PATH);
        List<List<String>> hours = readCSV(inputStream);
        inputStream.close();


        //TODO: add a function to format the file in the google GDU template
        //Concatenate open and close hours and add them to the services file

        int [] monHours;
        int [] tuesHours;
        int [] wedHours;
        int [] thursHours;
        int [] friHours;
        int [] satHours;
        int [] sunHours;

        monHours = new int[48];
        tuesHours = new int[48];
        wedHours = new int[48];
        thursHours = new int[48];
        friHours = new int[48];
        satHours = new int[48];
        sunHours = new int [48];

        for(List<String> service : services){
            String serviceID = service.get(0);


            for(List<String> hour : hours){
                if(serviceID.equals(hour.get(0))){

                    int open = timeToInt(hour.get(2));
                    int close = timeToInt(hour.get(3));


                    switch(Integer.valueOf(hour.get(4))) {

                        case 1:

                            while(open<close){
                                monHours[open-1]++;
                                open++;
                            }
                            break;
                        case 2:


                            while(open<close){
                                tuesHours[open-1]++;
                                open++;
                            }
                            break;
                        case 3:

                            while(open<close){
                                wedHours[open-1]++;
                                open++;
                            }
                            break;
                        case 4:

                            while(open<close){
                                thursHours[open-1] = thursHours[open]+1;
                                open++;
                            }
                            break;
                        case 5:

                            while(open<close){
                                friHours[open-1]++;
                                open++;
                            }
                            break;
                        case 6:

                            while(open<close){
                                satHours[open-1]++;
                                open++;
                            }
                            break;
                        case 7:

                            while(open<close){
                                sunHours[open-1]++;
                                open++;
                            }
                            break;

                    }
                }
            }
             if(service.size()<26){
                 service.add("");
               }


        }

        List<int[]> listofLists = new LinkedList<>();
        listofLists.add(monHours);
        listofLists.add(tuesHours);
        listofLists.add(wedHours);
        listofLists.add(thursHours);
        listofLists.add(friHours);
        listofLists.add(satHours);
        listofLists.add(sunHours);

        OutputStream outputStream = new FileOutputStream("list_of_hours_each_weekday.csv");
        WriteCSV(HEADER, listofLists, outputStream);
    }

    //copy this method too! takes header variable and list of values
    private static void WriteCSV(String header, List<int[]> lines, OutputStream outputStream) throws IOException {
        Writer writer = new OutputStreamWriter(outputStream);
        writer.write(HEADER);
        for(int[] line : lines){
            for(int i = 0; i < line.length; i++){
                Integer toWrite = line[i];
                writer.write(toWrite.toString());
                if(i < line.length-1){
                    writer.write(FILE_DELIMINATOR);
                }
            }
            writer.write("\n");
        }
        writer.close();
    }

    //copy this method in new program
    private static List<List<String>> readCSV(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<List<String>> lines = new ArrayList<>(); //each row is a list, this whole things is a list of rows
        String line = reader.readLine(); //Skips header

        while(true){
            line = reader.readLine();
            if(line == null) break; //exit the loop at the end of the file
            //each row is a large string separated by commas, this is putting each value in a cell

            List<String> list = new ArrayList(Arrays.asList(
                    line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)")));
            lines.add(list);
        }
        return lines;
    }


    public static int timeToInt(String s){

        int returnTime;
        String [] hourMin = s.split(":");
        int hour = Integer.parseInt(hourMin[0]);
        int minutes = Integer.parseInt(hourMin[1]);

        returnTime = hour*2;

        if(minutes == 30)
            returnTime++;
        if(minutes>30)
            returnTime+=2;


        return returnTime;

    }


}