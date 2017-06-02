import {FAIL_FUTURE_EVENTS, RECEIVE_FUTURE_EVENTS, REQUEST_FUTURE_EVENTS} from '../actions';

const initialState = {
    ready: false,
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
            return initialState;
        case RECEIVE_FUTURE_EVENTS:
            return Object.assign({}, initialState, {
                ready: true,
                lastUpdated: action.receivedAt,
                items: action.items,
            });
        case FAIL_FUTURE_EVENTS:
            return Object.assign({}, initialState, {
                ready: true,
            });
    }

    return state;
}
