import {
    DECREASE_SYNC_TIMEOUT,
    INVALIDATE_STATE,
    RECEIVE_AVAILABILITIES,
    SYNC_ERROR,
    SYNC_FAILURE,
    SYNC_START,
    SYNC_SUCCESS,
    TOGGLE_AVAILABILITY
} from '../actions';

const initialState = {
    events: [],
    serverSync: {
        retryCount: 4,
        stale: false,
        syncPending: false,
        syncTimeoutMs: 0,
        syncFailure: false,
    },
};

function eventsList(events, eventId) {
    if (events.indexOf(eventId) === -1) {
        return [...events, eventId];
    }

    return events.filter((v) => v !== eventId);
}

export default function (state = initialState, action) {
    switch (action.type) {
        case TOGGLE_AVAILABILITY:
            return Object.assign({}, state, {
                events: eventsList(state.events, action.eventId)
            });
        case RECEIVE_AVAILABILITIES:
            return Object.assign({}, state, {
                events: action.availabilities
            });

        case INVALIDATE_STATE:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    stale: true,
                    syncFailure: false,
                    syncTimeoutMs: 3000,
                })
            });
        case SYNC_START:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    stale: false,
                    syncPending: true,
                })
            });
        case SYNC_SUCCESS:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: initialState.serverSync.retryCount,
                    syncPending: false,
                })
            });
        case SYNC_ERROR:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: Math.max(0, state.serverSync.retryCount - 1),
                    stale: true,
                    syncPending: false,
                })
            });
        case SYNC_FAILURE:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: initialState.serverSync.retryCount,
                    syncFailure: true,
                    stale: false,
                    syncPending: false,
                })
            });
        case DECREASE_SYNC_TIMEOUT:
            if (state.serverSync.syncTimeoutMs > 0) {
                return Object.assign({}, state, {
                    serverSync: Object.assign({}, state.serverSync, {
                        syncTimeoutMs: Math.max(0, state.serverSync.syncTimeoutMs - 1000),
                    })
                });
            }
            return state;
        default:
            return state;
    }
}