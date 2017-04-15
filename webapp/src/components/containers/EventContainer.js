import Event from '../Event';
import {toggleAvailability, togglePlanning} from '../../reducers/actions';
import {connect} from 'react-redux';

function mapDispatchToProps(dispatch) {
    return {
        toggleAvailability: (eventId) => dispatch(toggleAvailability(eventId)),
        togglePlanning: (eventId) => dispatch(togglePlanning(eventId))
    };
}

export default connect(null, mapDispatchToProps)(Event);
