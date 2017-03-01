import React from 'react';
import Alert from '../core/Alert';
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
        syncFail: state.serverSync.retryCount === 0,
    };
}

export default connect(mapStateToProps)(AlertSyncFail);
