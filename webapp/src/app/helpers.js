import {push} from 'react-router-redux';
import fetch from 'isomorphic-fetch';
import {loginExpired} from '../app/auth/actions';
import {FETCH_ERROR, FETCH_ERROR_RESET} from './actions';

export function authenticatedFetch(url, dispatch, state, options = {}) {
    function extractOr(object, key, defaultValue) {
        if (typeof object[key] !== 'undefined') {
            return object[key];
        }

        return defaultValue;
    }

    if (state.app.auth.token !== null) {
        options = Object.assign({}, options, {
            headers: Object.assign({}, extractOr(options, 'headers', {}), {
                'X-Auth-Token': state.app.auth.token
            })
        });
    }

    dispatch({
        type: FETCH_ERROR_RESET,
    });

    return fetch(url, options)
        .then(response => {
            if (response.status === 401) {
                loginExpired()(dispatch);
                dispatch(push('/login'));
                // fixme: terminate processing
            }
            return response;
        });
}

export function guardedFetch(dispatch, fetchPromise) {
    return fetchPromise.catch(ex => {
        console.log('Error:', ex);
        dispatch({
            type: FETCH_ERROR,
        });
    });
}
