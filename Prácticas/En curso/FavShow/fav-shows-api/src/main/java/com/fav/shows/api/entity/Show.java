package com.fav.shows.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Show {

	public Show(String show_id, String type, String title, String director, String cast, String country,
			String date_added, String release_year, String rating, String duration, String listed_in,
			String description) {
		this.show_id = show_id;
		this.type = type;
		this.title = title;
		this.director = director;
		this.cast = cast;
		this.country = country;
		this.date_added = date_added;
		this.release_year = release_year;
		this.rating = rating;
		this.duration = duration;
		this.listed_in = listed_in;
		this.description = description;
	}

	@Id
	@Column(name="showid",unique=true,nullable=false)
	private String show_id;
	private String type;
	private String title;
	private String director;
	private String cast;
	private String country;
	private String date_added;
	private String release_year;
	private String rating;
	private String duration;
	private String listed_in;
	private String description;

	@Override
	public String toString() {
		return "Show [show_id=" + show_id + ", type=" + type + ", title=" + title + ", director=" + director + ", cast="
				+ cast + ", country=" + country + ", date_added=" + date_added + ", release_year=" + release_year
				+ ", rating=" + rating + ", duration=" + duration + ", listed_in=" + listed_in + ", description="
				+ description + "]";
	}

}
