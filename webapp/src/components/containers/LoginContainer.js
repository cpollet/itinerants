import Login from '../Login';
import {login} from '../../reducers/actions';
import {connect} from 'react-redux';

function mapStateToProps(state) {
    return {
        authenticated: state.app.auth.token !== null,
        invalidCredentials: state.app.auth.error === 'INVALID_CREDENTIALS',
    };
}

function mapDispatchToProps(dispatch) {
    return {
        login: (username, password) => dispatch(login(username, password))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Login);
