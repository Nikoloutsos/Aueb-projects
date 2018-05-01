import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class LoadAndExportUtil {
	private static final String[] TAGS = {"EXPENSE_TYPE_LIST", "EXPENSE_TYPE", "EXPENSE_TYPE_CODE", "EXPENSE_TYPE_DESCR", "TYPE",
			"COST_PER_SQUARE", "MONTHLY_FEE", "COST_PER_UNIT", "SECONDARY_COST_PER_UNIT", "MONTHLY COST TELEPHONE", "MONTHLY_COST_ERT","MEASUREMENT_UNIT",
			"BUILDING_LIST", "BUILDING", "BUILDING_CODE", "BUILDING_DESC", "ADDRESS", "SURFACE", "ZONECOST", "EXPENSES", "EXPENSE", "TYPE", "CONSUMPTION"};
	private LoadAndExportUtil() {
	}
	
	public static void ExportDataInTxt(List<Building> buildings, List<Expense> expenses, List<ExpenseType> expensesType) {
		String path1 = System.getProperty("user.home") + "/Desktop" + "/ExpenseTypeList.txt";
		String path2 = System.getProperty("user.home") + "/Desktop" + "/BuildingsAndExpenses.txt";
		try {
			ExportExpenseType(expensesType, path1);
			ExportBuildingsAndExpenses(buildings, expenses, path2);
		} catch (FileNotFoundException e) {
			System.out.println("!!!Critical : Error while saving Data!!!");
		}
	}
		
	
	public static void LoadDataFromTxt(String path) {
		
	}
	
	
	
	
	
	
	
	
	private static void ExportBuildingsAndExpenses(List<Building> buildings, List<Expense> expenses,String path) throws FileNotFoundException {
		File file = new File (path);
		PrintWriter printWriter;
		printWriter = new PrintWriter (file);
		printWriter.println (TAGS[0]);
		printWriter.println ("{");
		for(Building currentBuilding : buildings) {
			printWriter.println ("    " + TAGS[13]);
			printWriter.println ("    " +  "{");
			printWriter.println ("    " + "    " + TAGS[14] + "   " + currentBuilding.getCode());
			printWriter.println ("    " + "    " + TAGS[15] + "   " + currentBuilding.getDescription());
			printWriter.println ("    " + "    " + TAGS[16] + "   " + currentBuilding.getAddress());
			printWriter.println ("    " + "    " + TAGS[17] + "   " + currentBuilding.getSquareMeters());
			printWriter.println ("    " + "    " + TAGS[18] + "   " + currentBuilding.getZoneCost());
			printWriter.println ("    " + "    " + TAGS[19]);
			printWriter.println ("    " + "    " + "{");
			for(Expense currentExpense : expenses) {
				if (currentExpense.getBuilding() == currentBuilding) {
					printWriter.println ("    " +"    " + "    " +  TAGS[20]);
					printWriter.println ("    " +"    " + "    " + "{");
					printWriter.println ("    " +"    " + "    " + "    " + TAGS[4] + "   " + currentExpense.getType().getClass().getSimpleName());
					printWriter.println ("    " +"    " + "    " + "    " + TAGS[22] + "   " + currentExpense.getMonthlyConsumption());
					printWriter.println ("    " +"    " + "    " + "}");
				}
			}
			
			
			
			
			
			printWriter.println ("    " + "    " + "}");
			printWriter.println ("    " +"}");
		}
		printWriter.println ("}");
		printWriter.close ();  
	}
	
	private static void ExportExpenseType(List<ExpenseType> expensesType, String path) throws FileNotFoundException {
		File file = new File (path);
		PrintWriter printWriter;
		printWriter = new PrintWriter (file);
		printWriter.println (TAGS[0]);
		printWriter.println ("{");
		for (ExpenseType currentExpenseType : expensesType) {
			printWriter.println (TAGS[1]);
			printWriter.println ("{");
			printWriter.println(TAGS[2] + "   " + currentExpenseType.getCode().toString());
			printWriter.println(TAGS[3] + "   " + currentExpenseType.getDescription().toString());
			printWriter.println(TAGS[4] + "   " + currentExpenseType.getClass().getSimpleName());
			returnExtraTagInfo(printWriter, currentExpenseType);
			printWriter.println ("}");
		}
		printWriter.println ("}");
		printWriter.close ();  
		
	}
	private static void returnExtraTagInfo(PrintWriter printWriter,ExpenseType expenseType) {
		
		if (expenseType instanceof Water) {
			printWriter.println(TAGS[6] + "   " + ((Water)expenseType).getMonthlyFee() );
			printWriter.println(TAGS[7] + "   " + ((Water)expenseType).getCostPerUnit());
			printWriter.println(TAGS[8] + "   " +  ((Water)expenseType).getSecondaryCostPerUnit());		
			printWriter.println(TAGS[11] + "   " +  ((Water)expenseType).getUnit());	
		}else if (expenseType instanceof Telephone) {
			printWriter.println(TAGS[6] + "   " + ((Telephone)expenseType).getMonthlyFee());
			printWriter.println(TAGS[7] + "   " + ((Telephone)expenseType).getCostPerUnit());
			printWriter.println(TAGS[8] + "   " + ((Telephone)expenseType).getMonthlyCostTelephone());
			printWriter.println(TAGS[11] + "   " +  ((Telephone)expenseType).getUnit());	
		}else if (expenseType instanceof Energy) {
			printWriter.println(TAGS[6] + "   " + ((Energy)expenseType).getMonthlyFee());
			printWriter.println(TAGS[7] + "   " + ((Energy)expenseType).getCostPerUnit());
			printWriter.println(TAGS[10] + "   " + ((Energy)expenseType).getMonthlyCostERT());	
			printWriter.println(TAGS[11] + "   " +  ((Energy)expenseType).getUnit());	
		}else if (expenseType instanceof Rent) {
			printWriter.println(TAGS[5] + "   " + ((Rent)expenseType).getCostPerSquare());
		}else if (expenseType instanceof Cleaning) {
			printWriter.println(TAGS[5] + "   " + ((Cleaning)expenseType).getCostPerSquare());
		}
		
	}
}
