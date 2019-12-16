import React, { useEffect, useState, Fragment } from 'react'
import {
  BrowserRouter as Router,
  Route,
  Link,
  Redirect,
  useParams
} from 'react-router-dom'
import styled from 'styled-components'
import { useTranslation } from 'react-i18next'
import { connect } from 'react-redux'
import MDSpinner from 'react-md-spinner'
import { NotificationManager } from 'react-notifications'

import profileHeroCss from '../../styles/profile_hero'
import poolListCss from '../../styles/pool_list'
import profileCss from '../../styles/profile'
import reviewItemCss from '../../styles/review_item'

import Hero from '../../components/Hero'
import ReviewItem from '../../components/ReviewItem'
import HistoryItem from '../../components/HistoryItem'
import Loading from '../../components/Loading'

import { routes } from '../../App'
import TripPast from './TripPast'
import Trip from './Trip'
import Destiny from './Reservation'

import { getProfileById } from '../../services/User'
import { getReservationsByUser } from '../../services/Reservation'
import { getHistoryByUser } from '../../services/History'
import { getReviewsByUser } from '../../services/Review'
import { getTripsByUser } from '../../services/Trip'

import { requestCatch } from '../../utils/fetch'

const ProfileContainer = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
`

const PagePickerContainer = styled.div`
  display: inline-flex;
  background: #6cd298;
  align-items: center;
  justify-content: space-around;
  padding: 10px;
  border-radius: 4px;
`

const PagePickerButton = styled.a`
  cursor: pointer;

  ${({ disabled }) =>
    disabled &&
    `
  cursor: not-allowed;
  color: darkgray;
  `}
`

const Page = styled.span`
  margin: 0 5px;
