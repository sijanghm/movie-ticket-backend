//package com.sijan.ticketbooking.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.sql.Time;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "show_times")
//public class ShowTime {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany
//    @JoinColumn(name = "movie_id", referencedColumnName = "id")
//    private Movie movie;
//
////    @OneToMany
//    @JoinColumn(name = "theater_id", referencedColumnName = "id")
//    private Theater theater;
//
//    @Column(name="movie_show_time", nullable = false)
//    private Time movieShowTime;
//
//}
