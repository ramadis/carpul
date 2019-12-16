![logo](http://pawserver.it.itba.edu.ar/paw-2017b-6/logo.52b8927a.png)

### A cheap carpooling service focused on traveling far away.

---

## Key Features

- Find destinations to go
- Easily create a new trip
- Leave and read user reviews
- Search case insensitive and substring
- Get results for future trips
- Keep track of recent events
- Receive email notifications
- See the latest updates relating your trips
- Improved design
- See some suggestions in your home screen
- Every rated user has stars depending on his reviews

## Technical Features

- Automatic user location
- Custom annotations validations
- JUnit tests
- Hibernate integration

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

## Building

Run the following commands on the root of the project.

```bash
mvn clean package
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

- A user can't search for his/her own trips
- The earnings for a trip you own are calculated taking into account the amount of people registered for a trip and the cost per passenger. (# passengers with reservations \* cost per passenger)
- Trips are only reviewable after the arrival time. And after the departure time they are disabled (for both drivers and passengers.)

## New features for Dec 16 2019

```
Reserved Page
  Page when a user reserves a trip
  It redirects you to the trip page if you haven't reserved yet
	Allows to add the trip to your calendar
Unreserved Page
  Page when a user unreserves a trip
  Suggests trips to the same (or closer geographically) destintation
Add trips
  New restriction: 24hs max for a trip
	New restriction: Doesn't allow creation if theres an overlap with another event (another reservation or drived trip)
	New restriction: Doesn't allow departure time > arrival time
Search
  Find places nearby (geographically)
  Substring name match
	User automatic geolocalizaction (Allowed only over SSL or localhost)
  Search editing in search view
  Estimated trip path on each search result
  Share trip view
Profile
	Allow to change profile picture
  Allow to change cover picture
Review
	Allow to add a representative image
Email (Html + Plaintext. Locally sends with HTML but it seems the server doesn't allow it.)
	When someone reserves your trip
	When someone unreserves your trip
  When the driver cancel a trip you were signed on
  Welcome to carpul
	Add call to action to emails
Páginas vacías
	Add call to action to empty pages
Otras
	Endpoint pagination at DB level (w/restriction of per_page and page)
	Confirmation modal to dangerous operations
```

## Credits

This project was developed by group six.

- [Ramiro Olivera Fedi](https://github.com/ramadis)
- [Julian Antonielli](https://github.com/jjant)

#### License

MIT
