import Logout from '../Logout';
import {logout} from '../../reducers/actions';
import {connect} from 'react-redux';

function mapDispatchToProps(dispatch) {
    return {
        logout: () => dispatch(logout())
    };
}

export default connect(null, mapDispatchToProps)(Logout);
