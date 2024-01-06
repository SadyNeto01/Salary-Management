package salarymanagement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class SalaryManagement {

    public static void main(String[] args) throws FileNotFoundException {
        
        final int SIZE = 50;
        String[] names = new String[SIZE];
        int[] salaries = new int[SIZE];
        int numEmployees = 0, choice;
        
        do {
            
            choice = menu();
            
            switch (choice){
                
                case 0: 
                    JOptionPane.showMessageDialog(null, "Exit");
                    break;
                case 1: 
                    if (numEmployees < names.length) {
                        
                        insertEmployee(names, salaries, numEmployees);
                        
                        numEmployees++;
                    } else {
                        JOptionPane.showMessageDialog(null, "Array is full");
                    }
                    break;
                case 2:
                    if (numEmployees > 0){
                        
                        listEmployees(names, salaries, numEmployees);
                        
                    } else {
                        
                        JOptionPane.showMessageDialog(null, "No employees available");
                    }
                    break;
                case 3:
                    if (numEmployees > 0){
                        
                        updateSalary(names, salaries, numEmployees);
                        
                    } else {
                        
                        JOptionPane.showMessageDialog(null, "No employees available");
                        
                    }
                    break;
                case 4:
                    
                    if (numEmployees > 0){
                        if (deleteEmployee(names, salaries, numEmployees)){
                        
                            JOptionPane.showMessageDialog(null, "Employee deleted");
                            numEmployees--;
                        } else {
                        
                            JOptionPane.showMessageDialog(null, "No employees with this name");
                        
                        }
                    
                    } else {
                        
                        JOptionPane.showMessageDialog(null, "No employees available");
                    }
                    break;
                case 5:
                    if (numEmployees > 0){
                        
                        sortByName(names, salaries, numEmployees);
                        listEmployees(names, salaries, numEmployees);
                        
                    } else {
                        JOptionPane.showMessageDialog(null, "No employees available");
                        
                    }
                    break;
                case 6:
                    if (numEmployees < names.length) {
                        numEmployees = loadData(names, salaries, numEmployees);
                        
                    } else {
                         JOptionPane.showMessageDialog(null, "Array is full");
                    }
                    break;
                case 7:
                    
                   saveData(names, salaries, numEmployees);
                   
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option");
                    
            }
            
        } while (choice != 0);
        
    }

    private static int menu() {
        
        int choice;
        
        choice = Integer.parseInt(JOptionPane.showInputDialog("0 - Exit \n1 - Insert \n2 - List \n3 - Update Salary \n4 - Delete \n5 - Sort by Name \n6 - Load Data \n7 - Save Data"));
        
        return choice;
        
    }

    private static void insertEmployee(String[] names, int[] salaries, int numEmployees) {
        
       names[numEmployees] = JOptionPane.showInputDialog("Name?");
       
       salaries[numEmployees] = Integer.parseInt(JOptionPane.showInputDialog("Salary?"));
        
    }

    private static void listEmployees(String[] names, int[] salaries, int numEmployees) {
       
        int i;
        String message = "Employee Listing\n";
        
        for (i = 0; i < numEmployees; i++){
            
            message += "\n" + names[i] + " - " + salaries[i];
        }
        JOptionPane.showMessageDialog(null, message);
    }

    private static void updateSalary(String[] names, int[] salaries, int numEmployees) {
        
        String name = "";
        int position;
        
        name = JOptionPane.showInputDialog("Name to update the salary?");
        
        position = search(names, numEmployees, name);
        
        if (position != -1){
            
            salaries[position] = Integer.parseInt(JOptionPane.showInputDialog("New salary?"));
            
        } else {
            
            JOptionPane.showMessageDialog(null, "No employees with this name");
            
        }
    }

    private static int search(String[] names, int numEmployees, String name) {
        
        int position = 0;
        
        while (position < numEmployees && !name.equalsIgnoreCase(names[position])){
            
            position++;
            
        }
        
        if (position < numEmployees){
            
            return position;
            
        } else {
            
            return -1;
        }
        
    }

    private static boolean deleteEmployee(String[] names, int[] salaries, int numEmployees) {
        
        String name = "";
        int position, i;
        
        name = JOptionPane.showInputDialog("Name to delete?");
        
        position = search(names, numEmployees, name);
        
        if (position != -1){
            
            for (i = position; i < numEmployees - 1; i++){
            
                names[i] = names[i + 1];
                salaries[i] = salaries[i + 1];
            
            }
            return true;
        } else {
            
            return false;
        }
        
    }

    private static void sortByName(String[] names, int[] salaries, int numEmployees) {
        
        int i, j, tempSalary;
        String tempName = "";
        
        for (i = 0; i < numEmployees - 1; i++){
            for (j = i + 1; j < numEmployees; j++){
                
                if (names[j].compareToIgnoreCase(names[i]) < 0) {
                    
                    tempName = names[i];
                    names[i] = names[j];
                    names[j] = tempName;
                    
                    tempSalary = salaries[i];
                    salaries[i] = salaries[j];
                    salaries[j] = tempSalary;
                    
                }
                
            }
            
        }
        
    }

    private static int loadData(String[] names, int[] salaries, int numEmployees) throws FileNotFoundException {
        
        Scanner employeeFile = new Scanner(new File("employees.txt"));
        int copy = numEmployees;
        
        while (employeeFile.hasNextLine() && numEmployees < names.length){
            
            String line = employeeFile.nextLine();
            String[] lineArray = line.split(":");
            
            if (search(names, numEmployees, lineArray[0]) == -1){
                
                names[numEmployees] = lineArray[0].trim();
                salaries[numEmployees] = Integer.parseInt(lineArray[1].trim()); 
                numEmployees++;
            }
        }
        
        employeeFile.close();
        JOptionPane.showMessageDialog(null, (numEmployees - copy) + " employees were loaded");
        return numEmployees;
    }

    private static void saveData(String[] names, int[] salaries, int numEmployees) throws FileNotFoundException {
       
        Formatter employeeFile = new Formatter(new File("employees.txt"));
        
        for (int i = 0; i < numEmployees; i++){
            
            if (i > 0){
                
                employeeFile.format("\n");
                
            }
            employeeFile.format(names[i] + ":" + salaries[i]);
        }
        
        employeeFile.close();
        
    }
    
}
