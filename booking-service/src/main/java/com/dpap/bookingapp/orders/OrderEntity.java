//package com.dpap.bookingapp.orders;
//
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//@Entity
//@Table(name = "orders")
//public class OrderEntity {
//    @Id
//    @SequenceGenerator(name = "places_id_seq", sequenceName = "seq_places", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "places_id_seq")
//    private Long id;
//    @Convert(converter = LongListConverter.class)
//    @Column(name = "reservationids", nullable = false)
//    private List<Long> reservationids = new ArrayList<>();
//
//    public OrderEntity(Long id, List<Long> reservationids) {
//        this.id = id;
//        this.reservationids = reservationids;
//    }
//
//    public OrderEntity() {
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        OrderEntity that = (OrderEntity) o;
//        return Objects.equals(id, that.id) && Objects.equals(reservationids, that.reservationids);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, reservationids);
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public List<Long> getReservationids() {
//        return reservationids;
//    }
//
//    public void setReservationids(List<Long> reservationids) {
//        this.reservationids = reservationids;
//    }
//}
