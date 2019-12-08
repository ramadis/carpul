const translations = {
  common: {
    hero: {
      title: "Have been sharing adventures for {{0}} days",
      first_day: "New to a life of adventures",
    },
    navbar: {
      create: "Create account",
      logout: "Logout",
      login: "Login",
      profile: "Profile",
    },
  },
  error: {
    base: {
      page_title: "Carpul | Yikes!",
      title: "So... this is awkward.",
      subtitle: "(yep, this is a {{0}} error page)",
    },
  },
  reservation: {
    title: "Reservation submitted successfully",
    subtitle:
      "All set for your future trip. Here are the details and a way to contact your driver. Good luck in your adventure!",
    contact: "Contact your driver to the email ",
    profile: "Check out the trip in your profile",
    learn: " | Click here to learn more about your driver",
    cost: "For just ",
  },
  history: {
    item: {
      reserved: "reserved",
      unreserved: "unreserved",
      deleted: "deleted",
      all_set: "All set!",
      bummer: "Bummer :(",
      just_deleted: "Oh god no :( Try finding another trip that fits you.",
      message: "{{0}} just {{1}} your trip to {{2}} on {{3}}. {{4}}",
      deleted_message:
        "The driver just deleted his trip to {{0}} on {{1}}. {{2}}",
      kicked_message:
        "The driver just kicked you from his trip to {{0}} on {{1}}. {{2}}",
      happened: "Happened on {{0}}",
    },
  },
  user: {
    trip: {
      earning: "earning",
      nil: "nothing yet",
      delete: "Delete trip",
      map: "See planned route",
      depart: "Depart from {{0}} on {{1}} at {{2}}",
      depart_single: "Depart",
      arrive_single: "Arrive",
      departing: "Departing from {{0}}",
      arrive: "Arrive on {{0}} on {{1}} at {{2}}",
      unreserve: "Unreserve",
    },
    trip_past: {
      review: "Review",
      message: "Review your trip to",
    },
    login: {
      page_title: "Carpul | Enter a world of adventures",
      title: "Have we met yet?",
      subtitle1: "Create an account or login",
      subtitle2: "to start travelling to amazing places",
      username: "Username",
      password: "Password",
      submit: "Login",
      create: "Create account",
    },
    register: {
      page_title: "Carpul | Welcome to a new adventurer life",
      title: "Who are you?",
      subtitle: "Share something about you.",
      first_name: "First name*",
      last_name: "Last name*",
      phone_number: "Phone number",
      username: "Email*",
      password: "Password (at least 6 chars)*",
      submit: "Create account",
      login: "Login",
      errors: {
        name: {
          min:
            "Your name is really that short? Try writing a few characters more.",
          max: "Your name is really that long? Try removing a few characters.",
          pattern: "Your name should be written in letters. Regular letters.",
        },
        phone: {
          max:
            "There's something wrong with your phone. Maybe try deleting a few numbers?",
          pattern:
            "Your phone should be only numbers. No letters, no spaces, no weird emojis.",
        },
        username: {
          min: "Your email should be at least 5 characters long, doesn't it?",
          pattern: "Your email should be... well, an email.",
        },
        password: {
          min:
            "Your password should be at least 6 characters long to be a *little* secure.",
          max: "Your password should be have at most 100 characters.",
        },
      },
    },
    profile: {
      page_title: "Carpul | {{0}} {{1}} is awesome",
      hero: "So, this is your place. Feel like home.",
      reviews: "People are talking about you",
      history: "News about your trips!",
      next: "You're hitchhiking",
      trips: "You're driving",
      find: "Going somewhere? Find a trip",
      new: "Take some people to a new destiny",
      empty_title: "Seems like you don't have much to do here yet.",
      empty_subtitle: "Why not starting right now?",
      empty_new: "Take some people to a new destiny",
      empty_find: "Find somewhere incredible to travel cheap",
      empty_review: "People are not talking about you yet :(",
      empty_histories: "No notifications yet!",
      empty_reservations: "No reservations? Start now!",
      empty_trips: "Do you even drive bro?",
    },
    unauth: {
      profile: {
        reviews: "People are talking about {{0}}",
        page_title: "Carpul | {{0}} {{1}} is awesome too",
        hero: "Welcome to {{0}}s place.",
        empty_review: "People are not talking about {{0}} yet :(",
        is_hitch: "{{0}} is driving to places",
      },
    },
  },
  trip: {
    add: {
      page_title: "Carpul | Add a trip",
      hero: "Where are you going next?",
      title: "Add a new trip",
      from_city: "Departure place (red)*",
      to_city: "Arrival place (blue)*",
      seats: "Available seats*",
      today: "Today",
      timestamp: "dd/MM/yy HH:mm",
      time: "HH:mm",
      cost: "Cost per passenger (ARS)*",
      etd: "Date and time of departure*",
      eta: "Estimated date and time of arrival*",
      submit: "Start the adventure!",
      subtitle: "Set up your trip so we can show everybody.",
      errors: {
        place: {
          invalid: "Select the place from the list.",
        },
        seats: {
          min:
            "The amount of seats should be at least  1... you are sharing, right?",
          max:
            "The amount of seats should be smaller than 20. No buses allowed, sorry.",
        },
        cost: {
          min:
            "The cost of your trip should be greater than $0. Charge some money!",
          max:
            "The cost of your trip should be smaller than $10.000. People ain't *that* rich.",
        },
        date: {
          invalid:
            "The arrival date should be after the departure... or are you traveling through time?",
        },
        etd: {
          invalid:
            "The departure date should be in the future... or are you traveling through time?",
        },
      },
    },
    individual: {
      title: "This is the trip you were looking for",
      page_title: "Carpul | Travel cheap with carpul",
    },
  },
  home: {
    index: {
      page_title: "Carpul | Travel cheap long trips",
      title1: "Travel cheap to amazing places",
      title2: "by joining friendly adventurers.",
      subtitle: "Are you ready? Where next?",
      from: "From",
      to: "To",
      small_to: "to",
      on: "On",
      submit: "Search",
      leave_on: "Leave on",
      just_price: "For just",
      no_trips: "No trips in the near future, sorry! :/",
      suggestions: "These are some trips you might find interesting ;)",
    },
  },
  search: {
    search: {
      page_title: "Carpul | Travel cheap to {{0}}",
      from: "From",
      to: "To",
      on: "On",
      trips: "These are the options to go to {{to}} on {{when}}",
      trips_no_to:
        "These are some options to travel from {{ origin }} on {{when}}",
      no_trips: "Sorry, no adventure to {{to}} at this time :(",
      no_trips_no_to: "Sorry, no adventures from {{origin}} at this time :(",
      later_trips: "These are the options to go to {{to}} after {{when}}",
      create: "Create your own trip",
    },
    item: {
      from: "Leave from",
      arrive: "Arrive on",
      on_low: "on",
      at: "at",
      each: "each",
      reserve: "Reserve",
      unreserve: "Unreserve",
      available: "available",
    },
  },
  review: {
    add: {
      dropzone: "Upload an optional picture of the trip",
      page_title: "Carpul | Leave your mark",
      hero: "How was your trip?",
      title: "Add a new review",
      subtitle:
        "{{0}}, leave a message for future travelers. How was your trip with {{1}} from {{2}} to {{3}}?",
      message: "Rate your trip and leave a message for next travelers",
      placeholder: "Write a few words...",
      submit: "Leave your mark",
      errors: {
        stars: "Try giving a few stars to your driver.",
        message:
          "The message should have more than 10 characters, and less than 300.",
      },
    },
    item: {
      no_stars: "No stars",
      from: "From",
      to: "to",
      trip: "From {{0}} to {{1}}",
    },
  },
  Pattern: {
    userCreateForm: {
      first_name:
        "Your name should only contain letters (You should talk with your parents about this)",
      last_name:
        "Your name should only contain letters (You should talk with your parents about this)",
      phone_number: "Phone number should have only numbers",
    },
    searchForm: {
      to: "Arrival city should only contain letters",
      from: "Departure city should only contain letters",
    },
    tripCreateForm: {
      from_city: "Departure city should only contain letters",
      to_city: "Arrival city should only contain letters",
    },
  },
  Size: "Field should be between {{2}} and {{1}} characters",
  NotBlank: "Field should not be empty",
  NotNull: "Field should not be empty",
  Min: "Field should be at least {{1}}",
  Max: "Field should be at least {{1}}",
};

export default translations;
