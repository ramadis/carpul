
![logo](http://pawserver.it.itba.edu.ar/paw-2017b-6/static/images/logo.png)
### A cheap carpooling service focused on traveling far away.
---
## Key Features

* Find destinations to go
* Easily create a new trip
* Leave and read user reviews
* Keep track of recent events

## Technical Features
* Automatic user location
* Custom annotations validations

## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com).

```bash
# Clone this repository
$ git clone https://github.com/amitmerchant1990/electron-markdownify

# Go into the repository
$ cd carpul

# Install dependencies
$ mvn eclipse:eclipse

# Open in eclipse
$ eclipse
```

## Sample

Credentials for a user with an old trip pending review, a current reserved trip, an own empty trip, an own occupied trip, reviews, and history.

```bash
# Username
entrega
# Password
holahola
```

On the date `23/12/17` going from `Buenos Aires` to `Mar del plata` are several trips listed to play with.

## Comments

* A user can't search for his/her own trips
* The earnings for a trip are calculated taking into account the amount of people registered for a trip and the cost per passenger.
* Trips are only reviewable after the arrival time. If you review one, it won't display anymore in your profile.

## Credits

This project was developed by group six.

- [Ramiro Olivera Fedi](https://github.com/ramadis)
- [Julian Antonielli](https://github.com/jjant)

#### License

MIT
