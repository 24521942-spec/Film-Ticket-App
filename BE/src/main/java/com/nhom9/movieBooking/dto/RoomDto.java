package com.nhom9.movieBooking.dto;

public class RoomDto {
    private Integer roomId;  
    private String roomName; 
    private String roomType;  
    private Integer capacity;

    public RoomDto(Integer capacity, Integer roomId, String roomName, String roomType) {
        this.capacity = capacity;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }



}
