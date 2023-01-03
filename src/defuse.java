// -- // -- // -- // -- // -- // -- // -- //
//                                        //
//          Import des librairies         //
//                                        //
// -- // -- // -- // -- // -- // -- // -- //

import extensions.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class defuse extends Program{
    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions Primaires         //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

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

    // Fonction permettant d'afficher un tableau de String
    void printTable(String[] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            println("["+(i+1)+"]: "+table[i]);
            println();
        }
    }

    // Fonction permettant d'afficher un tableau de String en 2D
    void printTable(String[][] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            for (int j = 0; j < length(table, 2); j++){
                print(table[i][j]);
                print(" ");
            }
            println();
            println();
        }
    }

    // Fonction permettant de créer un String de n espaces
    String space(int n){
        String str = "";
        for (int i = 0; i < n; i++){
            str += " ";
        }
        return str;
    }

    // Fonction permettant de savoir si un fichier existe dans le dossier courant
    boolean fileExist(String folder, String fileName){
        String[] files = getAllFilesFromDirectory(folder);
        for (int i = 0; i < files.length; i++){
            if (equals(files[i], fileName)){
                return true;
            }
        }
        return false;
    }

    // Fonction permettant de centrer un String dans un espace de taille len
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

    // Fonction permettant de verifier si une table contient une valeur
    boolean containsString(String[] table, String value){
        for (int i = 0; i < length(table, 1); i++){
            if (equals(table[i], value)){
                return true;
            }
        }
        return false;
    }

    // Fonction permettant de récupérer l'index d'une valeur dans une table
    int indexOfString(String[] table, String value){
        for (int i = 0; i < length(table, 1); i++){
            if (equals(table[i], value)){
                return i;
            }
        }
        return -1;
    }

    // Fonction permettant de récupérer le contenu d'un fichier txt
    String getFileContent(String path){
        File f = newFile(path);
        String content = "";
        while (ready(f)) {
            String currentLine = readLine(f);
            content += currentLine + "\n";
        }
        return content;
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions Secondaires        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de vérifier l'existence des fichiers nécessaires au jeu
    void verifFiles(){
        if (!fileExist("../ressources/", "scoresboard.csv")){
            String[][] scoresboard = new String[11][3];
            scoresboard[0][0] = "Pseudo";
            scoresboard[0][1] = "Score";
            scoresboard[0][2] = "Date";
            saveCSV(scoresboard, "../ressources/scoresboard.csv");
        }
    }

    // Fonction permettant de sauvegarder un score dans le tableau des scores
    void saveScore(String pseudo, int score){
        String[][] scoresboard = csvToTable("../ressources/scoresboard.csv");
        int i = 1;
        // while (i < length(scoresboard, 1) && score < parseInt(scoresboard[i][1])){
        //     i++;
        // }
        for (int j = length(scoresboard, 1) - 1; j > i; j--){
            scoresboard[j][0] = scoresboard[j - 1][0];
            scoresboard[j][1] = scoresboard[j - 1][1];
            scoresboard[j][2] = scoresboard[j - 1][2];
        }
        scoresboard[i][0] = pseudo;
        scoresboard[i][1] = ""+score;
        scoresboard[i][2] = ""+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
        saveCSV(scoresboard, "../ressources/scoresboard.csv");
    }

    // Fonction permettant d'obtenir et de vérifier la réponse d'un utilisateur
    int getAnswer(String[] answers){
        String answer = readString();
        while (!containsString(answers, answer)){
            println();
            println("Veuillez entrer une réponse valide.");
            print(" > ");
            answer = readString();
        }
        return indexOfString(answers, answer) + 1;
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions d'affichage        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Function permettant d'afficher le logo du jeu
    void boomLogo(){
        println();
        println();
        println(getFileContent("../ressources/boum.txt"));
        println();
        println();
    }

    // Fonction permettant d'afficher le menu d'introduction
    String introduction(){
        System.out.print("\033[H\033[2J");
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
        print("Pour commencer entrez votre pseudo: ");
        return readString();
    }

    // Fonction permettant d'afficher le menu principal
    int menu(){
        System.out.print("\033[H\033[2J");
        boomLogo();
        println();
        println();
        println("1 - Lancez le Jeu");
        println("2 - Tableau des Scores");
        println("3 - Parametres");
        println("4 - Quitter");
        println();
        println();
        print("Que voulez-vous faire ? ");
        return getAnswer(new String[]{"1", "2", "3", "4"});
    }

    // Fonction permettant d'afficher le tableau des scores
    void scoresboard(){
        System.out.print("\033[H\033[2J");
        println();
        println();
        println("Voici le tableau des scores:");
        println();
        println();

        String[][] scoresboard = csvToTable("../ressources/scoresboard.csv");
        for (int i = 0; i < length(scoresboard, 1); i++){
            for (int j = 0; j < length(scoresboard, 2); j++){
                String content = scoresboard[i][j];
                if (equals(content, "null")){
                    content = "-";
                }
                print(centerString(content, 20));
            }
            println();
            println();
        }

        println();
        println();
        print("Appuyez sur entrée pour revenir au menu principal: ");
        readString();
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Corps du Programme           //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Corps du programme
    void algorithm(){
        verifFiles();
        boolean fini = false;
        while (!fini) {
            int id = menu();
            if (id == 1){
                println("Vous avez choisi de lancer le jeu !");
            }
            else if (id == 2){
                scoresboard();
            }
            else if (id == 3){
                println("Vous avez choisi d'afficher les paramètres !");
            }
            else if (id == 4){
                fini = true;
            }
        }
    }
}

/* Notes:

printTable(csvToTable("../ressources/test.csv"));

*/