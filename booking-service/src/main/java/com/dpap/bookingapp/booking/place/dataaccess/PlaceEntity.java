package com.dpap.bookingapp.booking.place.dataaccess;

import com.dpap.bookingapp.booking.place.PlaceCategory;
import com.dpap.bookingapp.booking.place.room.dataaccess.RoomEntity;
import com.dpap.bookingapp.users.UserEntity;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "places")
public class PlaceEntity {

    @Id
    @SequenceGenerator(name = "places_id_seq", sequenceName = "seq_places", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "places_id_seq")
    private Long id;
    private String name;
    private String description;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country")),
            @AttributeOverride(name = "building", column = @Column(name = "address_building")),
            @AttributeOverride(name = "voivodeship", column = @Column(name = "address_voivodeship"))
    })
    private Address address;
    @Enumerated(EnumType.STRING)
    private PlaceCategory category;
    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private String facilities;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<RoomEntity> rooms = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PlaceCategory getCategory() {
        return category;
    }

    public void setCategory(PlaceCategory category) {
        this.category = category;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRoom(RoomEntity room) {
        this.rooms.add(room);
    }

    public void removeRoomWithId(Long roomId) {
        this.rooms.remove(this.rooms.stream().filter(roomEntity -> roomEntity.getId().equals(roomId)).findFirst()
                .orElseThrow(() -> new RuntimeException("Not found room with id:" + roomId)));
    }

    public void removeRoom(RoomEntity room) {
        this.rooms.remove(room);
    }

    public List<RoomEntity> findRoomsCheaperThan(BigDecimal value) {
        return this.rooms
                .stream()
                .filter(roomEntity -> new BigDecimal(roomEntity.getPricePerNight()).setScale(2, RoundingMode.UP).compareTo(value.setScale(2, RoundingMode.UP)) > 0)
                .toList();
    }

}