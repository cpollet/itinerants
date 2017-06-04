import React from 'react';
import {connect} from 'react-redux';
import EditUser from '../screens/EditUser';
import {fetchProfile, saveProfile} from '../actions';
import Spinner from '../../../../widgets/Spinner';
import {sendResetPasswordToken} from '../../../resetPassword/actions';

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
        personId: state.app.auth.personId,
        ready: state.app.userModification.ready,
        saving: state.app.userModification.saving,
        resetPasswordTokenSent: state.app.userModification.resetPasswordTokenSent,
        errors: state.app.userModification.errors,
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: () => dispatch(fetchProfile()),
        save: (data) => dispatch(saveProfile(data)),
        sendResetPasswordToken: (personId) => dispatch(sendResetPasswordToken(personId)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(EditUserContainer);
