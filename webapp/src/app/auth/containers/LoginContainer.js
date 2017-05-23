import Login from '../screens/Login';
import {login} from '../actions';
import {connect} from 'react-redux';
import constants from '../constants';
import {sendResetPasswordToken} from '../../resetPassword/actions';

function mapStateToProps(state) {
    return {
        authenticated: state.app.auth.token !== null,
        invalidCredentials: state.app.auth.error === constants.invalidCredentials,
        loginExpired: state.app.auth.error === constants.sessionExpired,
        rememberMeUsername: state.rememberMe.username,
        username: state.app.auth.username,
        resetPasswordTokenSent: state.app.auth.resetPasswordTokenSent,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        login: (username, password) => dispatch(login(username, password)),
        sendPasswordResetToken: (username) => dispatch(sendResetPasswordToken(username)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
