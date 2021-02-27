package com.skilldistillery.filmquery.app;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
  
  DatabaseAccessor db = new DatabaseAccessorObject();

  public static void main(String[] args) {
    FilmQueryApp app = new FilmQueryApp();
//    app.test();
    app.launch();
    app.menuChoice();
  }

//  private void test() {
//    Film film = db.findFilmById(1);
//    System.out.println(film);
//  }

  private void launch() {
    Scanner input = new Scanner(System.in);
    
    startUserInterface(input);
    
    input.close();
  }

  private void startUserInterface(Scanner input) {
    boolean quit = true;
    System.out.println("*--------------------*");
    System.out.println("|                    |"); 
    System.out.println("|                    |"); 
    System.out.println("|  WELCOME TO MYSQL  |"); 
    System.out.println("|   Film DataBase    |"); 
    System.out.println("|      Project!      |"); 
    System.out.println("|                    |"); 
    System.out.println("|                    |"); 
    System.out.println("*--------------------*");
    
    
    while(quit) {
    	menuChoice();
    	
    	int usersChoice = input.nextInt();
    	switch(usersChoice) {
    	case 1:
    		pickFilmById(input);
    		break;
    	case 2:
			pickFilmByKeyword(input);
			break;
		case 3:
			System.out.println("Well I hope you found what you were looking for! Please come back again!.");
			quit = false;
			break;
		}
    	}
    }

private void menuChoice() {
	System.out.println("\nChoose how you would like to find your film");
	System.out.println("\nYou may choose option 1,2, or 3");
	System.out.println("**************************************************");
	System.out.println("* 1. Find a film by the film ID.                 *");
	System.out.println("*                                                *");
	System.out.println("* 2. Find a film by using a keyword.             *");
	System.out.println("*                                                *");
	System.out.println("* 3. You may choose option 3 to exit the program.*");
	System.out.println("*                                                *");
	System.out.println("*                                                *");
	System.out.println("**************************************************");
}

private void pickFilmByKeyword(Scanner input) {
	System.out.println("Enter a keyword to search films by: ");

	try {
		String userInputKeyword = input.next();
		List<Film> listOfFilms = db.findFilmByKeyword(userInputKeyword);
		if (listOfFilms.size() > 0) {
			for (Film film3 : listOfFilms) {
				System.out.println(film3 + " ");

			}
		} else {
			System.err.println("Sorry. There is no film that exists in our database with that keyword.");
		}
	} catch (Exception e) {
		System.err.println("Sorry, You have put in a unrecognizable selection.");
		input.next();
		startUserInterface(input);
	}
}	


private void pickFilmById(Scanner input) {
	System.out.println("Enter the film's id: ");

	try {
		int filmId = input.nextInt();
		Film selectFilm = db.findFilmById(filmId);
		if (selectFilm != null) {
			System.out.println(selectFilm);
		} else {
			System.err.print("Sorry, there is no film with that ID in our database. ");
		}
	} catch (Exception e) {
		System.err.println("Sorry, You have put in a unrecognizable selection.");
		input.next();
		startUserInterface(input);
	}

}	
}
  

