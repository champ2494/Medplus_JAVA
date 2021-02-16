package package_load;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;



public class Medplus_class
{
	WebDriver driver;
	

	public void setup_browser()
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://192.168.1.222/medplus/");
		driver.findElement(By.id("Username")).sendKeys("administrator");
		driver.findElement(By.id("Password")).sendKeys("Wbcuser_1");

	}
	

	@Test
	public void Test() throws InterruptedException, IOException
	{
		setup_browser();
		
		File f = new File("D:\\Loadtime.txt"); 
		if(f.exists())
		{
			f.delete();
		}
		long ResponseTime = 0;

		String[] elements = {"//button[@title='Sign in']","patient", "Patient_List","Menu_Patient", "//i[@class='fa fa-shopping-basket']","Menu_Order","Menu_PSCOrder","//i[@class='fa fa-cogs']","Edit_profile", "Users_Mn", "Menu_Physician",
				"Client","Menu_Configuration","Penidng_req","//*[@id='mCSB_1_container']/ul/li[7]/a/i","Menu_ICDCode",
				"Panel_code","Menu_TestCode","Menu_Dictionary","Sales_log","Menu_Import_Patient","Menu_Import_Client",
				"User_logs","IPA_Import","Menu_IPAClient","Menu_IPAPatient","Messages","Menu_Transaction","Contact"};
		
		

		for (int i=0;i<elements.length;i++)
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			if (elements[i].startsWith("//"))
			{
				driver.findElement(By.xpath(elements[i])).click();


				if (!new int[] {1, 4,7,14 }.equals(i))

				{
					ResponseTime = (Long)js.executeScript("return window.performance.timing.domContentLoadedEventEnd-window.performance.timing.navigationStart;");
					Thread.sleep(1000);

					//System.out.println(String.format("Page %1$s loading time is %2$s ms", driver.getTitle(), ResponseTime));
					String RT=String.format("Page %1$s loading time is %2$s ms", driver.getTitle(), ResponseTime);
					filecreate(RT);
				}


			}
			else
			{
				driver.findElement(By.id(elements[i])).click();
				if (!new int[] {1, 4,7,14 }.equals(i))
				{

					ResponseTime = (Long)(js.executeScript("return window.performance.timing.domContentLoadedEventEnd-window.performance.timing.navigationStart;"));
					Thread.sleep(1000);
					//System.out.println(String.format("Page %1$s loading time is %2$s ms", driver.getTitle(), ResponseTime));
					String RT=String.format("Page %1$s loading time is %2$s ms", driver.getTitle(), ResponseTime);
					filecreate(RT);

				}

			}




		}

		driver.quit();
		remove_duplicates();
		//return ResponseTime;
	}

	public void filecreate(String Response_details) throws IOException
	{
		
		//true is given to append the file and false means overwrite
		FileWriter myWriter = new FileWriter("D://Loadtime.txt",true);
		myWriter.write(Response_details+System.lineSeparator());
		myWriter.close();
		System.out.println("Successfully wrote to the file.");
		
		
	}
	
	public void remove_duplicates() throws IOException
	{
		 String filePath = "D://Loadtime.txt";
	      String input = null;
	      //Instantiating the Scanner class
	      Scanner sc = new Scanner(new File(filePath));
	      //Instantiating the FileWriter class
	      FileWriter writer = new FileWriter("D://output.txt");
	      //Instantiating the Set class
	      HashSet<String> set = new HashSet<String>();
	      while (sc.hasNextLine()) {
	         input = sc.nextLine();
	         if(set.add(input)) {
	            writer.append(input+"\n");
	         }
	      }
	      writer.flush();
	      writer.close();
	      sc.close();
	      System.out.println("Contents added............");
	}
		
		

	

}
