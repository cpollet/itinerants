import {authenticatedFetch} from '../../helpers';
import {FETCH_PROFILE, RECEIVED_PROFILE, SAVE_PROFILE, SAVED_PROFILE} from '../../actions';

export function fetchProfile() {
    return function (dispatch, getState) {
        dispatch({
            type: FETCH_PROFILE,
        });

        const personId = getState().app.auth.personId;

        authenticatedFetch('/api/people/' + personId, dispatch, getState())
            .then((response) => {
                if (response.status === 200) {
                    return response.json();
                }
                return null; // do something useful...
            })
            .then((json) => {
                if (json !== null) {
                    dispatch({
                        type: RECEIVED_PROFILE,
                        data: {
                            firstName: json.firstName,
                            lastName: json.lastName,
                            email: json.email,
                        }
                    });
                }
            })
            .catch((/*ex*/) => {
                // do something useful...
            });
    };
}

export function saveProfile(data) {
    return function (dispatch, getState) {
        dispatch({
            type: SAVE_PROFILE
        });

        const personId = getState().app.auth.personId;

        authenticatedFetch('/api/people/' + personId, dispatch, getState(), {
            method: 'PUT',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.status === 200) {
                dispatch({
                    type: SAVED_PROFILE,
                });
            }
            // handle error
        }).catch((/*ex*/) => {
            // do something useful
        });
    };
}