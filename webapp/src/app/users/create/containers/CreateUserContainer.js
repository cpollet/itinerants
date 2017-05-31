import {connect} from 'react-redux';
import CreateUser from '../screens/CreateUser';
import {create} from '../actions';

function mapStateToProps(state) {
    return {
        createUserError: state.app.userCreation.error,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        create: (data) => dispatch(create(data))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateUser);
