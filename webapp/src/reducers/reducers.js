import {
    REQUEST_FUTURE_EVENTS,
    RECEIVE_FUTURE_EVENTS,
    SYNC_START,
    SYNC_SUCCESS,
    SYNC_ERROR,
    SYNC_FAILURE,
    TOGGLE_AVAILABILITY,
    INVALIDATE_STATE,
    DECREASE_SYNC_TIMEOUT
} from './actions';

const initialState = {
    auth: {
        personId: 1,
    },
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
        retryCount: 4,
        stale: false,
        syncPending: false,
        syncTimeoutMs: 0,
        syncFailure: false,
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
                syncFailure: false,
                syncTimeoutMs: 3000,
            });
        case SYNC_START:
            return Object.assign({}, state, {
                stale: false,
                syncPending: true,
            });
        case SYNC_SUCCESS:
            return Object.assign({}, state, {
                retryCount: initialState.serverSync.retryCount,
                syncPending: false,
            });
        case SYNC_ERROR:
            return Object.assign({}, state, {
                retryCount: Math.max(0, state.retryCount - 1),
                stale: true,
                syncPending: false,
            });
        case SYNC_FAILURE:
            return Object.assign({}, state, {
                retryCount: initialState.serverSync.retryCount,
                syncFailure: true,
                stale: false,
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
        auth: state.auth,
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
        serverSync: serverSyncReducer(state.serverSync, action),
    };
}
