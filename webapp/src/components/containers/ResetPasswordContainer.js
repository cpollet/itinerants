// import React from 'react';
import {connect} from 'react-redux';
import ResetPassword from '../../screens/ResetPassword';
import {resetPassword} from '../../reducers/actions';

function mapStateToProps(state) {
    return {
        passwordsMatch: state.app.resetPassword.passwordsMatch,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        send: (username, password1, password2) => dispatch(resetPassword(username, password1, password2))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(ResetPassword);
