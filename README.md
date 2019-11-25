# TODO 

## FRONT

- [] Remove dummy values (login form, etc)
- [] Remove navbar from some pages (login, etc?)


![logo](http://pawserver.it.itba.edu.ar/paw-2017b-6/static/images/logo.png)
### A cheap carpooling service focused on traveling far away.
---
## Key Features

* Find destinations to go
* Easily create a new trip
* Leave and read user reviews
* Search case insensitive and substring
* Get results for future trips
* Keep track of recent events
* Receive email notifications
* See the latest updates relating your trips
* Improved design
* See some suggestions in your home screen
* Every rated user has stars depending on his reviews

## Technical Features
* Automatic user location
* Custom annotations validations
* JUnit tests
* Hibernate integration

## How To Use

To clone and run this application, you'll need [Git](https://git-scm.com).

```bash
# Clone this repository
$ git clone https://bitbucket.org/itba/paw-2017b-06

# Go into the repository
$ cd carpul

# Install dependencies
$ mvn eclipse:eclipse

# Open in eclipse
$ eclipse
```

## Sample

Credentials for users with useful data are:

```bash
# Username
entrega
# Password
holahola


# Username
carpul@mailinator.com
# Password
holahola
```

On the date `23/12/17` going from `Buenos Aires` to `Mar del plata` are several trips listed to play with.

## Comments

* A user can't search for his/her own trips
* The earnings for a trip you own are calculated taking into account the amount of people registered for a trip and the cost per passenger. (# passengers with reservations * cost per passenger)
* Trips are only reviewable after the arrival time. If you review one, it won't display anymore in your profile.

## Credits

This project was developed by group six.

- [Ramiro Olivera Fedi](https://github.com/ramadis)
- [Julian Antonielli](https://github.com/jjant)

#### License

MIT
