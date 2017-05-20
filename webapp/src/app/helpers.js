import {push} from 'react-router-redux';
import fetch from 'isomorphic-fetch';
import {loginExpired} from '../app/auth/actions';

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

    return fetch(url, options)
        .then(response => {
            if (response.status === 401) {
                loginExpired()(dispatch);
                dispatch(push('/login'));
            }
            return response;
        });
}
