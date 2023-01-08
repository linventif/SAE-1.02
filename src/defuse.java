// -- // -- // -- // -- // -- // -- // -- //
//                                        //
//          Sommaire du Programme         //
//                                        //
// -- // -- // -- // -- // -- // -- // -- //
//                                        //
// Ligne 001 : Sommaire                   //
// Ligne 010 : Import des librairies      //
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

class Module{
    String name = "Module Sans Nom";
    String[] answers = {"A", "B", "C", "D"};
    String[] correctAnswers = {"A", "B", "C", "D"};
    boolean resolved = false;
}

class Bombe{
    boolean defused = false;
    int nbModules = 3;
    int nbModulesResolve = 0;
}

class Player{
    String name = "Joueur Sans Nom";
    int errors = 0;
    StopWatch time = new StopWatch();
    int score = 0;
}

class Game{
    Player player = new Player();
    Bombe bombe = new Bombe();
}

class defuse extends Program{
    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //            Fonctions de Debug          //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant d'afficher un tableau de String
    void printTable(String[] table){
        println();
        for (int i = 0; i < length(table, 1); i++){
            println("["+(i+1)+"]: "+table[i]);
            println();
        }
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
    boolean containsString(String str, char c){
        for (int i = 0; i < length(str); i++){
            if (str.charAt(i) == c){
                return true;
            }
        }
        return false;
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

    // Fonction permettant de créer un String de n caractères
    String repeatChar(int n, char c){
        String str = "";
        for (int i = 0; i < n; i++){
            str += c;
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

    // -- // -- // -- // -- // -- // -- // -- //
    //                                        //
    //           Fonctions Secondaires        //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de vérifier reinitialiser les scores
    void resetScoresBoard(){
        String[][] scoresboard = new String[11][3];
        scoresboard[0][0] = "Pseudo";
        scoresboard[0][1] = "Score";
        scoresboard[0][2] = "Date";
        saveCSV(scoresboard, "../ressources/scoresboard.csv");
    }

    // Fonction permettant de sauvegarder un score dans le tableau des scores
    void addScore(String pseudo, int score){
        String[][] scoresboard = csvToTable("../ressources/scoresboard.csv");
        int index = 1;
        boolean stop = false;
        if (score < 0){
            score = 0;
        }
        while (index <= 10 && !stop){
            println("SPECIFIC : " + scoresboard[index][1]);
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
            saveCSV(scoresboard, "../ressources/scoresboard.csv");
        }
        readString();
    }

    // void debug(){
    //     boolean fin = false;
    //     while (!fin){
    //         String answer = readString();
    //         if (equals(answer, "add_score"))
    //     }
    // }

    // Fonction permettant de mettre une chaine de caractère en minuscule
    String sringLower(String str){
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

    // Fonction permettant d'obtenir et de vérifier la réponse d'un utilisateur
    int getAnswer(String[] answers){
        boolean debug = false;
        String answer = sringLower(readString());
        while (!containsString(answers, answer) && !debug){
            println();
            println("Veuillez entrer une réponse valide ! ");
            print("Réponse Possible: ");
            printAnswers(answers);
            print(" > ");
            answer = sringLower(readString());
            if (equals(answer, "debug")){
                debug = true;
            }
        }
        return indexOfString(answers, answer) + 1;
    }

    // Fonction permettant de verifier si un pseudo est valide
    String validPseudo(String pseudo){
        while (length(pseudo) < 3 || length(pseudo) > 20 || containsString(pseudo, ' ')){
            println("Votre pseudo doit contenir entre 3 et 20 caractères et ne doit pas contenir d'espace !");
            print(" > ");
            pseudo = readString();
        }
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
        assertEquals(true, fileExist("../ressources", "test.txt"));
        assertEquals(true, fileExist("../ressources", "test.csv"));
        assertEquals(true, fileExist("../ressources", "scoresboard.csv"));
        assertEquals(false, fileExist("../ressources", "test.txtt"));
        assertEquals(false, fileExist("../ressources", "test.cvs"));
        assertEquals(false, fileExist("../ressources", "scoresboard.cvs"));
        println("\u001B[32m" + "Test Function : " + centerString("File Exist", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de getFileContent
    void testGetFileContent(){
        assertEquals("JE SUIS UN FICHIER DE TEST\n", getFileContent("../ressources/test.txt"));
        println("\u001B[32m" + "Test Function : " + centerString("Get File Content", 30) + " : Passed" + "\u001B[0m");
    }

    // Fonction permettant de tester la fonction de csvToTable
    void testCsvToTable(){
        String[][] table = csvToTable("../ressources/test.csv");
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
        println(getFileContent("../ressources/logo.txt"));
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
        println(getFileContent("../ressources/logo.txt"));
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
        println(getFileContent("../ressources/logo.txt"));
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
        println(getFileContent("../ressources/death.txt"));
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
        println(getFileContent("../ressources/defuse.txt"));
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
        pressEnterToContinue("principal");
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
            }
            else if (id == 2){
                println("Vous avez choisi de réinitialiser les parametres !");
            }
            else if (id == 3){
                debugageTest();
            }
            else if (id == 4){
                println("Nom du joueur : ");
                String name = readString();
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
    //           Corps du Programme           //
    //                                        //
    // -- // -- // -- // -- // -- // -- // -- //

    // Fonction permettant de lancer le jeu
    void play(){
        Game game = new Game();
        game.player.name = introduction();
        game.player.time.start();
        System.out.print("\033[H\033[2J");
        while (game.player.errors < 3 || game.bombe.defused){
            String answer = readString();
            if (equals(answer, "defuse")){
                game.bombe.defused = true;
            } else {
                game.player.errors++;
            }
        }
        //addScore(game.player.name, game.player.score + ((int) game.player.time.getTimeSecs() * -2));
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
        boolean fini = false;
        while (!fini) {
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