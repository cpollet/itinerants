import {
    ATTENDANCES_FETCH_SUCCESS,
    AVAILABILITIES_FETCH_SUCCESS,
    FUTURE_EVENTS_FETCH,
    FUTURE_EVENTS_FETCH_ERROR,
    FUTURE_EVENTS_FETCH_SUCCESS
} from '../actions';
import {authenticatedFetch, guardedFetch} from '../helpers';

export function fetchFutureEvents(realPersonId = null) {
    return function (dispatch, getState) {
        const personId = realPersonId || getState().app.auth.personId;

        dispatch({
            type: FUTURE_EVENTS_FETCH,
            personId: personId,
        });

        guardedFetch(dispatch, authenticatedFetch('/api/events/future', dispatch, getState())
            .then(response => {
                return response.json();
            })
            .then(json => {
                if (json === null) {
                    throw 'Received null payload';
                }

                dispatch({
                    type: FUTURE_EVENTS_FETCH_SUCCESS,
                    items: json.map(e => ({
                        eventId: e.eventId,
                        name: e.name,
                        dateTime: e.dateTime
                    })),
                    receivedAt: Date.now()
                });
                dispatch({
                    type: AVAILABILITIES_FETCH_SUCCESS,
                    availabilities: json
                        .filter(e => e.availablePeople.filter(p => p.personId === personId).length > 0)
                        .map(e => e.eventId),
                });
                dispatch({
                    type: ATTENDANCES_FETCH_SUCCESS,
                    attendances: json
                        .filter(e => e.attendingPeople.filter(p => p.personId === personId).length > 0)
                        .map(e => e.eventId),
                });

            })
            .catch(ex => {
                dispatch({
                    type: FUTURE_EVENTS_FETCH_ERROR,
                });
                throw ex;
            }));
    };
}
