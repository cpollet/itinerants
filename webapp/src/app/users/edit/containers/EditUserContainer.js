import React from 'react';
import {connect} from 'react-redux';
import EditUser from '../screens/EditUser';
import {fetchProfile, saveProfile} from '../actions';
import Spinner from '../../../../widgets/Spinner';

class EditUserContainer extends React.Component {
    componentDidMount() {
        this.props.request();
    }

    render() {
        return (
            <div>
                {this.props.ready || <Spinner/> }
                {this.props.ready && <EditUser {...this.props} />}
            </div>
        );
    }
}

EditUserContainer.propTypes = {
    request: React.PropTypes.func.isRequired,
    ready: React.PropTypes.bool.isRequired,
};

EditUserContainer.defaultProps = {
    ready: false,
};

function mapStateToProps(state) {
    return {
        firstName: state.app.userModification.data.firstName,
        lastName: state.app.userModification.data.lastName,
        email: state.app.userModification.data.email,
        ready: state.app.userModification.ready,
        saving: state.app.userModification.saving,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: () => dispatch(fetchProfile()),
        save: (data) => dispatch(saveProfile(data)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(EditUserContainer);
