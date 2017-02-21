import React from 'react';
import Events from './Events';
import {loadFutureEvents, resetEvents} from '../reducers/actions';
import {connect} from 'react-redux';

class FutureEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <div>
                <button onClick={this.props.load}>refresh</button>
                <button onClick={this.props.reset}>reset</button>
                <Events when="future" len={this.props.events}/>
            </div>
        );
    }
}

FutureEvents.propTypes = {
    load: React.PropTypes.func.isRequired,
    reset: React.PropTypes.func.isRequired,
    events: React.PropTypes.number.isRequired
};

const mapStateToProps = (state) => ({
    events: state.futureEvents.length
});

var mapDispatchToProps = function (dispatch) {
    return {
        load: () => dispatch(loadFutureEvents()),
        reset: () => dispatch(resetEvents())
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(FutureEvents);
