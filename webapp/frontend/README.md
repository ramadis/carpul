# Parcel-React-Boilerplate

Build React.js project using Parcel, also includes:

- reboot.css
- styled-components
- react-router 4.2
- axios

# Usage

```
git clone https://github.com/J-F-Liu/parcel-react-boilerplate.git your-project
cd your-project
yarn
yarn start
yarn build
```

# Testing

Uses Mocha as test runner and react-testing-library to do the assertions.

```
yarn test
```

# TODO

- [x] Vista de suscribirse al viaje
- [x] Viaje actual vs expirado en el perfil
- [x] Add confetti (https://alampros.github.io/react-confetti/?path=/story/props-demos--snow) en pagina de trip reservado
- [x] Cuando ves el perfil al que le comentaste aparece el string gigante. (Tambien tienen un detalle de ui que no dividen el comentario en varias lineas, todo en una). Sospecho que este comportamiento pasa en todos los campos, no lo comprobe.
- [x] No hay feedback al reservar simplemente se recarga la home del usuario
- [x] Add confirm modal (https://ga-mo.github.io/react-confirm-alert/demo/) (unreserve)
- [x] No hay feedback al dereservar, simplemente se recarga la home del usuario
- [x] Agregar vista de “desreservar”
- [x] Cuando ves el perfil al que le comentaste aparece el string gigante. (Tambien tienen un detalle de ui que no dividen el comentario en varias lineas, todo en una). Sospecho que este comportamiento pasa en todos los campos, no lo comprobe.
- [x] Agregar perfiles de otros

- [ ] Agregar features a la vista de resultados: (Mostar carteles tipo, para este dia tenes estas opciones: ... Sino, para el futuro estas otras...)
- [ ] Paginación en frontend
- [ ] Agregar tests de frontend
- [ ] Polling de historias
- [ ] Checkear que todas las rutas estén bien, ponerlas mas lindas Metí todo en App.jsx para verlo mas facil
- [ ] Pasar las cosas por la store de redux
- [ ] Perfil: imagen de review deformada: https://puu.sh/EMXkB/587abd123f.png No se cuando pasa
