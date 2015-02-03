package com.shk.stats;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class JbossStats {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try(BufferedReader br = new BufferedReader(new FileReader("C:/Users/xoriant/Desktop/PROD_JBOSS_stats/20150126_094415/stats_PROD71_TM2_resources_CLI.txt"))) {
			String line = null;
			
			while((line=br.readLine()) != null) {
				if(line.indexOf("ActiveCount") != -1) {
					line = line.trim();
					System.out.print(line.substring(18, line.length() -2) + "        ");
				}
				if(line.indexOf("AvailableCount") != -1) {
					line = line.trim();
					System.out.print(line.substring(21, line.length() -2) + "        ");
				}
				if(line.indexOf("MaxUsedCount") != -1) {
					line = line.trim();
					System.out.print(line.substring(19, line.length() -2));
				}
				
				if(line.indexOf("TotalCreationTime") != -1) {
					System.out.println();
				}

				if(line.indexOf("heap-memory-usage") != -1) {
					break;
				}
			}

			System.out.println("##################");
		
			while((line=br.readLine()) != null) {
				if(line.indexOf("init") != -1) {
					line = line.trim();
					System.out.print(Long.parseLong(line.substring(10, line.length() -2))/(1048576) + "        ");
				}
				if(line.indexOf("used") != -1) {
					line = line.trim();
					System.out.print(Long.parseLong(line.substring(10, line.length() -2))/(1048576) + "        ");
				}
				if(line.indexOf("committed") != -1) {
					line = line.trim();
					System.out.print(Long.parseLong(line.substring(15, line.length() -2))/(1048576) + "        ");
				}
				if(line.indexOf("max") != -1) {
					line = line.trim();
					System.out.print(Long.parseLong(line.substring(9, line.length() -1))/(1048576) + "        ");
				}
				
				if(line.indexOf("verbose") != -1) {
					System.out.println();
				}
				if(line.indexOf("all-thread-ids") != -1) {
					break;
				}

			}

			
			System.out.println("##################");

			while((line=br.readLine()) != null) {
				line = line.trim();
				if(line.startsWith("\"thread-count")) {
					System.out.print(line.substring(18, line.length() -1) + "        ");
				}
				if(line.startsWith("\"daemon-thread-count")) {
					System.out.print(line.substring(25, line.length() -1) + "        ");
				}
				
				if(line.indexOf("current-thread-user-time") != -1) {
					System.out.println();
				}

			}

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
