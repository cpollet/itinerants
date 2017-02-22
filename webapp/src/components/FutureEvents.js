import React from 'react';
import Events from './Events';
import {fetchFutureEvents, resetEvents} from '../reducers/actions';
import {connect} from 'react-redux';

class FutureEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <button onClick={this.props.request}>refresh</button>
                <button onClick={this.props.reset}>reset</button>
                <Events when="future" events={this.props.events}/>
            </div>
        );
    }
}

FutureEvents.propTypes = {
    request: React.PropTypes.func.isRequired,
    reset: React.PropTypes.func.isRequired,
    events: React.PropTypes.array.isRequired
};

function mapStateToProps(state) {
    return {
        events: state.futureEvents.items
    };
}

function mapDispatchToProps(dispatch) {
    return {
        request: () => dispatch(fetchFutureEvents()),
        reset: () => dispatch(resetEvents())
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(FutureEvents);
