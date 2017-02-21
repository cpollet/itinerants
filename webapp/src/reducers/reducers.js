import {LOAD_FUTURE_EVENTS} from './actions';

const initialState = {
    pastEvents: [],
    futureEvents: []
};

export default function reducer(state = initialState, action) {
    switch (action.type) {
        case LOAD_FUTURE_EVENTS:
            return Object.assign({}, state, {
                futureEvents: [{
                    eventId: 0,
                    name: 'Event name',
                    dateTime: '2017-02-22T19:00:00',
                    availablePeople: []
                }]
            });

        default:
            return state;
    }
}