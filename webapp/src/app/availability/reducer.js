import {
    AVAILABILITY_DECREASE_SAVE_TIMEOUT,
    AVAILABILITY_INVALIDATE_STATE,
    AVAILABILITIES_FETCH_SUCCESS,
    AVAILABILITY_SAVE_RETRY,
    AVAILABILITY_SAVE_ERROR,
    AVAILABILITY_SAVE_START,
    AVAILABILITY_SAVE_SUCCESS,
    AVAILABILITY_TOGGLE
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
        case AVAILABILITY_TOGGLE:
            return Object.assign({}, state, {
                events: eventsList(state.events, action.eventId)
            });
        case AVAILABILITIES_FETCH_SUCCESS:
            return Object.assign({}, state, {
                events: action.availabilities
            });

        case AVAILABILITY_INVALIDATE_STATE:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    stale: true,
                    syncFailure: false,
                    syncTimeoutMs: 3000,
                })
            });
        case AVAILABILITY_SAVE_START:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    stale: false,
                    syncPending: true,
                })
            });
        case AVAILABILITY_SAVE_SUCCESS:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: initialState.serverSync.retryCount,
                    syncPending: false,
                })
            });
        case AVAILABILITY_SAVE_RETRY:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: Math.max(0, state.serverSync.retryCount - 1),
                    stale: true,
                    syncPending: false,
                })
            });
        case AVAILABILITY_SAVE_ERROR:
            return Object.assign({}, state, {
                serverSync: Object.assign({}, state.serverSync, {
                    retryCount: initialState.serverSync.retryCount,
                    syncFailure: true,
                    stale: false,
                    syncPending: false,
                })
            });
        case AVAILABILITY_DECREASE_SAVE_TIMEOUT:
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