INSERT INTO roles
VALUES (1, 'USER'),
       (2, 'ADMIN');

INSERT INTO users
VALUES ((SELECT nextval('seq_users')),
        'user00',
        'Dawid',
        'Plewik',
        'password',
        'dawid@plewik.com',
        '123456789');
INSERT INTO users
VALUES ((SELECT nextval('seq_users')),
        'user01',
        'Diwad',
        'Kiwelp',
        'password',
        'diwad@kiwelp.com',
        '123456789');
INSERT INTO users_roles
VALUES (1, 1);
INSERT INTO users_roles
VALUES (2, 2);

INSERT INTO voivodeships
VALUES (1, 'lubelskie'),
       (2, 'opolskie'),
       (3, 'malopolskie'),
       (4, 'slaskie'),
       (5, 'dolnoslaskie'),
       (6, 'lubuskie'),
       (7, 'mazowieckie'),
       (8, 'podkarpackie'),
       (9, 'pomorskie'),
       (10, 'podlaskie'),
       (11, 'zachodnio-pomorskie'),
       (12, 'warminsko-mazurskie'),
       (13, 'lodzkie'),
       (14, 'swietokrzyskie'),
       (15, 'kujawsko-pomorskie'),
       (16, 'lubelskie');

INSERT INTO facilities
VALUES (1, 'TV'),
       (2, 'AIR_CONDITIONER'),
       (3, 'DOUBLE_BED'),
       (4, 'SINGLE_BED'),
       (5, 'SAUNA'),
       (6, 'JACUZZI'),
       (7, 'WIFI'),
       (8, 'GRILL'),
       (9, 'GYM'),
       (10, 'SWIMMING_POOL');
INSERT INTO categories
VALUES (1, 'HOTEL'),
       (2, 'APARTMENTS'),
       (3, 'RESORTS'),
       (4, 'VILLAS'),
       (5, 'CAMPING');
INSERT INTO places(id, description, name, address_city, address_building, address_street, address_country, category,
                   address_voivodeship, user_id)
VALUES ((SELECT nextval('seq_places')), 'Hostel nieopdal centrum', 'Noclegownia', 'Lublin', '4', 'Lubelska', 'Polska',
        'HOTEL',
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Huzar', 'Lublin', '4', 'Lubelska', 'Polska', (SELECT name FROM categories WHERE id = 1),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Hotel Maximus', 'Lublin', '14', 'Lubelska', 'Polska', (SELECT name FROM categories WHERE id = 1),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Hotel Rozmus', 'Lublin', '24', 'Lubelska', 'Polska', (SELECT name FROM categories WHERE id = 1),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Zajazd u Strusia', 'Lublin', '34', 'Lubelska', 'Polska', (SELECT name FROM categories WHERE id = 1),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Rodzinna przystań', 'Lublin', '54', 'Lubelska', 'Polska', (SELECT name FROM categories WHERE id = 4),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Pole namiotowe u Karola', 'Lublin', '64', 'Zemborzycka', 'Polska', (SELECT name FROM categories WHERE id = 5),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Hotel Korona', 'Lublin', '14', 'Kazimierska', 'Polska', (SELECT name FROM categories WHERE id = 1),
        'LUBELSKIE', 1),
       ((SELECT nextval('seq_places')), 'Hostel', 'Apartamenty Jodłowa', 'Lublin', '234', 'Jodłowa', 'Polska', (SELECT name FROM categories WHERE id = 2),
        'LUBELSKIE', 1);

INSERT INTO rooms(id, capacity, state, place_id, price_per_night, name, description, image_url)
VALUES ((SELECT nextval('seq_rooms')), 1, 'AVAILABLE', 1, 50.00, 'Pokoj jednoosobowy z łazienką na korytarzu', '',
        'first'),
       ((SELECT nextval('seq_rooms')), 2, 'AVAILABLE', 1, 100.00, 'Pokoj dwuosobowy z łazienką na korytarzu', '',
        'second'),
       ((SELECT nextval('seq_rooms')), 3, 'AVAILABLE', 1, 150.00, 'Pokoj trzyosobowy z łazienką', '', 'third'),
       ((SELECT nextval('seq_rooms')), 2, 'AVAILABLE', 2, 300.00, 'Pokoj dwuosobowy z łazienką', '', 'third'),
       ((SELECT nextval('seq_rooms')), 2, 'AVAILABLE', 2, 100.00, 'Pokoj dwuosobowy z łazienką', '', 'third'),
       ((SELECT nextval('seq_rooms')), 3, 'AVAILABLE', 2, 125.00, 'Pokoj dwuosobowy z łazienką', '', 'third'),
       ((SELECT nextval('seq_rooms')), 8, 'AVAILABLE', 2, 300.00, 'Pokoj dwuosobowy z łazienką', '', 'third')
;

-- INSERT INTO room_details(id, name, description, image_url, room_id)
-- VALUES ((SELECT nextval('seq_room_details')), 'Pokoj jednoosobowy z łazienką na korytarzu', '', 'first', 1),
--        ((SELECT nextval('seq_room_details')), 'Pokoj dwuosobowy z łazienką na korytarzu', '', 'second', 2),
--        ((SELECT nextval('seq_room_details')), 'Pokoj trzyosobowy z łazienką', '', 'third', 3),
--        ((SELECT nextval('seq_room_details')), 'Pokoj dwuosobowy z łazienką', '', 'third', 4),
--        ((SELECT nextval('seq_room_details')), 'Pokoj dwuosobowy z łazienką', '', 'third', 5),
--        ((SELECT nextval('seq_room_details')), 'Pokoj trzyosobowy z łazienką', '', 'third', 6),
--        ((SELECT nextval('seq_room_details')), 'Apartament z łazienką', '', 'third', 7);

INSERT INTO reservation_states
VALUES (1, 'CONFIRMED'),
       (2, 'CHECK_IN'),
       (3, 'CHECK_OUT'),
       (4, 'WAITING'),
       (5, 'CANCELED');

INSERT INTO reservations
VALUES ((SELECT nextval('seq_reservations')), 1, 2,2, '2023-07-07T12:00', '2023-07-11T12:00', '2023-07-01T10:00:00', 1,
        400.00,
        14);

