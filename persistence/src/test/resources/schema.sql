DROP TABLE IF EXISTS trips CASCADE;
DROP TABLE IF EXISTS users CASCADE ;
DROP TABLE IF EXISTS trips_users CASCADE;
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS histories CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id INTEGER IDENTITY PRIMARY KEY,
  created timestamp,
  first_name varchar (100),
  phone_number varchar (100),
  last_name varchar (100),
  username varchar (100),
  password varchar (100)
);

CREATE TABLE IF NOT EXISTS trips (
  id INTEGER IDENTITY PRIMARY KEY,
  to_city varchar(100),
  from_city varchar(100),
  created timestamp,
  seats integer,
  driver_id integer,
  cost real,
  eta timestamp,
  deleted boolean,
  etd timestamp,
  departure_lat double precision,
  departure_lon double precision,
  arrival_lat double precision,
  arrival_lon double precision,
  FOREIGN KEY (driver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS trips_users (
  id INTEGER IDENTITY PRIMARY KEY,
  created timestamp,
  trip_id integer,
  user_id integer,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
  id INTEGER IDENTITY PRIMARY KEY,
  created timestamp,
  owner_id integer,
  reviewed_id integer,
  trip_id integer,
  stars integer,
  message varchar(10000),
  FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (reviewed_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS histories (
  id INTEGER IDENTITY PRIMARY KEY,
  created timestamp,
  user_id integer,
  trip_id integer,
  own boolean,
  type varchar(20),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

INSERT INTO users (id, first_name, last_name, username, phone_number) VALUES (1, 'First', 'Last', 'Username1', 'phone');
INSERT INTO users (id, first_name, last_name, username, phone_number) VALUES (2, 'First', 'Last', 'Username2', 'phone');
INSERT INTO users (id, first_name, last_name, username, phone_number) VALUES (3, 'First', 'Last', 'Username3', 'phone');