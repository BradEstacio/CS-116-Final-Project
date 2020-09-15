/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * Main class for OrderManager, OneTimeOrders, and RepeatedOrders.
 * Has methods that @throws OfficeSupplyUIException.
 * 
 * Allows the user the following options (in while loop):
 * 1. specify a file name of orders to load
 * 2. specify the information to add an order or repeated order
 * 3. specify an orderID to delete
 * 4. specify a CustomerID to display a list of orders for that customer in increasing date sorted order
 * 5. calculate and output an order inventory report (sorted by year, month and productID).
 * 6. Quit - user quits program breaking while loop
 * 
 * Assumptions:
 * User must have loaded a file before being able to access options 2-5
 * @see static boolean fileLoaded = false;
 * If user loads the file a second time, the program assumes the user wants to
 * overwrite the previous file and says so when using loadFile().
 * 
 * Note: Adding/deleting orders in OrderManager inventory does not affect the file
 * the user loaded.
 */
import java.io.*;
import java.util.*;

public class OfficeSupplyUI 
{
	static OrderManager inventory;
	static Scanner userInput = new Scanner(System.in);
	static boolean fileLoaded = false; //if user has loaded a file in the UI
	
	public static void main(String[] args)
	{
		int inputNum = 0;
		System.out.println("Welcome to the Office Supply Order System. How may we help you today?");
		while(true) 
		{		
			try
			{
				System.out.println("Enter a number corresponding to the options listed below...");
				System.out.println("1. Load a file"); //specify a file name of orders to load
				System.out.println("2. Add an order or repeated order"); //specify information to add an order or repeated order
				System.out.println("3. Delete an order"); //specify an order ID to delete
				System.out.println("4. List orders for a specific customer"); //specify a customer ID to display a list of orders for that customer - increasing date sorted order
				System.out.println("5. Inventory report"); //calculate and output inventory report - sorted by year, month and product ID
				System.out.println("6. Quit"); //quits program
				inputNum = getUserInput();
			}
			catch(OfficeSupplyUIException e) 
			{
				System.out.println(e.getMessage());
			}
			
			if(inputNum == 6) //user enters 6 - breaks out of while loop to terminate program
			{
				break;
			}
			
			try //try/catch for inputs 1-5
			{
				if(inputNum == 1)
				{
					loadFile();
				}
				else if(inputNum == 2)
				{
					addAnOrder();
				}
				else if(inputNum == 3)
				{
					deleteAnOrder();
				}
				else if(inputNum == 4)
				{
					listOrders();
				}
				else if(inputNum == 5)
				{
					inventoryReport();
				}
			}
			catch(OfficeSupplyUIException e)
			{
				System.out.println(e.getMessage());
			}	
		}
		
		System.out.println("We hope you enjoyed using the Office Supply Order System.");
		System.out.println("Have a nice day!");
	}
	
	public static int getUserInput() throws OfficeSupplyUIException //helps condense void main with exceptions
	{	
		while(!userInput.hasNextInt())
		{
			System.out.println("You must enter an integer.");
			userInput.next();
		}
		int input = userInput.nextInt();
		if((input <= 6) && (input > 0)) //makes sure user only enters #s 1-6
		{
			return input;
		}
		else //if they enter a negative number or a number greater than 6
		{
			throw new OfficeSupplyUIException("Something went wrong! Please try again."); 
		}
	}
	
	public static void loadFile() throws OfficeSupplyUIException //user enters 1
	{
		String fileName;
		
		System.out.println("***LOAD FILE***");
		System.out.println();
		System.out.println("Enter the name of the file you want to load...");
		userInput.nextLine(); //line is cleared to prevent error
		fileName = userInput.nextLine();
		
		if(fileLoaded) //user has already loaded file - assumes user wants to overwrite previous one
		{
			if(fileName.contains(".txt"))
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			else
			{
				try
				{
					File file = new File(fileName + ".txt");
					Scanner fileScanner = new Scanner(file);
					inventory = new OrderManager(fileScanner.nextLine());
					while(fileScanner.hasNextLine())
					{
						inventory.addOrder(fileScanner.nextLine());
					}
					fileScanner.close();
					fileLoaded = true;
					System.out.println("Previous file overwritten.");
					System.out.println("File successfully loaded!");
				}
				catch(FileNotFoundException e)
				{
					System.out.println("File not found!");	
					throw new OfficeSupplyUIException("Something went wrong! Please try again.");
				}
			}
		}
		else //if user is loading file for the first time
		{
			if(fileName.contains(".txt"))
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			else
			{
				try
				{
					File file = new File(fileName + ".txt");
					Scanner fileScanner = new Scanner(file);
					inventory = new OrderManager(fileScanner.nextLine());
					while(fileScanner.hasNextLine())
					{
						inventory.addOrder(fileScanner.nextLine());
					}
					fileScanner.close();
					fileLoaded = true;
					System.out.println("File successfully loaded!");
				}
				catch(FileNotFoundException e)
				{
					System.out.println("File not found!");	
					throw new OfficeSupplyUIException("Something went wrong! Please try again.");
				}
			}
		}
	}
	
