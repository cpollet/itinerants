export const LOAD_FUTURE_EVENTS = 'LOAD_FUTURE_EVENTS';
export const RESET = 'RESET';

export function loadFutureEvents() {
    return {
        type: LOAD_FUTURE_EVENTS
    };
}

export function resetEvents() {
    return {
        type: RESET
    };
}
