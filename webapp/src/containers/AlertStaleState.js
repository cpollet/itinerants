import React from 'react';
import FadeInAlert from '../components/FadeInAlert';
import {connect} from 'react-redux';

class AlertStaleState extends React.Component {
    render() {
        return !this.props.stale ? null :
            (
                <FadeInAlert type="warn" {...this.props} />
            );
    }
}

AlertStaleState.propTypes = {
    stale: React.PropTypes.bool,
    text: React.PropTypes.string.isRequired,
    title: React.PropTypes.string.isRequired,
};

function mapStateToProps(state) {
    return {
        stale: state.stale,
    };
}

export default connect(mapStateToProps)(AlertStaleState);
