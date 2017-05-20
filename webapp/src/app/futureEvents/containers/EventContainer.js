import Event from '../components/Event';
import {toggleAvailability} from '../../availability/actions';
import {togglePlanning} from '../../planning/actions';
import {connect} from 'react-redux';

function mapDispatchToProps(dispatch) {
    return {
        toggleAvailability: (eventId) => dispatch(toggleAvailability(eventId)),
        togglePlanning: (eventId) => dispatch(togglePlanning(eventId))
    };
}

export default connect(null, mapDispatchToProps)(Event);
