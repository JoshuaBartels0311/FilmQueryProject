package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private static final String user = "student";
	private static final String pass = "student";

	@Override
	public Film findFilmById(int filmId) {
		String sqltxt = "Select film.* from film where film.id = ?";
		Film film = null;
		ResultSet filmResult;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, filmId);
			filmResult = stmt.executeQuery();

			if (filmResult.next()) {
				film = new Film(filmId, filmId, filmId, sqltxt, sqltxt);
				film.setId(filmResult.getInt("id"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setActors(findActorsByFilmId(filmId));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return film;

	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
	

		
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt = "Select id, first_name, last_name from actor where id =?";
			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if(actorResult.next()) {
				actor = new Actor(sqltxt, sqltxt, actorId);
				actor.setId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getNString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
	List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sqltxt = "Select actor.id, actor.first_name, actor.last_name from actor join film_actor on actor.id = film_actor.actor_id WHERE film_actor.film_id =? ";
			PreparedStatement stmt = conn.prepareStatement(sqltxt);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			while(filmResult.next()) {
				Actor actor = new Actor(sqltxt, sqltxt, filmId);
				actor.setId(filmResult.getInt("id"));
				actor.setFirstName(filmResult.getString("first_name"));
				actor.setLastName(filmResult.getString("last_name"));
				actors.add(actor);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return actors;
	}
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();

		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
		Film film = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet filmResult = stmt.executeQuery();

			while (filmResult.next()) {
				film = new Film(0, null, 0, sql, sql);

				film.setId(filmResult.getInt("id"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setActors(findActorsByFilmId(film.getId()));
				film.setLanguage(languageOfFilm(film.getId()));
				films.add(film);

			}
			conn.close();
			stmt.close();
			filmResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return films;
	}

	
	@Override
	public String languageOfFilm(int filmId) {
		if (filmId <= 0) {
			return null;
		}

		String sql = "SELECT language.name FROM film JOIN language ON film.language_id = language.id WHERE film.id = ?";
		String language = "";

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				language = rs.getString("language.name");
			}
			conn.close();
			stmt.close();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return language;

	
	}
	
	
}
