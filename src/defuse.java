// -- // -- // -- // -- // -- // -- // -- //
//                                        //
//          Sommaire du Programme         //
//                                        //
// -- // -- // -- // -- // -- // -- // -- //
//                                        //
// Ligne 001 : Sommaire                   //
// Ligne 020 : Import des Librairies      //
// Ligne 030 : Classes                    //
// Ligne 020 : Fonctions de Debug         //
// Ligne 050 : Fonctions Primaires        //
// Ligne 200 : Fonctions Secondaires      //
// Ligne 250 : Fonctions de Test          //
// Ligne 350 : Fonctions d'Affichage      //
// Ligne 500 : Fonctions des Menu         //
// Ligne 600 : Corps du Programme         //
//                                        //
// -- // -- // -- // -- // -- // -- // -- //
//                                        //
//          Import des librairies         //
//                                        //
// -- // -- // -- // -- // -- // -- // -- //

import extensions.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class StopWatch{
    long startTime;
    long stopTime;
    boolean running = false;

    // Fonction permettant de démarrer le chronomètre
    public void start(){
        this.startTime = System.currentTimeMillis();
        this.running = true;
    }

    // Fonction permettant d'arrêter le chronomètre
    public void stop(){
        this.stopTime = System.currentTimeMillis();
        this.running = false;
    }

    // Fonction permettant de récupérer le temps en millisecondes
    public long getTime(){
        long elapsed;
        if(running){
            elapsed = (System.currentTimeMillis() - startTime);
        }
        else{
            elapsed = (stopTime - startTime);
        }
        return elapsed;
    }

    // Fonction permettant de récupérer le temps en secondes
    public long getTimeSecs(){
        long elapsed;
        if(running){
            elapsed = ((System.currentTimeMillis() - startTime) / 1000);
        }
        else{
            elapsed = ((stopTime - startTime) / 1000);
        }
        return elapsed;
    }
}

class Cable{
    boolean cut = false;
    boolean canBeCut = false;
    char color = 'R';
}

class IPAddress{
    int[] ip = {0, 0, 0, 0};
    String ipAnswer = "0.0.0.0";
    String[] ipBinary = new String[]{"00000000", "00000000", "00000000", "00000000"};
    String ipBinaryShow = "0000 0000 | 0000 0000 | 0000 0000 | 0000 0000";
}

class Morse{
    String world = "Secret";
    String morse = ".... . .-.. .-.. ---";
}

class Module{
    String name = "Module Sans Nom";
    boolean resolved = false;
    Cable[] cables = new Cable[5];
    int points = 1500;
    IPAddress ip = new IPAddress();
    Morse morse = new Morse();
}

class Bombe{
    int focusModule = 0;
    int nbModules = 3;
    int nbModulesResolve = 0;
    Module[] modules = new Module[nbModules];
}

class Player{
    String name = "Joueur Sans Nom";
    int errors = 0;
    StopWatch time = new StopWatch();
    int score = 0;
}

class Manual{
    int page = 0;
    int nbPages = 0;
}

class Game{
    Player player = new Player();
    Bombe bombe = new Bombe();
    Manual manual = new Manual();
    boolean quit = false;
    boolean cheat = false;
    int maxPoints = 10000;
    int errorCost = 350;
}

class Defuse extends Program{
    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions de Debug          //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant d'enregistre un type dans un fichier debug
    void saveDebug(String[] content){
        saveCSV(new String[][]{content}, "../ressources/test/debug.csv");
    }
    void saveDebug(boolean value){
        saveCSV(new String[][]{{""+value}}, "../ressources/test/debug.csv");
    }
    void saveDebug(String content){
        saveCSV(new String[][]{{content}}, "../ressources/test/debug.csv");
    }
    void saveDebug(String[][] content){
        saveCSV(content, "../ressources/test/debug.csv");
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

    // Fonction permettant de convertir un String en int
    int stringToNumber(String s){
        if (s == null || equals(s, "") || equals(s, "null")){
            return 0;
        }
        int result = 0;
        for (int i = 0; i < length(s); i++){
            result = result * 10 + (int)charAt(s, i) - 48;
        }
        return result;
    }

    String[][] settings = csvToTable("../ressources/csv/settings.csv");
    int slowSpeed = stringToNumber(settings[3][1]);

    void printSlow(String str){
        if (slowSpeed > 0){
            for (int i = 0; i < length(str); i++){
                print(str.charAt(i));
                delay(slowSpeed);
            }
        } else {
            print(str);
        }
    }

    void printlnSlow(String str){
        if (slowSpeed > 0){
            printSlow(str);
            println();
        } else {
            println(str);
        }
    }

    // Fonction permettant d'afficher un tableau de String
    void printTable(String[] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            printlnSlow("["+(i+1)+"]: "+table[i]);
            println();
        }
    }

