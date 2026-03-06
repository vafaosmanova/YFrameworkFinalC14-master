package ui_automation.utilities;


import com.github.javafaker.Faker;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PracticeExcelFiles {

    public static void main(String[] args) {

       // Let's read the data from Credential.xlsx
        String filePath = "src/test/resources/test_data/Credentials.xlsx";
        // ExcelUtility.printAllDataFromExcelFile(filePath, "credentials");

        // ExcelUtilities we need to set the file first
        ExcelUtility.setExcelFile(filePath, "credentials");

        String accountType = ExcelUtility.getCellData(0, 0).toString();
        String username = ExcelUtility.getCellData(1, 0).toString();
        String password = ExcelUtility.getCellData(2, 0).toString();
//
//        System.out.println(accountType);
//        System.out.println(username);
//        System.out.println(password);


        // Let's create a new file with ExcelUtils
//        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hhmmss")); // 754326527325432 -> 083133
//        String fileName = "NewSample" + time + ".xlsx";
//        String filePath = "src/test/resources/test_data/" + fileName;
//        String sheetName = "NEW_SHEET";
//
//        ExcelUtility.createNewExcelFile(filePath, sheetName);
//
//        ExcelUtility.writeToCell(filePath, sheetName, 0, 0, "First Name");
//        ExcelUtility.writeToCell(filePath, sheetName, 0, 1, "Last Name");
//        ExcelUtility.writeToCell(filePath, sheetName, 0, 2, "Email Address");
//        ExcelUtility.writeToCell(filePath, sheetName, 0, 3, "Address");
//        ExcelUtility.writeToCell(filePath, sheetName, 0, 4, "Education");
//
//        Faker faker = new Faker();
//
//        for (int i = 1; i < 100; i++) {
//            for (int j = 0; j < 5; j++) {
//               switch (j){
//                   case 0 :
//                       ExcelUtility.writeToCell(filePath, sheetName, i, j, faker.name().firstName());
//                       break;
//                   case 1 :
//                       ExcelUtility.writeToCell(filePath, sheetName, i, j, faker.name().lastName());
//                       break;
//                   case 2 :
//                       ExcelUtility.writeToCell(filePath, sheetName, i, j, faker.internet().emailAddress());
//                       break;
//                   case 3 :
//                       ExcelUtility.writeToCell(filePath, sheetName, i, j, faker.address().fullAddress());
//                       break;
//                   case 4 :
//                       ExcelUtility.writeToCell(filePath, sheetName, i, j, faker.demographic().educationalAttainment());
//                       break;
//               }
//            }
//        }

    }

}
