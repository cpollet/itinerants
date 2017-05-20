import {connect} from 'react-redux';
import Logout from '../screens/Logout';
import {logout} from '../actions';

function mapDispatchToProps(dispatch) {
    return {
        logout: () => dispatch(logout())
    };
}

export default connect(null, mapDispatchToProps)(Logout);
