import {FAIL_FUTURE_EVENTS, RECEIVE_FUTURE_EVENTS, REQUEST_FUTURE_EVENTS} from '../actions';

const initialState = {
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
    ],
};

export default function (state = initialState, action) {
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
        case FAIL_FUTURE_EVENTS:
            return Object.assign({}, state, {
                isFetching: false,
                didInvalidate: false,
                lastUpdated: 0,
                items: []
            });
        default:
            return state;
    }
}
