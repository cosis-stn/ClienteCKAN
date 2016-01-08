package br.gov.tesouro.fazenda.modelo;

import com.google.gson.annotations.SerializedName;

public class Rating {
	@SerializedName("rating average")
	private Integer rating_average;
	
	@SerializedName("rating count")
	private Integer rating_count;

	public Integer getRating_average() {
		return rating_average;
	}

	public void setRating_average(Integer rating_average) {
		this.rating_average = rating_average;
	}

	public Integer getRating_count() {
		return rating_count;
	}

	public void setRating_count(Integer rating_count) {
		this.rating_count = rating_count;
	}

}
