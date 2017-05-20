import React from 'react';
import {connect} from 'react-redux';

class InfoStaleState extends React.Component {
    render() {
        return !this.props.stale ? null :
            (
                <div style={{position: 'relative'}}>
                    <div style={{
                        position: 'absolute',
                        top: -20,
                        right: -15,
                        zIndex: 999,
                        fontSize: '80%',
                    }}>{this.props.text}</div>
                </div>
            );
    }
}

InfoStaleState.propTypes = {
    stale: React.PropTypes.bool.isRequired,
    text: React.PropTypes.string.isRequired,
};

function mapStateToProps(state) {
    return {
        stale: state.app.availabilities.serverSync.retryCount > 0 && (state.app.availabilities.serverSync.stale || state.app.availabilities.serverSync.syncPending),
    };
}

export default connect(mapStateToProps)(InfoStaleState);
