import {FUTURE_EVENTS_FETCH_ERROR, FUTURE_EVENTS_FETCH_SUCCESS, FUTURE_EVENTS_FETCH} from '../actions';

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
        case FUTURE_EVENTS_FETCH:
            return Object.assign({}, initialState, {
                realPersonId: action.personId,
            });
        case FUTURE_EVENTS_FETCH_SUCCESS:
            return Object.assign({}, initialState, {
                ready: true,
                lastUpdated: action.receivedAt,
                items: action.items,
                realPersonId: state.realPersonId,
            });
        case FUTURE_EVENTS_FETCH_ERROR:
            return Object.assign({}, initialState, {
                ready: true,
                realPersonId: state.realPersonId,
            });
    }

    return state;
}
