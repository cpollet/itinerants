import {
    REQUEST_FUTURE_EVENTS,
    RECEIVE_FUTURE_EVENTS,
    SYNCHRONIZE_STATE,
    SYNCHRONIZED_STATE_SUCCESS,
    SYNCHRONIZED_STATE_ERROR,
    TOGGLE_AVAILABILITY,
    INVALIDATE_STATE,
    DECREASE_SYNC_TIMEOUT
} from './actions';

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
    serverSync: {
        stale: false,
        syncPending: false,
        syncTimeoutMs: 0,
    },
};

function availabilityReducer(state, action) {
    switch (action.type) {
        case TOGGLE_AVAILABILITY:
            if (state.indexOf(action.eventId) == -1) {
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
        default:
            return state;
    }
}

function serverSyncReducer(state, action) {
    switch (action.type) {
        case INVALIDATE_STATE:
            return Object.assign({}, state, {
                stale: true,
                syncTimeoutMs: 3000,
            });
        case SYNCHRONIZE_STATE:
            return Object.assign({}, state, {
                stale: false,
                syncPending: true,
            });
        case SYNCHRONIZED_STATE_SUCCESS:
            return Object.assign({}, state, {
                syncPending: false,
            });
        case SYNCHRONIZED_STATE_ERROR:
            return Object.assign({}, state, {
                stale: true,
                syncPending: false,
            });
        case DECREASE_SYNC_TIMEOUT:
            if (state.syncTimeoutMs > 0) {
                return Object.assign({}, state, {
                    syncTimeoutMs: Math.max(0, state.syncTimeoutMs - 1000),
                });
            }
            return state;
        default:
            return state;
    }
}

export default function reducer(state = initialState, action) {
    return {
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
        serverSync: serverSyncReducer(state.serverSync, action),
    };
}
