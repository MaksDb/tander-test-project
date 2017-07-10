package main.java;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Maxim on 06.07.2017.
 */
public class DataBase {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int N;
    private String dbName;
    private ArrayList<Integer> field;

    public DataBase(String name, int N){
        connection = null;
        field = new ArrayList<>(N);
        setDbName(name);
        setN(N);
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
            statement = connection.createStatement();
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Соединение установлено!");
    }

    public void deleteDB(){
        try {
            statement.execute("DELETE FROM TEST");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Все данные таблицы TEST удалены");
    }

    public void writeDB(){
        try {

            for (int i=1;i<=N;i++)
                statement.execute("INSERT INTO TEST VALUES (" + i + ");" );
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Таблица TEST заполнена");
    }

    public void readDB(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM TEST");
            while (resultSet.next()){
                Integer fd = resultSet.getInt("FIELD");
                field.add(fd);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Данные таблицы TEST получены");
    }

    public void closeDB(){

        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("База данных закрыта");
    }

    public Connection getConnection() {
        return connection;
    }


    public Statement getStatement() {
        return statement;
    }


    public ResultSet getResultSet() {
        return resultSet;
    }


    public int getN() {
        return N;
    }

    public void setN(int n) {
        N = n;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public ArrayList<Integer> getField() {
        return field;
    }
}
