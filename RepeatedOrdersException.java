/**
 * @author Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * 
 * Exception class for RepeatedOrders.
 * Used to throw exceptions.
 */
public class RepeatedOrdersException extends Exception 
{
	RepeatedOrdersException()
	{
		super();
	}
	
	RepeatedOrdersException(String s)
	{
		super(s);
	}
}
