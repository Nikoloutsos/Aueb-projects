
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class LoadAndExportUtil {
	private static final String[] TAGS = {"EXPENSE_TYPE_LIST", "EXPENSE_TYPE", "EXPENSE_TYPE_CODE", "EXPENSE_TYPE_DESCR", "TYPE",
			"COST_PER_SQUARE", "MONTHLY_FEE", "COST_PER_UNIT", "SECONDARY_COST_PER_UNIT", "MONTHLY_COST_TELEPHONE", "MONTHLY_COST_ERT","MEASUREMENT_UNIT",
			"BUILDING_LIST", "BUILDING", "BUILDING_CODE", "BUILDING_DESCR", "ADDRESS", "SURFACE", "ZONECOST", "EXPENSES", "EXPENSE", "TYPE", "CONSUMPTION"};
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
		
	
	public static void LoadDataFromTxt(String path1, String path2, Company adidas) {
		List<ExpenseType> expensesType = loadExpensesTypeFromTxt(path1);
		if (!expensesType.isEmpty()) {
			adidas.getExpensesType().addAll(expensesType);
		}
		loadBuildingAndExpenses(adidas, path2);
		System.out.println("Building and expenses loaded from: \n" + path2 + "\n" +
		"Expenses type loaded from: " + "\n" + path1 + "\n");
	}
	
	
	// Helping method for loading/parsing data from a txt file.
	public static void loadBuildingAndExpenses(Company company, String path) {
		BufferedReader br = null;
		FileReader fr = null;
		boolean hasCode , hasDescr, hasAddress, hasSurface, hasCodeForType, hasConsumption;
		String sCurrentLine, code = null, descr = null, address = null, codeForType = null;
		double surface = 0, zoneCost = 0, consumption = 0;
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
		}catch (IOException e) {
			System.out.println("Problem with finding the file!");
		}
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				while(sCurrentLine.trim().isEmpty()) {
					sCurrentLine = br.readLine(); //skip blank lines
				}
				if (sCurrentLine.trim().equalsIgnoreCase("BUILDING_LIST")) {
//					System.out.println(1);
					sCurrentLine = br.readLine();
					while(sCurrentLine.trim().isEmpty()) {
						sCurrentLine = br.readLine(); //skip blank lines
					}
					if (sCurrentLine.trim().equals("{")) {
//						System.out.println(2);
						while(!sCurrentLine.trim().equals("}") && sCurrentLine != null) {
							sCurrentLine = br.readLine();
							while(sCurrentLine != null && sCurrentLine.trim().isEmpty()) {
								sCurrentLine = br.readLine(); //skip blank lines
							}
							if (sCurrentLine != null && sCurrentLine.trim().equalsIgnoreCase("BUILDING")) {
//								System.out.println(3);
								hasCode = hasDescr = hasSurface = hasAddress = false;
								sCurrentLine = br.readLine();
								while(sCurrentLine.trim().isEmpty()) {
									sCurrentLine = br.readLine(); //skip blank lines
								}
								if (sCurrentLine.trim().equals("{")) {
//									System.out.println(4);
									sCurrentLine = br.readLine();
									zoneCost = 10;
									while (!sCurrentLine.trim().equalsIgnoreCase("EXPENSES" ) && !sCurrentLine.trim().equalsIgnoreCase("}" )){
//										System.out.println(sCurrentLine);
										if (sCurrentLine.matches("(?i)\\s*BUILDING_CODE\\s*(\\S+\\s*)*")) {
											hasCode = true;
											code = sCurrentLine.trim().substring("BUILDING_DESC".length()).trim();
										}
										else if (sCurrentLine.matches("(?i)\\s*BUILDING_DESCR\\s*(\\S+\\s*)*")) {
											hasDescr = true;
											descr = sCurrentLine.trim().substring("BUILDING_DESCR".length()).trim();
											
										}
										else if (sCurrentLine.matches("(?i)\\s*ADDRESS\\s*(\\S+\\s*)*")) {
											hasAddress = true;
											address = sCurrentLine.trim().substring("ADDRESS".length()).trim();
										}
										else if (sCurrentLine.matches("(?i)\\s*SURFACE\\s*(\\S+\\s*)*")) {
											hasSurface = true;
											surface = Double.parseDouble(sCurrentLine.trim().substring("SURFACE".length()).trim());

										}
										else if (sCurrentLine.matches("(?i)\\s*ZONECOST\\s*(\\S+\\s*)*")) {
											zoneCost = Double.parseDouble(sCurrentLine.trim().substring("ZONECOST".length()).trim());
										}
										sCurrentLine = br.readLine();
								}
								if (hasCode && hasDescr && hasAddress && hasSurface) {
//									System.out.println("Successful");
									company.addBuilding(code, descr, address, zoneCost, surface);
									Building tempBuilding = company.getBuildings().get(company.findBuildingByCode(code));
									
									sCurrentLine = br.readLine();
									while(sCurrentLine.trim().isEmpty()) {
										sCurrentLine = br.readLine(); //skip blank lines
									}
									if (sCurrentLine.trim().equals("{")) {
										sCurrentLine = br.readLine();
										while(!sCurrentLine.trim().equals("}") && sCurrentLine != null) {
											while(sCurrentLine.trim().isEmpty()) {
												sCurrentLine = br.readLine(); //skip blank lines
											}
											if (sCurrentLine.trim().equalsIgnoreCase("EXPENSE")) {
//												System.out.println("I am inside expense");
												sCurrentLine = br.readLine();
												while(sCurrentLine.trim().isEmpty()) {
													sCurrentLine = br.readLine(); //skip blank lines
												}
												if (sCurrentLine.trim().equals("{")) {
//													System.out.println("5");
													hasCodeForType = hasConsumption = false;
													consumption = 0;
													sCurrentLine = br.readLine();
													while (!sCurrentLine.trim().equals("}") && sCurrentLine != null ) {
//														System.out.println("6" + sCurrentLine);
														if (sCurrentLine.matches("(?i)\\s*EXPENSE_TYPE_CODE\\s*(\\S+\\s*)*")) {
															hasCodeForType = true;
															codeForType = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
															
														}else if (sCurrentLine.matches("(?i)\\s*CONSUMPTION\\s*(\\S+\\s*)*")) {
															hasConsumption = true;
															consumption = Double.parseDouble(sCurrentLine.trim().substring("CONSUMPTION".length()).trim());
														}
														
													sCurrentLine = br.readLine();
													
													}
													if (hasCodeForType && hasConsumption) {
														if (company.findExpenseTypeByCode(codeForType) != -1) {
//															System.out.println("created");
															if(!company.expenseTypeAlreadyExistsAtBuilding(tempBuilding, company.getExpensesType().get(company.findExpenseTypeByCode(codeForType)))) {
																company.addBuildingExpense(tempBuilding, company.getExpensesType().get(company.findExpenseTypeByCode(codeForType)), consumption);
																
															}
																
														}
													
														sCurrentLine = br.readLine();
														while(sCurrentLine.trim().isEmpty()) {
															sCurrentLine = br.readLine(); //skip blank lines
														}
//														System.out.println("last line " + sCurrentLine);
													}
													
												}
											}
											
										}
									}
									
		
								} 
								
								
							}
							}
								if(sCurrentLine == null) break;
								sCurrentLine = "";
						}
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("Problem with parsing the file!");
		}catch (Exception e) {
			
		}
		try {
			br.close();
		} catch (IOException e) {
			
		}catch (Exception e) {
			
		}
		
		
	}
	
	
	
	
	
	
	
	public static List<ExpenseType> loadExpensesTypeFromTxt(String path){
		
		ArrayList<ExpenseType> expensestype = new ArrayList<>();
		String sCurrentLine, type = null;
		boolean hasCode = false, hasType = false, hasDescr = false;
		BufferedReader br = null;
		FileReader fr = null;
		String code = null , descr = null;
		double monthlyFee = 0, costPerUnit = 0 , secondaryCostPerUnit = 0, monthlyCostERT = 0, costPerSquare = 0, monthlyCostTelephone=0;
		
		try {
			fr = new FileReader(path);
			br = new BufferedReader(fr);
		}catch (IOException e) {
			System.out.println("Problem with finding the file!");
			
		}
		
		try {
			while ((sCurrentLine = br.readLine()) != null) {
				while(sCurrentLine.trim().isEmpty()) {
					sCurrentLine = br.readLine(); //skip blank lines
				}
				if(sCurrentLine.trim().equalsIgnoreCase("EXPENSE_TYPE_LIST")) { 
					sCurrentLine = br.readLine();
					
					while(sCurrentLine.trim().isEmpty()) {
						sCurrentLine = br.readLine(); //skip blank lines
					}
					if (sCurrentLine.trim().equals("{")) {
						while (!sCurrentLine.trim().equals("}") && sCurrentLine != null) {
//							System.out.println("gogo" + sCurrentLine);
							sCurrentLine = br.readLine();
							while(sCurrentLine.trim().isEmpty()) {
								sCurrentLine = br.readLine(); //skip blank lines
							}
							if (sCurrentLine.trim().equalsIgnoreCase("EXPENSE_TYPE")) {
								hasCode = hasDescr = hasType = false;
								sCurrentLine = br.readLine();
								while(sCurrentLine.trim().isEmpty()) {
									sCurrentLine = br.readLine(); //skip blank lines
								}
								if (sCurrentLine.equals("{")) {
//									System.out.println("i am inside mark");
									br.mark(9000);
									
									while(!sCurrentLine.equals("}") && sCurrentLine != null ) {
										sCurrentLine = br.readLine();
										if (sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")) {
											hasCode = true;
										}
										else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
											hasDescr = true;
										}
										else if (sCurrentLine.matches("(?i)\\s*type\\s*\\S+\\s*")) {
											hasType = true;
											type = sCurrentLine.trim().substring(5).trim();
										}
									}
								}
//								System.out.println("Type : " + type);
								if (hasCode && hasDescr && hasType) {
									
									br.reset();
									sCurrentLine = br.readLine();
									if (type.trim().equalsIgnoreCase("water")) {
										monthlyFee = 20;
										costPerUnit = 0.15;
										secondaryCostPerUnit = 0.10;
										
										while(!sCurrentLine.trim().equals("}")) {
											if(sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")){
												code = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
												 descr = sCurrentLine.trim().substring("EXPENSE_TYPE_DESCR".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*MONTHLY_FEE\\s*(\\S+\\s*)*")) {
												monthlyFee = Double.parseDouble(sCurrentLine.trim().substring("monthly_fee".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*cost_per_unit\\s*(\\S+\\s*)*")) {
												costPerUnit = Double.parseDouble(sCurrentLine.trim().substring("cost_per_unit".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*SECONDARY_COST_PER_UNIT\\s*(\\S+\\s*)*")) {
												secondaryCostPerUnit = Double.parseDouble(sCurrentLine.trim().substring("SECONDARY_COST_PER_UNIT".length()));
											}
											sCurrentLine = br.readLine();
											
										}
										expensestype.add(new Water(code, descr, monthlyFee, costPerUnit, secondaryCostPerUnit  ));
									}
									else if (type.trim().equalsIgnoreCase("energy")) {
//										System.out.println(2);
										monthlyFee = 20;
										costPerUnit = 0.2;
										monthlyCostERT = 25;
										while(!sCurrentLine.trim().equals("}")) {
											if(sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")){
												code = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
												 descr = sCurrentLine.trim().substring("EXPENSE_TYPE_DESCR".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*MONTHLY_FEE\\s*(\\S+\\s*)*")) {
												monthlyFee = Double.parseDouble(sCurrentLine.trim().substring("monthly_fee".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*COST_PER_UNIT\\s*(\\S+\\s*)*")) {
												costPerUnit = Double.parseDouble(sCurrentLine.trim().substring("COST_PER_UNIT".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*MONTHLY_COST_ERT\\s*(\\S+\\s*)*")) {
												monthlyCostERT = Double.parseDouble(sCurrentLine.trim().substring("MONTHLY_COST_ERT".length()).trim());
											}
											
											sCurrentLine = br.readLine();
										}
										expensestype.add(new Energy(code, descr, monthlyFee, costPerUnit, monthlyCostERT));
									}
									else if (type.trim().equalsIgnoreCase("telephone")) {
//										System.out.println(3);
										monthlyFee = 20;
										costPerUnit = 0.3;
										monthlyCostTelephone = 30;
										while(!sCurrentLine.trim().equals("}")) {
											if(sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")){
												code = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
												 descr = sCurrentLine.trim().substring("EXPENSE_TYPE_DESCR".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*MONTHLY_FEE\\s*(\\S+\\s*)*")) {
												monthlyFee = Double.parseDouble(sCurrentLine.trim().substring("monthly_fee".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*COST_PER_UNIT\\s*(\\S+\\s*)*")) {
												costPerUnit = Double.parseDouble(sCurrentLine.trim().substring("COST_PER_UNIT".length()).trim());
											}
											else if (sCurrentLine.matches("(?i)\\s*MONTHLY_COST_TELEPHONE\\s*(\\S+\\s*)*")) {
												monthlyCostTelephone = Double.parseDouble(sCurrentLine.trim().substring("MONTHLY_COST_TELEPHONE".length()));
											}
											
											sCurrentLine = br.readLine();
										}
										expensestype.add(new Telephone(code, descr, monthlyFee, costPerUnit, monthlyCostTelephone));
									}
									else if (type.trim().equalsIgnoreCase("rent")) {
//										System.out.println(4);
										costPerSquare = 10;
										while(!sCurrentLine.trim().equals("}")) {
											if(sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")){
												code = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
												 descr = sCurrentLine.trim().substring("EXPENSE_TYPE_DESCR".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*COST_PER_SQUARE\\s*(\\S+\\s*)*")) {
												costPerSquare = Double.parseDouble(sCurrentLine.trim().substring("COST_PER_SQUARE".length()).trim());
											}
											sCurrentLine = br.readLine();
										}
											expensestype.add(new Rent(code, descr, costPerSquare));
									}
									else if (type.trim().equalsIgnoreCase("cleaning")) {
//										System.out.println(5);
										costPerSquare = 10;
										while(!sCurrentLine.trim().equals("}")) {
											if(sCurrentLine.matches("(?i)\\s*expense_type_code\\s*(\\S+\\s*)*")){
												code = sCurrentLine.trim().substring("EXPENSE_TYPE_CODE".length()).trim();
											}
											else if (sCurrentLine.matches("(?i)\\s*expense_type_descr\\s*(\\S+\\s*)*")) {
												 descr = sCurrentLine.trim().substring("EXPENSE_TYPE_DESCR".length()).trim();
												 
											}
											else if (sCurrentLine.matches("(?i)\\s*COST_PER_SQUARE\\s*(\\S+\\s*)*")) {
												costPerSquare = Double.parseDouble(sCurrentLine.trim().substring("COST_PER_SQUARE".length()).trim());
											}
											
											sCurrentLine = br.readLine();
										}
										expensestype.add(new Cleaning(code, descr, costPerSquare));
										
									}
									
									while (!sCurrentLine.trim().equals("}")) {
										sCurrentLine = br.readLine();
										System.out.println(sCurrentLine);
									}
									
								}
								sCurrentLine = "";
								
							}
							
						}
					}
				}
				
			}
		} catch (IOException e) {
			System.out.println("Problem with parsing the file!");
		
		}catch (NullPointerException e) {
			
		}
		try {
			br.close();
		} catch (IOException e) {
			
		} catch (Exception e) {
			
		}

		return expensestype;
		
	}
	
	
	
	
	
	//Exporting to a data to a text file.
	
	private static void ExportBuildingsAndExpenses(List<Building> buildings, List<Expense> expenses,String path) throws FileNotFoundException {
		File file = new File (path);
		PrintWriter printWriter;
		printWriter = new PrintWriter (file);
		printWriter.println (TAGS[12]);
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
					printWriter.println ("    " +"    " + "    " + "    " + TAGS[2] + "   " + currentExpense.getType().getCode());
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
			printWriter.println(TAGS[9] + "   " + ((Telephone)expenseType).getMonthlyCostTelephone());
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