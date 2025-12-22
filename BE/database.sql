CREATE DATABASE MOVIETICKETBOOKING;
use MOVIETICKETBOOKING;

CREATE TABLE CINEMA(
	CinemaID int primary key auto_increment,
    CineName varchar(100) not null,
    Address varchar(255) not null,
    PhoneNumber varchar(20)
);

CREATE TABLE ROOM(
	RoomID int primary key auto_increment,
    CinemaID INT NOT NULL,
    RoomName VARCHAR(100) NOT NULL,
    RoomType VARCHAR(50),
    Capacity INT NOT NULL,
    
    constraint fk_room_cinema
		foreign key (CinemaID) references CINEMA(CinemaID)
        on delete cascade
        on update cascade
);

CREATE TABLE SEAT(
	SeatID int primary key auto_increment,
    RoomID int not null,
    SeatCode varchar(10) not null,
    RowLabel varchar(10),
    ColLabel int,
    SeatType varchar(20),
    
    constraint fk_seat_room
		foreign key (RoomID) references ROOM(RoomID)
        on delete cascade
        on update cascade
);

CREATE TABLE FILM(
	FilmID int primary key auto_increment,
    Title varchar(200) not null,
    Genre varchar(100),
    Duration int,
    AgeRating varchar(20),
    DescriptionFilm text,
    StartDate date,
    EndDate date,
    PosterURL varchar(255),
    CreatedAt datetime,
    UpdatedAt datetime
);


CREATE TABLE SHOWTIME(
	ShowTimeID int primary key auto_increment,
    FilmID int not null,
    RoomID int not null,
    StartTime datetime not null,
    BasePrice decimal(10,2) not null,
    LanguageFilm varchar(50),
    Subtitle varchar(50),
    StatusFilm varchar(20),
    CreatedAt datetime,
    UpdatedAt datetime,
    
    constraint fk_film_showtime
		foreign key (FilmID) references FILM(FilmID)
        on delete cascade
        on update cascade,
	
     constraint fk_room_showtime
		foreign key (RoomID) references ROOM(RoomID)
        on delete cascade
        on update cascade
		
);


CREATE TABLE USERS(
	UserID int primary key auto_increment,
    FullName varchar(100) not null,
    Email varchar(100) not null,
    Phone varchar(20),
    HashPW varchar(255) not null,
    RoleUser varchar(20) not null, 
    DOB date,
    Gender varchar(10),
    Address varchar(255),
    CreatedAt datetime,
    UpdatedAt datetime
);

CREATE TABLE BOOKING(
	BookingID int primary key,
    UserID int not null,
    ShowTimeID int not null, 
    CreatedAt datetime not null,
    TotalTickets int,
    TotalPay decimal(10,2),
    StatusBooking varchar(20),
    Note varchar(255),
    
	constraint fk_user_booking
		foreign key (UserID) references USERS(UserID)
        on delete cascade
        on update cascade,
	
     constraint fk_showtime_booking
		foreign key (ShowTimeID) references SHOWTIME(ShowTimeID)
        on delete cascade
        on update cascade
);

CREATE TABLE TICKET(
	TicketID int primary key auto_increment,
    BookingID int not null,
    ShowTimeID int not null,
    SeatID int not null, 
    QRcode varchar(255),
    Price decimal(10,2),
    StatusTicket varchar(20),
    CreatedAt datetime,
    
	constraint fk_booking_ticket
		foreign key (BookingID) references BOOKING(BookingID)
        on delete cascade
        on update cascade,
	
     constraint fk_showtime_ticket
		foreign key (ShowTimeID) references SHOWTIME(ShowTimeID)
        on delete cascade
        on update cascade,
	
	constraint fk_seat_ticket
		foreign key (SeatID) references SEAT(SeatID)
        on delete cascade
        on update cascade
	
);

CREATE TABLE FOOD (
	FoodID int primary key auto_increment,
    FoodName varchar(100) not null,
    FoodType varchar(50),
    Size varchar(20),
    UnitPrice decimal(20),
    FoodStatus varchar(20),
    CreatedAt datetime,
    UpdatedAt datetime
);

CREATE TABLE BOOKINGFOOD (
	BookingFoodID int primary key auto_increment,
    BookingID int not null,
    FoodID int not null, 
    Quantity int not null,
    UnitPriceAtOrder decimal(10,2),
    LineTotal decimal(10,2),
    
	constraint fk_booking_bookingfood
		foreign key (BookingID) references BOOKING(BookingID)
        on delete cascade
        on update cascade,
	
	constraint fk_food_bookingfood
		foreign key (FoodID) references FOOD(FoodID)
        on delete cascade
        on update cascade
    
);

CREATE TABLE PAYMENTMETHOD(
	PaymentMethodID int primary key auto_increment,
	PaymentMethodName varchar(100) not null, 
    DescriptionPaymentMethod varchar(255),
    PaymentMethodStatus varchar(20)
);

CREATE TABLE PAYMENTTRANSACTION(
	TransactionID int primary key auto_increment,
    BookingID int not null,
    PaymentMethodID int not null,
    PaidAt datetime,
    Amount decimal(10,2),
    TransactionStatus varchar(20),
    GatewayTransactionRef varchar(100),
    
	constraint fk_booking_paymenttransaction
		foreign key (BookingID) references BOOKING(BookingID)
        on delete cascade
        on update cascade,
	
	constraint fk_paymentmethod_paymenttransaction
		foreign key (PaymentMethodID) references PAYMENTMETHOD(PaymentMethodID)
        on delete cascade
        on update cascade
    
);


CREATE TABLE REVIEW(
	ReviewID int primary key auto_increment,
    UserID int not null,
    FilmID int not null,
    Rating int not null,
    RVComment text,
    CreatedAt datetime,
    
	constraint fk_review_user
		foreign key (UserID) references USERS(UserID)
        on delete cascade
        on update cascade,
	
	constraint fk_film_reivew
		foreign key (FilmID) references FILM(FilmID)
        on delete cascade
        on update cascade
);