`

const isLoadingFirstTime = (
  { histories, trips, reservations, reviews },
  isOwnProfile
) => {
  const result =
    (isOwnProfile ? histories : false) ||
    trips ||
    (isOwnProfile ? reservations : false) ||
    reviews

  return result
}

const PagePicker = ({
  loading,
  isLastPage,
  page,
  onPreviousPage,
  onNextPage
}) => {
  return (
    <PagePickerContainer>
      <PagePickerButton
        disabled={loading || page === 0}
        onClick={onPreviousPage}
      >
        {'<Prev'}
      </PagePickerButton>
      <Page> {page} </Page>
      <PagePickerButton disabled={loading || isLastPage} onClick={onNextPage}>
        {'Next>'}
      </PagePickerButton>
    </PagePickerContainer>
  )
}

const setData = setter => ({ data, isLastPage }) =>
  setter(current => ({ ...current, data, loading: false, isLastPage }))

const webDataInitial = { data: [], loading: true, page: 0 }

const setPage = (obj, setter) => newPage =>
  setter({ ...obj, loading: true, page: newPage })

const Profile = ({ token, hero_message, loggedUser, dispatch }) => {
  const { t } = useTranslation()
  const [reviews, setReviews] = useState(webDataInitial)
  const [trips, setTrips] = useState(webDataInitial)
  const [reservations, setReservations] = useState(webDataInitial)
  const [histories, setHistories] = useState(webDataInitial)
  const [user, setUser] = useState(null)
  const [firstLoadInfo, setFirstLoadInfo] = useState({
    // true = loading below
    reviews: true,
    trips: true,
    reservations: true,
    histories: true
  })
  const { userId } = useParams()

  const isLogged = !!token
  const isLoadingUser = user === null

  const isOwnProfile = loggedUser && userId === `${loggedUser.id}`

  const translationPrefix = isOwnProfile ? 'user.profile' : 'user.profileOther'

  const onUpdateTrip = (trip, reason) => {
    if (reason === 'unreserve') {
      const { data: ts } = trips
      const tripIdx = ts.findIndex(t => t.id === trip.id)
      const updatedTrips = [...ts]
      updatedTrips[tripIdx] = trip
      setData(setTrips)(updatedTrips)
      return
    } else if (reason === 'delete') {
      setTrips(webDataInitial)
      getTripsByUser(userId)
        .then(setData(setTrips))
        .catch(requestCatch)
    }
  }

  // Only reload trips on delete. Keep same page
  const onUpdateTrip2 = () => {
    setTrips({ ...webDataInitial, page: trips.page })
    getTripsByUser(userId, trips.page)
      .then(setData(setTrips))
      .catch(requestCatch)
  }

  const areAllEmpty = [
    ...(isOwnProfile ? [histories, reservations] : []),
    reviews,
    trips
  ].every(({ data, loading, page }) => data.length === 0 && page === 0)

  useEffect(() => {
    const fetchUsers = async () => {
      getProfileById(userId)
        .then(setUser)
        .catch(requestCatch)
    }

    setUser(null)
    setFirstLoadInfo({
      reviews: true,
      trips: true,
      reservations: true,
      histories: true
    })
    setReservations(webDataInitial)
    setHistories(webDataInitial)
    setReviews(webDataInitial)
    setTrips(webDataInitial)
    fetchUsers()
  }, [userId])

  useEffect(() => {
    if (!user) return
    getReviewsByUser(user.id, reviews.page)
      .then(setData(setReviews))
      .then(() => {
        setFirstLoadInfo(firstLoadInfo => ({
          ...firstLoadInfo,
          reviews: false
        }))
      })
      .catch(requestCatch)
  }, [reviews.page, user])

  useEffect(() => {
    if (!user) return
    isOwnProfile &&
      getHistoryByUser(user.id, histories.page)
        .then(setData(setHistories))
        .then(
          setFirstLoadInfo(firstLoadInfo => ({
            ...firstLoadInfo,
            histories: false
          }))
        )
        .catch(requestCatch)
  }, [histories.page, user])

  useEffect(() => {
    if (!user) return
    isOwnProfile &&
      getReservationsByUser(user.id, reservations.page)
        .then(setData(setReservations))
        .then(
          setFirstLoadInfo(firstLoadInfo => ({
            ...firstLoadInfo,
            reservations: false
          }))
        )
        .catch(requestCatch)
  }, [reservations.page, user])

  useEffect(() => {
    if (!user) return
    getTripsByUser(user.id, trips.page)
      .then(setData(setTrips))
      .then(
        setFirstLoadInfo(firstLoadInfo => ({ ...firstLoadInfo, trips: false }))
      )
      .catch(requestCatch)
  }, [trips.page, user])

  useEffect(() => {
    if (user) {
      window.document.title = `Carpul | ${user.first_name} ${user.last_name} is awesome`
    } else {
      window.document.title = `Carpul | Loading user...`
    }
  }, [user])

  return (
    <React.Fragment>
      <style jsx>{poolListCss}</style>
      <style jsx>{profileCss}</style>
      <style jsx>{reviewItemCss}</style>
      <style jsx>{profileHeroCss}</style>
      {isLoadingUser ? (
        <div className='flex-center spinner-class'>
          <MDSpinner size={36} />
        </div>
      ) : (
        <React.Fragment>
          <Hero
            user={user}
            onUserUpdate={setUser}
            hero_message={t(`${translationPrefix}.hero`, {
              user: user.first_name
            })}
            editable={isOwnProfile}
          />
          {isLoadingFirstTime(firstLoadInfo, isOwnProfile) ? (
            <div className='flex-center spinner-class'>
              <MDSpinner size={36} />
            </div>
          ) : areAllEmpty ? (
            <EmptyProfile isOwnProfile={isOwnProfile} />
          ) : (
            <ProfileContainer>
              <ReviewsSection
                isFirstLoad={isLoadingFirstTime(firstLoadInfo, isOwnProfile)}
                isOwnProfile={isOwnProfile}
                reviews={reviews}
              />
              {isOwnProfile && (
                <HistoriesSection
                  isFirstLoad={isLoadingFirstTime(firstLoadInfo, isOwnProfile)}
                  histories={histories}
                  setPage={setPage(histories, setHistories)}
                />
              )}
              {isOwnProfile && (
                <ReservationsSection
                  isFirstLoad={isLoadingFirstTime(firstLoadInfo, isOwnProfile)}
                  reservations={reservations}
                  setPage={setPage(reservations, setReservations)}
                />
              )}
              <TripsSection
                isFirstLoad={isLoadingFirstTime(firstLoadInfo, isOwnProfile)}
                isOwnProfile={isOwnProfile}
                trips={trips}
                onUpdate={onUpdateTrip2}
                setPage={setPage(trips, setTrips)}
              />
            </ProfileContainer>
          )}
        </React.Fragment>
      )}
    </React.Fragment>
  )
}

const ProfileSection = styled.section`
  box-sizing: border-box;
  min-width: 370px;
  padding: 20px;
  padding-top: 40px;
