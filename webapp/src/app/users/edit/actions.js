import {authenticatedFetch, guardedFetch} from '../../helpers';
import {
    FAIL_PROFILE,
    FAIL_VALIDATION_PROFILE,
    FETCH_PROFILE,
    RECEIVED_PROFILE,
    SAVE_PROFILE,
    SAVED_PROFILE
} from '../../actions';

export function fetchProfile() {
    return function (dispatch, getState) {
        dispatch({
            type: FETCH_PROFILE,
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
                    type: RECEIVED_PROFILE,
                    data: {
                        firstName: json.firstName,
                        lastName: json.lastName,
                        email: json.email,
                    }
                });
            })
            .catch(ex => {
                dispatch({
                    type: FAIL_PROFILE,
                });
                throw ex;
            }));
    };
}

export function saveProfile(data) {
    return function (dispatch, getState) {
        dispatch({
            type: SAVE_PROFILE
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
                        type: FAIL_VALIDATION_PROFILE,
                        errors: json.errors
                    });
                });
                return;
            }

            dispatch({
                type: SAVED_PROFILE,
            });
        }).catch(ex => {
            dispatch({
                type: FAIL_PROFILE,
            });
            throw ex;
        }));
    };
}
