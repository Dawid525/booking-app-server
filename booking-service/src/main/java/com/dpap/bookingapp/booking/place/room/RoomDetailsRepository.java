//package com.dpap.bookingapp.booking.room;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public class RoomDetailsRepository {
//    private final JdbcTemplate jdbcTemplate;
//
//    public RoomDetailsRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public void save(RoomDetails roomDetails) {
//        this.jdbcTemplate.update(
//                """
//                        INSERT INTO room_details(id, name, description, image_url, room_id, facilities)
//                        VALUES((SELECT nextval('seq_room_details')),?,?,?,?,?)
//                        """,
//                roomDetails.getName(),
//                roomDetails.getDescription(),
//                roomDetails.getImageUrl(),
//                roomDetails.getRoomId(),
//                roomDetails.getFacilities()
//        );
//    }
//
//    public List<RoomDetails> findByRoomIds(List<Long> roomIds) {
//        return jdbcTemplate.query(
//                """
//                        SELECT * FROM room_details WHERE room_id IN (?)
//                        """,
//                (rs, num) ->
//                        new RoomDetails(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("description"),
//                                rs.getString("image_url"),
//                                rs.getString("facilities")
//                        ),
//                roomIds);
//    }
//
//    public RoomDetails findById(Long id) {
//        return jdbcTemplate.query(
//                """
//                        SELECT * FROM room_details WHERE id = ?
//                        """,
//                (rs, num) ->
//                        new RoomDetails(
//                                rs.getLong("id"),
//                                rs.getString("name"),
//                                rs.getString("description"),
//                                rs.getString("image_url"),
//                                rs.getString("facilities")
//                        ),
//                id).get(0);
//    }
//}
