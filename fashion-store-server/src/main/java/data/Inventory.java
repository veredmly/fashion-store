package data;

import exceptions.ItemNotFoundException;
import exceptions.NotEnoughItemsToSellException;
import observer.ISellObserver;


import java.io.*;
import java.util.HashMap;

public class Inventory implements ISellObserver {
    protected String filePath;
    protected String inventoryInFile;

    public Inventory(String branchName) {
        this.filePath = "src/"+ branchName + ".txt";
    }

    //find item in the file of inventory to return the price
    public double findPriceOfItemFromFile(String item) throws FileNotFoundException,ItemNotFoundException, IOException {
        String line;

        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            String[] lineAfterSplit = splitLine(line);
            if (lineAfterSplit[0].equals(item)) {
                return Integer.parseInt(lineAfterSplit[1]);
            }
        }
        reader.close();
        throw new ItemNotFoundException();
    }

    //find the item in inventory and return the count of item
    public int findAmountItemsFromFile(String item) throws FileNotFoundException,ItemNotFoundException, IOException {
        String line;

        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            String[] lineAfterSplit = splitLine(line);
            if (lineAfterSplit[0].equals(item)) {
                return Integer.parseInt(lineAfterSplit[3]);
            }
        }
        reader.close();
        throw new ItemNotFoundException();
    }

    //split the line of item from the inventory file and return array list
    public String[] splitLine(String line) {
        String[] lineAfterSplit = line.split(":", 6);
        return lineAfterSplit;
    }

    //create sell - get items and count, down them from inventory file and return the total price
    @Override
    public int sell(String typeItem, int countItems) throws IOException, NotEnoughItemsToSellException, FileNotFoundException,ItemNotFoundException {
        String line;
        String lineAfterChange;
        String totalDataToFile = "";
        boolean ifExistItem = false;
        int priceOfSell = 0;
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while ((line = reader.readLine()) != null) {
            String[] arrayLineToSplit = splitLine(line);
            if (arrayLineToSplit[0].equals(typeItem)) {
                int currentAmountItem = Integer.parseInt(arrayLineToSplit[3]);
                if (( currentAmountItem-countItems)>=0) {
                    Integer countItemAfterSell = currentAmountItem - countItems;
                    priceOfSell = countItems* Integer.parseInt(arrayLineToSplit[1]);
                    arrayLineToSplit[3] = countItemAfterSell.toString();
                    lineAfterChange = arrayLineToSplit[0] + ":" +
                            arrayLineToSplit[1] + ":" + arrayLineToSplit[2] +
                            ":" +arrayLineToSplit[3] +":"+arrayLineToSplit[4]+
                            ":" + arrayLineToSplit[5];
                    totalDataToFile += lineAfterChange;
                    ifExistItem = true;
                }
                else {
                    throw new NotEnoughItemsToSellException();
                }
            } else {
                totalDataToFile += line;
            }
            totalDataToFile +="\n";

        }
        if (!ifExistItem) {
            throw new ItemNotFoundException();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(totalDataToFile);
        writer.close();
        return priceOfSell;
    }

    //check count of items and return them to the employee
    @Override
    public int itemAmount(String typeItem) throws IOException, FileNotFoundException,ItemNotFoundException {
        int countOfItem = findAmountItemsFromFile(typeItem);
        return countOfItem;
    }

    //check price item and return
    @Override
    public double checkItemPrice(String typeItem) throws IOException, FileNotFoundException,ItemNotFoundException {
        double price = findPriceOfItemFromFile(typeItem);
        return price;

    }

    //return report from the file of sells by item
    public HashMap<String,Integer> getReportSellsByItem() throws IOException,FileNotFoundException {
        HashMap<String,Integer> reportByItems = new HashMap<String, Integer>();
        String line;
        Boolean firstLine = true;
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            String[] lineAfterSplit = splitLine(line);
            int countSells = Integer.parseInt(lineAfterSplit[2])-Integer.parseInt(lineAfterSplit[3]);
            reportByItems.put(lineAfterSplit[0],countSells);
        }
        reader.close();
        return reportByItems;
    }


    //return report from the file of sells by category

    public HashMap<String,Integer> getReportSellsByCategory() throws IOException,FileNotFoundException{
        HashMap<String,Integer> reportByCategory = new HashMap<String, Integer>();
        String line;
        Boolean firstLine = true;
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            String[] lineAfterSplit = splitLine(line);
            int countSells = Integer.parseInt(lineAfterSplit[2])-Integer.parseInt(lineAfterSplit[3]);
            if (reportByCategory.containsKey(lineAfterSplit[5])) {
                countSells +=reportByCategory.get(lineAfterSplit[5]);
            }
            reportByCategory.put(lineAfterSplit[5],countSells);
        }
        reader.close();
        return reportByCategory;
    }

    //return report from the file of sells by type

    public HashMap<String,Integer> getReportSellsByType() throws IOException,FileNotFoundException{
        HashMap<String,Integer> reportByType = new HashMap<String, Integer>();
        String line;
        Boolean firstLine = true;
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((line = reader.readLine()) != null) {
            if (firstLine) {
                firstLine = false;
                continue;
            }
            String[] lineAfterSplit = splitLine(line);
            int countSells = Integer.parseInt(lineAfterSplit[2])-Integer.parseInt(lineAfterSplit[3]);
            if (reportByType.containsKey(lineAfterSplit[4])) {
                countSells +=reportByType.get(lineAfterSplit[4]);
            }
            reportByType.put(lineAfterSplit[4],countSells);
        }
        reader.close();
        return reportByType;
    }
}