//package com.dpap.bookingapp.booking.room;
//
//import com.dpap.bookingapp.booking.room.dto.RoomFilter;
//import com.dpap.bookingapp.booking.room.dto.RoomId;
//import io.vavr.control.Option;
//import io.vavr.control.Try;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//import static io.vavr.control.Option.none;
//import static io.vavr.control.Option.of;
//
//@Component
//
//public class RoomRepository implements RoomDatabase {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public RoomRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Option<Room> findRoomById(RoomId roomId) {
//        return Try
//                .ofSupplier(
//                        () -> of(
//                                jdbcTemplate.queryForObject(
//                                        "SELECT r.* FROM rooms r WHERE r.id = ?",
//                                        roomRowMapper(),
//                                        roomId.getId()
//                                )
//                        )
//                )
//                .getOrElse(none());
//    }
//
//    @Override
//    public List<Room> findAllByPlaceId(Long placeId) {
//        return jdbcTemplate.query("select * from rooms where place_id = ?",
//                roomRowMapper(),
//                placeId
//        );
//    }
//
//    @Override
//    public List<Room> findAll() {
//        return jdbcTemplate.query(
//                "select * from rooms",
//                roomRowMapper()
//        );
//    }
//
//    @Override
//    public List<Room> findAllByVoivodeship(String voivodeship) {
//        return jdbcTemplate.query(
//                "SELECT * FROM rooms r JOIN places p on r.place_id = p.id WHERE p.city LIKE ?",
//                roomRowMapper(),
//                voivodeship
//        );
//    }
//
//    @Override
//    public List<Room> findAllByCity(String city) {
//        return jdbcTemplate.query(
//                "SELECT * FROM rooms r JOIN places p on r.place_id = p.id WHERE p.city LIKE ?",
//                roomRowMapper(),
//                city
//        );
//    }
//
//    @Override
//    public List<Room> findAllByCityAndStreet(String city, String street) {
//        return jdbcTemplate.query(
//                "SELECT * FROM rooms r JOIN places p on r.place_id = p.id WHERE p.city LIKE ? AND p.street LIKE ?",
//                roomRowMapper(),
//                city, street
//        );
//    }
//
//    @Override
//    public List<Room> findAllByFilters(RoomFilter roomFilter) {
//        System.out.println(roomFilter);
//        StringBuilder query = new StringBuilder(
//                " SELECT * FROM rooms r JOIN places p on r.place_id = p.id WHERE ");
//        if (roomFilter.isEmpty()) {
//            return findAll();
//        }
//        if (roomFilter.getPlaceId().isPresent()) {
//            return findAllByPlaceId(roomFilter.placeId());
//        }
//        if (roomFilter.getCapacity().isPresent()) {
//            query.append("r.capacity").append(" = ").append(roomFilter.capacity()).append(" AND ");
//        }
//        if (roomFilter.getMinPricePerNight().isPresent()) {
//            query.append("r.price_per_night").append(" >= ").append(roomFilter.minPricePerNight()).append(" AND ");
//        }
//        if (roomFilter.getMaxPricePerNight().isPresent()) {
//            query.append("r.price_per_night").append(" <= ").append(roomFilter.maxPricePerNight()).append(" AND ");
//        }
//        if (roomFilter.getState().isPresent()) {
//            query.append("r.state").append(" = '").append(roomFilter.state().name()).append("' AND ");
//        }
//
//        query.replace(query.lastIndexOf(" AND"), query.lastIndexOf(" AND") + 4, "");
//        query.append(" ORDER BY r.place_id");
//        System.out.println(query);
//        return jdbcTemplate.query(
//                query.toString(),
//                roomRowMapper()
//        );
//    }
//
//    @Override
//    public void updateState(Long id, RoomState state) {
//        jdbcTemplate.update("UPDATE rooms SET state ='?' WHERE id = ?", state.name(), id);
//    }
//
//    @Override
//    public void updateRoom(Room room) {
//        jdbcTemplate.update("UPDATE rooms SET description = ?, name = ?, price_per_night = ?, capacity = ?, facilities =?::jsonb WHERE id = ?",
//                room.getDescription(),
//                room.getName(),
//                room.getPricePerNight(),
//                room.getCapacity(),
//                room.getFacilities(),
//                room.getId().getId()
//        );
//    }
//
//    @Override
//    public RoomId addRoom(Room room) {
//        var id = jdbcTemplate.queryForObject("SELECT nextval('seq_rooms')", Long.class);
//        if (id != null) {
//            jdbcTemplate.update("INSERT INTO rooms VALUES(?,?,?,?,?,?::jsonb,?,?)",
//                    id,
//                    room.getCapacity(),
//                    room.getState().name(),
//                    room.getPlaceId(),
//                    room.getPricePerNight(),
//                    room.getFacilities(),
//                    room.getName(),
//                    room.getDescription()
//            );
//            return RoomId.fromLong(id);
//        }
//        throw new RuntimeException("SEQUENCE ERROR");
//    }
//
//    public void assignRoomToPlace(Long placeId, Long id) {
//        jdbcTemplate.update("UPDATE rooms SET place_id = ? WHERE id = ?", placeId, id);
//    }
//
//    public RoomState fetchRoomState(RoomId roomId) {
//        return this.jdbcTemplate.queryForObject(
//                "SELECT state FROM rooms where id = ?",
//                (rs, rowNum) -> RoomState.valueOf(rs.getString("state")),
//                roomId.getId()
//        );
//    }
//
//    private RowMapper<Room> roomRowMapper() {
//        return (rs, rowNum) ->
//                new Room(
//                        RoomId.fromLong(rs.getLong("id")),
//                        rs.getInt("capacity"),
//                        RoomState.valueOf(rs.getString("state")),
//                        rs.getLong("place_id"),
//                        rs.getLong("price_per_night"),
//                        rs.getString("description"),
//                        rs.getString("name"),
//                        rs.getString("facilities")
//                );
//    }
//
//}
