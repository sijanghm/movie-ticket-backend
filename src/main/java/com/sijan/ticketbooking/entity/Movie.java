package com.sijan.ticketbooking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name", nullable = false)
    private String movieName;

    @Column(nullable = false)
    private String description;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "is_showing", nullable = false)
    @JsonProperty("isShowing")
    private Boolean isShowing = true;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
    private List<ShowTime> movieShowTime;
}
