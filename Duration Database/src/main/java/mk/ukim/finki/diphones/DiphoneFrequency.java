package mk.ukim.finki.diphones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DiphoneFrequency {

	public static String read() {
		
		BufferedReader br = null;
		try {
				br = new BufferedReader(new FileReader("C:\\Users\\DANIEL\\Desktop\\magisterska\\diphone-frequency.txt"));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
	
		    while (line != null) {
		    		line = line.split(" ")[3];
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    String everything = sb.toString();
		    return everything;
		} catch (IOException e) {
	    e.printStackTrace();
    } finally {
		    try {
	        br.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		}
		return null;
	}
	
	
	public static void main (String [] args) {
		
		String file = read();
		String[] percentages = file.split(System.lineSeparator());
		
		Integer numberOfDiphones = Integer.valueOf(args[0]);
		double sum = 0.0;
		for (int i = 0; i < numberOfDiphones; i++) {
			sum += Double.parseDouble(percentages[i]);
		}
		
		System.out.println("The first " + numberOfDiphones + " diphones cover: " + sum + " of the corpus");
	}
}
