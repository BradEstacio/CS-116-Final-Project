/**
 * Bradley Estacio and Patrick Inosanto
 * 12/6/19
 * Test cases for OrderManager
 * @see Office Supply Order System UML
 */
public class OrderManagerTest {

	public static void main(String[] args) 
	{
		try
		{
			//Test 1
			System.out.println("Test 1");
			OrderManager test1 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
			test1.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
			test1.addOrder("R,Peak Enterprises,A2,11/18/2006,10,7,12/21/2006");
			System.out.println(test1.toString());
			System.out.println();
			//Test 2
			System.out.println("Test 2");
			OrderManager test2 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
			test2.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
			test2.addOrder("R,Peak Enterprises,A2,11/18/2006,10,7,12/21/2006");
			test2.deleteOrder(1);
			System.out.println(test2.toString());
			//Test 3
			System.out.println("Test 3");
			OrderManager test3 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
			test3.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
			test3.addOrder("R,Peak Enterprises,A2,11/18/2006,10,7,12/21/2006");
			test3.deleteOrder(1);
			test3.deleteOrder(2);
			test3.deleteOrder(3);
			System.out.println(test3.toString());
			//Test 4
			System.out.println("Test 4");
			OrderManager test4 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
			test4.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
			test4.addOrder("R,Orange Inc.,A2,10/6/2006,6,30,11/7/2006");
			test4.listOrder("Orange Inc.");
			System.out.println();
			//Test 5
			System.out.println("Test 5");
			OrderManager test5 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
			test5.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
			test5.addOrder("R,Peak Enterprises,A2,11/18/2006,10,7,12/21/2006");
			test5.deleteOrder(5);
		}
		catch(OrderManagerException e)
		{
			System.out.println(e.getMessage());
		}
		finally
		{
			try
			{
				//Test 6
				System.out.println("Test 6");
				OrderManager test6 = new OrderManager("O,Orange Inc.,A1,6/25/2004,5");
				test6.addOrder("O,Macrohard Inc.,C2,8/2/2004,1");
				test6.addOrder("R,Peak Enterprises,A2,11/18/2006,10,7,12/21/2006");
				test6.listOrder("HAL Industries");
				System.out.println();
			}
			catch(OrderManagerException e)
			{
				System.out.println(e.getMessage());
			}
		}
	}
}