`

const SectionHeader = styled.h3`
  font-size: 25px;
  font-weight: 100;
  margin: 0;
  color: rgba(0, 0, 0, 0.8);
  margin-bottom: 30px;

  ${({ empty }) =>
    empty &&
    `margin-top: 30px;
	font-size: 20px;
  color: #a0a0a0;`}
`

const List = styled.ul`
  list-style-type: none;
  padding: 0;
  margin-top: 40px;
`

const TripsList = styled.ul`
  list-style-type: none;
  padding: 0;
  margin-top: 40px;
  display: flex;
  flex-wrap: wrap;
`

const SpinnerContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 200px;
`

const ProfileSpinner = () => {
  return (
    <SpinnerContainer>
      <MDSpinner size={36} />
    </SpinnerContainer>
  )
}

const isLastPage = webdata => webdata.isLastPage // ({ loading, data }) => !loading && data.length === 0

const onNext = (webdata, page, setPage) => () =>
  !isLastPage(webdata) && setPage(page + 1)

const onPrev = (page, setPage) => () => page - 1 >= 0 && setPage(page - 1)

const ReviewsSection = ({ isFirstLoad, reviews, isOwnProfile }) => {
  const [page, setPage] = useState(0)
  const { t } = useTranslation()
  const { data, loading } = reviews
  const webdata = reviews

  const translationPrefix = isOwnProfile ? 'user.profile' : 'user.profileOther'

  if (isFirstLoad) {
    return <ProfileSpinner />
  }

  return (
    <ProfileSection>
      <SectionHeader>{t(`${translationPrefix}.reviews`)}</SectionHeader>

      {loading ? (
        <ProfileSpinner />
      ) : data.length === 0 ? (
        <SectionHeader empty>
          {t(`${translationPrefix}.empty_review`)}
        </SectionHeader>
      ) : (
        <List>
          {data.map(review => (
            <ReviewItem review={review} key={review.id} />
          ))}
        </List>
      )}
      <PagePicker
        loading={loading}
        page={page}
        isLastPage={isLastPage(webdata)}
        onNextPage={onNext(webdata, page, setPage)}
        onPreviousPage={onPrev(page, setPage)}
      />
    </ProfileSection>
  )
}

const HistoriesSection = ({ isFirstLoad, histories, setPage }) => {
  // const [page, setPage] = useState(0)
  const { t } = useTranslation()
  const webdata = histories
  const { page, loading } = histories

  if (isFirstLoad) {
    return <ProfileSpinner />
  }

  return (
    <ProfileSection>
      <SectionHeader>{t('user.profile.history')}</SectionHeader>

      {histories.loading ? (
        <ProfileSpinner />
      ) : histories.data.length === 0 ? (
        <SectionHeader empty>{t('user.profile.empty_histories')}</SectionHeader>
      ) : (
        <List>
          {histories.data.map(history => (
            <HistoryItem history={history} key={history.id} />
          ))}
        </List>
      )}
      <PagePicker
        loading={loading}
        page={page}
        isLastPage={isLastPage(webdata)}
        onNextPage={onNext(webdata, page, setPage)}
        onPreviousPage={onPrev(page, setPage)}
      />
    </ProfileSection>
  )
}

