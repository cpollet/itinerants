import {connect} from 'react-redux';
import CreateUser from '../screens/CreateUser';
import {create} from '../actions';

function mapStateToProps(/*state, ownProps*/) {
    return {};
}

function mapDispatchToProps(dispatch) {
    return {
        create: (data) => dispatch(create(data))
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateUser);
