import java.io.*;
import java.util.*;

public class Genres {

	public static void main(String[] args)throws IOException {
		//Variables, readers
		BufferedReader week = new BufferedReader(new FileReader("movies.csv"));
		HashMap<String, String> MovieGenres = new HashMap<>();
		File output = new File("output.txt");
		PrintWriter out = new PrintWriter(output);
		String line = week.readLine();
		String title;
		String genre;
		int year = 0;
		String tempYear;
		
		//Array of Genres I amassed after searching through the file and 
		//looking for the different types
		String[] genres = { "Action","Thriller","War","Romance","Crime",
							"Musical","Comedy","Horror","Documentary",
							"Western","Fantasy","Drama","Children",
							"Adventure","Children","Sci-Fi","Mystery"};	
				
		//While loop reads in the text after the first comma(',') and before 
		//the last comma. Done this way because some titles had commas inside their 
		//titles which made it difficult to use .split(","). The block of text was then
		//subsetted into a title and parsed into a year separately. The genres were then 
		//substringed from the last comma in the line to the end of the line. 
		//Then the title were used as keys and the release Year + genre were the values.
		while (week.ready()) {
			line = week.readLine();
			String token = line.substring(line.indexOf(",")+1,line.lastIndexOf(","));//Reads line 
			title = token.substring(0, token.lastIndexOf(" ") + 1 );//Substrings the title
			tempYear = token.substring(token.lastIndexOf("(")+1, token.lastIndexOf(")") );//Substrings the year from the first ( to the last )
			genre = line.substring(line.lastIndexOf(",") + 1);
			MovieGenres.put(title,tempYear + " " + genre);
			
		}	
		
		//Genre Count for different Genres included in the file
		out.println("|Genre Count OverAll|");
		for(int i =0;i <genres.length;i++)
			GenreCount(MovieGenres,genres[i],out);
		
		//Formatting in output file
		out.println();
		
		//Last Five Year Genre Count
		out.println("|Genre Count based on last five years|");	
		for(int i =0;i <genres.length;i++)
			FiveYearGenreCount(MovieGenres,genres[i],out);
		
		//These 2 lines call methods that will return the most recent year 
		//and the least recent year. Max being 2018 and Min being 1902
		int max = MaxYear(MovieGenres);
		int min = MinYear(MovieGenres);
	
		//Formatting
		out.println();
		out.println("|MOVIES OF EACH GENRE PER YEAR FROM 1902 TO 2018|");
		
		/*
		 * Nested for loop. Outer loop keeps track of year we found earlier in the
		 * form of min and max. Inner loop runs the genres. Will run from 1902 to 2018 
		 * counting how many movies of each genre are present 
		 */
		for(int Year = min; Year <= max; Year++) {
			for(int mGenre =0; mGenre<genres.length;mGenre++) {
				YearCount(MovieGenres, genres[mGenre],Year,out);
			}
			out.println();
		}
		out.close();
	}
	
	//Takes a Map of String, String type, the genre type and a PrintWriter object. 
	//Iterates through hashmap, extracts the movie genre 
	//Then uses .contain method to see if the genre applies
	//increments a count value and writes to file.
	public static void GenreCount(Map<String, String> map, String genre,PrintWriter out) {
		
		int count = 0;
		String gen;
		for(String Movies : map.keySet()) {
			gen = map.get(Movies);
			if (gen.contains(genre)) {
				count++;
			}
		}
		out.println(genre + " Movies = " + count);	
	}
	
	//Takes a Map of String String type, the genre type and a PrintWriter object. 
	//Iterates through hashmap, extracts the movie genre Then uses .contain method 
	//to see if the genre applies increments a count value and writes to file. Also 
	//parses the year from the value of the HashMap key into an integer, then only allow
	//count increments if the year is more than 2015. 
	public static void FiveYearGenreCount(Map<String,String> map, String genre, PrintWriter out) {
		
		int count = 0;
		int year;
		String gen;
		for(String Movies : map.keySet()) {
			gen = map.get(Movies);
			year = Integer.parseInt(gen.substring(0,4));
			
			if (gen.contains(genre) && year>=2015) {
				count++;
			}
		}
		
		out.println(genre + " Movies = " + count);	
	}
	
	/*
	 * This method is going to be used in the nested for loop above. It takes a Map object, a genre
	 * a year and PrintWriter object. The map will be iterated through and the genre and year variables will
	 * be compared with the values in the Hashmap to find how many movies of a specific genre came out
	 * in a specific year.
	 */
	public static void YearCount(Map<String,String> map, String genre,int year, PrintWriter out) {
		
		int count = 0;
		int mYear;
		String gen;
		
		for(String Movies : map.keySet()) {
			gen = map.get(Movies);
			mYear =Integer.parseInt(gen.substring(0,4));//Extracts year and parses into Integer for comparing to parameter year
			
			//If the genre AND year match it increments count
			if (gen.contains(genre) && year==mYear) {
				count++;
			}
		}
		out.println(genre + " movies in " + year + " = " + count);	
	}
	
	
	//Basically iterates through HashMap trying to find the maximum year which came out to 2018
	public static int MaxYear(Map<String, String> map) {
		int max = 0;
		int year;
		String gen;
		for(String Movies : map.keySet()) {
			gen = map.get(Movies);
			year = Integer.parseInt(gen.substring(0,4));
			if(year > max) {
				max = year;
			}
		}
		return max;
		
	}
	//Iterates through HashMap to find minimum. Which came out to 1902
	public static int MinYear(Map<String, String> map) {
		int min = 99999;
		int year;
		String gen;
		for(String Movies : map.keySet()) {
			gen = map.get(Movies);
			year = Integer.parseInt(gen.substring(0,4));
			if(year < min) {
				min = year;
			}
		}
		return min;
	}
	

}
