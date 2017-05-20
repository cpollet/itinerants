import authReducer from './auth/reducer';
import resetPasswordReducer from './resetPassword/reducer';
import planningReducer from './planning/reducer';
import futureEventsReducer from './futureEvents/reducer';
import availabilityReducer from './availability/reducer';

export default function reducer(state = {}, action) {
    return {
        auth: authReducer(state.auth, action),
        futureEvents: futureEventsReducer(state.futureEvents, action),
        availabilities: availabilityReducer(state.availabilities, action),
        planning: planningReducer(state.planning, action),
        resetPassword: resetPasswordReducer(state.resetPassword, action)
    };
}
