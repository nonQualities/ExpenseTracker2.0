package org.exptrkr;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.*;
import java.util.Date;

public class ServiceRun {


    /**
     * @author ronit
     */


    Repository repo = Repository.getRepository();

    private int choice;
    Scanner scan = new Scanner(System.in);

    public void showMenu() throws IOException, ParseException, SQLException {
        while (true) {

            printMenu();
            switch (choice) {
                case 1 -> {

                    System.out.println(" adding Expense");
                    addExpense();
                    pressKeyToCont();
                }
                case 2 -> {
                    AddCat();
                    pressKeyToCont();
                }
                case 3 -> {
                    showCatList();
                    pressKeyToCont();

                }
                case 4 -> {
                    showMonthlyRep();
                    pressKeyToCont();
                }
                case 5 -> {
                    showYearlyList();
                    pressKeyToCont();
                }
                case 6 -> {
                    showCatExpList();
                    pressKeyToCont();
                }
                case 7 -> {
                    repo.clearDatabase();
                    System.out.println("All data cleared");
                    pressKeyToCont();
                }

                case 0 -> System.exit(0);
                default -> System.err.print("Choose Again");
            }
        }

    }

    private void showCatExpList() {
        System.out.println("Enter Category Name: ");
        String catName = scan.next();
        List<Categories> expList = repo.getCategories();
        System.out.println("Category Report");
        for (Categories exp : expList) {
            System.out.println("Category ID: " + exp.getCatID() + " Category Name: " + exp.getCatName());
        }
    }

    private void showYearlyList() throws ParseException {
        System.out.println("Enter Year: ");
        String year = scan.next();
        Date year1 = UtilDate.StringToDate(year,"yyyy");
        List<Expense> expList = repo.getYearlyReport(Integer.parseInt(year));
        System.out.println("Yearly Report");
        for (Expense exp : expList) {
            System.out.println("Expense ID: " + exp.getExpID() + " Expense Name: " + exp.getExpName() + " Expense " +
                    "Amount: " + exp.getExpAmount() + " Expense Date: " + exp.getExpDate(year1));}
    }

    private void showMonthlyRep() throws ParseException {
        System.out.println("Enter Month: ");
        String month = scan.next();
        Date month1 = UtilDate.StringToDate(month,"MM");
        System.out.println("Enter Year: ");
        int year = scan.nextInt();
        List<Expense> expList = repo.getMonthlyReport(Integer.parseInt(month), year);
        System.out.println("Monthly Report");
        for (Expense exp : expList) {
            System.out.println("Expense ID: " + exp.getExpID() + " Expense Name: " + exp.getExpName() + " Expense Amount: " + exp.getExpAmount() + " Expense Date: " +
                    exp.getExpDate(month1) + " Expense Remark: " + exp.getExpRemark());
        }
    }

    private void showCatList() {
        List<Categories> catList = repo.getCategories();
        System.out.println("Category List");
        for (Categories cat : catList) {
            System.out.println("Category ID: " + cat.getCatID() + " Category Name: " + cat.getCatName());
        }
    }

    private void AddCat() {
        Categories cat = new Categories();
        System.out.println("Enter Category Name: ");
        cat.setCatName(scan.next());
        cat.setCatID(System.currentTimeMillis());
        repo.addCategory(cat);
        System.out.println("Category Added Successfully");
    }

    private void addExpense() throws ParseException {
        Expense exp = new Expense();
        System.out.println("Enter Expense Name: ");
        exp.setExpName(scan.next());
        System.out.println("Enter Expense Amount: ");
        exp.setExpAmount(scan.nextDouble());
        System.out.println("Enter Category Name: ");
        exp.setCatName(scan.next());
        System.out.println("Enter Expense Date: ");
        exp.setExpDate(UtilDate.StringToDate(scan.next(),"dd/MM/yyyy"));
        System.out.println("Enter Expense Remark: ");
        exp.setExpRemark(scan.next());
        exp.setExpID(System.currentTimeMillis());
        repo.addExpense(exp);
        System.out.println("Expense Added Successfully");
    }




    private int printMenu() {
        System.out.println("--------Expense Manager-------");
        System.out.println("1. Add Expense");
        System.out.println("2. Add Category");
        System.out.println("3. Show Category list");
        System.out.println("4. Monthly Report");
        System.out.println("5. Yearly Report");
        System.out.println("6. Category Report");
        System.out.println("7. Clear all Data");
        System.out.println("0. Exit Application");
        System.out.println("--------------------------------");
        System.out.print("Enter index to activate the option function: ");
        choice = scan.nextInt();
        return choice;

    }


    public void pressKeyToCont() throws IOException {
        System.out.println("press any key to continue:");
        System.in.read();
    }


}

