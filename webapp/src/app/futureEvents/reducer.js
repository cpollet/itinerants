import {FAIL_FUTURE_EVENTS, RECEIVE_FUTURE_EVENTS, REQUEST_FUTURE_EVENTS} from '../actions';

const initialState = {
    ready: false,
    didInvalidate: false,
    lastUpdated: 0,
    realPersonId: null,
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
            return Object.assign({}, initialState, {
                realPersonId: action.personId,
            });
        case RECEIVE_FUTURE_EVENTS:
            return Object.assign({}, initialState, {
                ready: true,
                lastUpdated: action.receivedAt,
                items: action.items,
                realPersonId: state.realPersonId,
            });
        case FAIL_FUTURE_EVENTS:
            return Object.assign({}, initialState, {
                ready: true,
                realPersonId: state.realPersonId,
            });
    }

    return state;
}
