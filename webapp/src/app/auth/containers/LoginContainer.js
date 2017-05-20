import Login from '../screens/Login';
import {login} from '../actions';
import {connect} from 'react-redux';
import constants from '../constants';

function mapStateToProps(state) {
    return {
        authenticated: state.app.auth.token !== null,
        invalidCredentials: state.app.auth.error === constants.invalidCredentials,
        loginExpired: state.app.auth.error === constants.sessionExpired,
        rememberMeUsername: state.rememberMe.username,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        login: (username, password) => dispatch(login(username, password))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
