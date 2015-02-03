package com.shk.stats;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.shk.stats.beans.DataSource;
import com.shk.stats.beans.Memory;
import com.shk.stats.beans.Resources;
import com.shk.stats.beans.Slice;

public class JBossStatsNew {

	public static void main(String[] args) {

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		
		List<Slice> slices = new ArrayList<Slice>();


		String filename = "C:\\Users\\xoriant\\Desktop\\PROD_JBOSS_stats\\20150201_212229\\stats.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = null;
			StringBuilder stringBuilder = new StringBuilder();

			while ((line = br.readLine()) != null) {
				stringBuilder.append(line);
			}
			String text = stringBuilder.toString();
			int sliceIndex = -1;

			while ((sliceIndex = text.indexOf("##")) != -1) {
				int lastIndex = text.indexOf("##", sliceIndex + 2);

				Slice slice = null;

				if (lastIndex != -1) {
					slice = new Slice(text.substring(sliceIndex, lastIndex));
					text = text.substring(lastIndex);
				} else {
					slice = new Slice(text);
					text = "";
				}
				slices.add(slice);
			}

			for (Slice slice : slices) {
				
				
				Session session = sessionFactory.openSession();
				session.beginTransaction();
				
				session.persist(slice);
			session.getTransaction().commit();
				
				session.close();
				
/*				int index = 0;
				for (Resources resource : slice.getNsp().getDatasources()) {
					

  					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
 
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + datasourceList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm1().getDatasources()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + datasourceList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm2().getDatasources()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + datasourceList.get(index) + ","
							+ resource.toString());
					index++;
				}

				index = 0;
				for (Resources resource : slice.getNsp().getMemoryList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + memoryList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm1().getMemoryList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + memoryList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm2().getMemoryList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + memoryList.get(index) + ","
							+ resource.toString());
					index++;
				}

				index = 0;
				for (Resources resource : slice.getNsp().getThreadList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + threadList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm1().getThreadList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + threadList.get(index) + ","
							+ resource.toString());
					index++;
				}
				for (Resources resource : slice.getTm2().getThreadList()) {
					System.out.println(slice.getEnvironment() + "," + slice.getMachineName() + ","
							+ slice.getSliceName() + "," + slice.getTime()
							+ "," + threadList.get(index) + ","
							+ resource.toString());
					index++;
				}
*/
			}
			

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}