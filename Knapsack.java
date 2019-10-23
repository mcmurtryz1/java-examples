//Zackary McMurtry
//CSC 364
//The file is meant to solve the '0/1 knapsack problem' optimally by reading the options from a provided text file
//then building the optimal list while maintaining a complete history of the best projects. Afterwards, it writes the results to
//a provided text file.

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Knapsack {

	public static void main(String[] args) {
		//Start the input scanner
		Scanner userInput = new Scanner(System.in);

		//Prompt the user for the necessary information
		System.out.print("Enter the number of available employee work weeks: ");
		int weeks = userInput.nextInt();
		System.out.print("Enter the name of input file: ");
		String inputPath = userInput.next();
		System.out.print("Enter the name of output file: ");
		String outputPath = userInput.next();
		
		//Close the scanner
		userInput.close();
		
		//Try to initialize the file scanner
		Scanner dataFile = null;
		try {
			dataFile = new Scanner(new File(inputPath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Populate an array list with the projects
		ArrayList<Project> projects = new ArrayList<>();
		while (dataFile.hasNextLine()) {
			String line = dataFile.nextLine();
			String[] split = line.split(" ");
			projects.add(new Project(split[0], Integer.parseInt(split[1]), Integer.parseInt(split[2])));
		}
		
		//Close the scanner
		dataFile.close();
		
		System.out.println("Number of projects = " + projects.size());
		
		//Get the optimal projects
		KnapsackProjects optimal = knapsack(weeks, projects);
		
		//Try to initialize a file output
		PrintWriter outputFile = null;
		try {
			outputFile = new PrintWriter(outputPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Write the necessary information to the file
		outputFile.println("Number of projects available: " + (projects.size()));
		outputFile.println("Available employee work weeks: " + weeks);
		outputFile.println("Number of projects chosen: " + (optimal.projects.size()));
		outputFile.println("Total profit: " + optimal.value);
		for (int x = 0; x < optimal.projects.size(); x++) {
			outputFile.println(optimal.projects.get(x).toString());
		}
		
		//Close the file output
		outputFile.close();
		
		//Write done statement
		System.out.println("Done");
	}
	
	@SuppressWarnings("unchecked")
	static KnapsackProjects knapsack(int weeks, ArrayList<Project> projects) {
		//New 2d array of KnapsackProjects
		KnapsackProjects[][] knapsackArray = new KnapsackProjects[projects.size() + 1][weeks + 1];
		
		//Loop through the 2d array
		for(int x = 0; x <= projects.size(); x++) {
			for(int y = 0; y <= weeks; y++) {
				//If we are on the top row or left row set it to a KnapsackProjects with profit 0
				if (x == 0 || y == 0) {
					knapsackArray[x][y] = new KnapsackProjects(0);
				//If we can possibly put the current project in
				} else if (projects.get(x - 1).workWeeks <= y) {
					//If the new project is more optimal, create a new KnapsackProjects at this location with the appropriate data
					if(projects.get(x - 1).profit + knapsackArray[x - 1][y - projects.get(x - 1).workWeeks].value > knapsackArray[x - 1][y].value) {
						knapsackArray[x][y] = new KnapsackProjects(projects.get(x - 1).profit + knapsackArray[x - 1][y - projects.get(x - 1).workWeeks].value, 
								(ArrayList<Project>) knapsackArray[x - 1][y - projects.get(x - 1).workWeeks].projects.clone());
						knapsackArray[x][y].projects.add(projects.get(x - 1));
					//Otherwise, just take the previous best
					} else {
						knapsackArray[x][y] = knapsackArray[x - 1][y];
					}
				//Otherwise, just take the previous best
				} else {
					knapsackArray[x][y] = knapsackArray[x - 1][y];
				}
			}
		}
		
		//Return the optimal KnapsackProjects
		return knapsackArray[projects.size()][weeks];
	}
	
	//A class that has a name, workWeeks, and a profit
	private static class Project {
		public String name;
		public int workWeeks;
		public int profit;
		
		//Constructor for a new project with all fields
		public Project(String name, int workWeeks, int profit) {
			this.name = name;
			this.workWeeks = workWeeks;
			this.profit = profit;
		}
		
		//toString method to easily write out projects to file
		@Override
		public String toString() {
			return this.name + " " + this.workWeeks + " " + this.profit;
		}
	}

	//A class that holds a total value for profit and an array list of projects
	//The only point of this class is to hold the history of the projects chosen
	private static class KnapsackProjects {
		public int value;
		public ArrayList<Project> projects = new ArrayList<>();
		
		public KnapsackProjects(int value, ArrayList<Project> projects) {
			this.value = value;
			this.projects = projects;
		}
		
		public KnapsackProjects(int value) {
			this.value = value;
		}
	}
}
