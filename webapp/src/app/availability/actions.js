import {
    AVAILABILITY_DECREASE_SAVE_TIMEOUT,
    AVAILABILITY_INVALIDATE_STATE,
    AVAILABILITY_SAVE_RETRY,
    AVAILABILITY_SAVE_ERROR,
    AVAILABILITY_SAVE_START,
    AVAILABILITY_SAVE_SUCCESS,
    AVAILABILITY_TOGGLE
} from '../actions';
import {authenticatedFetch} from '../helpers';

export function toggleAvailability(eventId) {
    return function (dispatch) {
        dispatch({
            type: AVAILABILITY_TOGGLE,
            eventId: eventId,
        });
        dispatch({
            type: AVAILABILITY_INVALIDATE_STATE,
        });
    };
}

export function sync() {
    return function (dispatch, getState) {
        dispatch({
            type: AVAILABILITY_SAVE_START,
        });

        const {futureEvents, availabilities, auth} = getState().app;

        const personId = futureEvents.realPersonId || auth.personId;

        Promise.all([]
            .concat(futureEvents.items
                .map((e) => e.eventId)
                .filter((eventId) => availabilities.events.indexOf(eventId) < 0)
                .map((eventId) => ({
                    action: 'DELETE',
                    data: {
                        eventId: eventId,
                        personId: personId,
                    }
                })))
            .concat(availabilities.events
                .map((eventId) => ({
                    action: 'PUT',
                    data: {
                        eventId: eventId,
                        personId: personId,
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
                    type: AVAILABILITY_SAVE_SUCCESS,
                });
            })
            .catch((/* error */) => {
                if (availabilities.serverSync.retryCount === 0) {
                    dispatch({
                        type: AVAILABILITY_SAVE_ERROR,
                    });
                } else {
                    setTimeout(() => dispatch({
                        type: AVAILABILITY_SAVE_RETRY,
                    }), 5 * 1000);
                }
            });
    };
}

export function decreaseSyncTimeout() {
    return {
        type: AVAILABILITY_DECREASE_SAVE_TIMEOUT,
    };
}
