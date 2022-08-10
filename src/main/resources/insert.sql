--users table
INSERT INTO users(login, email, password, role)
VALUES ('RabbitAdm', 'rabbit_adm@tut.by', 'abcd', 'admin'),
       ('RabbitCli', 'rabbit_cli@tut.by', 'dcba', 'client');

--userJpa details
INSERT INTO userdetails(user_id, name, surname, address, phone, birthday, registration_date)
VALUES ((SELECT id FROM users WHERE login LIKE 'RabbitAdm'), 'Ivan', 'Ivanov', 'Minsk', '+375 29 124 56 78',
        TO_TIMESTAMP('1986-07-02 10:30:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-07-07 17:55:00', 'YYYY-MM-DD HH24:MI:SS')),
       ((SELECT id FROM users WHERE login LIKE 'RabbitCli'), 'Piotr', 'Petrov', 'London', '+44 20 124 56 78',
        TO_TIMESTAMP('1998-10-01 11:30:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-07-07 15:55:00', 'YYYY-MM-DD HH24:MI:SS'));


--driver licence
INSERT INTO driverlicense(user_details_id, number, issue_date, expired_date)
VALUES ((SELECT ud.id
         FROM userdetails ud
                  left join users u on ud.user_id = u.id
         WHERE u.login LIKE 'RabbitCli'), 'AB12345', TO_TIMESTAMP('2015-03-02', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2025-03-01', 'YYYY-MM-DD HH24:MI:SS'));

--priceJpa
INSERT INTO price(price)
VALUES ('50'),
       ('100'),
       ('150');


--categories
INSERT INTO categories(name, price_id)
VALUES ('ECONOMY', (SELECT p.id FROM price p WHERE p.priceJpa = '50')),
       ('LUXURY', (SELECT p.id FROM price p WHERE p.priceJpa = '150')),
       ('BUSINESS', (SELECT p.id FROM price p WHERE p.priceJpa = '100')),
       ('SPORT', (SELECT p.id FROM price p WHERE p.priceJpa = '100'));


--brandJpa
INSERT INTO brand(name)
VALUES ('audi'),
       ('bmw'),
       ('citroen'),
       ('honda'),
       ('ford'),
       ('nissan'),
       ('opel'),
       ('skoda'),
       ('toyota'),
       ('volvo'),
       ('mercedes'),
       ('lexus'),
       ('peugeot'),
       ('suzuki'),
       ('hyndai');

--model
INSERT INTO model(brand_id, category_id, name, transmission, engine_type)
VALUES ((SELECT b.id FROM brand b WHERE b.name = 'audi'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        '100',
        'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'audi'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'A8',
        'automatic', 'electric'),
       ((SELECT b.id FROM brand b WHERE b.name = 'audi'), (SELECT c.id FROM categories c WHERE c.name = 'SPORT'), 'TT',
        'robot', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'bmw'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'), 'i3',
        'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'bmw'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'X7',
        'automatic', 'electric'),
       ((SELECT b.id FROM brand b WHERE b.name = 'bmw'), (SELECT c.id FROM categories c WHERE c.name = 'LUXURY'), 'Z4',
        'robot', 'electric'),
       ((SELECT b.id FROM brand b WHERE b.name = 'citroen'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Zsara', 'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'citroen'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Xantia', 'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'citroen'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'C8', 'robot', 'electric'),
       ((SELECT b.id FROM brand b WHERE b.name = 'honda'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'CR-V', 'automatic', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'honda'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Accord', 'manual', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'honda'), (SELECT c.id FROM categories c WHERE c.name = 'LUXURY'),
        'Odyssey', 'robot', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'ford'), (SELECT c.id FROM categories c WHERE c.name = 'SPORT'),
        'Mustang', 'automatic', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'nissan'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Micra', 'automatic', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'nissan'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Almera', 'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'opel'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Omega', 'manual', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'skoda'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Fabia', 'manual', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'skoda'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'Octavia', 'robot', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'toyota'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Corolla', 'automatic', 'gas'),
       ((SELECT b.id FROM brand b WHERE b.name = 'toyota'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Yaris', 'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'volvo'), (SELECT c.id FROM categories c WHERE c.name = 'LUXURY'),
        'XC90', 'manual', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'mercedes'), (SELECT c.id FROM categories c WHERE c.name = 'LUXURY'),
        'CLS', 'robot', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'lexus'), (SELECT c.id FROM categories c WHERE c.name = 'BUSINESS'),
        'NX I', 'automatic', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'peugeot'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        '308', 'automatic', 'fuel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'suzuki'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Grand Vitara', 'manual', 'diesel'),
       ((SELECT b.id FROM brand b WHERE b.name = 'suzuki'), (SELECT c.id FROM categories c WHERE c.name = 'ECONOMY'),
        'Solaris', 'manual', 'fuel');


--car
INSERT INTO car(model_id, color, year, car_number, vin, is_repaired)
VALUES ((SELECT m.id FROM model m WHERE m.name = '100'), 'white', '2020', '7865AE-7', 'AmhBHqJ8BgD0p3PRgkoi', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'A8'), 'black', '2020', '7869AE-7', '6iFm73ncHPAAmpZoi0pM', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'TT'), 'black', '2018', '7845HR-7', 'UR1g99JsoqcyovJzXhLJ', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'i3'), 'red', '2021', '9823JK-7', '5Wgm1wipFhdUviBZ4Qil', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'X7'), 'yellow', '2022', '9983SL-7', '6EaVJP9u5tip98oEoKHu', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Z4'), 'blue', '2015', '3562AS-7', 'TyHALJfzxxAbI3bWv4vu', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Zsara'), 'green', '2019', '6753MM-7', 'vrG8tK2NmFruzTjMZ5X7',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Xantia'), 'black', '2016', '3425MM-7', 'ry4r95f5iCSVhhLtFrxi',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'C8'), 'black', '2021', '7654LM-7', 'D0wIIzZG0M1gIapEVLck', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'CR-V'), 'white', '2001', '3462LM-7', 'XgKb21CnAQmCDYBCWTOZ', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Accord'), 'red', '2017', '6784DS-7', 'zCwNgYF9AvMsRFnUIvFt', 'true'),
       ((SELECT m.id FROM model m WHERE m.name = 'Odyssey'), 'black', '2022', '8769ES-7', 'i9b7a4Ut7O0bRlNFKiws',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Mustang'), 'black', '2022', '8770ES-7', 'pJTvhGLZm6MdP2PnFErg',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Micra'), 'green', '2014', '5467AL-7', 'YbHdXw3iwuwg078TrHzh',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Almera'), 'blue', '2013', '5367Kl-7', 'xZHKoXpuHL2mUOVC5kaI',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Omega'), 'black', '2017', '9076Kl-7', 'TvYjOVcvNn1slI7nLPrA',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Fabia'), 'red', '2019', '9874NM-7', 'he4tYZ2FvFhEd6ft4dGu', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Octavia'), 'white', '2021', '9976BG-7', 'Rw6hl3GJmZ0WLXOerR1A',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Corolla'), 'black', '2020', '6732VA-7', '36ft6b3IKuNLteNp6yNJ',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Yaris'), 'blue', '2013', '7619FS-7', 'cJiqu4QwyBTwfi83skhN', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'XC90'), 'black', '2021', '9845HN-7', 'GFlHws5QLhjJPH77WDct', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'CLS'), 'black', '2022', '9945CX-7', 'Wjj6yqGUTyC2UQ3gELpp', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'NX I'), 'red', '2022', '9949FX-7', 'qy823ZVYGQXlk08lYnGe', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = '308'), 'white', '2018', '6748KM-7', 'UaaoXEefc7GUddmgLu6e', 'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Grand Vitara'), 'green', '2017', '4598DF-7', '9qz4CVJYJcGtRAx7lbRo',
        'false'),
       ((SELECT m.id FROM model m WHERE m.name = 'Solaris'), 'yellow', '2010', '3298DF-7', 'nm7UtHoNXTdZ0v3QJusb',
        'false');


