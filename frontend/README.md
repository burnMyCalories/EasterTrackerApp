# Easter Egg Map - Frontend

## Introduction
This is a geographic location-based social application. Friends can bury each other's easter eggs and share fun and interesting things about their geographic location in real time. This app is designed to appeal to users to go out and see more interesting things in the city, while also promoting friendship between friends.

## Technology

- Vue: Front-end framework
- Vuex: Global state management
- Vue-Router: Front-end routing management
- Axios: AJAX communication
- Google Maps JavaScript: API for map rendering and geographic information processing
- BootStrap & AwesomeFont: Basic UI frame and icons

## Directory

- **public**: Html frame of the program

- **src**
  - asset: Static files
  - components: Vue components used in the project
    - Alert.vue: Global notification components
    - CheckEgg.vue: Open Easter eggs
    - HideEgg.vue: Hide Easter eggs
    - Map.vue: Map component
    - Navbar.vue: Navigation bar
  - router: Routing configuration file
  - store: Global State Management Configuration File
  - styles: Styles and fonts used in the program
  - views: Separate pages in the program
    - About.vue: Project information and author information page
    - AddProfile.vue: Modify and add personal information page
    - Cycle.vue: Social circle page (view easter eggs and friends list)
    - Home.vue: Home page
    - MyProfile.vue: Various information and program settings page
    - Register.vue: Register customers page
    - Welcome.vue: Welcome and landing page
  
  - App.vue: Program entry file
  - main.js: Program entry file

- **static**: Static files for the program


## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
