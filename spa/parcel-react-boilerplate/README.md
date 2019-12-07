# Parcel-React-Boilerplate

Build React.js project using Parcel, also includes:

* reboot.css
* styled-components
* react-router 4.2
* axios

# Usage

```
git clone https://github.com/J-F-Liu/parcel-react-boilerplate.git your-project
cd your-project
yarn
yarn start
yarn build
```

# TODO

- [\] Agregar perfiles de otros
        Empezado: faltan cosas (cambiar texto y que se yo), está todo en `Profile.jsx`
- [ ] Vista de resultados
- [ ] Vista de suscribirse al viaje
        Esta creo que es la de `/trips/:id` (`pages/Trips/Single`)
- [ ] Agregar features a la vista de resultados
- [ ] Paginación en frontend
- [ ] Viaje actual vs expirado en el perfil
- [ ] Polling de historias
- [ ] Add confirm modal (https://ga-mo.github.io/react-confirm-alert/demo/)
- [ ] Add confetti (https://alampros.github.io/react-confetti/?path=/story/props-demos--snow)
- [ ] Pasar las cosas por la store de redux
- [ ] Agregar vista de “desreservar”
- [ ] Agregar tests de frontend
- [ ] Cuando ves el perfil al que le comentaste aparece el string gigante. (Tambien tienen un detalle de ui que no dividen el comentario en varias lineas, todo en una). Sospecho que este comportamiento pasa en todos los campos, no lo comprobe.
- [ ] Perfil: imagen de review deformada: https://puu.sh/EMXkB/587abd123f.png
        No se cuando pasa
- [ ] No hay feedback al reservar / dereservar, simplemente se recarga la home del usuario
- [ ] Checkear que todas las rutas estén bien, ponerlas mas lindas
        Metí todo en App.jsx para verlo mas facil