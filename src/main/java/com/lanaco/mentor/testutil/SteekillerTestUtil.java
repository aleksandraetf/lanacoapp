package com.lanaco.mentor.testutil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SteekillerTestUtil {
	
	
	public static ArrayList<String> readFromFile(String path) throws IOException{
		ArrayList<String> result=new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(new File(path)))) {
		    String line;
		    while ((line = br.readLine()) != null)
		    	result.add(line);
		}
		return result;
	}
	
	public static void writeToFile(String path,ArrayList<String> lines) throws IOException {
		File file=new File(path);
		
		//overwriting file
		if(file.exists())
			file.delete();
		file.createNewFile();

		try (PrintWriter printer = new PrintWriter(file)) {
		    for(String line : lines)
		    	printer.println(line);
		}
	}	
	
	
}
