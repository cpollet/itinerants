import React from 'react';
import Events from './Events';
import {loadFutureEvents} from '../reducers/actions';
import {connect} from 'react-redux';

class FutureEvents extends React.Component {
    constructor() {
        super();
    }

    render() {
        let self = this;

        return (
            <div>
                <button onClick={self.props.load}>
                    refresh
                </button>
                <Events when="future" len={this.props.events}/>
            </div>
        );
    }
}

FutureEvents.propTypes = {
    load: React.PropTypes.func.isRequired,
    events: React.PropTypes.number.isRequired
};

const mapStateToProps = (state) => ({
    events: state.futureEvents.length
});

var mapDispatchToProps = function (dispatch) {
    return {
        load: function () {
            dispatch(loadFutureEvents());
        }
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(FutureEvents);
