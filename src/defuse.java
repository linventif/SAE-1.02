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

class Module{
    String name = "Module Sans Nom";
    String id = "Aucun ID";
    String graphic = "Aucun Graphique";
    String[] answers = {"A", "B", "C", "D"};
    String[] correctAnswers = {"A", "B", "C", "D"};
    Cable[] cables = new Cable[5];
    boolean resolved = false;
    String manuel = "Aucun Manuel";
}

class Bombe{
    boolean defused = false;
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
}

class Defuse extends Program{
    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions de Debug          //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant d'enregistre dans un fichier debug
    void saveDebug(String[] content){
        saveCSV(new String[][]{content}, "../ressources/debug.csv");
    }

    // Fonction permettant d'enregistre dans un fichier debug
    void saveDebug(String content){
        saveCSV(new String[][]{{content}}, "../ressources/debug.csv");
    }

    // Fonction permettant d'enregistre dans un fichier debug
    void saveDebug(String[][] content){
        saveCSV(content, "../ressources/debug.csv");
    }

    // Fonction permettant d'afficher un tableau de String
    void printTable(String[] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            println("["+(i+1)+"]: "+table[i]);
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

    // Fonction permettant de convertir du morse en "string"
    String morseToString(String morse){
        String[] morseTable = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
        String[] morseTableMorse = {".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----", "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.", "-----"};
        for (int i = 0; i < length(morseTableMorse, 1); i++){
            morse = replace(morse, morseTableMorse[i], morseTable[i]);
        }
        return morse;
    }

    // Fonction permettant de convertir un nombre en binaire (String)
    String numberToBinary(int number){
        String binary = "";
        while (number > 0){
            binary = (number % 2) + binary;
            number /= 2;
        }
        return binary;
    }

    // Fonction permettant de calculer le score d'un joueur
    int calculateScore(Player player){
        return (100 - player.errors) * (int)player.time.getTimeSecs();
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

    int randomInt(int min, int max){
        return (int)(random() * (max - min + 1) + min);
    }

    // Fonction permettant de récupérer un fichier aléatoire dans un dossier
    String getRandomFile(String folder){
        String[] files = getAllFilesFromDirectory(folder);
        return files[randomInt(0, length(files) - 1)];
    }

    // Fonction permettant de récupérer une valeur aléatoire dans un tableau de char
    char getRandomChar(char[] chars){
        return chars[randomInt(0, length(chars) - 1)];
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
        String[][] settings = new String[2][2];
        settings[0][0] = "TEST";
        settings[0][1] = "1";
        settings[1][0] = "TEST2";
        settings[1][1] = "1";
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

    // void debug(){
    //     boolean fin = false;
    //     while (!fin){
    //         String answer = readString();
    //         if (equals(answer, "add_score"))
    //     }
    // }

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
            println("Veuillez entrer une réponse valide ! ");
            print("Réponse Possible: ");
            printAnswers(answers);
            print(" > ");
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
            println("Votre pseudo doit contenir entre 3 et 20 caractères et ne doit pas contenir que des lettres minuscules !");
            print(" > ");
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
        println("\u001B[32m" + "Test Function : " + centerString("Contains String", 30) + " : Passed" + "\u001B[0m");
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
        println("\u001B[32m" + "Test Function : " + centerString("Repeat Char", 30) + " : Passed" + "\u001B[0m");
    }

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
        println("\u001B[32m" + "Test Function : " + centerString("Center String", 30) + " : Passed" + "\u001B[0m");
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
        println("\u001B[32m" + "Test Function : " + centerString("Index Of String", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de fileExist
    void testFileExist(){
        assertEquals(true, fileExist("../ressources/test", "test.txt"));
        assertEquals(true, fileExist("../ressources/test", "test.csv"));
        assertEquals(true, fileExist("../ressources/csv", "scoresboard.csv"));
        assertEquals(false, fileExist("../ressources/test", "test.txtt"));
        assertEquals(false, fileExist("../ressources/test", "test.cvs"));
        assertEquals(false, fileExist("../ressources/csv", "scoresboard.cvs"));
        println("\u001B[32m" + "Test Function : " + centerString("File Exist", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de getFileContent
    void testGetFileContent(){
        assertEquals("JE SUIS UN FICHIER DE TEST\n", getFileContent("../ressources/test/test.txt"));
        println("\u001B[32m" + "Test Function : " + centerString("Get File Content", 30) + " : Passed" + "\u001B[0m");
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
        println("\u001B[32m" + "Test Function : " + centerString("Csv To Table", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de tableToCsv
    void testStringToNumber(){
        for (int i = 0; i < 10; i++){
            assertEquals(i, stringToNumber("" + i));
        }
        println("\u001B[32m" + "Test Function : " + centerString("String To Number", 30) + " : Passed" + "\u001B[0m");
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions d'affichage        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    void pressEnterToContinue(String menu){
        print("Appuyez sur entrée pour continuer vers le menu " + menu + ".");
        readString();
    }

    // Fonction permettant d'afficher le menu d'introduction
    String introduction(){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        println("Bienvenue dans le jeu Defuse !");
        println();
        println("Votre mission est de désamorcer une bombe avant qu'elle n'explose.");
        println("Pour cela vous devrez résoudre chaque module de la bombe.");
        println("Chaque module est différent, vous devrez donc trouver la bonne méthode pour le désamorcer.");
        println("Pour votre mission vous aurez un manuel d'utilisation de la bombe.");
        println("Il vous sera utile pour résoudre les modules sans faire d'erreur.");
        println("Cependant la bombe est instable, si vous faite 3 erreurs, la bombe explosera.");
        println();
        println("Bonne chance !");
        println();
        println();
        print("Pour commencer entrez votre nom d'agent: ");
        return validPseudo(readString());
    }

    // Fonction permettant d'afficher le menu principal
    int mainMenu(){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        println("1 - Lancez le Jeu");
        println("2 - Tableau des Scores");
        println("3 - Parametres & Debugage");
        println("4 - Quitter");
        println();
        println();
        print("Que voulez-vous faire ? ");
        return getAnswer(new String[]{"1", "2", "3", "4"});
    }

    // Fonction permettant d'afficher le menu des parametres
    int parameterMenu(){
        System.out.print("\033[H\033[2J");
        println(getFileContent("../ressources/ascii/logo.txt"));
        println();
        println();
        println("1 - Réinitialiser les Scores");
        println("2 - Réinitialiser les Parametres");
        println("3 - Test de Debugage");
        println("4 - Ajouter un Score");
        println("5 - Retour");
        println();
        println();
        print("Que voulez-vous faire ? ");
        return getAnswer(new String[]{"1", "2", "3", "4", "5"});
    }

    // Fonction permettant d'afficher le tableau des scores
    void scoresboard(){
        System.out.print("\033[H\033[2J");
        println();
        println();
        println("Voici le tableau des scores:");
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
        println("La bombe a explosé, vous avez perdu !");
        println();
        println("Statistiques:");
        println("Modue Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        println("Temps: " + game.player.time.getTimeSecs() + " secondes");
        println("Score: " + game.player.score);
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
        println("La bombe a été désamorcée, vous avez gagné !");
        println();
        println("Statistiques:");
        println("Erreur: " + game.player.errors);
        println("Temps: " + game.player.time.getTimeSecs() + " secondes");
        println("Score: " + game.player.score);
        println();
        println();
        pressEnterToContinue("tableau des scores");
    }

    void wait(String msg, int time){
        System.out.print("\033[H\033[2J");
        println();
        println();
        print(msg);
        StopWatch timer = new StopWatch();
        timer.start();
        delay(time * 1000);
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
            if (id == 1){
                resetScoresBoard();
                wait("Réinitialisation des Scores: OK", 2);
            }
            else if (id == 2){
                resetSettings();
                wait("Réinitialisation des Parametres: OK", 2);
            }
            else if (id == 3){
                debugageTest();
            }
            else if (id == 4){
                println("Nom du joueur : ");
                String name = validPseudo(readString());
                println("Score : ");
                int score = readInt();
                addScore(name, score);
                scoresboard();
            }
            else if (id == 5){
                quit = true;
            }
        }
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //                 Modules                //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    void showCable(Module cable){
        for (int i = 0; i < 4; i++){
            println(repeatString(length(cable.cables), "  | |"));
        }
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < length(cable.cables); j++){
                if (cable.cables[j].cut){
                    print("  / /");
                }
                else{
                    print("  | |");
                }
            }
            println();
        }
        for (int i = 0; i < 4; i++){
            println(repeatString(length(cable.cables), "  | |"));
        }
        println();
        print(" ");
        for (int i = 0; i < length(cable.cables); i++){
            print(" [" + cable.cables[i].color + "] ");
        }
        println();
        print(" ");
        for (int i = 0; i < length(cable.cables); i++){
            print(" [" + (i + 1) + "] ");
        }
    }

    boolean cutCable(Module cable){
        showCable(cable);
        println("Quel cable couper ?");
        String[] answers = new String[length(cable.cables)];
        for (int i = 0; i < length(cable.cables); i++){
            answers[i] = "" + (i + 1);
        }
        int id = getAnswer(answers);
        if (cable.cables[id - 1].cut){
            println("Ce cable est deja coupé !");
            delay(1000);
            cutCable(cable);
        } else {
            cable.cables[id - 1].cut = true;
            showCable(cable);
            println("Fils coupé !");
            if (cable.cables[id - 1].color == 'R'){
                println("Bravo, vous avez resolu le module !");
                return true;
            } else if (cable.cables[id - 1].color == 'V'){
                println("Bravo, vous avez resolu le module !");
                return true;
            } else if (cable.cables[id - 1].color == 'B'){
                println("Bravo, vous avez resolu le module !");
                return true;
            } else {
                println("Malheureusement, ce n'est pas le bon cable !");
                cable.cables[id - 1].cut = true;
                delay(2500);
                cutCable(cable);
            }
        }
        return false;
    }

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Corps du Programme           //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    int selectModule(Game game) {
        println();
        println();
        println("Module Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        println();
        for (int i = 0; i < game.bombe.nbModules; i++){
            println("Module n°" + (i + 1) + " : " + centerString(game.bombe.modules[i].name, 20) + " : " + getModuleState(game.bombe.modules[i].resolved));
        }
        println();
        println("Quel module voulez vous resoudre ?");
        int length = game.bombe.nbModules - game.bombe.nbModulesResolve;
        String [] answers = new String[length];
        for (int i = 0; i < length(game.bombe.modules); i++){
            if (!game.bombe.modules[i].resolved){
                answers[i] = "" + (i + 1);
            }
        }
        return getAnswer(answers);
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
        println("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Bombe", 44));
        println();
        println();
        println("Erreur: " + game.player.errors + " / 3");
        println("Module Resolu: " + game.bombe.nbModulesResolve + " / " + game.bombe.nbModules);
        println();
        if (game.bombe.focusModule == 0){
            for (int i = 0; i < game.bombe.nbModules; i++){
                println("Module n°" + (i + 1) + " : " + centerString(game.bombe.modules[i].name, 20) + " : " + getModuleState(game.bombe.modules[i].resolved));
            }
        } else if (equals(game.bombe.modules[game.bombe.focusModule - 1].name, "Fils")){
            println();
            println("// -- // -- // -- // -- // -- // -- // -- //");
            println();
            println(centerString("Module : " + game.bombe.modules[game.bombe.focusModule].name, 44));
            println();
            println();


            Module cable = new Module();
            for (int i = 0; i < 5; i++){
                cable.cables[i] = new Cable();
                cable.cables[i].color = getRandomChar(new char[]{'R', 'V', 'B', 'M', 'J'});
            }
            showCable(cable);
            //cutCable(cable);
        }
        println();
        println();
        println("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Manuel - Page " + game.manual.page, 44));
        println();
        println();
        println(getFileContent("../ressources/manual/" + game.manual.page + ".txt"));
        println();
        println("// -- // -- // -- // -- // -- // -- // -- //");
        println();
        println(centerString("Que voulez-vous faire ?", 44));
        println();
        println();
        println("1. Manuel - Page Suivante");
        println("2. Manuel - Page Précédente");
        if (game.bombe.focusModule == 0){
            println("3. Bombe - Résoudre un Module");
        } else {
            println("3. Bombe - Retour a la Bombe");
        }
        if (equals(game.bombe.modules[game.bombe.focusModule].name, "Fils")){
            println("4. Bombe - Couper un Cable");
        }
        println();
        println();
        print("Votre choix: ");
        int choice = getAnswer(new String[]{"1", "2", "3"});
        if (choice == 1){
            game.manual.page = clamp(game.manual.page + 1, 0, game.manual.nbPages);
        } else if (choice == 2){
            game.manual.page = clamp(game.manual.page - 1, 0, game.manual.nbPages);
        } else if (choice == 3 && game.bombe.focusModule == 0){
            game.bombe.focusModule = selectModule(game);
        } else if (choice == 3 && game.bombe.focusModule != 0){
            game.bombe.focusModule = 0;
        } else if (choice == 4 && equals(game.bombe.modules[game.bombe.focusModule].name, "Fils")){
            Module cable = game.bombe.modules[game.bombe.focusModule];
            cutCable(cable);
        }
    }

    // Fonction permettant de lancer le jeu
    void play(){
        Game game = new Game();
        game.player.name = introduction();
        game.player.time.start();
        game.manual.nbPages = numberOfFiles("../ressources/manual") - 1;
        for (int i = 0; i < game.bombe.nbModules; i++){
            String[][] moduleInfo = csvToTable("../ressources/modules/" + getRandomFile("../ressources/modules"));
            game.bombe.modules[i] = new Module();
            game.bombe.modules[i].name = moduleInfo[0][1];
            // game.bombe.modules[i].graphic = getFileContent("../ressources/ascii/" + moduleInfo[1][1]);
            // game.bombe.modules[i].id = moduleInfo[2][1];
        }
    // // String[] answers = {"A", "B", "C", "D"};
    // // String[] correctAnswers = {"A", "B", "C", "D"};
        System.out.print("\033[H\033[2J");
        while (game.player.errors < 3 && !game.bombe.defused){
            playInterface(game);
        }
        addScore(game.player.name, game.player.score + ((int) game.player.time.getTimeSecs() * -2));
        game.player.time.stop();
        if (game.bombe.defused){
            defuseScreen(game);
        } else {
            deathScreen(game);
        }
        scoresboard();
    }

    // Corps du programme
    void algorithm(){
        print(morseToString(".... . .-.. .-.. --- / .-- --- .-. .-.. -.."));
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

/*


### Page n°0

Manuel de désamorçage de bombe

Dans ce manuel vous trouveraus toutes les information

nécéssaire pour désamorcer une bombe.

Vous trouverez également des informations sur les

différents modules de la bombe.

Pour naviguer dans le manuel, utilisez les commandes

### Page n°1

Instrunction du Module : Fils

Votre objectif est de coupé le bon fil !

les fil on des couleur indiquer par des lettre (r, v, b, j, o, g)

Si il n'y a qu'un seul fil rouge et qu'il est au bord droit, coupé le fils vert.
Si il n'y a au moins deux fil vert voisin, coupé le fils vert de gauche.
Si il n'y a qu'un seul fil jaune, coupé le fils rouge.
Si il y a plus de 3 couleur de fils, coupé le fils rouge
Si il y a pas de fils bleu, coupé le fils jaune.
Si il y a plus de 2 fil vert, coupé le fils orange.
Si aucune des solution précédente n'est valid coupé le fil du millieu.

### Page n°2

Instruction du Module : Binaires

Votre objectif est de trouver le code secret.

Pour cela, il vous suffit de convertir chaque chiffre du nombre binaire en décimal.

0000 0001 = 1
0000 0010 = 2
0000 0011 = 3
[...]
0000 1000 = 8
0000 1111 = 15
0011 0000 = 48
1111 1111 = 255

### Page n°3

Instruction du Module : Morse

Votre objectif est de trouver le mot de passe.

Pour cela, il vous suffit de convertir chaque lettre du mot en morse.

A = .-    B = -...  C = -.-.  D = -..   E = .
F = ..-.  G = --.   H = ....  I = ..    J = .---
K = -.-   L = .-..  M = --    N = -.    O = ---
P = .--.  Q = --.-  R = .-.   S = ...   T = -
U = ..-   V = ...-  W = .--   X = -..-  Y = -.--
Z = --..

BONJOUR = -... --- -. -. --- --- ..- .-. --- .-.
LINVENTIF = .-.. .. -. ... - .. -. - .. .. - .... .. -.

### Page n°4

Instruction du Module : Terminal

Votre objectif est de supprimer le fichier qui déclanche la bombe.

Pour cela, il vous devez utuliser les commandes suivantes :

cd : Permet de changer de dossier
rm : Permet de supprimer un fichier
ls : Permet de lister les fichiers d'un dossier
pwd : Permet de savoir dans quel dossier on se trouve

*/