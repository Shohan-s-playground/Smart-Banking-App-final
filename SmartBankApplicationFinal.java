import java.util.Scanner;

public class SmartBankApplicationFinal{
    

    private static final Scanner SCANNER = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        final String CLEAR = "\033[H\033[2J";
        final String COLOR_BLUE_BOLD = "\033[34;1m";
        final String COLOR_RED_BOLD = "\033[31;1m";
        final String RESET = "\033[0m";
        final String DASHBOARD = "\u1F477 WELCOME TO SMART  BANKING";
        final String CREATE_ACCOUNT = "\u2795 Create New Account";
        final String DEPOSIT = "Deposit";
        final String WITHDRAW = "Withdraw";
        final String TRANSFER = "Transfer";
        final String CHECK_AC_BALANCE = "Check Account Balance";
        final String DELETE_ACCOUNT= "Delete Account";

        
        String screen = DASHBOARD;
        String[][] cusDetails = new String[0][];
        int currentBalance=0;
        
        loop2:
        do {
            
            final String APP_TITLE = String.format("%s%s%s",COLOR_BLUE_BOLD, screen, RESET);

            System.out.println(CLEAR);
            System.out.println("\t" + APP_TITLE + "\n");

            switch(screen){
                case DASHBOARD: 
                    System.out.println("\n[1]. Create New Account");
                    System.out.println("[2]. Deposit");
                    System.out.println("[3]. Withdraw");
                    System.out.println("[4]. Transfer");
                    System.out.println("[5]. Check Account balance");
                    System.out.println("[6]. Delete Account");
                    System.out.println("[7]. Exit\n");
                    System.out.print("Enter an option to continue > ");
                    int option = SCANNER.nextInt();
                    SCANNER.nextLine();

                    switch (option){
                        case 1: screen = CREATE_ACCOUNT; break;
                        case 2: screen = DEPOSIT; break;
                        case 3: screen = WITHDRAW; break;
                        case 4: screen = TRANSFER; break;
                        case 5: screen = CHECK_AC_BALANCE; break;
                        case 6: screen = DELETE_ACCOUNT; break;
                        case 7: System.out.println(CLEAR);System.exit(0);break;
                        default:continue;
                    }
                    break;

                    case CREATE_ACCOUNT:
                    String accid = String.format("SDB-%06d", (cusDetails.length+1));

                    System.out.println("New Account ID: "+accid);

                    boolean valid;
                    String acholname;
                    int initialdepo;
                    
                    
                    do{
                        valid = true;
                        System.out.print("Enter Account Holder's Name: ");
                        acholname = SCANNER.nextLine().strip();
                        if (acholname.isBlank()){
                            System.out.printf("%sName can't be empty%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                            continue;
                        }
                        for (int i = 0; i < acholname.length(); i++) {
                            if (!(Character.isLetter(acholname.charAt(i)) || 
                                Character.isSpaceChar(acholname.charAt(i))) ) {
                                System.out.printf("%sInvalid Name Type%s\n", COLOR_RED_BOLD, RESET);
                                valid = false;
                                break;
                            }
                        }                       
                    }while(!valid);

                    do{
                        
                        valid = true;
                        System.out.print("Enter Initial Deposit Amount: ");
                        initialdepo = SCANNER.nextInt();
                        currentBalance=initialdepo;
                        if (initialdepo<5000){
                            System.out.printf("%sInsufficient Initial Deposit Amount:You should deposit at least RS.5000 as initial amount%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                            continue;
                        }
                        
                    }while(!valid);
                    
                    String[][] newCusDetails = new String[cusDetails.length + 1][3];
                    for (int i = 0; i < cusDetails.length; i++) {
                        newCusDetails[i] = cusDetails[i];
                    }
                    newCusDetails[newCusDetails.length - 1][0] = accid;
                    newCusDetails[newCusDetails.length - 1][1] = acholname;
                    newCusDetails[newCusDetails.length - 1][2] = initialdepo + "";

                    cusDetails = newCusDetails;

                    System.out.println();
                    System.out.print(accid+":"+acholname + " created an account sucessfully. Do you want to add another new customer (Y/n)? ");
                    String response=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
               
                case DEPOSIT:
                
                    String accNo;
                    int position=0;
                    int deposittedAmount;
                    boolean flag2;
                 
                    do{
                        System.out.print("Enter Account No: ");
                        accNo = SCANNER.nextLine().strip();
                        flag2=validatingAccNo(accNo, COLOR_RED_BOLD, RESET);
                        if (!flag2)continue;

                        for(int r=0;r<cusDetails.length;r++){
                            if(cusDetails[r][0].equals(accNo)){
                                position=r;
                                break;

                            }
                            if(r==cusDetails.length-1){
                                System.out.printf("%sThere is no any account no as you entered,Please retry%s\n",COLOR_RED_BOLD,RESET);
                                System.out.println("Do you want to retry (Y/n)?") ;
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    flag2=false;
                                    continue;
                                }
                                screen = DASHBOARD;
                                continue loop2;
                            }                       
                        }    
                    }while(!flag2);
                
                    do{
                        
                        valid = true;
                        System.out.printf("Current balance: %s\n",cusDetails[position][2]);
                        System.out.print("Enter Deposit amount : ");
                        deposittedAmount = SCANNER.nextInt();
                        if (deposittedAmount<500){
                            System.out.printf("%sInsufficient Deposit Amount:You should deposit at least RS.500 as initial amount%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                        }
                        
                    }while(!valid);
                    currentBalance=Integer.parseInt(cusDetails[position][2]);
                    currentBalance+=deposittedAmount;
                    System.out.printf("New account balance : %s",currentBalance);

                    cusDetails[position][2]=Integer.toString(currentBalance);

                    System.out.println();
                    System.out.print("Money has been deposited sucessfully. Do you want to deposit more money(Y/n)? ");
                    String response1=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
                
            case WITHDRAW:
                                   
                    String accNoToWith;
                    int position2=0;
                    int withdrawAmount;
                    boolean flag3;
                 
                    do{
                        System.out.print("Enter Account No: ");
                        accNoToWith = SCANNER.nextLine().strip();
                        flag3=validatingAccNo(accNoToWith, COLOR_RED_BOLD, RESET);
                        if (!flag3)continue;

                        for(int r=0;r<cusDetails.length;r++){
                            if(cusDetails[r][0].equals(accNoToWith)){
                                position2=r;
                                break;

                            }
                            if(r==cusDetails.length-1){
                                System.out.printf("%sThere is no any account no as you entered%s\n",COLOR_RED_BOLD,RESET);
                                System.out.println("Do you want to retry (Y/n)?") ;
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    flag3=false;
                                    continue;
                                }
                                screen = DASHBOARD;
                                continue loop2;
                            }
                        
                        }
                    }while(!flag3);
                
                    do{
                        
                        valid = true;
                        System.out.printf("Current balance: %s\n",cusDetails[position2][2]);
                        System.out.print("Enter Withdraw amount : ");
                        withdrawAmount = SCANNER.nextInt();
                        if (withdrawAmount<100){
                            System.out.printf("%sInsufficient Amount for withdraw:You should withdraw minimum of RS.100 %s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                        }
                        currentBalance=Integer.parseInt(cusDetails[position2][2]);

                        if (currentBalance-withdrawAmount<500){
                            System.out.printf("%sInsufficient Account balance for withdraw:You should keep minimum of RS.500 to maintain the account %s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                        }
                        
                    }while(!valid);

                    currentBalance-=withdrawAmount;
                    cusDetails[position2][2]=Integer.toString(currentBalance);
                    System.out.printf("New account balance : %s",currentBalance);

                    
                    System.out.println();
                    System.out.print("Money has been withdrawed sucessfully. Do you want to withdraw again(Y/n)? ");
                    String response2=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;

            case TRANSFER:
                                   
                    String accNoFrom;
                    String accNoTo;
                    int currentBalanceFrom;
                    int currentBalanceTo;
                    int positionTo=-1;
                    int positionFrom=-1;
                    int transferAmount;
                                    
                 loop4:   
                    do{
                        valid = true;
                        System.out.print("Enter From Account No: ");
                        accNoFrom = SCANNER.nextLine().strip();
                        System.out.print("Enter To Account No: ");
                        accNoTo = SCANNER.nextLine().strip();
                        if (accNoTo.isBlank()||accNoFrom.isBlank()){
                            System.out.printf("%sAccount number can't be empty,Please retry%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                            continue;
                        }

                        if(!accNoTo.startsWith("SDB-")||accNoTo.length()>10||!accNoFrom.startsWith("SDB-")||accNoFrom.length()>10){
                            System.out.printf("%sInvalid format of Account Number,Please try again%s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                            continue;
                        }
                        
                        for (int i = 0; i < accNoTo.substring(4).length(); i++) {
                            if (!(Character.isDigit(accNoTo.substring(4).charAt(i)))||!(Character.isDigit(accNoFrom.substring(4).charAt(i)))) {
                                System.out.printf("%sInvalid Format of Account Number,Please try again%s\n", COLOR_RED_BOLD, RESET);
                                valid = false;
                                continue loop4;
                            }
                        }

                        for(int r=0;r<cusDetails.length;r++){
                            if(cusDetails[r][0].equals(accNoTo)){
                                positionTo=r;
                            }
                            if(cusDetails[r][0].equals(accNoFrom)){
                                positionFrom=r;
                            }
                        }
                    if(positionTo==-1||positionFrom==-1){
                                System.out.printf("%sAccount numbers are not in current format,Please retry%s\n",COLOR_RED_BOLD,RESET);
                                System.out.println("Do you want to retry (Y/n)?") ;
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    valid=false;
                                    continue loop4;
                                }
                                screen = DASHBOARD;
                                continue loop2;
                            }
                                                
                    }while(!valid);
                
                    do{
                        
                        valid = true;
                        System.out.printf("From account holder's name: %s\n",cusDetails[positionFrom][1]);
                        System.out.printf("Current balance of From account: %s\n",cusDetails[positionFrom][2]);
                        System.out.printf("To account holder's name: %s\n",cusDetails[positionTo][1]);
                        System.out.printf("Current balance of To account: %s\n",cusDetails[positionTo][2]);
                        
                        System.out.println(CLEAR);
                        System.out.print("Enter Transfer amount : ");
                        transferAmount = SCANNER.nextInt();
                        if (transferAmount<100){
                            System.out.printf("%sInsufficient Amount for transfer:You should transfer minimum of RS.100 %s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                        }
                        currentBalanceFrom=Integer.parseInt(cusDetails[positionFrom][2]);
                        currentBalanceTo=Integer.parseInt(cusDetails[positionTo][2]);
                        currentBalanceFrom-=(transferAmount+transferAmount*0.02);
                        currentBalanceTo+=transferAmount;

                        if (currentBalanceFrom<500){
                            System.out.printf("%sInsufficient Account balance for transfer:You should keep minimum of RS.500 to maintain the account %s\n", COLOR_RED_BOLD, RESET);
                            valid = false;
                        }
                        
                    }while(!valid);
                    cusDetails[positionFrom][2]=Integer.toString(currentBalanceFrom);
                    cusDetails[positionTo][2]=Integer.toString(currentBalanceTo);
                    System.out.printf("New From account balance : %s\n",currentBalanceFrom);
                    System.out.printf("New To account balance : %s",currentBalanceTo);

                    
                    System.out.println();
                    System.out.print("Money transfered sucessfully. Do you want to transfer more money(Y/n)? ");
                    String response3=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;

            case CHECK_AC_BALANCE:
                  
                    String accNoToCheck;
                    int positionCheck=0;
                    boolean flag5;
      
                    do{
                        System.out.print("Enter Account No: ");
                        accNoToCheck = SCANNER.nextLine().strip();
                        flag5=validatingAccNo(accNoToCheck, COLOR_RED_BOLD, RESET);
                        if (!flag5)continue;

                        for(int r=0;r<cusDetails.length;r++){
                            if(cusDetails[r][0].equals(accNoToCheck)){
                                positionCheck=r;
                                break;

                            }
                            if(r==cusDetails.length-1){
                                System.out.printf("%sThere is no any account no as you entered%s",COLOR_RED_BOLD,RESET);
                                System.out.println("Do you want to retry (Y/n)?") ;
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    flag5=false;
                                    continue;
                                }
                                screen = DASHBOARD;
                                continue loop2;
                            }
                        
                        }   
                    }while(!flag5);

                    System.out.printf("Current balance: %s\n",cusDetails[positionCheck][2]);
                    System.out.printf("Account holder's name: %s\n",cusDetails[positionCheck][1]);

                    currentBalance=Integer.parseInt(cusDetails[positionCheck][2]);
                    System.out.printf("Available account balance for withdraw : %s",currentBalance-500);

                    
                    System.out.println();
                    System.out.print("Do you want to check account balance again (Y/n)? ");
                    //String response4=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
            
            case DELETE_ACCOUNT:
                  
                    String accNoToDelete;
                    int positionDelete=0;
                    boolean flag6;
                    
                    if(cusDetails.length>0){
                    do{
                        System.out.print("Enter Account No: ");
                        accNoToDelete = SCANNER.nextLine().strip();
                        flag6=validatingAccNo(accNoToDelete, COLOR_RED_BOLD, RESET);
                        if (!flag6)continue;
                        
                        for(int r=0;r<cusDetails.length;r++){
                            if(cusDetails[r][0].equals(accNoToDelete)){
                                positionDelete=r;
                                break;

                            }
                            if(r==cusDetails.length-1){
                                System.out.printf("%sThere is no any account number as you entered%s\n",COLOR_RED_BOLD,RESET);
                                System.out.println("Do you want to retry (Y/n)?") ;
                                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                                    flag6=false;
                                    continue;
                                }
                                screen = DASHBOARD;
                                continue loop2;
                            }
                        
                        }   
                    }while(!flag6);

                    System.out.printf("Account holder's name: %s\n",cusDetails[positionDelete][1]);
                    System.out.printf("Current balance: %s\n",cusDetails[positionDelete][2]);
   
                    System.out.println();

                    System.out.print("Are you sure to delete the account (Y/n)? ");
                    //String response5=SCANNER.nextLine().strip().toUpperCase();

                    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                    String newCusDetails2[][] = new String[cusDetails.length-1][];
                
                    for (int i = 0; i < cusDetails.length; i++) {
                        if (i < positionDelete){
                            newCusDetails2[i] = cusDetails[i];
    
                        }else if (i == positionDelete){
                            continue;
                        }else{
                            newCusDetails2[i - 1] = cusDetails[i];
                        }
                    }
                    System.out.printf("%s : %s's account has been deleted successfully\n",cusDetails[positionDelete][0],cusDetails[positionDelete][1]);
                    cusDetails = newCusDetails2;
                    
                    }

                    System.out.print("Do you want to Delete another account (Y/n)? ");
                    //String response6=SCANNER.nextLine().strip().toUpperCase();
                     if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) continue;
                    screen = DASHBOARD;
                    break;
    
}if(cusDetails.length==0){
    System.out.printf("%SThere is no any account to delete%S\n",COLOR_RED_BOLD,RESET);
    System.out.print("Do you want to go back? (Y/n) ");
    if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) screen = DASHBOARD;
    break;
                
}                          
}
}while(true); 
}

public static boolean validatingAccNo(String accNo,String COLOR_RED_BOLD,String RESET){
    if (accNo.isBlank()){
        System.out.printf("%sAccount number can't be empty,please retry%s\n", COLOR_RED_BOLD, RESET);
        return false;
        }

    if(!accNo.startsWith("SDB-")||accNo.length()>10){
        System.out.printf("%sInvalid format of Account Number,Please retry%s\n", COLOR_RED_BOLD, RESET);
        return false;
        }
                        
    for (int i = 0; i < accNo.substring(4).length(); i++) {
        if (!(Character.isDigit(accNo.substring(4).charAt(i)))) {
        System.out.printf("%sInvalid Format of Account Number,Please retry%s\n", COLOR_RED_BOLD, RESET);
        return false;
            }
        }
    return true;
}
public static void askingForContinueorExit(){

}

}