const ReservationsSection = ({ isFirstLoad, reservations, setPage }) => {
  const { t } = useTranslation()
  const { data, loading, page } = reservations
  const webdata = reservations

  if (isFirstLoad) {
    return <ProfileSpinner />
  }

  return (
    <ProfileSection>
      <SectionHeader>{t('user.profile.next')}</SectionHeader>

      <Link className='no-margin login-button' to='/'>
        {t('user.profile.find')}
      </Link>

      {loading ? (
        <ProfileSpinner />
      ) : data.length === 0 ? (
        <SectionHeader empty>
          {t('user.profile.empty_reservations')}
        </SectionHeader>
      ) : (
        <List>
          {data.map(reservation =>
            reservation.expired ? (
              reservation.reviewed ? (
                <Destiny trip={reservation} key={reservation.id} />
              ) : (
                <TripPast reservation={reservation} key={reservation.id} />
              )
            ) : (
              <Destiny
                trip={reservation}
                editable={true}
                key={reservation.id}
              />
            )
          )}
        </List>
      )}
      <PagePicker
        loading={loading}
        page={page}
        isLastPage={isLastPage(webdata)}
        onNextPage={onNext(webdata, page, setPage)}
        onPreviousPage={onPrev(page, setPage)}
      />
    </ProfileSection>
  )
}

const TripsSection = ({
  isFirstLoad,
  trips,
  isOwnProfile,
  onUpdate,
  setPage
}) => {
  const { t } = useTranslation()
  const { data, loading, page } = trips
  const webdata = trips

  const translationPrefix = isOwnProfile ? 'user.profile' : 'user.profileOther'
  const TripList = isOwnProfile ? List : TripsList

  if (isFirstLoad) {
    return <ProfileSpinner />
  }

  return (
    <ProfileSection>
      <SectionHeader>{t(`${translationPrefix}.trips`)}</SectionHeader>

      {isOwnProfile && (
        <Link className='no-margin login-button' to={routes.addTrip}>
          {t('user.profile.new')}
        </Link>
      )}

      {loading ? (
        <ProfileSpinner />
      ) : data.length > 0 ? (
        <TripList>
          {data.map(trip => (
            <Trip
              trip={trip}
              key={trip.id}
              isOwner={isOwnProfile}
              onUpdate={onUpdate}
            />
          ))}
        </TripList>
      ) : (
        <SectionHeader empty>
          {t(`${translationPrefix}.empty_trips`)}
        </SectionHeader>
      )}
      <PagePicker
        loading={loading}
        page={page}
        isLastPage={isLastPage(webdata)}
        onNextPage={onNext(webdata, page, setPage)}
        onPreviousPage={onPrev(page, setPage)}
      />
    </ProfileSection>
  )
}

const EmptyProfileContainer = styled.div`
  text-align: center;
  width: 100%;
  margin-top: 20px;
  margin-bottom: 50px;
`

const EmptyProfile = ({ isOwnProfile }) => {
  const { t } = useTranslation()

  const translationPrefix = isOwnProfile ? 'user.profile' : 'user.profileOther'

  return (
    <ProfileContainer>
      <EmptyProfileContainer>
        <SectionHeader empty>
          {t(`${translationPrefix}.empty_title`)}
        </SectionHeader>
        <h4 className='empty-subtitle'>
          {t(`${translationPrefix}.empty_subtitle`)}
        </h4>
        {isOwnProfile && (
          <Link className='login-button empty-button' to={routes.addTrip}>
            {t(`${translationPrefix}.empty_new`)}
          </Link>
        )}
        <Link className='login-button empty-button' to='/'>
          {t(`${translationPrefix}.empty_find`)}
        </Link>
      </EmptyProfileContainer>
    </ProfileContainer>
  )
}
export default connect(({ user, reservations, token }) => ({
  loggedUser: user,
  reservations,
  token
}))(Profile)
