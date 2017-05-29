import {authenticatedFetch} from '../../helpers';

export function create(data) {
    return function (dispatch, getState) {
        authenticatedFetch('/api/people', dispatch, getState(), {
            method: 'POST',
            body: JSON.stringify({
                firstName: data.firstName,
                lastName: data.lastName,
                username: data.username,
                email: data.email,
                targetRatio: data.targetRatio,
            }),
            headers: {
                'Content-Type': 'application/json',
            },
        }).then((response) => {
            if (response.status === 200) {
                // do something
            }

            // do something else
        }).catch((/* ex */) => {
            // handle fatal error
        });
    };
}
