import {
    FAIL_FUTURE_EVENTS,
    RECEIVE_ATTENDANCES,
    RECEIVE_AVAILABILITIES,
    RECEIVE_FUTURE_EVENTS,
    REQUEST_FUTURE_EVENTS
} from '../actions';
import {authenticatedFetch, guardedFetch} from '../helpers';

export function fetchFutureEvents() {
    return function (dispatch, getState) {
        dispatch({
            type: REQUEST_FUTURE_EVENTS
        });

        const personId = getState().app.auth.personId;

        guardedFetch(dispatch, authenticatedFetch('/api/events/future', dispatch, getState())
            .then(response => {
                if (!response.ok) {
                    throw 'Received HTTP status code ' + response.status;
                }
                return response.json();
            })
            .then(json => {
                if (json === null) {
                    throw 'Received null payload';
                }

                dispatch({
                    type: RECEIVE_FUTURE_EVENTS,
                    items: json.map(e => ({
                        eventId: e.eventId,
                        name: e.name,
                        dateTime: e.dateTime
                    })),
                    receivedAt: Date.now()
                });
                dispatch({
                    type: RECEIVE_AVAILABILITIES,
                    availabilities: json
                        .filter(e => e.availablePeople.filter(p => p.personId === personId).length > 0)
                        .map(e => e.eventId),
                });
                dispatch({
                    type: RECEIVE_ATTENDANCES,
                    attendances: json
                        .filter(e => e.attendingPeople.filter(p => p.personId === personId).length > 0)
                        .map(e => e.eventId),
                });

            })
            .catch(ex => {
                dispatch({
                    type: FAIL_FUTURE_EVENTS,
                });
                throw ex;
            }));
    };
}