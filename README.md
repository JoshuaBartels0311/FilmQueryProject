# Film Query Project


## Description
Using the FilmQueryApp class, the app is able to query the database "sdvid", which has multiples tables. It uses the film, language, film_actor, and actor tables. The user is shown a menu to choose from. The application calls on a class called DatabaseAccessorObject which has a mySQL driver and connection to "sdvid" with JDBC API methods. The user is shown which films meet their criteria with a system output of film title, year, rating, description, language, and its list of actors.


## Technologies used
* Technologies Used
* Java
* mySQL
* Interfaces, OO, Polymorphism
* EER Diagrams
* JDBC via java.sql library
* Maven, pom.xml file, with mySQL driver


## Lesson Learned

* Being able to use JDBC API methods with a mySQL driver to access a database.
* I used PreparedStatements to help avoid SQL Injection.
