import React from 'react';
import Alert from '../../../widgets/Alert';
import {connect} from 'react-redux';

class AlertSyncFail extends React.Component {
    render() {
        return !this.props.syncFail ? null :
            (
                <Alert type="error" {...this.props} />
            );
    }
}

AlertSyncFail.propTypes = {
    syncFail: React.PropTypes.bool.isRequired,
    text: React.PropTypes.string.isRequired,
    title: React.PropTypes.string,
};

function mapStateToProps(state) {
    return {
        syncFail: state.app.availabilities.serverSync.syncFailure,
    };
}

export default connect(mapStateToProps)(AlertSyncFail);
