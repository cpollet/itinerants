import {
    DECREASE_SYNC_TIMEOUT,
    INVALIDATE_STATE,
    SYNC_ERROR,
    SYNC_FAILURE,
    SYNC_START,
    SYNC_SUCCESS,
    TOGGLE_AVAILABILITY
} from '../actions';
import {authenticatedFetch} from '../helpers';

export function toggleAvailability(eventId) {
    return function (dispatch) {
        dispatch({
            type: TOGGLE_AVAILABILITY,
            eventId: eventId,
        });
        dispatch({
            type: INVALIDATE_STATE,
        });
    };
}

export function sync() {
    return function (dispatch, getState) {
        // console.log('sync...');
        dispatch({
            type: SYNC_START,
        });

        const {futureEvents, availabilities, auth} = getState().app;

        Promise.all([]
            .concat(futureEvents.items
                .map((e) => e.eventId)
                .filter((eventId) => availabilities.events.indexOf(eventId) < 0)
                .map((eventId) => ({
                    action: 'DELETE',
                    data: {
                        eventId: eventId,
                        personId: auth.personId,
                    }
                })))
            .concat(availabilities.events
                .map((eventId) => ({
                    action: 'PUT',
                    data: {
                        eventId: eventId,
                        personId: auth.personId,
                    }
                })))
            .map((action) =>
                new Promise(function (resolve, reject) {
                    authenticatedFetch('/api/availabilities', dispatch, getState(), {
                        method: action.action,
                        body: JSON.stringify(action.data),
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    }).then((response) => {
                        if (response.ok) {
                            resolve(action);
                        } else {
                            response.json().then((msg) => reject(msg));
                        }
                    }).catch((ex) => reject(ex));
                })
            ))
            .then((/* response */) => {
                // console.log('ok', response);
                dispatch({
                    type: SYNC_SUCCESS,
                });
            })
            .catch((/* error */) => {
                // console.log('error', error);
                if (availabilities.serverSync.retryCount === 0) {
                    // console.log('tried max amount of times');
                    dispatch({
                        type: SYNC_FAILURE,
                    });
                } else {
                    // console.log('retry in 5sec');
                    setTimeout(() => dispatch({
                        type: SYNC_ERROR,
                    }), 5 * 1000);
                }
            });
    };
}

export function decreaseSyncTimeout() {
    return {
        type: DECREASE_SYNC_TIMEOUT,
    };
}
