package excelDataDriven;

import java.util.ArrayList;

public class DataDrivenTest {

	public static void main(String[] args) {
		DataDriven dd = new DataDriven();
		ArrayList<String> data = dd.getData("Add Profile");
		
		for(int x = 0; x < data.size(); x++) {
			System.out.println(data.get(x));
		}
		
	}
	
}
