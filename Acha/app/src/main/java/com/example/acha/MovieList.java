package com.example.acha;

public class MovieList {
  private String Title;
  private String Genre;
  private String Rating;

  /*public MovieList(String Title, String Genre, String Rating){
    this.Title = Title;
    this.Genre = Genre;
    this.Rating = Rating;
  }*/

  public String getTitle() {
    return Title;
  }

  public void setTitle(String title) {
    this.Title = title;
  }

  public String getGenre() {
    return Genre;
  }

  public void setGenre(String genre) {
    this.Genre = genre;
  }

  public String getRating() {
    return Rating;
  }

  public void setRating(String rating) {
    this.Rating = rating;
  }
}
