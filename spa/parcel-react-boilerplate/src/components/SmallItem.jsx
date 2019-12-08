import React, { Fragment } from 'react'
import { useTranslation } from 'react-i18next'
import { format } from 'date-fns'
import styled from 'styled-components'
import profileHeroCss from '../styles/profile_hero'
import poolListCss from '../styles/pool_list'
import profileCss from '../styles/profile'
import reviewItemCss from '../styles/review_item'

const ItemContainer = styled.div`
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
  width: 300px;
  min-height: 275px;
  text-align: center;
  color: white;
  padding: 20px;
  background: url('../images/cabin.jpg');
  background-size: cover;
  background-color: rgba(0, 0, 0, 0.2);
  background-blend-mode: darken;
  background-position: center;
  border-radius: 5px;
`

const HeaderContainer = styled.div`
  margin-bottom: 10px;
`

const Header = styled.h4`
  font-weight: 700;
  font-size: 30px;
  margin: 0;
`

const SmallItem = ({ user, trip, hero_message }) => {
  const { t, i18n } = useTranslation()

  const getStyle = () => {
    const seed = `${trip.to_city}`.replace(/\s/g, '')
    return {
      backgroundImage: `url(https://picsum.photos/seed/${seed}/200/300)`
    }
  }

  return (
    <Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>

      <div className='pool-item flex-center' style={getStyle()}>
        <ItemContainer>
          <HeaderContainer>
            <Header>
              {trip.from_city} {t('home.index.small_to')} {trip.to_city}
            </Header>
          </HeaderContainer>

          <div>
            {t('home.index.leave_on')}
            <span className='bold'> {format(trip.etd, 'DD/MM/YYYY')} </span>
            {t('search.item.at')}
            <span className='bold'> {format(trip.etd, 'HH:mm')} </span>
          </div>

          <div className=''>
            {t('home.index.just_price')}
            <span className='bold'> ${trip.cost}</span>
          </div>
          {trip.reserved ? (
            <button className='inline-block CTA login-button main-color'>
              {t('search.item.unreserve')}
            </button>
          ) : (
            <button className='inline-block CTA login-button'>
              {t('search.item.reserve')}
            </button>
          )}
        </ItemContainer>
      </div>
    </Fragment>
  )
}

export default SmallItem
