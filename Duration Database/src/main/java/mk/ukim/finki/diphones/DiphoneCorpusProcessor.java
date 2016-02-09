package mk.ukim.finki.diphones;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class DiphoneCorpusProcessor {

public static String read() {
		
		BufferedReader br = null;
		try {
				br = new BufferedReader(new InputStreamReader(
            new FileInputStream("C:\\Users\\DANIEL\\Desktop\\magisterska\\selected_sentences_180-checked.txt"), "UTF8"));
				StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
	
		    while (line != null) {
		    		line = clearLine(line);
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
	
	private static String clearLine(String line) {
		String cleanLine = line.replaceAll("\\.", "");
		cleanLine = cleanLine.replaceAll("recording-mrt-\\d{3}", "");
		cleanLine = cleanLine.replaceAll(",", "");
		cleanLine = cleanLine.replaceAll("\\?", "");
		cleanLine = cleanLine.replaceAll("\\-", " ");
		cleanLine = cleanLine.trim();
		cleanLine = cleanLine.replaceAll(" ", "_");
		cleanLine += "_";
		return cleanLine;
}

	public static void main (String [] args) {
		
		String file = read();
		String lines [] = file.split(System.lineSeparator());
		
		Map<String, Integer> diphones = new HashMap<String, Integer>();
		double diphoneCounter = 0;
		
		for (String line : lines) {
			Integer lineLength = line.toCharArray().length;
			
			for (int i = 0; i < lineLength - 1; i++) {
				String diphone = line.charAt(i) + "_" + line.charAt(i + 1);
				if (diphones.containsKey(diphone)) {
					Integer counter = diphones.get(diphone);
					diphones.put(diphone, counter + 1);
				} else {
					diphones.put(diphone, Integer.valueOf(1));
					diphoneCounter += 1;
				}
			}
		}
		
		Map<String, Integer> sortedDiphonesByFrequency = sortDiphonesByFrequency(diphones);
		printResult(diphoneCounter, sortedDiphonesByFrequency);
		System.out.println("Finished!");
	}

	private static Map<String, Integer> sortDiphonesByFrequency(Map<String, Integer> diphones) {
		
		LinkedList<Map.Entry<String, Integer>> list =
        new LinkedList<Map.Entry<String, Integer>>( diphones.entrySet() );
    Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
    {
        @Override
        public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
        {
            return (o2.getValue()).compareTo(o1.getValue() );
        }
    } );

    Map<String, Integer> result = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> entry : list)
    {
        result.put( entry.getKey(), entry.getValue() );
    }
    return result;
  }

	private static void printResult(double diphoneCounter, Map<String, Integer> diphones) {
		PrintWriter writer = null;
    try {
	    writer = new PrintWriter("C:\\Users\\DANIEL\\Desktop\\magisterska\\diphone-coverage.txt", "UTF-8");
	    writer.println("Total number of diphones: " + diphoneCounter);
	    for (String diphone : diphones.keySet()) {
	    	writer.println(diphone + " " + diphones.get(diphone) + ", " + diphones.get(diphone)/diphoneCounter);
	    }
	    writer.close();
    } catch (FileNotFoundException e) {
	    e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
	    e.printStackTrace();
    }
  }
}
