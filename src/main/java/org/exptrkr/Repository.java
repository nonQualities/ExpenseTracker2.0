package org.exptrkr;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class Repository {
    public List<Expense> expList = new ArrayList<>();
    public List<Categories> catList = new ArrayList<>();
    public static Repository repository;

    private Repository() {}

    public static Repository getRepository() {
        if (repository == null) repository = new Repository();
        return repository;
    }

    public void createTables() {
        String createCategoriesTable = "CREATE TABLE IF NOT EXISTS categories ("
                + "categoryID INTEGER PRIMARY KEY,"
                + "categoryName TEXT NOT NULL"
                + ");";

        String createExpensesTable = "CREATE TABLE IF NOT EXISTS expenses ("
                + "expenseID INTEGER PRIMARY KEY,"
                + "categoryID INTEGER,"
                + "amount REAL,"
                + "name TEXT,"
                + "remark TEXT,"
                + "date TEXT,"
                + "FOREIGN KEY (categoryID) REFERENCES categories (categoryID)"
                + ");";

        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createCategoriesTable);
            stmt.execute(createExpensesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Method to add a category to the database
    public void addCategory(Categories category) {
        String sql = "INSERT INTO categories (categoryID, categoryName) VALUES (?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, category.getCatID());
            pstmt.setString(2, category.getCatName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding category: " + e.getMessage());
        }
    }

    public void addExpense(Expense expense) {
        String sql = "INSERT INTO expenses (expenseID, categoryID, amount, name, remark, date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, expense.getExpID());
            pstmt.setLong(2, expense.getCatID());
            pstmt.setDouble(3, expense.getExpAmount());
            pstmt.setString(4, expense.getExpName());
            pstmt.setString(5, expense.getExpRemark());
            pstmt.setString(6, UtilDate.DateToString(expense.getExpDate(), "dd-MM-yyyy"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch categories from the database
    public List<Categories> getCategories() {

        List<Categories> categories = new ArrayList<>();
        String sql = "SELECT categoryID, categoryName FROM categories";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Categories category = new Categories(rs.getLong("categoryID"), rs.getString("categoryName"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    public List<Expense> getExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Expense expense = new Expense(rs.getDouble("amount"), rs.getString("name"),rs.getString("date"));
                expense.setExpID(rs.getLong("expenseID"));
                expense.setCatID(rs.getLong("categoryID"));
                expense.setCatName(rs.getString("categoryName"));
                expense.setExpRemark(rs.getString("remark"));
                expense.setExpDate(rs.getString(UtilDate.DateToString(expense.getExpDate(), "dd-MM-yyyy")));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }

    // Method to fetch monthly report from the database
    public List<Expense> getMonthlyReport(int month, int year) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.*, c.categoryName FROM expenses e "
                + "JOIN categories c ON e.categoryID = c.categoryID "
                + "WHERE strftime('%m', e.date) = ? "
                + "AND strftime('%Y', e.date) = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
             pstmt.setString(1, String.format("%02d", month));
             pstmt.setString(2,String.valueOf(year));
             ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(rs.getFloat("amount"), rs.getString("name"), rs.getString("date"));
                expense.setCatID(rs.getInt("categoryID"));
                expense.setCatName(rs.getString("categoryName"));
                expense.setExpName(rs.getString("name"));
                expense.setExpAmount(rs.getFloat("amount"));
                expense.setExpRemark(rs.getString("remark"));
                expense.setExpDate(rs.getString("date"));
                expenses.add(expense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return expenses;
    }
    public List<Expense> getYearlyReport(int year) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT e.*, c.categoryName FROM expenses e "
                + "JOIN categories c ON e.categoryID = c.categoryID "
                + "WHERE strftime('%Y', e.date) = ?)";
        try (Connection conn = DBConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, String.valueOf(year));
             ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Expense expense = new Expense(rs.getFloat("amount"), rs.getString("name"),rs.getString("date"));
                expense.setCatID(rs.getInt("categoryID"));
                expense.setCatName(rs.getString("categoryName"));
                expense.setExpName(rs.getString("name"));
                expense.setExpAmount(rs.getFloat("amount"));
                expense.setExpRemark(rs.getString("remark"));
                expense.setExpDate(rs.getString("date"));
                expenses.add(expense);
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
        }
        return expenses;
    }




    public void clearDatabase() {
        String clearExpensesTable = "DELETE FROM expenses";
        String clearCategoriesTable = "DELETE FROM categories";
        try (Connection conn = DBConfig.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(clearExpensesTable);
            stmt.executeUpdate(clearCategoriesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}