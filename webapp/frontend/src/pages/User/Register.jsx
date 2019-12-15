import React, { useState } from 'react'
import { Link, Redirect } from 'react-router-dom'
import { useTranslation } from 'react-i18next'
import { connect } from 'react-redux'
import { isEmpty } from 'lodash'
import useForm from 'react-hook-form'
import styled from 'styled-components'
import { signupUser } from '../../services/User'
import { loginUser } from '../../services/Auth'
import MDSpinner from 'react-md-spinner'
import { requestCatch } from '../../utils/fetch'

import { routes } from '../../App'

const RegisterButton = styled.button`
  width: 140px;
  height: 38px;
  margin-left: 10px;
  font-size: 16px;
  background: #6cd298;
  text-decoration: none;
  cursor: pointer;
  border: 0;
  border-radius: 3px;
  transition: 0.2s ease-out background;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0;
`

const Register = ({ user }) => {
  const { t, i18n } = useTranslation()
  const [loading, setLoading] = useState(false)
  const { handleSubmit, register, errors, triggerValidation } = useForm({
    mode: 'onChange'
  })

  const onSubmit = async values => {
    setLoading(true)
    try {
      await signupUser(values).catch(requestCatch)
      await loginUser(values.username, values.password)
    } catch (e) {
      setLoading(false)
    }
  }

  const isLogged = !!user
  if (isLogged) return <Redirect to={routes.profile(user.id)} />

  return (
    <div className='flex-center full-height' style={{ background: '#e36f4a' }}>
      <form onSubmit={handleSubmit(onSubmit)} className='user-form'>
        <div className='top-border' />
        <div className='text-container'>
          <span>carpul</span>
          <span className='catchphrase'>{t('user.register.title')}</span>
          <span className='catchphrase-description'>
            {t('user.register.subtitle')}
          </span>
        </div>

        <div className='field-container'>
          <label className='field-label' htmlFor='first_name'>
            {t('user.register.first_name')}
          </label>
          <input
            className={`field ${errors.first_name && 'error'}`}
            ref={register({
              required: 'error',
              minLength: 2,
              maxLength: 30,
              pattern: /^[A-Za-z]+$/
            })}
            type='text'
            name='first_name'
          />
          {errors.first_name && errors.first_name.type === 'minLength' ? (
            <label className='label-error'>
              {t('user.register.errors.name.min')}
            </label>
          ) : null}
          {errors.first_name && errors.first_name.type === 'maxLength' ? (
            <label className='label-error'>
              {t('user.register.errors.name.max')}
            </label>
          ) : null}
          {errors.first_name && errors.first_name.type === 'pattern' ? (
            <label className='label-error'>
              {t('user.register.errors.name.pattern')}
            </label>
          ) : null}

          <label className='field-label' htmlFor='last_name'>
            {t('user.register.last_name')}
          </label>
          <input
            className={`field ${errors.last_name && 'error'}`}
            ref={register({ required: 'error', minLength: 2, maxLength: 20 })}
            type='text'
            name='last_name'
          />
          {errors.last_name && errors.last_name.type === 'minLength' ? (
            <label className='label-error'>
              {t('user.register.errors.name.min')}
            </label>
          ) : null}
          {errors.last_name && errors.last_name.type === 'maxLength' ? (
            <label className='label-error'>
              {t('user.register.errors.name.max')}
            </label>
          ) : null}
          {errors.last_name && errors.last_name.type === 'pattern' ? (
            <label className='label-error'>
              {t('user.register.errors.name.pattern')}
            </label>
          ) : null}

          <label className='field-label' htmlFor='phone_number'>
            {t('user.register.phone_number')}
          </label>
          <input
            className={`field ${errors.phone_number && 'error'}`}
            ref={register({ maxLength: 20, pattern: /^\d*$/ })}
            type='text'
            name='phone_number'
          />
          {errors.phone_number && errors.phone_number.type === 'maxLength' ? (
            <label className='label-error'>
              {t('user.register.errors.phone.max')}
            </label>
          ) : null}
          {errors.phone_number && errors.phone_number.type === 'pattern' ? (
            <label className='label-error'>
              {t('user.register.errors.phone.pattern')}
            </label>
          ) : null}

          <label className='field-label' htmlFor='username'>
            {t('user.register.username')}
          </label>
          <input
            className={`field ${errors.username && 'error'}`}
            name='username'
            ref={register({
              required: 'error',
              minLength: 5,
              pattern: /^\S+@\S+$/
            })}
            type='email'
          />
          {errors.username && errors.username.type === 'minLength' ? (
            <label className='label-error'>
              {t('user.register.errors.username.min')}
            </label>
          ) : null}
          {errors.username && errors.username.type === 'pattern' ? (
            <label className='label-error'>
              {t('user.register.errors.username.pattern')}
            </label>
          ) : null}

          <label className='field-label' htmlFor='password'>
            {t('user.register.password')}
          </label>
          <input
            className={`field ${errors.password && 'error'}`}
            ref={register({ required: 'error', minLength: 6, maxLength: 100 })}
            type='password'
            name='password'
          />
          {errors.password && errors.password.type === 'minLength' ? (
            <label className='label-error'>
              {t('user.register.errors.password.min')}
            </label>
          ) : null}
          {errors.password && errors.password.type === 'maxLength' ? (
            <label className='label-error'>
              {t('user.register.errors.password.max')}
            </label>
          ) : null}
        </div>

        <div className='actions'>
          <Link to={routes.login} className='create-account'>
            {t('user.register.login')}
          </Link>
          <RegisterButton
            type='submit'
            className='login-button'
            disabled={!isEmpty(errors) || loading}
          >
            {loading ? <MDSpinner size={16} /> : t('user.register.submit')}
          </RegisterButton>
        </div>
      </form>
    </div>
  )
}

export default connect(state => ({ user: state.user }))(Register)
