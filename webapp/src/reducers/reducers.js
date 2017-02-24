import {REQUEST_FUTURE_EVENTS, RECEIVE_FUTURE_EVENTS, RESET, TOGGLE_AVAILABILITY} from './actions';

const initialState = {
    futureEvents: {
        isFetching: false,
        didInvalidate: false,
        lastUpdated: 0,
        items: [
//          {
//              eventId,
//              name,
//              dateTime,
//              availablePeople: []
//          }, ...
        ]
    },
    availabilities: [
//      eventId, ...
    ],
};

function availabilityReducer(state, action) {
    switch (action.type) {
        case TOGGLE_AVAILABILITY:
            if (state.indexOf(action.eventId)==-1) {
                return [...state, action.eventId];
            }

            return state.filter((v) => v !== action.eventId);
        default:
            return state;
    }
}

function futureEventsReducer(state, action) {
    switch (action.type) {
        case REQUEST_FUTURE_EVENTS:
            return Object.assign({}, state, {
                isFetching: true,
                didInvalidate: false,
                lastUpdated: 0,
                items: [],
            });
        case RECEIVE_FUTURE_EVENTS:
            return Object.assign({}, state, {
                isFetching: false,
                didInvalidate: false,
                lastUpdated: action.receivedAt,
                items: action.items
            });
        case RESET:
            return Object.assign({}, state, {
                isFetching: true,
                didInvalidate: false,
                lastUpdated: 0,
                items: []
            });
        default:
            return state;
    }
}

export default function reducer(state = initialState, action) {
    return {
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
    };
}
