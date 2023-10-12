//package com.dpap.bookingapp.booking.place;
//
//import io.vavr.control.Option;
//import io.vavr.control.Try;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Locale;
//
//import static io.vavr.control.Option.none;
//import static io.vavr.control.Option.of;
//
//@Repository
//public class PlaceRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public PlaceRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<Place> findPlacesByCity(String city) {
//        return jdbcTemplate.query(
//                """
//                        SELECT p.id, c.name AS category, p.street, p.description, p.name, p.building, p.city,p.building, p.building, p.landlord_id
//                        FROM places p JOIN categories c ON c.id = p.category_id WHERE p.city = ?
//                        """,
//                placeRowMapper(),
//                city
//        );
//    }
//
//    public void addPlace(Place place) {
//
//        Long categoryId = jdbcTemplate.queryForObject("SELECT id from categories where name like ?", Long.class, place.getCategory().name());
//        Long voivodeshipId = jdbcTemplate.queryForObject("SELECT id from voivodeships where name = ?", Long.class, place.getAddress().voivodeship().toLowerCase(Locale.ROOT));
//        jdbcTemplate.update(
//                "INSERT INTO places VALUES((SELECT nextval('seq_places')),?,?,?,?,?,?,?,?,?)",
//                place.getDescription(),
//                place.getName(),
//                place.getAddress().city(),
//                place.getAddress().country(),
//                place.getAddress().street(),
//                place.getAddress().building(),
//                voivodeshipId,
//                categoryId,
//                place.getLandlordId()
//        );
//    }
//
//    public Option<Place> findPlaceById(Long placeId) {
//        return Try
//                .ofSupplier(
//                        () -> of(
//                                jdbcTemplate.query(
//                                        """
//                                                SELECT p.id, c.name AS category, p.street, p.description, p.name, p.building, p.city,p.building, p.landlord_id
//                                                FROM places p JOIN categories c ON c.id = p.category_id WHERE p.id = ?
//                                                """,
//                                        placeRowMapper(),
//                                        placeId
//                                ).get(0)
//                        )
//                )
//                .getOrElse(none());
//    }
//
//    public List<Place> findAll() {
//        return jdbcTemplate.query(
//                """
//                        SELECT p.id, c.name AS category, p.street, p.description, p.name, p.building, p.city, p.landlord_id
//                        FROM places p JOIN categories c ON c.id = p.category_id
//                        """,
//                placeRowMapper()
//        );
//    }
//
//    public void deleteById(Long id, Long userId) {
//        jdbcTemplate.update("DELETE FROM places WHERE id = ? AND landlord_id = ?", id, userId);
//    }
//
//    public List<Place> findAllByFilters(PlaceSearchFilter filter) {
//
//        if (filter.isEmpty()) {
//            return findAll();
//        }
//        return jdbcTemplate.query(
//                buildSearchWithFiltersQuery(filter),
//                placeRowMapper()
//        );
//    }
//
//    private String buildSearchWithFiltersQuery(PlaceSearchFilter filter) {
//        StringBuilder query = new StringBuilder(
//                "SELECT p.id, c.name  category, p.street, p.description, p.name, p.city, p.building,p.landlord_id" +
//                        " FROM places p JOIN categories c ON c.id = p.category_id WHERE ");
//
//        query.append(filter.getUserId().map(userId -> "p.landlord_id = " + userId + " AND ").orElse(""));
//        query.append(filter.getCategory().map(category -> "c.name LIKE '" + category.name() + "' AND ").orElse(""));
//        query.append(filter.getVoivodeship().map(voivodeship -> "voivodeship LIKE '" + voivodeship + "' AND ").orElse(""));
//        query.append(filter.getCity().map(city -> "city LIKE '" + city + "' AND ").orElse(""));
//        query.replace(query.lastIndexOf(" AND"), query.lastIndexOf(" AND") + 4,"");
//        return query.toString();
//}
//
//    private RowMapper<Place> placeRowMapper() {
//        return (rs, rowNum) ->
//                new Place(
//                        rs.getLong("id"),
//                        rs.getString("name"),
//                        rs.getString("description"),
//                        new Address(
//                                rs.getString("street"),
//                                rs.getString("city"),
//                                rs.getString("building"),
//                                "POLSKA",
//                                "LUBELSKIE"
//                        ),
//                        PlaceCategory.valueOf(rs.getString("category")),
//                        rs.getLong("landlord_id")
//                );
//    }
//
//}
