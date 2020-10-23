package Temp;



import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class BrokenLinks {
	WebDriver dr;
	String url="http://www.zlti.com";
	String newUrl="";
	HttpURLConnection huc = null;
	int respCode=200;

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver","D:\\Selenium\\chromedriver.exe");
		dr=new ChromeDriver();
		dr.get(url);
		dr.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		dr.quit();
	}

	@Test
	public void test() {
       List<WebElement>links=dr.findElements(By.tagName("a"));
		
		Iterator<WebElement>it=links.iterator();
		
		while(it.hasNext()){
			
			newUrl=it.next().getAttribute("href");
			System.out.println(newUrl);
			
			if (newUrl==null||newUrl.isEmpty()){
				System.out.println("Its Empty");
				continue;
			}
			if (!newUrl.startsWith(url)){
				System.out.println("url does not match");
			}
			try{
				huc=(HttpURLConnection)(new URL(newUrl).openConnection());
				huc.setRequestMethod("HEAD");
				huc.connect();
				respCode=huc.getResponseCode();

				if(respCode>=400){
					System.out.println(newUrl+"is broken link");
				}
				else{
					System.out.println(newUrl+"is valid link");
				}
			}
			catch(MalformedURLException e){
				e.printStackTrace();
				
			}
			catch(IOException e){
				e.printStackTrace();
	}
  }

}
}
		
