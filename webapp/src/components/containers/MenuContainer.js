import {connect} from 'react-redux';
import Menu from '../Menu';

function mapStateToProps(state) {
    return {
        isAdmin: state.app.auth.roles.indexOf('admin') > -1,
        isAuthenticated: state.app.auth.token !== null
    };
}

export default connect(mapStateToProps, null)(Menu);