	/**
	 * Uses Scanner class to ask user for everything necessary
	 * needed to add in order into OrderManager inventory
	 * orderType and productID are case sensitive
	 * @throws OfficeSupplyUIException
	 */
	public static void addAnOrder() throws OfficeSupplyUIException //user enters 2
	{
		String orderType;
		String customerID;
		String productID;
		String date;
		int amount;
		int period;
		String endDate;
		String newOrder;
		
		if(!fileLoaded) //ensures user has loaded a file to add orders to
		{
			System.out.println("File not loaded.");
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		
		System.out.println("***ADD ORDER***");
		System.out.println();
		System.out.println("Enter 'O' for a repeated order, or 'R' for a repeated order...");
		userInput.nextLine(); //line is cleared to prevent error
		orderType = userInput.nextLine();
		//orderType is case sensitive
		if((!orderType.contains("O") & (!orderType.contains("R")))) //single & makes sure it has one or the other
		{															//using && will check both resulting in always throwing the exception
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		System.out.println("Enter a customer ID...");
		customerID = userInput.nextLine();
		System.out.println("Enter a product ID (case sensitive)...");
		productID = userInput.nextLine();
		//Guarantees valid product ID, case sensitive.
		if(!Character.isUpperCase(productID.charAt(0)) || (Integer.parseInt(String.valueOf(productID.charAt(1))) >  9) 
				|| (Integer.parseInt(String.valueOf(productID.charAt(1))) < 0))
		{
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
			System.out.println("Enter a date in the mm/dd/yyyy format...");
		date = userInput.nextLine();
		if(!date.contains("/"))
		{
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
			System.out.println("Enter an amount...");
		if((!userInput.hasNextInt()))
		{
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		
		if(orderType.equals("O"))
		{
			amount = userInput.nextInt();
			if(amount < 0)
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			else
			{
				newOrder = orderType + "," + customerID + "," + productID + "," + date + "," + amount;
				inventory.addOrder(newOrder); //If this is a one time order, add it to the order manager
			}
		}
		else //orderType.equals("R");
		{
			userInput.nextLine(); //line is cleared to prevent error
			amount = userInput.nextInt();
			if(amount < 0)
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			System.out.println("Enter a period");
			if(!(userInput.hasNextInt()))
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			period = userInput.nextInt();
			System.out.println("Enter an end date in the mm/dd/yyyy format...");
			endDate = userInput.nextLine();
			if(!endDate.contains("/"))
			{
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
			else 
			{
				newOrder = orderType + "," + customerID + "," + productID + "," + date + "," + amount + "," + period + "," + endDate;
				inventory.addOrder(newOrder);
			}
		}
		System.out.println("Order successfully added!");
	}

	public static void deleteAnOrder() throws OfficeSupplyUIException //user enters 3
	{
		if(!fileLoaded) //ensures user has loaded a file to add orders to
		{
			System.out.println("File not loaded.");
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		
		System.out.println("***DELETE ORDER***");
		System.out.println();
		System.out.println("Specify an order ID to remove...");
		if(userInput.hasNextInt())
		{
			int orderID = userInput.nextInt();
			try
			{
				inventory.deleteOrder(orderID);
			}
			catch(OrderManagerException e)
			{
				System.out.println(e.getMessage());
				throw new OfficeSupplyUIException("Something went wrong! Please try again.");
			}
		}
		else //if anything but a number is entered by the user
		{
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
	}
	
	public static void listOrders() throws OfficeSupplyUIException //user enters 4
	{
		if(!fileLoaded) //ensures user has loaded a file to add orders to
		{
			System.out.println("File not loaded.");
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		
		System.out.println("***LIST ORDERS***");
		System.out.println();
		System.out.println("Specify a customer ID...");
		userInput.nextLine();
		String customerID = userInput.nextLine();
		
		try
		{
			inventory.listOrder(customerID);
		}
		catch(OrderManagerException e)
		{
			System.out.println(e.getMessage());
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
	}
	
	/**
	 * Calculates order inventory report.
	 * Sorted by year, month, and productID
	 * using a Scanner class.
	 * User input is not case sensitive.
	 * @throws OfficeSupplyUIException
	 * @see inventoryReport - OrderManager
	 */
	public static void inventoryReport() throws OfficeSupplyUIException //user enters 5
	{
		String sortType = "";
		if(!fileLoaded) //ensures user has loaded a file to add orders to
		{
			System.out.println("File not loaded.");
			throw new OfficeSupplyUIException("Something went wrong! Please try again.");
		}
		
		System.out.println("***INVENTORY REPORT***");
		System.out.println();
		System.out.println(inventory.toString());
		System.out.println();
		inventory.inventoryReport();
		System.out.println();
		System.out.println("Inventory report successfully loaded.");		
	}
}