--orderJpa
INSERT INTO orders(date, user_id, car_id, passport, insurance, order_status, sum)
VALUES (TO_TIMESTAMP('2022-07-01 10:30:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT u.id FROM users u WHERE u.login LIKE 'RabbitCli'),
        (SELECT c.id FROM car c WHERE c.vin = 'i9b7a4Ut7O0bRlNFKiws'), 'MP1234567', 'true', 'PAYED', 1020),
       (TO_TIMESTAMP('2022-07-04 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT u.id FROM users u WHERE u.login LIKE 'RabbitCli'),
        (SELECT c.id FROM car c WHERE c.vin = 'YbHdXw3iwuwg078TrHzh'), 'MP1234567', 'true', 'NOT_PAYED', 535),
       (TO_TIMESTAMP('2022-07-07 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        (SELECT u.id FROM users u WHERE u.login LIKE 'RabbitCli'),
        (SELECT c.id FROM car c WHERE c.vin = 'qy823ZVYGQXlk08lYnGe'), 'MP1234567', 'false', 'CONFIRMATION_WAIT', 700);


--carrentaltime
INSERT INTO carrentaltime(order_id, start_rental_date, end_rental_date)
VALUES ((SELECT o.id FROM orders o WHERE o.order_status = 'PAYED'),
        TO_TIMESTAMP('2022-07-02 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-07-03 23:59:00', 'YYYY-MM-DD HH24:MI:SS')),
       ((SELECT o.id FROM orders o WHERE o.order_status = 'NOT_PAYED'),
        TO_TIMESTAMP('2022-07-04 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-07-04 23:59:00', 'YYYY-MM-DD HH24:MI:SS')),
       ((SELECT o.id FROM orders o WHERE o.order_status = 'CONFIRMATION_WAIT'),
        TO_TIMESTAMP('2022-07-07 00:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2022-07-08 23:59:00', 'YYYY-MM-DD HH24:MI:SS'));


--accidentJpa
INSERT INTO accidentJpa(order_id, accident_date, description, damage)
VALUES ((SELECT o.id FROM orders o WHERE o.order_status = 'PAYED'),
        TO_TIMESTAMP('2022-07-02 16:34:00', 'YYYY-MM-DD HH24:MI:SS'),
       'faced tree', '75.50'),
       ((SELECT o.id FROM orders o WHERE o.order_status = 'NOT_PAYED'),
        TO_TIMESTAMP('2022-07-04 01:00:00', 'YYYY-MM-DD HH24:MI:SS'),
        'broke wheel', '10');