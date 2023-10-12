//package com.dpap.bookingapp.booking.room;
//
//
//import com.dpap.bookingapp.booking.common.JsonToCollectionMapper;
//import com.dpap.bookingapp.booking.facility.FacilityType;
//
//import java.util.List;
//import java.util.Set;
//
//public class RoomDetails {
//    private Long id;
//    private Long roomId;
//    private String name;
//    private String description;
//    private String imageUrl;
//    private String facilities;
//
//    public RoomDetails(Long roomId, String name, String description, String imageUrl) {
//        this.roomId = roomId;
//        this.name = name;
//        this.description = description;
//        this.imageUrl = imageUrl;
//    }
//    public RoomDetails(Long roomId, String name, String description, String imageUrl, String facilities) {
//        this.roomId = roomId;
//        this.name = name;
//        this.description = description;
//        this.imageUrl = imageUrl;
//        this.facilities = facilities;
//    }
//    public void addFacilities(Set<FacilityType> facilities){
//        facilities.forEach(this::addFacility);
//    }
//    public void addFacility(FacilityType facilityType) {
//       var set =  JsonToCollectionMapper.deserialize(facilities);
//       set.add(facilityType);
//       this.facilities = JsonToCollectionMapper.serialize(set);
//    }
//
//    public List<FacilityType> getFacilityTypes() {
//        return JsonToCollectionMapper
//                .deserialize(facilities)
//                .stream()
//                .toList();
//    }
//    public Long getId() {
//        return id;
//    }
//
//    public Long getRoomId() {
//        return roomId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public String getFacilities() {
//        return facilities;
//    }
//}
