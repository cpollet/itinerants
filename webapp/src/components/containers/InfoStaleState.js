import React from 'react';
import FadeInAlert from '../FadeInAlert';
import {connect} from 'react-redux';

class InfoStaleState extends React.Component {
    render() {
        return !this.props.stale ? null :
            (
                <FadeInAlert type="info" {...this.props} />
            );
    }
}

InfoStaleState.propTypes = {
    stale: React.PropTypes.bool.isRequired,
    text: React.PropTypes.string.isRequired,
    title: React.PropTypes.string,
};

function mapStateToProps(state) {
    return {
        stale: state.app.serverSync.retryCount > 0 && (state.app.serverSync.stale || state.app.serverSync.syncPending),
    };
}

export default connect(mapStateToProps)(InfoStaleState);
