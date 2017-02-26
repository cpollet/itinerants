import Event from '../components/Event';
import {toggleAvailability} from '../reducers/actions';
import {connect} from 'react-redux';

function mapDispatchToProps(dispatch) {
    return {
        toggle: (eventId) => dispatch(toggleAvailability(eventId))
    };
}

export default connect(null, mapDispatchToProps)(Event);
