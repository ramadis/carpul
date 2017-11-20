-- TODO: Add key constraints

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  created timestamp,
  first_name varchar (100),
  phone_number varchar (100),
  last_name varchar (100),
  username varchar (100),
  password varchar (100)
);

CREATE TABLE IF NOT EXISTS trips (
  id SERIAL PRIMARY KEY,
  to_city varchar(100),
  from_city varchar(100),
  created timestamp,
  seats integer,
  driver_id integer,
  cost real,
  eta timestamp,
  etd timestamp,
  departure_lat double precision,
  departure_lon double precision,
  arrival_lat double precision,
  arrival_lon double precision,
  FOREIGN KEY (driver_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS trips_users (
  id SERIAL PRIMARY KEY,
  created timestamp,
  trip_id integer,
  user_id integer,
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reviews (
  id SERIAL PRIMARY KEY,
  created timestamp,
  owner_id integer,
  reviewed_id integer,
  trip_id integer,
  stars integer,
  message text,
  FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (reviewed_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS histories (
  id SERIAL PRIMARY KEY,
  created timestamp,
  user_id integer,
  trip_id integer,
  own boolean,
  type varchar(20),
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  FOREIGN KEY (trip_id) REFERENCES trips(id) ON DELETE CASCADE
);
