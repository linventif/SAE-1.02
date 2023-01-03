import extensions.*;
class defuse extends Program{
    String introduction(){
        println();
        println();
        println("Bienvenue dans le jeu Defuse !");
        println();
        println("Vous devez defuser une bombe avant qu'elle n'explose !");
        println("Voici votre manuel d'utilisation de la bombe, il vous expliquera comment la désamorcer.");
        println("Cependant la bombe est instable, si vous faite 3 erreurs, la bombe explosera.");
        println();
        println("Bonne chance !");
        println();
        println();
        print("Pour commencer entrez votre pseudo : ");
        return readString();
    }
    int menu(){
        println();
        println();
        println("1 - Lancez le jeu");
        println("2 - Tableau des scores");
        println("3 - Paramètres");
        println("4 - Quitter");
        println();
        println();
        print("Que voulez-vous faire ? ");
        return readInt();
    }

    boolean fileExist(String[] files, String fileName){
        for (int i = 0; i < files.length; i++){
            if (files[i] == fileName){
                return true;
            }
        }
        return false;
    }

    String space(int n){
        String str = "";
        for (int i = 0; i < n; i++){
            str += " ";
        }
        return str;
    }

    String centerString(String str, int len){
        if (length(str) > len){
            return substring(str, (0), (length(str) - 3)) + "...";
        } else {
            int half = (len - length(str)) / 2;
            if (length(str) % 2 == 0){
                return space(half) + str + space(half);
            } else {
                return space(half + 1) + str + space(half);
            }
        }
    }

    void scoresboard(){
        String[][] scoresboard = new String[11][3];
        scoresboard[0][0] = "Pseudo";
        scoresboard[0][1] = "Score";
        scoresboard[0][2] = "Date";

        for (int i = 1; i < length(scoresboard, 1); i++){
            for (int j = 0; j < length(scoresboard, 2); j++){
                scoresboard[i][j] = "-";
            }
        }

        if (fileExist(getAllFilesFromCurrentDirectory(), "scoresboard.csv")){
            //scoresboard = loadCSV("scoresboard.csv");
        } else {
            saveCSV(scoresboard, "scoresboard.csv");
        }

        println();
        println();
        println("Voici le tableau des scores :");
        println();
        println();
        for (int i = 0; i < length(scoresboard, 1); i++){
            for (int j = 0; j < length(scoresboard, 2); j++){
                print(centerString(scoresboard[i][j], 20));
            }
            println();
            println();
        }
        println();
        println();
        print("Appuyez sur entrée pour revenir au menu principal : ");
        readString();
    }

    // Fonction permettant de créer un tableau de String en 2D à partir d'un fichier csv
    String[][] csvToTable(String csvPath){
        CSVFile csv = loadCSV(csvPath);
        String[][] table = new String[rowCount(csv)][columnCount(csv)];
        for (int i = 0; i < rowCount(csv); i++){
            for (int j = 0; j < columnCount(csv); j++){
                table[i][j] = getCell(csv, i, j);
            }
        }
        return table;
    }

    // Fonction permettant d'afficher un tableau de String en 2D
    void printTable(String[][] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            print(" ");
            for (int j = 0; j < length(table, 2); j++){
                print(table[i][j]);
                print(" ");
            }
            println();
            println();
        }
    }

    void algorithm(){
        // boolean fini = false;
        // while (!fini) {
        //     int action = menu();
        //     if (action == 1){
        //         println("Vous avez choisi de lancer le jeu !");
        //     }
        //     else if (action == 2){
        //         scoresboard();
        //     }
        //     else if (action == 3){
        //         println("Vous avez choisi d'afficher les paramètres !");
        //     }
        //     else if (action == 4){
        //         fini = true;
        //     }
        //     else{
        //         println("Vous n'avez pas choisi une action valide !");
        //     }
        // }
        //String pseudo = introduction();
        println("ALL IS GOOD");
        //printTable(csvToTable("test.csv"));
    }
}