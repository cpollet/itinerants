import {REQUEST_FUTURE_EVENTS, RECEIVE_FUTURE_EVENTS, RESET} from './actions';

const initialState = {
    futureEvents: {
        isFetching: false,
        didInvalidate: false,
        lastUpdated: 0,
        items: []
    }
};

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case REQUEST_FUTURE_EVENTS:
            return Object.assign({}, state, Object.assign({}, state.futureEvents, {
                isFetching: true,
                didInvalidate: false
            }));
        case RECEIVE_FUTURE_EVENTS:
            return Object.assign({}, state, {
                futureEvents: {
                    isFetching: false,
                    didInvalidate: false,
                    lastUpdated: action.receivedAt,
                    items: action.items
                }
            });
        case RESET:
            return Object.assign({}, state, {
                futureEvents: {
                    isFetching: true,
                    didInvalidate: false,
                    lastUpdated: 0,
                    items: []
                }
            });
        default:
            return state;
    }
}