import {connect} from 'react-redux';
import ResetPassword from '../screens/ResetPassword';
import {resetPassword, sendResetPasswordToken} from '../actions';

function mapStateToProps(state, ownProps) {
    return {
        token: ownProps.params.token,
        passwordsMatch: state.app.resetPassword.passwordsMatch,
        passwordTooShort: state.app.resetPassword.passwordTooShort,
        tokenNotValid: !state.app.resetPassword.tokenValid,
        usernameEmpty: state.app.resetPassword.usernameEmpty,
        username: state.app.resetPassword.username,
        resetPasswordTokenSent: state.app.resetPassword.resetPasswordTokenSent,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        send: (username, password1, password2, token) => dispatch(resetPassword(username, password1, password2, token)),
        sendNewToken: (username) => dispatch(sendResetPasswordToken(username)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ResetPassword);
