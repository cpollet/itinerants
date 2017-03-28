import Login from '../Login';
import {login} from '../../reducers/actions';
import {connect} from 'react-redux';
import constants from '../../constants';

function mapStateToProps(state) {
    return {
        authenticated: state.app.auth.token !== null,
        invalidCredentials: state.app.auth.error === constants.login.invalidCredentials,
        loginExpired: state.app.auth.error === constants.login.sessionExpired,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        login: (username, password) => dispatch(login(username, password))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
