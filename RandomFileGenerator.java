/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * This program generates a random file called "randomOrders.txt".
 * Takes in an integer taken in by the user using a Scanner class.
 * Uses companylist.txt to fill static variable companyNames.
 * companyNames variable used in various methods of the program.
 * Program also makes sure user enters a positive integer.
 * 
 * Assumptions:
 * customerIDs comes from the current NYSE Company list (see companylist.txt file in Final
 * Project folder in Blackboard)
 * productIDs are all a single captial letter followed by a single numeric digit
 * orderDates are all in 2019
 * orderAmounts are all integers less than 100
 * repeatedOrderPeriods are all 1 to 30 days
 * repeatedOrderEndDates all in 2019
 */
import java.util.*;
import java.io.*;

public class RandomFileGenerator 
{
	static ArrayList<String> companyNames = new ArrayList<String>(); //static so the array list can be acessed in other methods
	final static int ORDER_YEAR = 2019; //all order dates need to be in 2019
	
	public static void main(String[] args) 
	{
		int genNum = 0;
		String customerID;
		String productID;
		String startDate;
		int orderAmount;
		int period;
		String endDate;
		
		try //put company names into array list
		{
			File fileLoad = new File("companylist.txt");
			Scanner fileScanner = new Scanner(fileLoad);
			while(fileScanner.hasNextLine())
			{
				companyNames.add(fileScanner.nextLine());
			}
			fileScanner.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found!");
		}
		
		Scanner myScan = new Scanner(System.in);
		boolean positiveNum = false;

		System.out.println("Enter the number of lines you want in the randomly generated file...");
		while(!positiveNum) //makes sure user enters a positive number
		{
			genNum = myScan.nextInt();
			if(genNum < 0)
			{
				System.out.println("Please enter a positive number.");
			}
			else
			{
				positiveNum = true;
			}
		}
		myScan.close();
		
		try //starting to write to the file
		{
			FileOutputStream fos = new FileOutputStream("randomOrders.txt", false);
			PrintWriter pw = new PrintWriter(fos);
			
			for(int i = 0; i < genNum; i++)
			{
				boolean repeat = isRepeat(); //determines whether to make a one-time or repeat order
				
				if(repeat) //repeat order
				{
					customerID = customerIDs();
					productID = productIDs();
					startDate = orderDates();
					orderAmount = orderAmounts();
					period = repeatedOrderPeriods();
					endDate = repeatedOrderEndDates(startDate);
					pw.println("R," + customerID + "," + productID + "," + startDate + "," + orderAmount + ","
							+ period + "," + endDate);
				} 
				else //one-time order
				{
					customerID = customerIDs();
					productID = productIDs();
					startDate = orderDates();
					orderAmount = orderAmounts();
					pw.println("O," + customerID + "," + productID + "," + startDate + "," + orderAmount);
				}
			}
			
			pw.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("No file!");
		}
		
		System.out.println("File \"randomOrders.txt\" generated!");
	}
	
	public static boolean isRepeat()
	{
		Random myRand = new Random();
		int randNum = myRand.nextInt(2);
		
		if(randNum == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static String customerIDs() //comes from companyNames aka NYSE Company list
	{
		int randNum = (int) (Math.random() * companyNames.size());
		return companyNames.get(randNum);
	}
	
	public static String productIDs() //alpha numerical with capital letter
	{
		char letter = (char) ((int) ((Math.random() * 26) + 65)); //capital letters only
		int randNum = (int) (Math.random() * 9) + 1; //number 1-9
		return "" + letter + randNum;
	}
	
	public static String orderDates()
	{
		int randMonth = (int) (Math.random() * 12) + 1;
		int randDay = 0;
		if(randMonth == 1|| randMonth == 3|| randMonth == 5 || randMonth == 7 || randMonth == 8 || randMonth == 10) //months with 31 days
		{
			randDay= (int) (Math.random() * 31) + 1;
		}
		else if(randMonth == 4 || randMonth ==6 || randMonth == 9 || randMonth == 11 || randMonth == 12) //months with 30 days
		{
			randDay= (int) (Math.random() * 30) + 1;
		}
		else //February - not a leap year
		{
			randDay = (int) (Math.random() * 28) + 1; 
		}
		
		return randMonth + "/" + randDay + "/" + ORDER_YEAR;
	}
	
	public static int orderAmounts() //amounts are all integers less than 100 - excluding 0
	{
		int randAmount = (int) (Math.random() * 99) + 1; 
		return randAmount;
	}
	
	public static int repeatedOrderPeriods() //all periods are 1 to 30 days
	{
		int randPeriod = (int) (Math.random() * 30) + 1;
		return randPeriod;
	}
	
	public static String repeatedOrderEndDates(String startDate) //also based off the start date
	{															 //needs parameter of the startDate to split the string
		int randMonth = 1;
		int randDay = 1;
		String[] splitStartDate = startDate.split("/"); //startDate is split to compare the month and day (if necessary)
		boolean afterStartDate = false;
		
		while(!afterStartDate)
		{
			randMonth = (int) (Math.random() * 12) + 1;
			if(randMonth == Integer.parseInt(splitStartDate[0])) //if they are in the same month
			{
				if(randMonth == 1|| randMonth == 3|| randMonth == 5 || randMonth == 7 || randMonth == 8 || randMonth == 10) //months with 31 days
				{
					randDay= (int) (Math.random() * 31) + 1;
				}
				else if(randMonth == 4 || randMonth ==6 || randMonth == 9 || randMonth == 11) //months with 30 days
				{
					randDay = (int) (Math.random() * 30) + 1;
				}
				else if(randMonth == 12) //guarantees to not generate a date of Dec. 31st. This to ensure that if it was a repeated order
				{						 //the repeated order would be on Dec. 31st.
					randDay = (int) (Math.random() * 29) + 1;
				}
				else //February - not a leap year
				{
					randDay = (int) (Math.random() * 28) + 1; 
				}
				
				if(randDay > Integer.parseInt(splitStartDate[1])) //makes sure that the end date day is after the start date day
				{												  //if they are in the same month
					afterStartDate = true;
				}
			}
			else if(randMonth > Integer.parseInt(splitStartDate[0])) //end date month after start date month
			{														 //does not require to verify day if after the start month
				if(randMonth == 1|| randMonth == 3|| randMonth == 5 || randMonth == 7 || randMonth == 8 || randMonth == 10) //months with 31 days
				{
					randDay= (int) (Math.random() * 31) + 1;
				}
				else if(randMonth == 4 || randMonth ==6 || randMonth == 9 || randMonth == 11 || randMonth == 12) //months with 30 days
				{
					randDay= (int) (Math.random() * 30) + 1;
				}
				else //February - not a leap year
				{
					randDay = (int) (Math.random() * 28) + 1; 
				}
				
				afterStartDate = true;
			}
		}
		
		return randMonth + "/" + randDay + "/"  + ORDER_YEAR;
	}
}
