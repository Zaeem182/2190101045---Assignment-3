package za.ac.cput.assignment3;

/**
 *
 * @author Zaeem Petersen - 2190101045
 */

import java.io.*;
import java.text.*;
import java.time.*;
import java.util.*;


public class ReadStakeholderSer {

    ObjectInputStream input;
    ArrayList<Customer> customerArray = new ArrayList<>();
    ArrayList<Supplier> supplierArray = new ArrayList<>();
    int age[];
    LocalDate todayDate;
    LocalDate DOB;
    Period CalcAge;

    int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    // Open file
    public void openFile() {
        try {
            input = new ObjectInputStream(new FileInputStream("stakeholder.ser"));
            System.out.println("Opened Serialized File");
        }
        catch (IOException ioe) {
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    //add to Arraylist(Customer Arraylist)
    public void CustomerAddToArray() {
        Stakeholder s;

        try {

            while (true) {
                s = (Stakeholder) input.readObject();

                //adding Customer Object to customerArray
                if (s instanceof Customer) {

                    customerArray.add((Customer) s);
                }
            }
        } 
        catch (EOFException ex) {

        } 
        catch (ClassNotFoundException cnfe) {
            System.out.println("error reading from ser file" + cnfe);
        } 
        catch (IOException ioe) {
            System.out.println("error reading from ser file" + ioe);
        }
    }
    
    //Display print Customer in Output
    public void PrintCustomer() {

        for (int i = 0; i < customerArray.size(); i++) {
            System.out.println(customerArray.get(i));
        }
    }
    
    //Sort customer by Stakeholder ID
    public void SortCustomer() {
        customerArray.sort(Comparator.comparing(Stakeholder::getStHolderId));
    }
   
     
    
    //Create customerOutFile.txt
    public void WriteCustomerText() throws ParseException
    {
        try
        {
            FileWriter fw = new FileWriter("customerOutFile.txt");
            
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("===============================================Customer===============================================");
            bw.newLine();
            bw.write("ID\t\tName\t\tSurname\t\t\tDate of birth\t\tAge");
            bw.newLine();
            bw.write("======================================================================================================"); 
            bw.newLine();
            
            for (int i = 0; i < customerArray.size(); i++) {

                
                if (customerArray.get(i).getSurName().length() < 8) {

                    bw.write(customerArray.get(i).getStHolderId() + "\t\t" + customerArray.get(i).getFirstName() + "\t\t" + customerArray.get(i).getSurName() 
                            + "\t\t\t" + customerArray.get(i).getDateOfBirth() + "\t\t" + age[i] + "\t\t");
                    bw.newLine();
                } 
                else {
                    bw.write(customerArray.get(i).getStHolderId() + "\t\t" + customerArray.get(i).getFirstName() + "\t\t" + customerArray.get(i).getSurName() 
                            + "\t\t" + customerArray.get(i).getDateOfBirth() + "\t\t" + age[i] + "\t\t");
                    bw.newLine();
                }

               

            }
            
                bw.write("Number of customers who can rent: "+CustomerRentTrue() );
                bw.newLine();
                bw.write("Number of customers who cannot rent: "+CustomerRentFalse() );
            System.out.println("customerOutFile.txt has been successfully created.");
            bw.close();
        }
        
        catch(IOException e)
        {
            System.out.println("Could not create customer File" + e.getMessage());
        }
        
        System.out.println("File was created successfully"); 
    }
    
   //Determine the Age
   public void age()
            {

                age = new int[customerArray.size()];
                for(int i=0;i<customerArray.size();i++)

                    {
                        todayDate = LocalDate.now();
                        DOB = LocalDate.parse(customerArray.get(i).getDateOfBirth());
                        CalcAge = Period.between(DOB, todayDate);
                        age[i] = CalcAge.getYears();
                    }
            }
            
    //Format the Date
     public void dateFormat() throws ParseException
            {
                DateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
                
                for(int i = 0; i< customerArray.size(); i++)
                    {
                    String arrayDate = customerArray.get(i).getDateOfBirth();
                    Date list = f1.parse(arrayDate);
                    DateFormat f2 = new SimpleDateFormat("dd MMM yyy");
                    customerArray.get(i).setDateOfBirth(f2.format(list));
                    }
            } 
    
    //Customers that can rent
      public int CustomerRentTrue() {
        int canRent = 0;

        for (int i = 0; i < customerArray.size(); i++) {
            customerArray.get(i);

            if (customerArray.get(i).getCanRent() == true) {
                canRent++;
            }
        }
        return canRent;
    }

      //Customers that cannot rent
      public int CustomerRentFalse() {
        int cannotRent = 0;

        for (int i = 0; i < customerArray.size(); i++) {
            customerArray.get(i);

            if (customerArray.get(i).getCanRent() == false) {
                cannotRent++;
            }
        }
        return cannotRent;
    }
      
      
      //Add to Arraylist(Supplier Arraylist)
     public void SupplierAddToArray() {
         Stakeholder s;
        try {
            int i = 0;

            while (true) {
                s = (Stakeholder) input.readObject();

                //adding Customer Object to customerArray
                if (s instanceof Supplier) {

                    supplierArray.add(i, (Supplier) s);
                }
            }
        } 
        catch (EOFException ex) {
            System.out.println("EOF reached");
        } 
        catch (ClassNotFoundException cnfe) {
            System.out.println("error reading from ser file" + cnfe);
        } 
        catch (IOException ioe) {
            System.out.println("error reading from ser file" + ioe);
        }
    }
     
     //Print Supplier Output
     public void PrintSupplier()
    {
      
        for (int i = 0; i < supplierArray.size();i++) 
          {               
              System.out.println(supplierArray.get(i));         
          }   
    }
    public void SortSupplier(){
    
         supplierArray.sort(Comparator.comparing(Supplier::getName));    
     }

    //close file
    public void closeFile(){
        try{
        input.close( ); 
            System.out.println("File closed");
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    
    //Create supplierOutFile.txt
    public void WriteSupplierText()
    {
        try
        {
            FileWriter fw = new FileWriter("supplierOutFile.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("====================================Supplier====================================");
            bw.newLine();
            bw.write("ID\tName\t\t\tProd Type\tDescription");
            bw.newLine();
            bw.write("================================================================================"); 
            bw.newLine();
            for(int i=0;i<supplierArray.size();i++){
                
                bw.write(supplierArray.get(i).toString());
                bw.newLine();
            }
            System.out.println("supplierOutFile.txt has been successfully created.");
            bw.close();
        }
        catch(IOException e)
        {
            System.out.println("Could not create supplier File" + e.getMessage());
        }
        
        System.out.println("File was created successfully"); 
    }
    
    //main method
    public static void main(String[] args) throws ParseException {
        ReadStakeholderSer obj= new ReadStakeholderSer();
        obj.openFile();
        obj.CustomerAddToArray();
        obj.SortCustomer();
        obj.PrintCustomer();
        obj.age();
        obj.dateFormat();
        obj.closeFile();

        obj.openFile();
        obj.SupplierAddToArray();
        obj.SortSupplier();
        obj.PrintSupplier();
        obj.closeFile();
        
        obj.WriteCustomerText();
        obj.WriteSupplierText();
    }
}
    
    
    