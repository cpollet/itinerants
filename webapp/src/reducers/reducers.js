import {
    REQUEST_FUTURE_EVENTS,
    RECEIVE_FUTURE_EVENTS,
    FAIL_FUTURE_EVENTS,
    SYNC_START,
    SYNC_SUCCESS,
    SYNC_ERROR,
    SYNC_FAILURE,
    RECEIVE_AVAILABILITIES,
    TOGGLE_AVAILABILITY,
    INVALIDATE_STATE,
    DECREASE_SYNC_TIMEOUT,
    LOGIN_SUCCESS,
    LOGIN_EXPIRED,
    LOGIN_INVALID
} from './actions';
import constants from '../constants';

const initialState = {
    auth: {
        personId: null,
        token: null,
        username: null,
        roles: [],
        error: 'NOT_AUTHENTICATED'
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
        case RECEIVE_AVAILABILITIES:
            return action.availabilities;
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

function authReducer(state, action) {
    switch (action.type) {
        case LOGIN_SUCCESS:
            return Object.assign({}, state, {
                personId: action.personId,
                username: action.username,
                token: action.token,
                roles: action.roles,
                error: null
            });
        case LOGIN_INVALID:
            return Object.assign({}, state, {
                personId: null,
                username: null,
                token: null,
                roles: [],
                error: constants.login.invalidCredentials
            });
        case LOGIN_EXPIRED:
            return Object.assign({}, state, {
                token: null,
                error: constants.login.sessionExpired
            });
    }
    return state;
}

export default function reducer(state = initialState, action) {
    return {
        auth: authReducer(state.auth, action),
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
        serverSync: serverSyncReducer(state.serverSync, action),
    };
}
