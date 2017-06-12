import {push} from 'react-router-redux';
import fetch from 'isomorphic-fetch';
import {loginExpired} from '../app/auth/actions';
import {SERVER_ERROR, SERVER_ERROR_RESET} from './actions';

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
        type: SERVER_ERROR_RESET,
    });

    return fetch(url, options)
        .then(response => {
            if (!response.ok) {
                console.log('throws ex');
                throw {
                    message: 'Received HTTP status code ' + response.status,
                    response: response,
                };
            }

            return response;
        });
}

export function guardedFetch(dispatch, fetchPromise) {
    return fetchPromise.catch(ex => {
        console.log('catch ex');
        if (ex.hasOwnProperty('response') && ex.response.status === 401) {
            loginExpired()(dispatch);
            dispatch(push('/login'));
            return;
        }

        console.log('Error:', ex);

        dispatch({
            type: SERVER_ERROR,
        });
    });
}
