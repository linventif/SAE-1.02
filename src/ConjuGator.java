import extensions.File;

class ConjuGator extends Program{

    final String NOM = "ConjuGator";
    final String DESSIN = "../ressources/alligator.txt";
    
    final String[] PRONOMS = new String[]{"Je","Tu","Il","Nous","Vous","Ils"};
    final String[] PRONOMS_3EME_SIN = new String[]{"Il","Elle","On"};
    final String[] PRONOMS_3EME_PLU = new String[]{"Ils","Elles"};
    final String[] CONJUGAISONS_1ER = new String[]{"E","ES","E","ONS","EZ","ENT"};
    final String[] VERBES_1ER = new String[]{"AIMER","DÉVORER","JOUER", "MARCHER","PARLER", "RIGOLER"};

    final int TEMPS_PAUSE = 500;

    String prenom = "Élève";
    
    //Fonction principale
    void algorithm(){
	int score = 0;
	boolean perdu = false;
	int pronomNum;
	String pronom, verbe;
	clearScreen();
	println("==========");
	println("ConjuGator");
	println("==========");
	println();
	print("Appuyer sur entrée pour commencer à jouer");
	readString();
	clearScreen();
	prenom = readStringNonVide("Entrez votre prénom");
	clearScreen();
	afficherFichier(DESSIN);
	conjugatorParle("Bonjour " + prenom);
	conjugatorParle("Je suis "+ NOM +", l'alligator qui mange les enfants qui font des erreurs de conjugaison");
	conjugatorParle("Voyons voir si tu seras mon prochain repas...");
	while(!perdu){
	    pronomNum = randomIdx(PRONOMS);
	    verbe = random(VERBES_1ER);
	    pronom = recupererPronom(pronomNum, verbe);
	    String reponse = readStringNonVide(verbe + " : " + pronom + "...");	    
	    if (estValide(verbe,pronomNum,reponse)){
		score++;
		conjugatorParle("Bonne réponse");
	    }else{
		conjugatorParle("Mauvaise réponse");
		conjugatorParle("La bonne réponse était : " + bonneReponse(verbe,pronomNum));
		perdu = true;
	    }
	}	
	conjugatorParle(score + " bonne(s) réponse(s)");
	conjugatorParle("À table ! ");
    }

    //Procédure d'affichage des phrases dites par la ou le prof suivies d'un temps de pause
    void conjugatorParle(String phrase){
	println(NOM + " : " + phrase);
	delay(TEMPS_PAUSE);
    }
    
    //renvoie un indice au hasard pour un tableau donné
    int randomIdx(String[] tab){
	return (int) (random() * length(tab));
    }

    //renvoie un élément au hasard dans un tableau de chaînes de caractères donné
    String random(String[] tab){
	return tab[randomIdx(tab)];
    }

    //retourne la chaîne de caractères saisie par l'utilisateur après l'avoir mise en majuscule
    //redemande une autre chaîne en cas de chaîne vide
    String readStringNonVide(String txt){
	String res;
	do{
	    conjugatorParle(txt);
	    print(prenom + " : ");
	    res = readString();
	}while(length(res)<1);
	return res;
    }

    //Permet d'obtenir le pronom à partir de son numéro (entre 0 et 5) et d'un verbe donné. Adapte le Je en J' dans le cas d'un verbe commençant par une voyelle
    String recupererPronom(int numero, String verbe){
	String resultat	= PRONOMS[numero];
	if (equals(resultat,"Il")){
	    resultat = random(PRONOMS_3EME_SIN);
	}else if (equals(resultat,"Ils")){
	    resultat = random(PRONOMS_3EME_PLU);
	}else if (equals(resultat,"Je") && estVoyelleMaj(charAt(verbe,0))){
	    resultat = "J'";	    
	}
	return resultat;
    }
    
    //Indique si un caractère est une voyelle majuscule
    boolean estVoyelleMaj(char c){
	return c == 'A' || c == 'E' || c == 'I'
	    || c == 'O' || c == 'U' || c == 'Y';
    }

    //Calcule et retourne la bonne réponse pour une conjugaison donnée
    String bonneReponse(String verbe, int numero){
	return substring(verbe,0,length(verbe)-2)+CONJUGAISONS_1ER[numero];
    }

    //Indique si une réponse est bonne étant donné le verbe à conjugué et le numero de personne
    boolean estValide(String verbe, int numero, String reponse){
	return equals(toUpperCase(reponse),bonneReponse(verbe,numero));
    }
    
    //Affichage du contenu d'un fichier texte d'après son chemin
    void afficherFichier(String chemin){

	File unTexte = newFile(chemin);

	//Stockage dans une variable de la ligne suivante dans le fichier

	while(ready(unTexte)){
	    //affichage du contenu de la ligne suivante
	    println(readLine(unTexte));
	}
    }

    /*******
     *TESTS
     ******/
    //Commenter ou renommer void algorithm() (en haut) pour qu'ils soient exécutés

    void testEstVoyelleMaj(){
	String voyelles = "AEIOUY";
	for (int i=0;i<length(voyelles);i++){
	    assertEquals(true,estVoyelleMaj(charAt(voyelles,i)));
	}
	assertEquals(false,estVoyelleMaj('a'));
	assertEquals(false,estVoyelleMaj('Z'));
    }

    void testEstValide(){
	assertEquals(true,estValide("JOUER",0,"JoUE"));
	assertEquals(true,estValide("PARLER",1,"parles"));
	assertEquals(true,estValide("DÉVORER",4,"déVOrez"));
	assertEquals(false,estValide("AIMER",5,"mangent"));
    }
}
