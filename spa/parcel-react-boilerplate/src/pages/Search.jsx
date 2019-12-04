import React, { useEffect, useState } from 'react'
import styled from 'styled-components'
import { connect } from 'react-redux'
import { NotificationContainer } from 'react-notifications'
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Link,
  Redirect
} from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { useLocation } from 'react-router-dom'
import poolListCss from '../styles/pool_list'
import { search } from '../services/search'

function useQuery () {
  return [...new URLSearchParams(useLocation().search).entries()].reduce(
    (ac, [key, val]) => ({ ...ac, [key]: val }),
    {}
  )
}

const HeaderContainer = styled.div`
  background: white;
  height: 45px;
  border: 1px solid darkgray;
  border-right: 0;
  border-left: 0;
  padding: 0 30px;
  display: flex;
  align-items: center;
`

const Search = ({}) => {
  const { t, i18n } = useTranslation()
  const [trips, setTrips] = useState([])
  const [laterTrips, setLaterTrips] = useState([])
  const { to, from, when } = useQuery()

  useEffect(() => {
    const exRep = [
      {
        id: 58,
        etd: 1576101600000,
        eta: 1576706400000,
        from_city: 'Buenos aires',
        to_city: 'Pinamar',
        cost: 100.0,
        seats: 4,
        departure: { latitude: -36.3789925, longitude: -60.3855889 },
        arrival: { latitude: -37.1099492, longitude: -56.8539007 },
        occupied_seats: 0,
        driver: {
          username: 'juli',
          first_name: 'Julian',
          last_name: 'Antonielli',
          created: 1507678063362,
          id: 2
        },
        passengers: [],
        expired: false
      }
    ]
    search({ to, from, when }).finally(res => setTrips(res || exRep))
  }, [])

  return (
    <div>
      <style jsx>{poolListCss}</style>

      <HeaderContainer>
        <div className='destination-container'>
          <span className='bold m-r-5'>{t('search.search.from')}</span>
          <span className='clear'>{from}</span>
        </div>
        <div className='destination-container'>
          <span className='bold m-r-5'>{t('search.search.to')}</span>
          <span className='clear'>{to}</span>
        </div>
        <div className='destination-container'>
          <span className='bold m-r-5'>{t('search.search.on')}</span>
          <span className='clear'>
            {when}
            {/* <fmt:formatDate value="{search.when}" pattern="dd/MM/yyyy"/> */}
          </span>
        </div>
      </HeaderContainer>

      <div className='list-container'>
        {trips.length > 0 && (
          <React.Fragment>
            <span className='list-subtitle'>
              {t('search.search.trips', { to, when })}
            </span>
            {trips.map(trip => (
              <Trip trip={trip} key={trip.id} />
            ))}
          </React.Fragment>
        )}

        {laterTrips.length > 0 && (
          <React.Fragment>
            <span className='list-subtitle'>
              {t('search.search.later_trips', { to, when: isDate })}
            </span>
            {laterTrips.map(trip => (
              <Trip trip={trip} key={trip.id} />
            ))}
          </React.Fragment>
        )}

        {trips.length === 0 && laterTrips.length === 0 && (
          <span className='list-subtitle'>
            {t('search.search.no_trips', { to })}
          </span>
        )}
      </div>
    </div>
  )
}

const Trip = ({ trip }) => {
  const { t } = useTranslation()
  return (
    <div className='pool-item flex-center'>
      <div className='user-info flex space-around align-center column h-150'>
        <div className='user-image'>
          <img
            src='https://ui-avatars.com/api/?rounded=true&size=85&background=e36f4a&color=fff&name=${trip.driver.first_name} ${trip.driver.last_name}'
            alt=''
          />
        </div>
        <div className='user-name'>{trip.driver.first_name}</div>
        <span className='user-rating'>
          <img src="<c:url value='/static/images/star.png' />" />
          <img src="<c:url value='/static/images/star.png' />" />
          <img src="<c:url value='/static/images/star.png' />" />
          <img src="<c:url value='/static/images/star.png' />" />
          <img src="<c:url value='/static/images/star.png' />" />
        </span>
      </div>

      <div className='pool-info'>
        <div className='map-container'>
          <img
            src='https://maps.googleapis.com/maps/api/staticmap?key=AIzaSyCKIU4-Ijaeex54obPySJ7kXLwLnrV5BRA&size=1200x200&markers=color:green|label:A|${trip.departure_lat}, ${trip.departure_lon}&markers=color:blue|label:B|${trip.arrival_lat}, ${trip.arrival_lon}&path=color:0x0000ff80|weight:1|${trip.arrival_lat}, ${trip.arrival_lon}|${trip.departure_lat}, ${trip.departure_lon}'
            style={{ width: '100%', height: '100%' }}
          />
        </div>
        <div className='bg-white'>
          <div className='price-container flex space-between align-center'>
            <span className='clear gray sz-13'>
              {t('search.item.from')}
              <span className='bold black'> {trip.from_city}</span>
              {t('search.item.on_low')}
              <span className='bold black'>
                {/* <fmt:formatDate value="${trip.etd}" pattern="dd/MM/yyyy"/> */}
              </span>
              {t('search.item.at')}
              <span className='bold black'>
                {/* <fmt:formatDate value="${trip.etd}" pattern="HH:mm"/> */}
              </span>
              <br />
              {t('search.item.arrive')}
              <span className='bold black'> ${trip.to_city}</span>
              {t('search.item.on_low')}
              <span className='bold black'>
                {/* <fmt:formatDate value="${trip.eta}" pattern="dd/MM/yyyy"/> */}
              </span>
              {t('search.item.at')}
              <span className='bold black'>
                {/* <fmt:formatDate value="${trip.eta}" pattern="HH:mm"/> */}
              </span>
            </span>
            <div>
              <span className='price gray'>
                <span className='bold black'>
                  ${trip.cost}/{t('search.item.each')}
                </span>
              </span>

              {!trip.reserved && (
                <form
                  className='inline-block'
                  method='post'
                  action='${url}trip/${trip.id}/reserve'
                >
                  <button className='login-button'>
                    {t('search.item.reserve')}
                  </button>
                </form>
              )}
              {trip.reserved && (
                <form
                  className='inline-block'
                  method='post'
                  action='${url}trip/${trip.id}/unreserve'
                >
                  <button className='login-button main-color'>
                    {t('search.item.unreserve')}
                  </button>
                </form>
              )}
            </div>
          </div>

          <div className='pool-features flex space-between align-center'>
            <div className='features-container' />
            <div className='seats-container'>
              <span className='seats bold gray'>
                <img
                  className='seats-icon'
                  src="<c:url value='/static/images/seats.png' />"
                />
                {trip.available_seats} {t('search.item.available')}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}
export default Search
