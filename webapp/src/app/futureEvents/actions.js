import {
    FAIL_FUTURE_EVENTS,
    RECEIVE_ATTENDANCES,
    RECEIVE_AVAILABILITIES,
    RECEIVE_FUTURE_EVENTS,
    REQUEST_FUTURE_EVENTS
} from '../actions';
import {authenticatedFetch} from '../helpers';

export function fetchFutureEvents() {
    return function (dispatch, getState) {
        dispatch({
            type: REQUEST_FUTURE_EVENTS
        });

        const personId = getState().app.auth.personId;

        return authenticatedFetch('/api/events/future', dispatch, getState())
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                }
                return null;
            })
            .then(json => {
                if (json !== null) {
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
                } else {
                    dispatch({
                        type: FAIL_FUTURE_EVENTS
                    });
                }
            });
    };
}