/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * This class is used to make repeated orders for OrderManager.
 * Extends OneTimeOrders.
 * Has methods that @throws RepeatedOrdersException.
 * Is able to get/set endDate and period and has a toString method.
 */
import java.util.*;

public class RepeatedOrders extends OneTimeOrders 
{
	private int period;
	private Date endDate;
	
	public RepeatedOrders(String customerID, String productID, String date, int amount, int period, String endDate) 
	{
		super(customerID, productID, date, amount);
		this.period = period;
		try
		{
			setEndDate(endDate);
			if(super.getDate().after(this.endDate))
			{
				throw new RepeatedOrdersException("Sorry you entered an invalid date. Please try again.");
			}
		}
		catch(RepeatedOrdersException e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	//getters
	public int getPeriod()
	{
		return period;
	}
	
	public Date getEndDate()
	{
		return endDate;
	}
	
	//setters
	public void setPeriod(int period)
	{
		this.period = period;
	}
	
	public void setEndDate(String endDate) throws RepeatedOrdersException
	{
		String[] splitEndDate = endDate.split("/"); //splits string of date into string for date class {month, day, year}
		if((Integer.parseInt(splitEndDate[0]) > 12) || (Integer.parseInt(splitEndDate[0]) < 0) || (Integer.parseInt(splitEndDate[1]) > 31) || ((Integer.parseInt(splitEndDate[1]) < 0)) 
				|| (Integer.parseInt(splitEndDate[2]) < 0) || (Integer.parseInt(splitEndDate[2]) > 2019)) //makes sure mm/dd/yyyy is valid
		{
			throw new RepeatedOrdersException("Sorry you entered an invalid date. Please try again.");
		}
		Date makeEndDate = new Date(Integer.parseInt(splitEndDate[2]), Integer.parseInt(splitEndDate[0]) - 1, Integer.parseInt(splitEndDate[1])); //need year, month, day
		if(makeEndDate.before(super.getDate()))
		{
			throw new RepeatedOrdersException("Sorry you entered an invalid date. Please try again.");
		}
		this.endDate = makeEndDate; //month is minus one due to how date class works
	}
	
	public String toString()
	{
		return super.toString() + ", Period: every " + getPeriod() + " days, End date for repeated orders: " + getEndDate();
	}
}