    String replace(String text, String old, String new_){
        String[] textTable = new String[]{old, new_};
        String newText = "";
        for (int i = 0; i < length(textTable, 1); i++){
            newText += textTable[i];
            if (i != length(textTable, 1)-1){
                newText += new_;
            }
        }
        return newText;
    }

    // Fonction permettant de convertir un nombre en binaire (String)
    String numberToBinary(int number){
        String binary = "";
        while (number > 0){
            binary = (number % 2) + binary;
            number /= 2;
        }
        if (length(""+binary) < 8){
            while (length(""+binary) < 8){
                binary = "0"+binary;
            }
        }
        return binary;
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

    int clamp(int value, int min, int max){
        //saveDebug(new String[]{"clamp", ""+value, ""+min, ""+max});
        if (value < min){
            return min;
        }
        else if (value > max){
            return max;
        }
        else{
            return value;
        }
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions Primaires         //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de verifier si une table contient une valeur
    boolean containsString(String[] table, String value){
        if (value == null){
            return false;
        }
        for (int i = 0; i < length(table, 1); i++){
            if (equals(table[i], value)){
                return true;
            }
        }
        return false;
    }

    // Fonction permettant de verifier si une chaine de caractère est un caractère
    boolean containsOnlyChar(String str, char strart_c, char end_c){
        for (int i = 0; i < length(str); i++){
            if (str.charAt(i) < strart_c || str.charAt(i) > end_c){
                return false;
            }
        }
        return true;
    }

    // Fonction permettant de récupérer le nombre fichier dans un dossier
    int numberOfFiles(String path){
        String[] files = getAllFilesFromDirectory(path);
        return length(files);
    }

    // Fonction permettant d'afficher les réponses possibles
    void printAnswers(String[] answers){
        for (int i = 0; i < length(answers); i++){
            if (i == (length(answers) - 1)){
                print(answers[i]);
            } else {
                print(answers[i] + ", ");
            }
        }
        println();
    }

    // Fonction permettant de créer un String de n caractères c
    String repeatChar(int n, char c){
        String str = "";
        for (int i = 0; i < n; i++){
            str += c;
        }
        return str;
    }

    // Fonction permettant verifier une egalité entre deux char
    boolean equals(char c1, char c2){
        return c1 == c2;
    }

    // Fonction permettant de nettoyer une chaine de caractère
    String onlyLetter(String str){
        String newStr = "";
        for (int i = 0; i < length(str); i++){
            if ((str.charAt(i) >= 'a' && str.charAt(i) <= 'z') || (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')){
                newStr += str.charAt(i);
            }
        }
        return newStr;
    }

    String onlyInt(String str){
        String newStr = "";
        for (int i = 0; i < length(str); i++){
            if ((str.charAt(i) >= '0' && str.charAt(i) <= '9')){
                newStr += str.charAt(i);
            }
        }
        return newStr;
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

    // Fonction permettant de créer un String de n fois s
    String repeatString(int n, String s){
        String str = "";
        for (int i = 0; i < n; i++){
            str += s;
        }
        return str;
    }

    // Fonction permettant de savoir si un fichier existe dans le dossier courant
    boolean fileExist(String folder, String fileName){
        String[] files = getAllFilesFromDirectory(folder);
        return containsString(files, fileName);
    }

    // Fonction permettant de centrer un String dans un espace de taille len
    String centerString(String str, int len){
        if (length(str) > len){
            return substring(str, (0), (length(str) - 3)) + repeatChar(len, '.');
        } else {
            double half = (double)(len - length(str)) / 2;
            if (half % 1 == 0){
                return repeatChar((int)half, ' ') + str + repeatChar((int)half, ' ');
            } else {
                return repeatChar((int)half + 1, ' ') + str + repeatChar((int)half , ' ');
            }
        }
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

    // Fonction permettant de savoir le nombre de lignes d'un fichier txt
    int getNumberOfLines(String path){
        File f = newFile(path);
        int nbLines = 0;
        while (ready(f)) {
            String currentLine = readLine(f);
            nbLines++;
        }
        return nbLines;
    }

    // Fonction récupérant un nombre aléatoire entre min et max
    int randomInt(int min, int max){
        return (int)(random() * (max - min + 1) + min);
    }

    // Fonction récupérant une String aléatoire dans un tableau de String
    String randomStringTable(String[] table){
        return table[randomInt(0, length(table) - 1)];
    }

    // Fonction récupérant un char aléatoire dans un tableau de char
    char randomCharTable(char[] table){
        return table[randomInt(0, length(table) - 1)];
    }

    // Fonction permettant de récupérer un fichier aléatoire dans un dossier
    String getRandomFile(String folder){
        String[] files = getAllFilesFromDirectory(folder);
        return files[randomInt(0, length(files) - 1)];
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions Secondaires        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de reinitialiser les scores
    void resetScoresBoard(){
        String[][] scoresboard = new String[11][3];
        scoresboard[0][0] = "Pseudo";
        scoresboard[0][1] = "Score";
        scoresboard[0][2] = "Date";
        saveCSV(scoresboard, "../ressources/csv/scoresboard.csv");
    }

    // Fonction permettant de reinitialiser les paramettre
    void resetSettings(){
        settings[0][0] = "cheat";
        settings[0][1] = "false";
        settings[1][0] = "maxPoints";
        settings[1][1] = "1000000";
        settings[2][0] = "errorCost";
        settings[2][1] = "350";
        settings[3][0] = "textSpeed";
        settings[3][1] = "0";
        saveCSV(settings, "../ressources/csv/settings.csv");
    }

    // Fonction permettant de sauvegarder un score dans le tableau des scores
    void addScore(String pseudo, int score){
        String[][] scoresboard = csvToTable("../ressources/csv/scoresboard.csv");
        int index = 1;
        boolean stop = false;
        if (score < 0){
            score = 0;
        }
        while (index <= 10 && !stop){
            int last_score = stringToNumber(scoresboard[index][1]);
            if (score < last_score){
                println(index + " " + last_score + " " + score + " " + stop);
                index++;
            } else {
                stop = true;
            }
        }
        if (index <= 10){
            for (int i = 10; i > index; i--){
                if (i <= 10){
                    scoresboard[i][0] = scoresboard[i - 1][0];
                    scoresboard[i][1] = scoresboard[i - 1][1];
                    scoresboard[i][2] = scoresboard[i - 1][2];
                }
            }
            scoresboard[index][0] = pseudo;
            scoresboard[index][1] = ""+score;
            scoresboard[index][2] = ""+DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now());
            saveCSV(scoresboard, "../ressources/csv/scoresboard.csv");
        }
    }

    // Fonction permettant de mettre une chaine de caractère en minuscule
    String stringLower(String str){
        String result = "";
        for (int i = 0; i < length(str); i++){
            char c = charAt(str, i);
            if (c >= 'A' && c <= 'Z'){
                c += 32;
            }
            result += c;
        }
        return result;
    }

    // Fonction permettant de mettre la première lettre d'une chaine de caractère en majuscule
    String firstLetterUpper(String str){
        String result = "";
        for (int i = 0; i < length(str); i++){
            char c = charAt(str, i);
            if (i == 0 && c >= 'a' && c <= 'z'){
                c -= 32;
            }
            result += c;
        }
        return result;
    }

    // Fonction permettant d'obtenir et de vérifier la réponse d'un utilisateur
    int getAnswer(String[] answers){
        boolean debug = false;
        String answer = stringLower(readString());
        while (!containsString(answers, answer) && !debug){
            println();
            printlnSlow("Veuillez entrer une réponse valide ! ");
            printSlow("Réponse Possible: ");
            printAnswers(answers);
            printSlow(" > ");
            answer = stringLower(readString());
            if (equals(answer, "debug")){
                debug = true;
            }
        }
        return stringToNumber(answer);
    }

    // Fonction permettant de verifier si un pseudo est valide
    String validPseudo(String pseudo){
        while (length(pseudo) < 3 || length(pseudo) > 20 || !containsOnlyChar(pseudo, 'a', 'z')){
            printlnSlow("Votre pseudo doit contenir entre 3 et 20 caractères et ne doit pas contenir que des lettres minuscules !");
            printSlow(" > ");
            pseudo = readString();
        }
        pseudo = firstLetterUpper(pseudo);
        return pseudo;
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions de Test           //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de tester la fonction de containsString
    void testContainsString() {
        String[] table = {"a", "b", "c", "d", "e"};
        assertEquals(true, containsString(table, "a"));
        assertEquals(true, containsString(table, "b"));
        assertEquals(true, containsString(table, "c"));
        assertEquals(true, containsString(table, "d"));
        assertEquals(true, containsString(table, "e"));
        assertEquals(false, containsString(table, "f"));
        assertEquals(false, containsString(table, "g"));
        assertEquals(false, containsString(table, "h"));
        assertEquals(false, containsString(table, "i"));
        assertEquals(false, containsString(table, "j"));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Contains String", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction repeatChar
    void testRepeatChar(){
        assertEquals("a", repeatChar(1, 'a'));
        assertEquals("bb", repeatChar(2, 'b'));
        assertEquals("ccc", repeatChar(3, 'c'));
        assertEquals("dddd", repeatChar(4, 'd'));
        assertEquals("eeeee", repeatChar(5, 'e'));
        assertEquals("ffffff", repeatChar(6, 'f'));
        assertEquals("       ", repeatChar(7, ' '));
        assertEquals("        ", repeatChar(8, ' '));
        assertEquals("         ", repeatChar(9, ' '));
        assertEquals("          ", repeatChar(10, ' '));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Repeat Char", 30) + " : Passed" + "\u001B[0m");
    }

    // void testIP(){
    //     assertEquals("127.0.0.1", 
    // }

    // Fonction permettant de tester la fonction de centerString
    void testCenterString(){
        assertEquals(" a ", centerString("a", 3));
        assertEquals("  a ", centerString("a", 4));
        assertEquals("  a  ", centerString("a", 5));
        assertEquals("   a  ", centerString("a", 6));
        assertEquals("   a   ", centerString("a", 7));
        assertEquals("    a   ", centerString("a", 8));
        assertEquals("    a    ", centerString("a", 9));
        assertEquals("     a    ", centerString("a", 10));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Center String", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de indexOfString
    void testIndexOfString(){
        String[] table = {"a", "b", "c", "d", "e"};
        assertEquals(0, indexOfString(table, "a"));
        assertEquals(1, indexOfString(table, "b"));
        assertEquals(2, indexOfString(table, "c"));
        assertEquals(3, indexOfString(table, "d"));
        assertEquals(4, indexOfString(table, "e"));
        assertEquals(-1, indexOfString(table, "f"));
        assertEquals(-1, indexOfString(table, "g"));
        assertEquals(-1, indexOfString(table, "h"));
        assertEquals(-1, indexOfString(table, "i"));
        assertEquals(-1, indexOfString(table, "j"));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Index Of String", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de fileExist
    void testFileExist(){
        assertEquals(true, fileExist("../ressources/test", "test.txt"));
        assertEquals(true, fileExist("../ressources/test", "test.csv"));
        assertEquals(true, fileExist("../ressources/csv", "scoresboard.csv"));
        assertEquals(false, fileExist("../ressources/test", "test.txtt"));
        assertEquals(false, fileExist("../ressources/test", "test.cvs"));
        assertEquals(false, fileExist("../ressources/csv", "scoresboard.cvs"));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("File Exist", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de getFileContent
    void testGetFileContent(){
        assertEquals("JE SUIS UN FICHIER DE TEST\n", getFileContent("../ressources/test/test.txt"));
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Get File Content", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de csvToTable
    void testCsvToTable(){
        String[][] table = csvToTable("../ressources/test/test.csv");
        assertEquals("a", table[0][0]);
        assertEquals("b", table[0][1]);
        assertEquals("c", table[0][2]);
        assertEquals("d", table[1][0]);
        assertEquals("e", table[1][1]);
        assertEquals("f", table[1][2]);
        assertEquals("g", table[2][0]);
        assertEquals("h", table[2][1]);
        assertEquals("i", table[2][2]);
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("Csv To Table", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de tableToCsv
    void testStringToNumber(){
        for (int i = 0; i < 10; i++){
            assertEquals(i, stringToNumber("" + i));
        }
        printlnSlow("\u001B[32m" + "Test Function : " + centerString("String To Number", 30) + " : Passed" + "\u001B[0m");
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions d'affichage        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    void pressEnterToContinue(String menu){
        printSlow("Appuyez sur entrée pour continuer vers le menu " + menu + ".");
        readString();
    }

    // Fonction permettant d'afficher le menu d'introduction
    String introduction(){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        printlnSlow("Bienvenue dans le jeu Defuse !");
        println();
        printlnSlow("Votre mission est de désamorcer une bombe avant qu'elle n'explose.");
        printlnSlow("Pour cela vous devrez résoudre chaque module de la bombe.");
        printlnSlow("Chaque module est différent, vous devrez donc trouver la bonne méthode pour le désamorcer.");
        printlnSlow("Pour votre mission vous aurez un manuel d'utilisation de la bombe.");
        printlnSlow("Il vous sera utile pour résoudre les modules sans faire d'erreur.");
        printlnSlow("Cependant la bombe est instable, si vous faite 3 erreurs, la bombe explosera.");
        println();
        printlnSlow("Bonne chance !");
        println();
        println();
        printSlow("Pour commencer entrez votre nom d'agent: ");
        return validPseudo(readString());
    }

    // Fonction permettant d'afficher le menu principal
    int mainMenu(){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        printlnSlow("1 - Lancez le Jeu");
        printlnSlow("2 - Tableau des Scores");
        printlnSlow("3 - Parametres & Debugage");
        printlnSlow("4 - Quitter");
        println();
        println();
        printSlow("Que voulez-vous faire ? ");
        return getAnswer(new String[]{"1", "2", "3", "4"});
    }

    // Fonction permettant d'afficher le menu des parametres
    int parameterMenu(){
        System.out.print("\033[H\033[2J");
        String[][] settings = csvToTable("../ressources/csv/settings.csv");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        printlnSlow("0 - Retour");
        printlnSlow("1 - Réinitialiser les Scores");
        printlnSlow("2 - Réinitialiser les Parametres");
        printlnSlow("3 - Test de Debugage");
        printlnSlow("4 - Ajouter un Score");
        if (equals(settings[0][1], "true")){
            printlnSlow("5 - Désactiver le Mode Triche");
        } else {
            printlnSlow("5 - Activer le Mode Triche");
        }
        printlnSlow("6 - Vitesse du Text : " + settings[3][1]);
        println();
        println();
        printSlow("Que voulez-vous faire ? ");
        return getAnswer(new String[]{"0", "1", "2", "3", "4", "5", "6"});
    }

    // Fonction permettant d'afficher le tableau des scores
    void scoresboard(){
        System.out.print("\033[H\033[2J");
        println();
        println();
        printlnSlow("Voici le tableau des scores:");
        println();
        println();
        String[][] scoresboard = csvToTable("../ressources/csv/scoresboard.csv");
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
        pressEnterToContinue("principal");
    }

    void debugageTest(){
        System.out.print("\033[H\033[2J");
        println();
        println();
        testContainsString();
        testRepeatChar();
        testCenterString();
        testIndexOfString();
        testGetFileContent();
        testFileExist();
        testStringToNumber();
        // testGetLineCount();
        println();
        println();
        pressEnterToContinue("des parametres");
    }

    // Fonction permettant d'afficher le menu de fin de partie (mort)
    void deathScreen(Game game){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/death.txt"));
        println();
        println();
        printlnSlow("La bombe a explosé, vous avez perdu !");
        println();
        printlnSlow("Statistiques:");
        printlnSlow("Modue Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        printlnSlow("Temps: " + game.player.time.getTimeSecs() + " secondes");
        printlnSlow("Score: " + game.player.score);
        println();
        println();
        pressEnterToContinue("tableau des scores");
    }

    // Fonction permettant d'afficher le menu de fin de partie (victoire)
    void defuseScreen(Game game){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/defuse.txt"));
        println();
        println();
        printlnSlow("La bombe a été désamorcée, vous avez gagné !");
        println();
        printlnSlow("Statistiques:");
        printlnSlow("Erreur: " + game.player.errors);
        printlnSlow("Temps: " + game.player.time.getTimeSecs() + " secondes");
        printlnSlow("Score: " + game.player.score);
        println();
        println();
        pressEnterToContinue("tableau des scores");
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions Menu              //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    void parameter(){
        boolean quit = false;
        while (!quit){
            int id = parameterMenu();
            if (id == 0){
                quit = true;
            } else if (id == 1){
                resetScoresBoard();
                printlnSlow("Réinitialisation des Scores: OK");
                delay(2000);
            } else if (id == 2){
                resetSettings();
                printlnSlow("Réinitialisation des Parametres: OK");
                delay(2000);
            } else if (id == 3){
                debugageTest();
            } else if (id == 4){
                printlnSlow("Nom du joueur : ");
                String name = validPseudo(readString());
                printlnSlow("Score : ");
                int score = readInt();
                addScore(name, score);
                scoresboard();
            } else if (id == 5){
                if (equals(settings[0][1], "true")){
                    settings[0][1] = "false";
                } else {
                    settings[0][1] = "true";
                }
                saveCSV(settings, "../ressources/csv/settings.csv");
            } else if (id == 6){
                printlnSlow("Vitesse du Text : (0 = instantané)");
                settings[3][1] = onlyInt(readString());
                slowSpeed = stringToNumber(settings[3][1]);
                saveCSV(settings, "../ressources/csv/settings.csv");
            }
        }
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //                 Modules                //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de définir le module en cours comme résolu
    void sucessModule(Game game, String msg){
        game.bombe.modules[game.bombe.focusModule - 1].resolved = true;
        game.bombe.nbModulesResolve++;
        game.player.score += game.bombe.modules[game.bombe.focusModule - 1].points;
        println(msg);
        delay(2000);
        game.bombe.focusModule = 0;
    }

    // Fonction permettant de d'ajouter une erreur
    void addError(Game game, String msg){
        game.player.errors++;
        game.player.score -= game.errorCost;
        println(msg);
        delay(2500);
    }

    // Fonction permettant de créer les modules de la bombe
    void initModules(Game game){
        for (int i = 0; i < game.bombe.nbModules; i++){
            String[][] moduleInfo = csvToTable("../ressources/modules/" + getRandomFile("../ressources/modules"));
            game.bombe.modules[i] = new Module();
            game.bombe.modules[i].name = moduleInfo[0][1];
            game.bombe.modules[i].points = stringToNumber(moduleInfo[1][1]);
            if (equals(game.bombe.modules[i].name, "Binaire")){
                game.bombe.modules[i].ip.ip = new int[]{randomInt(0, 255), randomInt(0, 255), randomInt(0, 255), randomInt(0, 255)};
                game.bombe.modules[i].ip.ipAnswer = ipToString(game.bombe.modules[i].ip.ip);
                game.bombe.modules[i].ip.ipBinary = ipBinary(game.bombe.modules[i].ip.ip);
                game.bombe.modules[i].ip.ipBinaryShow = ipBinaryToString(game.bombe.modules[i].ip.ipBinary);
            } else if (equals(game.bombe.modules[i].name, "Morse")){
                String[] words = new String[length(moduleInfo) - 2];
                for (int j = 0; j < length(words); j++){
                    words[j] = moduleInfo[j + 2][1];
                }
                game.bombe.modules[i].morse.world = stringLower(randomStringTable(words));
                game.bombe.modules[i].morse.morse = stringToMorse(game.bombe.modules[i].morse.world);
            } else if (equals(game.bombe.modules[i].name, "Fils")){
                for (int j = 0; j < length(game.bombe.modules[i].cables); j++){
                    game.bombe.modules[i].cables[j] = new Cable();
                    game.bombe.modules[i].cables[j].color = randomCharTable(new char[]{'R', 'B', 'J', 'N', 'V'});
                }
            }
        }
    }

    // -- // -- // -- // -- //
    // Fonctions du Cable
    // -- // -- // -- // -- //

    void showCable(Module cable){
        for (int i = 0; i < 4; i++){
            println(repeatString(length(cable.cables), "  | |"));
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < length(cable.cables); j++){
                if (!cable.cables[j].cut){
                    printSlow("  | |");
                } else{
                    printSlow("  / /");
                }
            }
            println();
        }
        for (int i = 0; i < 4; i++){
            println(repeatString(length(cable.cables), "  | |"));
        }
        println();
        printSlow(" ");
        for (int i = 0; i < length(cable.cables); i++){
            printSlow(" [" + cable.cables[i].color + "] ");
        }
        println();
        printSlow(" ");
        for (int i = 0; i < length(cable.cables); i++){
            printSlow(" [" + (i + 1) + "] ");
        }
    }

    void cutCable(Game game){
        printlnSlow("Quel cable couper ?");
        String[] answers = new String[length(game.bombe.modules[game.bombe.focusModule - 1].cables)];
        for (int i = 0; i < length(game.bombe.modules[game.bombe.focusModule - 1].cables); i++){
            answers[i] = "" + (i + 1);
        }
        int id = getAnswer(answers);
        if (game.bombe.modules[game.bombe.focusModule - 1].cables[id - 1].cut){
            printlnSlow("Ce cable est deja coupé !");
            delay(1000);
            cutCable(game);
        } else {
            Module moduleTemp = game.bombe.modules[game.bombe.focusModule - 1];
            int nbRed, nbBlue, nbGreen, nbYellow, nbBlack;
            nbRed = nbBlue = nbGreen = nbYellow = nbBlack = 0;
            int nbCable = length(moduleTemp.cables);
            for (int i = 0; i < nbCable; i++){
                if (moduleTemp.cables[i].color == 'R'){
                    nbRed++;
                } else if (moduleTemp.cables[i].color == 'B'){
                    nbBlue++;
                } else if (moduleTemp.cables[i].color == 'J'){
                    nbYellow++;
                } else if (moduleTemp.cables[i].color == 'N'){
                    nbBlack++;
                } else if (moduleTemp.cables[i].color == 'V'){
                    nbGreen++;
                }
            }
            moduleTemp.cables[id - 1].cut = true;
            if (moduleTemp.cables[id - 1].color == 'B' && nbRed > 2){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (id == nbCable && nbRed == 0){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (id == 1 && moduleTemp.cables[id - 1].color == 'V'){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (id == nbCable && moduleTemp.cables[id - 1].color == 'B'){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (moduleTemp.cables[id - 1].color == 'R' && nbRed > 1){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (nbRed > 5 || nbBlack > 5 || nbGreen > 5 || nbBlue > 5 || nbYellow > 5){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else if (nbRed >= 1 || nbBlack >= 1 || nbGreen >= 1 || nbBlue >= 1 || nbYellow >= 1){
                sucessModule(game, "Bravo, vous avez réussi le module Cable !");
            } else {
                addError(game, "Malheureusement, ce n'est pas le bon cable !");
                moduleTemp.cables[id - 1].cut = true;
            }
        }
    }

    // -- // -- // -- // -- //
    // Fonctions du Module Morse
    // -- // -- // -- // -- //

    String stringToMorse(String str){
        str = stringLower(onlyLetter(str));
        String morse = "";
        String[] morseTable = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] morseTableMorse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.."};
        for (int i = 0; i < length(str); i++){
            String letter = "" + charAt(str, i);
            int idx = indexOfString(morseTable, letter);
            morse += " " + morseTableMorse[idx];
        }
        return morse;
    }

    void enterWord(Game game){
        if (game.cheat){
            println(game.bombe.modules[game.bombe.focusModule - 1].morse.world);
        }
        printSlow("Entrer le mot de passe : ");
        String word = stringLower(onlyLetter(readString()));
        if (equals(word, game.bombe.modules[game.bombe.focusModule - 1].morse.world)){
            sucessModule(game, "Mot de Passe Corect !");
        } else {
            addError(game, "Mot de Passe Incorect !");
        }
    }

    // -- // -- // -- // -- //
    // Fonctions du Module Binaire
    // -- // -- // -- // -- //

    String ipToString(int[] ip){
        String str = "";
        for (int i = 0; i < length(ip); i++){
            str += ip[i];
            if (i != length(ip) - 1){
                str += ".";
            }
        }
        return str;
    }

    String[] ipBinary(int[] ip){
        String[] ipBinary = new String[length(ip)];
        for (int i = 0; i < length(ip); i++){
            ipBinary[i] = numberToBinary(ip[i]);
        }
        return ipBinary;
    }

    String ipBinaryToString(String[] ipBinary){
        String str = "";
        for (int i = 0; i < length(ipBinary); i++){
            if (ipBinary[i] == null && i == 0){
                str += "0000 0000";
            } else if (ipBinary[i] == null){
                str += " 0000 0000";
            } else if (i == 0){
                str += substring(ipBinary[i], 0, 4) + " " + substring(ipBinary[i], 4, 8);
            } else {
                str += " | " + substring(ipBinary[i], 0, 4) + " " + substring(ipBinary[i], 4, 8);
            }
        }
        return str;
    }

    void enterIP(Game game){
        if (game.cheat){
            println(game.bombe.modules[game.bombe.focusModule - 1].ip.ipAnswer);
        }
        printSlow("Entrer l'ip du Serveur : ");
        String ip = readString();
        if (equals(ip, game.bombe.modules[game.bombe.focusModule - 1].ip.ipAnswer)){
            sucessModule(game, "IP Serveur Corect !");
        } else {
            addError(game, "IP Serveur Incorect !");
        }
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Corps du Programme           //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    int selectModule(Game game) {
        System.out.print("\033[H\033[2J");
        println();
        println();
        printlnSlow("Module Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        println();
        printlnSlow("0 : Quitter");
        for (int i = 0; i < game.bombe.nbModules; i++){
            printlnSlow("Module n°" + (i + 1) + " : " + centerString(game.bombe.modules[i].name, 20) + " : " + getModuleState(game.bombe.modules[i].resolved));
        }
        println();
        printlnSlow("Quel module voulez vous resoudre ?");
        String[] answers = new String[game.bombe.nbModules+1];
        for (int i = 0; i <= game.bombe.nbModules; i++){
            answers[i] = "" + i;
        }
        int id = getAnswer(answers);
        if (id != 0 && game.bombe.modules[id - 1].resolved){
            printlnSlow("Ce module est deja resolu !");
            delay(1000);
            return selectModule(game);
        }
        return id;
    }

    String getModuleState(boolean state){
        if (state){
            return "Resolu";
        } else {
            return "Non Resolu";
        }
    }

    void playInterface(Game game){
        System.out.print("\033[H\033[2J");
        printlnSlow("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Bombe", 44));
        println();
        println();
        printlnSlow("Erreur: " + game.player.errors + " / 3");
        printlnSlow("Module Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        printlnSlow("Score: " + clamp((game.player.score + ((int) game.player.time.getTimeSecs() * -2)), 0, game.maxPoints));
        printlnSlow("Temps: " + game.player.time.getTimeSecs() + " secondes");
        println();
        if (game.bombe.focusModule == 0){
            for (int i = 0; i < game.bombe.nbModules; i++){
                printlnSlow("Module n°" + (i + 1) + " : " + centerString(game.bombe.modules[i].name, 20) + " : " + getModuleState(game.bombe.modules[i].resolved));
            }
        } else {
            println();
            printlnSlow("// -- // -- // -- // -- // -- // -- // -- //");
            println();
            println(centerString("Module : " + game.bombe.modules[game.bombe.focusModule - 1].name, 44));
            println();
            println();
            if (equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Fils")) {
                printlnSlow("Trouver le bon fil a coupé !");
                println();
                showCable(game.bombe.modules[game.bombe.focusModule - 1]);
            } else if (equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Binaire")){
                printlnSlow("Trouver l'ip de ce serveur : " + game.bombe.modules[game.bombe.focusModule - 1].ip.ipBinaryShow);
            } else if (equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Morse")){
                printlnSlow("Trouver me mot secret : " + game.bombe.modules[game.bombe.focusModule - 1].morse.morse);
            }
        }
        println();
        println();
        printlnSlow("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Manuel - Page " + game.manual.page, 44));
        println();
        println();
        println(getFileContent("../ressources/manual/" + game.manual.page + ".txt"));
        println();
        printlnSlow("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Que voulez-vous faire ?", 44));
        println();
        println();
        printlnSlow("0. Quitte la partie");
        printlnSlow("1. Manuel - Page Suivante");
        printlnSlow("2. Manuel - Page Précédente");
        if (game.bombe.focusModule == 0){
            printlnSlow("3. Bombe - Résoudre un Module");
        } else {
            printlnSlow("3. Bombe - Retour a la Bombe");
        }
        if (game.bombe.focusModule != 0 && equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Fils")){
            printlnSlow("4. Module - Couper un Cable");
        } else if (game.bombe.focusModule != 0 && equals(game.bombe.modules[game.bombe.focusModule -1].name, "Binaire")){
            printlnSlow("4. Module - Entrer l'ip du Serveur");
        } else if (game.bombe.focusModule != 0 && equals(game.bombe.modules[game.bombe.focusModule -1].name, "Morse")){
            printlnSlow("4. Module - Entrer le mot secret");
        }
        println();
        println();
        printSlow("Votre choix: ");
        String[] answers = new String[]{"0", "1", "2", "3"};
        if (game.bombe.focusModule != 0){
            answers = new String[]{"0", "1", "2", "3", "4"};
        }
        int choice = getAnswer(answers);
        if (choice == 0){
            game.quit = true;
        } else if (choice == 1){
            game.manual.page = clamp(game.manual.page + 1, 0, game.manual.nbPages);
        } else if (choice == 2){
            game.manual.page = clamp(game.manual.page - 1, 0, game.manual.nbPages);
        } else if (choice == 3 && game.bombe.focusModule == 0){
            game.bombe.focusModule = selectModule(game);
        } else if (choice == 3 && game.bombe.focusModule != 0){
            game.bombe.focusModule = 0;
        } else if (choice == 4 && equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Fils")){
            cutCable(game);
        } else if (choice == 4 && equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Binaire")){
            enterIP(game);
        } else if (choice == 4 && equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Morse")){
            enterWord(game);
        }
    }

    void play(){
        Game game = new Game();
        game.cheat = equals(settings[0][1], "true");
        game.maxPoints = stringToNumber(settings[1][1]);
        game.errorCost = stringToNumber(settings[2][1]);
        game.player.time.start();
        game.manual.nbPages = numberOfFiles("../ressources/manual") - 1;
        if (game.cheat){
            game.player.name = "DEBUG - TEST";
        } else {
            game.player.name = introduction();
        }
        initModules(game);
        System.out.print("\033[H\033[2J");
        while (game.player.errors < 3 && game.bombe.nbModulesResolve != game.bombe.nbModules && !game.quit){
            playInterface(game);
        }
        if (!game.cheat){
            addScore(game.player.name, clamp((game.player.score + ((int) game.player.time.getTimeSecs() * -2)), 0, game.maxPoints));
            game.player.time.stop();
            if (!game.quit){
                if (game.bombe.nbModulesResolve == game.bombe.nbModules){
                    defuseScreen(game);
                } else {
                    deathScreen(game);
                }
            }
            scoresboard();
        }
    }

    // Corps du programme
    void algorithm(){
        // println(stringToMorse("bonjour"));
        boolean fini = false;
        while (!fini){
            int id = mainMenu();
            if (id == 1){
                play();
            }
            else if (id == 2){
                scoresboard();
            }
            else if (id == 3){
                parameter();
            }
            else if (id == 4){
                fini = true;
            }
        }
    }
}