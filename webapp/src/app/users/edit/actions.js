import {authenticatedFetch, guardedFetch} from '../../helpers';
import {
    PROFILE_FETCH_ERROR,
    PROFILE_SAVE_ERROR_VALIDATION,
    PROFILE_FETCH,
    PROFILE_FETCH_SUCCESS,
    PROFILE_SAVE_START,
    PROFILE_SAVE_SUCCESS
} from '../../actions';

export function fetchProfile() {
    return function (dispatch, getState) {
        dispatch({
            type: PROFILE_FETCH,
        });

        const personId = getState().app.auth.personId;

        guardedFetch(dispatch, authenticatedFetch('/api/people/' + personId, dispatch, getState())
            .then((response) => {
                if (!response.ok) {
                    throw 'Received HTTP status code ' + response.status;
                }
                return response.json();
            })
            .then((json) => {
                if (json === null) {
                    throw 'Received null payload';
                }

                dispatch({
                    type: PROFILE_FETCH_SUCCESS,
                    data: {
                        firstName: json.firstName,
                        lastName: json.lastName,
                        email: json.email,
                    }
                });
            })
            .catch(ex => {
                dispatch({
                    type: PROFILE_FETCH_ERROR,
                });
                throw ex;
            }));
    };
}

export function saveProfile(data) {
    return function (dispatch, getState) {
        dispatch({
            type: PROFILE_SAVE_START
        });

        const personId = getState().app.auth.personId;

        guardedFetch(dispatch, authenticatedFetch('/api/people/' + personId, dispatch, getState(), {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (!response.ok && response.status !== 400) {
                throw 'Received HTTP status code ' + response.status;
            }

            if (response.status === 400) {
                response.json().then(json => {
                    dispatch({
                        type: PROFILE_SAVE_ERROR_VALIDATION,
                        errors: json.errors
                    });
                });
                return;
            }

            dispatch({
                type: PROFILE_SAVE_SUCCESS,
            });
        }).catch(ex => {
            dispatch({
                type: PROFILE_FETCH_ERROR,
            });
            throw ex;
        }));
    };
}
